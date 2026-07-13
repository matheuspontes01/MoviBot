package com.group.movibot.dto;

import jakarta.validation.constraints.NotBlank;

public record DebugMensagemRequest(
	@NotBlank String telefone,
	@NotBlank String texto
) {
}