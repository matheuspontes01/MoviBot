package com.group.movibot.dto;

import java.util.Map;

import com.group.movibot.model.EstadoConversa;

public record ConversationFlowResult(
	EstadoConversa novoEstado,
	Map<String, Object> novoContexto,
	String respostaBot,
	boolean criarLead
) {
}