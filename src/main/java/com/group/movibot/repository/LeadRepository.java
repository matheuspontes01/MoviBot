package com.group.movibot.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group.movibot.model.Lead;
import com.group.movibot.model.StatusLead;

public interface LeadRepository extends JpaRepository<Lead, UUID> {

	Optional<Lead> findFirstByClienteIdAndConversaIdOrderByCriadoEmDesc(UUID clienteId, UUID conversaId);

	List<Lead> findByStatus(StatusLead status);
}