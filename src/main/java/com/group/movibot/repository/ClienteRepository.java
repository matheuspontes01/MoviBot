package com.group.movibot.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group.movibot.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {

	Optional<Cliente> findByTelefone(String telefone);

	boolean existsByTelefone(String telefone);
}