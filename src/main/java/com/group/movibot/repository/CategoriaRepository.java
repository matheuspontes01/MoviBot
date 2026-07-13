package com.group.movibot.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group.movibot.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, UUID> {

	Optional<Categoria> findByNomeIgnoreCase(String nome);
}