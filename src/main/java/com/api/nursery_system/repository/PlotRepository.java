package com.api.nursery_system.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.nursery_system.entity.Plot;

public interface PlotRepository  extends JpaRepository<Plot, String> {

    List<Plot> findByIsDeletedFalse();
    Optional<Plot> findTopByOrderByPlotIdDesc();
    
}
