package com.group.movibot.service;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.group.movibot.dto.ConversationFlowResult;
import com.group.movibot.model.EstadoConversa;

@Service
public class MenuFlowService {

	private static final Pattern MENU_OPTION = Pattern.compile("^\\s*([1-5])\\s*$");
	private static final String KEY_NOME = "nomeCliente";
	private static final String KEY_FAQ_PERGUNTA = "faqPergunta";
	private static final String KEY_ORIGEM_PEDIDO = "origemPedido";

	private final QualificacaoService qualificacaoService;

	public MenuFlowService(QualificacaoService qualificacaoService) {
		this.qualificacaoService = qualificacaoService;
	}

	public ConversationFlowResult processar(EstadoConversa estadoAtual, Map<String, Object> contexto, String mensagemCliente) {
		Map<String, Object> novoContexto = copiarContexto(contexto);
		String texto = normalizar(mensagemCliente);

		if (pedeTransferenciaHumana(texto)) {
			return new ConversationFlowResult(
				EstadoConversa.TRANSFERIDA_HUMANO,
				novoContexto,
				"Essa solicitação precisa de um atendente humano. Vou direcionar seu atendimento.",
				false
			);
		}

		return switch (estadoAtual) {
			case INICIO -> new ConversationFlowResult(
				EstadoConversa.SAUDACAO_INICIAL,
				novoContexto,
				"Bem-vindo à MoviBot, o assistente de pré-atendimento da nossa loja de colchões e móveis. Vou te ajudar com menu, qualificação, FAQ e localização.\n\nComo posso te chamar?",
				false
			);
			case SAUDACAO_INICIAL -> {
				if (deveCapturarNome(texto)) {
					String nome = extrairNome(mensagemCliente);
					novoContexto.put(KEY_NOME, nome);
					yield new ConversationFlowResult(
						EstadoConversa.MENU_PRINCIPAL,
						novoContexto,
						"Prazer, " + nome + ". Escolha uma opção:\n1 - Catálogo\n2 - Qualificação de produto\n3 - FAQ\n4 - Localização\n5 - Dúvidas técnicas",
						false
					);
				}

				yield new ConversationFlowResult(
					EstadoConversa.IDENTIFICACAO_CLIENTE,
					novoContexto,
					"Perfeito. Me diga seu nome para eu seguir com o atendimento.",
					false
				);
			}
			case IDENTIFICACAO_CLIENTE -> {
				String nome = extrairNome(mensagemCliente);
				if (nome.isBlank()) {
					yield new ConversationFlowResult(
						EstadoConversa.IDENTIFICACAO_CLIENTE,
						novoContexto,
						"Não consegui identificar seu nome. Pode me responder só com seu nome?",
						false
					);
				}

				novoContexto.put(KEY_NOME, nome);
				yield new ConversationFlowResult(
					EstadoConversa.MENU_PRINCIPAL,
					novoContexto,
					"Prazer, " + nome + ". Escolha uma opção:\n1 - Catálogo\n2 - Qualificação de produto\n3 - FAQ\n4 - Localização\n5 - Dúvidas técnicas",
					false
				);
			}
			case MENU_PRINCIPAL, MENU_APOS_QUALIFICACAO -> processarMenuPrincipal(texto, novoContexto);
			case QUALIFICACAO_PRODUTO, QUALIFICACAO_TAMANHO, QUALIFICACAO_FAIXA_PRECO -> qualificacaoService.processar(estadoAtual, novoContexto, mensagemCliente);
			case AGUARDANDO_FAQ -> new ConversationFlowResult(
				EstadoConversa.MENU_PRINCIPAL,
				desdePerguntaFaq(novoContexto, mensagemCliente),
				"Recebi sua dúvida: '" + mensagemCliente.trim() + "'. Um resumo ficará disponível para o atendente.\n\n" + menuPrincipal(),
				false
			);
			case LIVRE_IA -> new ConversationFlowResult(
				EstadoConversa.MENU_PRINCIPAL,
				novoContexto,
				"Posso ajudar com perguntas técnicas sobre os produtos. Se quiser, responda pelo menu:\n" + menuPrincipal(),
				false
			);
			case TRANSFERIDA_HUMANO -> new ConversationFlowResult(
				EstadoConversa.TRANSFERIDA_HUMANO,
				novoContexto,
				"Seu atendimento já foi transferido para um humano. Assim que possível, alguém vai seguir por aqui.",
				false
			);
		};
	}

