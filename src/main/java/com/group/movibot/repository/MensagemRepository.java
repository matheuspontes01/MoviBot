package com.group.movibot.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group.movibot.model.Mensagem;

public interface MensagemRepository extends JpaRepository<Mensagem, UUID> {

	List<Mensagem> findByConversaIdOrderByCriadoEmAsc(UUID conversaId);

	Optional<Mensagem> findByWhatsappMessageId(String whatsappMessageId);
}