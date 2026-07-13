package com.group.movibot.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group.movibot.model.FaqItem;

public interface FaqItemRepository extends JpaRepository<FaqItem, UUID> {

	List<FaqItem> findByAtivoTrueOrderByCategoriaAscPerguntaAsc();

	List<FaqItem> findByCategoriaIgnoreCaseAndAtivoTrue(String categoria);
}