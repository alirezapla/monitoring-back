package com.example.monitor.management.domain.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;

import java.util.Optional;

public interface DocumentRepository extends RevisionRepository<Document, String, Long>, JpaRepository<Document, String> {

    @Query(
            value = "SELECT d From Document d WHERE (:searchTerm IS NULL OR LOWER(d.name) LIKE LOWER(CONCAT('%',:searchTerm,'%'))) ")
    Page<Document> findAllDocument(Pageable pageable,String searchTerm);

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {
                    "computingTableItems",
                    "docTables",
                    "docTables.indicators",
                    "docTables.indicators.computations"
            }
    )
    @Query(value = "SELECT d From Document d " +
            "left outer JOIN d.docTables dd " +
            "left outer JOIN dd.indicators ddi " +
            "left outer join  ddi.computations ic " +
            "left outer JOIN d.computingTableItems dc " +
            "WHERE d.isHided = false and dd.isHided = false and ddi.isHided = false and dc.isHided = false and  d.id=:id")
    Optional<Document> findIsNotHided(String id);

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {
                    "computingTableItems",
                    "docTables",
                    "docTables.indicators",
                    "docTables.indicators.computations"
            }
    )
    @Query(value = "SELECT d From Document d WHERE  d.id=:id")
    Optional<Document> find(String id);

}
