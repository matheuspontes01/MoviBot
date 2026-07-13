package com.group.movibot.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group.movibot.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, UUID> {

	List<Produto> findByCategoriaIdAndAtivoTrueOrderByNomeAsc(UUID categoriaId);

	List<Produto> findByAtivoTrueOrderByNomeAsc();
}