package com.api.nursery_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.nursery_system.entity.PlotTask;

public interface PlotTaskRepository extends JpaRepository<PlotTask, Long> {

    List<PlotTask> findByIsDeletedFalse();
    
}
