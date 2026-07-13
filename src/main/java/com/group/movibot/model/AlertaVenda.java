package com.group.movibot.model;

import java.time.Instant;
import java.util.UUID;

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
@Table(name = "alerta_venda")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlertaVenda {

	@Id
	@UuidGenerator
	@Column(nullable = false, updatable = false)
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "conversa_id", nullable = false)
	private Conversa conversa;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "gatilho_id", nullable = false)
	private GatilhoVenda gatilho;

	@Column(name = "score_acumulado", nullable = false)
	private Integer scoreAcumulado;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 50)
	private PrioridadeAlerta prioridade;

	@Column(name = "notificado_em")
	private Instant notificadoEm;

	@Enumerated(EnumType.STRING)
	@Column(name = "status_atendimento", nullable = false, length = 50)
	private StatusAtendimento statusAtendimento;
}