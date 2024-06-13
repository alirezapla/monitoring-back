package com.example.monitor.management.domain.model;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.history.RevisionRepository;

import java.util.List;
import java.util.Set;

public interface DocTableRepository extends RevisionRepository<DocTable, String, Long>, JpaRepository<DocTable, String> {

}
