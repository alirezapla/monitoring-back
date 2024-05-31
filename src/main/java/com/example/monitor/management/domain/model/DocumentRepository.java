package com.example.monitor.management.domain.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;

import java.util.Optional;

public interface DocumentRepository extends RevisionRepository<Document, String, Long>,JpaRepository<Document, String> {

    Page<Document> findAllByOrderByCreatedDateDesc(Pageable pageable);

    @Query(value = "SELECT d From Document d WHERE d.isHided = false and d.id=:id")
    Document findIsNotHided(String id);

    Optional<Document> findById(String id);

    Page<Document> findByName(Pageable pageable,String name);

    @Modifying
    @Query(value = "UPDATE Document d  SET d.isDeleted=true,d.updatedBy=:actor WHERE d.id=:id")
    void softDeleteById(String actor,String id);
}
