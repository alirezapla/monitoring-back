package com.example.monitor.management.domain.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface IndicatorRepository extends JpaRepository<Indicator, String>{
}
