package com.group.movibot.model;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "faq_item")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FaqItem {

	@Id
	@UuidGenerator
	@Column(nullable = false, updatable = false)
	private UUID id;

	@Column(nullable = false, columnDefinition = "text")
	private String pergunta;

	@Column(nullable = false, columnDefinition = "text")
	private String resposta;

	@Column(nullable = false)
	private String categoria;

	@Column(nullable = false)
	private boolean ativo;
}