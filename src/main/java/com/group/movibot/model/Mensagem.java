package com.group.movibot.model;

import java.time.Instant;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "mensagem")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mensagem {

	@Id
	@UuidGenerator
	@Column(nullable = false, updatable = false)
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "conversa_id", nullable = false)
	private Conversa conversa;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 50)
	private RemetenteMensagem remetente;

	@Enumerated(EnumType.STRING)
	@Column(name = "origem_resposta", length = 50)
	private OrigemResposta origemResposta;

	@Column(nullable = false, columnDefinition = "text")
	private String conteudo;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 50)
	private TipoMensagem tipo;

	@Column(name = "whatsapp_message_id", unique = true, length = 128)
	private String whatsappMessageId;

	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private Instant criadoEm;
}