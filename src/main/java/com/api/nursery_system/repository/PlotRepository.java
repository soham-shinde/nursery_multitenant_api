package com.api.nursery_system.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.api.nursery_system.entity.Plot;

public interface PlotRepository  extends JpaRepository<Plot, String> {

    List<Plot> findByIsDeletedFalse();
    Optional<Plot> findTopByOrderByPlotIdDesc();
    
  // 1) All ACTIVE & not deleted
  List<Plot> findByStatusAndIsDeletedFalse(Plot.PlotStatus status);

  // 2) CLOSED & not deleted, updated today
  @Query("SELECT p FROM Plot p " +
         " WHERE p.status = :status" +
         "   AND p.isDeleted = false" +
         "   AND p.updatedAt BETWEEN :startOfDay AND :endOfDay")
  List<Plot> findClosedToday(
      @Param("status") Plot.PlotStatus status,
      @Param("startOfDay") LocalDateTime startOfDay,
      @Param("endOfDay")   LocalDateTime endOfDay
  );

  // 3) CLOSED & not deleted, updated before today
  List<Plot> findByStatusAndIsDeletedFalseAndUpdatedAtBefore(
      Plot.PlotStatus status,
      LocalDateTime cutoff
  );
}
