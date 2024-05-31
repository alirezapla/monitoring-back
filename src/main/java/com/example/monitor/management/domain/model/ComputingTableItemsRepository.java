package com.example.monitor.management.domain.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ComputingTableItemsRepository extends JpaRepository<ComputingTableItems, String> {
    @Query(value = "SELECT c From ComputingTableItems c WHERE c.document.id = :docId")
    List<ComputingTableItems> findByDocumentId(String docId);

    @Query(value = "SELECT c From ComputingTableItems c WHERE c.document.id = :docId")
    Page<ComputingTableItems> findAllByDocumentId(Pageable pageable, String docId);

    @Query(value = "SELECT c From ComputingTableItems c WHERE c.document.id = :docId and c.isHided= false ")
    Page<ComputingTableItems> findAllUnHidedByDocumentId(Pageable pageable, String docId);
}
