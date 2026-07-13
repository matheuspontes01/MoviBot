package com.group.movibot.dto;

import java.util.Map;
import java.util.UUID;

import com.group.movibot.model.EstadoConversa;

public record DebugMensagemResponse(
	UUID clienteId,
	UUID conversaId,
	String telefone,
	EstadoConversa estado,
	Map<String, Object> contexto,
	String respostaBot
) {
}