	private ConversationFlowResult processarMenuPrincipal(String texto, Map<String, Object> contexto) {
		Matcher matcher = MENU_OPTION.matcher(texto);
		if (matcher.matches()) {
			return switch (matcher.group(1)) {
				case "1" -> new ConversationFlowResult(EstadoConversa.MENU_PRINCIPAL, contexto, "Catálogo: posso te mostrar colchões, camas, guarda-roupas e cabeceiras. Se quiser qualificar uma compra, escolha a opção 2.", false);
				case "2" -> new ConversationFlowResult(EstadoConversa.QUALIFICACAO_PRODUTO, contexto, "Vamos qualificar sua necessidade. Qual produto você procura?", false);
				case "3" -> new ConversationFlowResult(EstadoConversa.AGUARDANDO_FAQ, contexto, "Perfeito. Escreva sua dúvida em texto livre e eu registro para análise.", false);
				case "4" -> new ConversationFlowResult(EstadoConversa.MENU_PRINCIPAL, contexto, "Nossa localização será integrada com Google Maps. Por enquanto, um atendente pode te passar a unidade mais próxima.", false);
				case "5" -> new ConversationFlowResult(EstadoConversa.LIVRE_IA, contexto, "Pode mandar sua dúvida técnica sobre os produtos que eu tento ajudar com IA depois.", false);
				default -> new ConversationFlowResult(EstadoConversa.MENU_PRINCIPAL, contexto, menuPrincipal(), false);
			};
		}

		if (texto.contains("qualifica") || texto.contains("comprar") || texto.contains("quero comprar")) {
			return new ConversationFlowResult(EstadoConversa.QUALIFICACAO_PRODUTO, contexto, "Vamos qualificar sua necessidade. Qual produto você procura?", false);
		}

		return new ConversationFlowResult(EstadoConversa.MENU_PRINCIPAL, contexto, menuPrincipal(), false);
	}

	private boolean pedeTransferenciaHumana(String texto) {
		return texto.contains("estoque")
			|| texto.contains("disponibilidade")
			|| texto.contains("disponivel")
			|| texto.contains("disponível")
			|| texto.contains("tem disponível")
			|| texto.contains("tem disponivel");
	}

	private boolean deveCapturarNome(String texto) {
		return !texto.isBlank() && !MENU_OPTION.matcher(texto).matches() && !ehSaudacao(texto);
	}

	private boolean ehSaudacao(String texto) {
		return texto.equals("oi")
			|| texto.equals("olá")
			|| texto.equals("ola")
			|| texto.equals("bom dia")
			|| texto.equals("boa tarde")
			|| texto.equals("boa noite");
	}

	private String extrairNome(String mensagemCliente) {
		String texto = mensagemCliente == null ? "" : mensagemCliente.trim();
		if (texto.isBlank()) {
			return "";
		}

		String[] prefixes = {"me chamo", "meu nome é", "meu nome eh", "sou", "eu sou"};
		String lower = texto.toLowerCase(Locale.ROOT);
		for (String prefix : prefixes) {
			if (lower.startsWith(prefix)) {
				return texto.substring(prefix.length()).trim();
			}
		}

		return texto;
	}

	private Map<String, Object> copiarContexto(Map<String, Object> contexto) {
		return contexto == null ? new LinkedHashMap<>() : new LinkedHashMap<>(contexto);
	}

	private Map<String, Object> desdePerguntaFaq(Map<String, Object> contexto, String mensagemCliente) {
		Map<String, Object> novoContexto = copiarContexto(contexto);
		novoContexto.put(KEY_FAQ_PERGUNTA, mensagemCliente.trim());
		return novoContexto;
	}

	private String menuPrincipal() {
		return "1 - Catálogo\n2 - Qualificação de produto\n3 - FAQ\n4 - Localização\n5 - Dúvidas técnicas";
	}

	private String normalizar(String texto) {
		return texto == null ? "" : texto.toLowerCase(Locale.ROOT).trim();
	}
}