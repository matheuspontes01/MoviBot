package com.group.movibot.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group.movibot.model.GatilhoVenda;
import com.group.movibot.model.TipoGatilho;

public interface GatilhoVendaRepository extends JpaRepository<GatilhoVenda, UUID> {

	List<GatilhoVenda> findByAtivoTrueAndTipoGatilhoOrderByPesoScoreDesc(TipoGatilho tipoGatilho);
}