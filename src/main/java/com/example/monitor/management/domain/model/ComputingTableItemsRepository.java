package com.example.monitor.management.domain.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ComputingTableItemsRepository extends JpaRepository<ComputingTableItems, String> {
    @Query(value = "SELECT c From ComputingTableItems c WHERE c.document.id = :docId")
    List<ComputingTableItems> findByDocumentId(String docId);
}
