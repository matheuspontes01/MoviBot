package com.group.movibot.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;

import com.group.movibot.model.EstadoConversa;

class MenuFlowServiceTests {

	private final MenuFlowService menuFlowService = new MenuFlowService(new QualificacaoService());

	@Test
	void deveIniciarFluxoComSaudacao() {
		var result = menuFlowService.processar(EstadoConversa.INICIO, Map.of(), "oi");

		assertThat(result.novoEstado()).isEqualTo(EstadoConversa.SAUDACAO_INICIAL);
		assertThat(result.respostaBot()).contains("MoviBot");
	}

	@Test
	void deveCapturarNomeEExibirMenu() {
		var result = menuFlowService.processar(EstadoConversa.IDENTIFICACAO_CLIENTE, Map.of(), "João Silva");

		assertThat(result.novoEstado()).isEqualTo(EstadoConversa.MENU_PRINCIPAL);
		assertThat(result.novoContexto()).containsEntry("nomeCliente", "João Silva");
	}

	@Test
	void deveCapturarNomeNaSaudacaoQuandoUsuarioJaRespondeComTextoLivre() {
		var result = menuFlowService.processar(EstadoConversa.SAUDACAO_INICIAL, Map.of(), "Maria Souza");

		assertThat(result.novoEstado()).isEqualTo(EstadoConversa.MENU_PRINCIPAL);
		assertThat(result.novoContexto()).containsEntry("nomeCliente", "Maria Souza");
	}

	@Test
	void deveTransferirDisponibilidadeParaHumano() {
		var result = menuFlowService.processar(EstadoConversa.MENU_PRINCIPAL, Map.of(), "Tem estoque disponível?");

		assertThat(result.novoEstado()).isEqualTo(EstadoConversa.TRANSFERIDA_HUMANO);
	}
}