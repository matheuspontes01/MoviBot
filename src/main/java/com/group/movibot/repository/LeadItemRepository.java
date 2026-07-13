package com.group.movibot.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.group.movibot.model.LeadItem;

public interface LeadItemRepository extends JpaRepository<LeadItem, UUID> {

	List<LeadItem> findByLeadId(UUID leadId);
}