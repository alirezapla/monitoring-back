package com.example.monitor.management.domain.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DocumentRepository extends JpaRepository<Document, String> {

    Page<Document> findAllByOrderByCreatedDateDesc(Pageable pageable);

    @Query(value = "SELECT d From Document d WHERE d.isHided = false and d.id=: id")
    Page<Document> findAllIsNotHided(Pageable pageable, String id);

    Page<Document> findAllById(Pageable pageable, String id);
}
