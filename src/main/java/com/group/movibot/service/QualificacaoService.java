package com.group.movibot.service;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.group.movibot.dto.ConversationFlowResult;
import com.group.movibot.model.EstadoConversa;

@Service
public class QualificacaoService {

	private static final String KEY_PRODUTO = "produtoDesejado";
	private static final String KEY_TAMANHO = "tamanhoDesejado";
	private static final String KEY_FAIXA_PRECO = "faixaPrecoDesejada";

	public ConversationFlowResult processar(EstadoConversa estadoAtual, Map<String, Object> contexto, String mensagemCliente) {
		Map<String, Object> novoContexto = copiarContexto(contexto);
		String mensagemNormalizada = normalizar(mensagemCliente);

		if (estadoAtual == EstadoConversa.QUALIFICACAO_PRODUTO) {
			novoContexto.put(KEY_PRODUTO, mensagemCliente.trim());
			return new ConversationFlowResult(
				EstadoConversa.QUALIFICACAO_TAMANHO,
				novoContexto,
				"Qual tamanho você procura? Ex.: solteiro, casal, queen ou king.",
				false
			);
		}

		if (estadoAtual == EstadoConversa.QUALIFICACAO_TAMANHO) {
			novoContexto.put(KEY_TAMANHO, mensagemCliente.trim());
			return new ConversationFlowResult(
				EstadoConversa.QUALIFICACAO_FAIXA_PRECO,
				novoContexto,
				"Perfeito. Qual faixa de preço faz sentido para você?",
				false
			);
		}

		if (estadoAtual == EstadoConversa.QUALIFICACAO_FAIXA_PRECO) {
			novoContexto.put(KEY_FAIXA_PRECO, mensagemCliente.trim());
			String produto = String.valueOf(novoContexto.getOrDefault(KEY_PRODUTO, "produto"));
			String tamanho = String.valueOf(novoContexto.getOrDefault(KEY_TAMANHO, "tamanho"));
			String faixaPreco = mensagemCliente.trim();
			return new ConversationFlowResult(
				EstadoConversa.MENU_APOS_QUALIFICACAO,
				novoContexto,
				"Qualificação concluída. Registrei interesse em " + produto + ", tamanho " + tamanho + " e faixa " + faixaPreco + ".\n\n" + menuFinal(),
				true
			);
		}

		return new ConversationFlowResult(
			estadoAtual,
			novoContexto,
			"Vamos continuar pela qualificação. Qual produto você procura?",
			false
		);
	}

	private Map<String, Object> copiarContexto(Map<String, Object> contexto) {
		return contexto == null ? new LinkedHashMap<>() : new LinkedHashMap<>(contexto);
	}

	private String normalizar(String texto) {
		return texto == null ? "" : texto.toLowerCase(Locale.ROOT).trim();
	}

	private String menuFinal() {
		return "Menu principal:\n1 - Catálogo\n2 - Qualificação de produto\n3 - FAQ\n4 - Localização\n5 - Dúvidas técnicas";
	}
}