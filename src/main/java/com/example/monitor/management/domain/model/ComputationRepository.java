package com.example.monitor.management.domain.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;

import java.util.List;

public interface ComputationRepository extends RevisionRepository<Computation, String, Long>, JpaRepository<Computation, String> {
    @Query(value = "SELECT c From Computation c WHERE c.indicator.id = :id")
    List<Computation> findAllByIndicatorId(String id);
}
