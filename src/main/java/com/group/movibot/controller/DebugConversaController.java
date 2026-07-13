package com.group.movibot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group.movibot.dto.DebugMensagemRequest;
import com.group.movibot.dto.DebugMensagemResponse;
import com.group.movibot.service.ConversationOrchestrator;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/debug/conversa")
public class DebugConversaController {

	private final ConversationOrchestrator conversationOrchestrator;

	public DebugConversaController(ConversationOrchestrator conversationOrchestrator) {
		this.conversationOrchestrator = conversationOrchestrator;
	}

	@PostMapping("/mensagem")
	public ResponseEntity<DebugMensagemResponse> receberMensagem(@Valid @RequestBody DebugMensagemRequest request) {
		return ResponseEntity.ok(conversationOrchestrator.processarMensagem(request.telefone(), request.texto()));
	}
}