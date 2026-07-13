package com.group.movibot.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group.movibot.model.AlertaVenda;
import com.group.movibot.model.StatusAtendimento;

public interface AlertaVendaRepository extends JpaRepository<AlertaVenda, UUID> {

	List<AlertaVenda> findByStatusAtendimento(StatusAtendimento statusAtendimento);

	List<AlertaVenda> findByConversaId(UUID conversaId);
}