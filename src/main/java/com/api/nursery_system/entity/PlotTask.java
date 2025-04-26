package com.api.nursery_system.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "plot_task_table")
public class PlotTask {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long plotTaskId;

    @ManyToOne
    @JoinColumn(name = "plot_id")
    private Plot plot;  

    private String taskTitle;

    @Column(columnDefinition = "TEXT")
    private String taskDescription;

    private String assignTo;

    private String workerName;

    private LocalDateTime insertedAt;
    
    private LocalDateTime updatedAt;
    private String updatedDevice;
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private boolean isDeleted;

    public enum TaskStatus {
        ASSIGN,IN_PROGRESS, COMPLETED,
    }
}
