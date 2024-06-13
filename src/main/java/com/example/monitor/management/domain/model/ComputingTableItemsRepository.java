package com.example.monitor.management.domain.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;

import java.util.List;
import java.util.Set;

public interface ComputingTableItemsRepository extends RevisionRepository<ComputingTableItems, String, Long>,JpaRepository<ComputingTableItems, String> {
    @Query(value = "SELECT c From ComputingTableItems c WHERE c.document.id = :docId")
    List<ComputingTableItems> findByDocumentId(String docId);
}
