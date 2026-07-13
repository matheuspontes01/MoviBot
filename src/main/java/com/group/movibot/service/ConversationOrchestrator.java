package com.group.movibot.service;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.group.movibot.dto.ConversationFlowResult;
import com.group.movibot.dto.DebugMensagemResponse;
import com.group.movibot.model.Cliente;
import com.group.movibot.model.Conversa;
import com.group.movibot.model.EstadoConversa;
import com.group.movibot.model.Lead;
import com.group.movibot.model.Mensagem;
import com.group.movibot.model.OrigemCliente;
import com.group.movibot.model.RemetenteMensagem;
import com.group.movibot.model.StatusConversa;
import com.group.movibot.model.StatusLead;
import com.group.movibot.model.TipoMensagem;
import com.group.movibot.repository.ClienteRepository;
import com.group.movibot.repository.ConversaRepository;
import com.group.movibot.repository.LeadRepository;
import com.group.movibot.repository.MensagemRepository;

@Service
public class ConversationOrchestrator {

	private final ClienteRepository clienteRepository;
	private final ConversaRepository conversaRepository;
	private final MensagemRepository mensagemRepository;
	private final LeadRepository leadRepository;
	private final MenuFlowService menuFlowService;

	public ConversationOrchestrator(
		ClienteRepository clienteRepository,
		ConversaRepository conversaRepository,
		MensagemRepository mensagemRepository,
		LeadRepository leadRepository,
		MenuFlowService menuFlowService
	) {
		this.clienteRepository = clienteRepository;
		this.conversaRepository = conversaRepository;
		this.mensagemRepository = mensagemRepository;
		this.leadRepository = leadRepository;
		this.menuFlowService = menuFlowService;
	}

	@Transactional
	public DebugMensagemResponse processarMensagem(String telefone, String texto) {
		Cliente cliente = clienteRepository.findByTelefone(telefone)
			.orElseGet(() -> clienteRepository.save(Cliente.builder()
				.telefone(telefone)
				.nome("Não informado")
				.origem(OrigemCliente.WHATSAPP)
				.build()));

		Conversa conversa = conversaRepository.findFirstByClienteIdAndStatusOrderByCriadoEmDesc(cliente.getId(), StatusConversa.ATIVA)
			.orElseGet(() -> conversaRepository.save(Conversa.builder()
				.cliente(cliente)
				.estado(EstadoConversa.INICIO)
				.contexto(new LinkedHashMap<>())
				.status(StatusConversa.ATIVA)
				.build()));

		Mensagem mensagemEntrada = mensagemRepository.save(Mensagem.builder()
			.conversa(conversa)
			.remetente(RemetenteMensagem.CLIENTE)
			.conteudo(texto)
			.tipo(TipoMensagem.TEXTO)
			.build());

		ConversationFlowResult flowResult = menuFlowService.processar(
			Optional.ofNullable(conversa.getEstado()).orElse(EstadoConversa.INICIO),
			Optional.ofNullable(conversa.getContexto()).orElse(Map.of()),
			texto
		);

		conversa.setEstado(flowResult.novoEstado());
		conversa.setContexto(new LinkedHashMap<>(flowResult.novoContexto()));
		conversaRepository.save(conversa);

		if (flowResult.criarLead()) {
			boolean leadExiste = leadRepository.findFirstByClienteIdAndConversaIdOrderByCriadoEmDesc(cliente.getId(), conversa.getId()).isPresent();
			if (!leadExiste) {
				leadRepository.save(Lead.builder()
					.cliente(cliente)
					.conversa(conversa)
					.status(StatusLead.NOVO)
					.build());
			}
		}

		Mensagem mensagemSaida = mensagemRepository.save(Mensagem.builder()
			.conversa(conversa)
			.remetente(RemetenteMensagem.BOT)
			.origemResposta(deduzirOrigemResposta(flowResult.novoEstado()))
			.conteudo(flowResult.respostaBot())
			.tipo(TipoMensagem.TEXTO)
			.build());

		cliente.setUltimaInteracaoEm(Instant.now());
		clienteRepository.save(cliente);

		return new DebugMensagemResponse(
			cliente.getId(),
			conversa.getId(),
			telefone,
			conversa.getEstado(),
			conversa.getContexto(),
			mensagemSaida.getConteudo()
		);
	}

	private com.group.movibot.model.OrigemResposta deduzirOrigemResposta(EstadoConversa estado) {
		return switch (estado) {
			case QUALIFICACAO_PRODUTO, QUALIFICACAO_TAMANHO, QUALIFICACAO_FAIXA_PRECO, LIVRE_IA -> com.group.movibot.model.OrigemResposta.IA;
			case TRANSFERIDA_HUMANO -> com.group.movibot.model.OrigemResposta.HUMANO;
			default -> com.group.movibot.model.OrigemResposta.MENU;
		};
	}
}