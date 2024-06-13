package com.example.monitor.management.domain.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;

import java.util.List;
import java.util.Set;

public interface IndicatorRepository extends RevisionRepository<Indicator, String, Long>, JpaRepository<Indicator, String> {
    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {
                    "computations"
            }
    )
    @Query(value = "SELECT i From Indicator i WHERE i.docTable.id = :id")
    List<Indicator> findAllByDocTableId(String id);

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {
                    "computations"
            }
    )
    @Query(value = "SELECT i.id From Indicator i WHERE i.docTable.id = :id")
    Set<String> findAllJustIdsByDocTableId(String id);

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {
                    "computations"
            }
    )
    @Query(value = "SELECT i From Indicator i WHERE i.docTable.id = :id and i.isHided= false ")
    Page<Indicator> findAllUnHidedByDocTableId(Pageable pageable, String id);

    @EntityGraph(
            type = EntityGraph.EntityGraphType.FETCH,
            attributePaths = {
                    "computations"
            }
    )
    @Query(value = "SELECT i From Indicator i WHERE i.docTable.id = :id")
    Page<Indicator> findAllByDocTableId(Pageable pageable, String id);
}
