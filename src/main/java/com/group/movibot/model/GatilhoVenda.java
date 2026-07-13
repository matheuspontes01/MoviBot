package com.group.movibot.model;

import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "gatilho_venda")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GatilhoVenda {

	@Id
	@UuidGenerator
	@Column(nullable = false, updatable = false)
	private UUID id;

	@Column(nullable = false, columnDefinition = "text")
	private String padrao;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo_gatilho", nullable = false, length = 50)
	private TipoGatilho tipoGatilho;

	@Column(name = "peso_score", nullable = false)
	private Integer pesoScore;

	@Column(nullable = false)
	private boolean ativo;
}