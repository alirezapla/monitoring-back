package com.example.monitor.management.domain.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.history.RevisionRepository;

import java.util.List;
import java.util.Optional;

public interface DocTableRepository extends RevisionRepository<DocTable, String, Long>,JpaRepository<DocTable, String> {


    @Query(value = "SELECT d From DocTable d WHERE d.document.id = :docId")
    Page<DocTable> findAllByDocumentId(Pageable pageable, String docId);

    @Query(value = "SELECT d From DocTable d WHERE d.document.id = :docId")
    List<DocTable> findAllByDocumentId(String docId);

    @Query(value = "SELECT d From DocTable d WHERE d.document.id = :docId and d.isHided= false ")
    Page<DocTable> findAllUnHidedByDocumentId(Pageable pageable, String docId);
}
