package com.group.movibot.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group.movibot.model.Conversa;
import com.group.movibot.model.StatusConversa;

public interface ConversaRepository extends JpaRepository<Conversa, UUID> {

	Optional<Conversa> findFirstByClienteIdAndStatusOrderByCriadoEmDesc(UUID clienteId, StatusConversa status);

	List<Conversa> findByClienteIdOrderByCriadoEmDesc(UUID clienteId);

	List<Conversa> findByStatus(StatusConversa status);
}