package com.api.nursery_system.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "plot_table")
public class Plot {
    @Id
    private String plotId;

    private String plotTitle;

    @Column(columnDefinition = "TEXT")
    private String plotDescription;

    private LocalDateTime insertedAt;

    private boolean isDeleted;

    @Enumerated(EnumType.STRING)
    private PlotStatus status;

    @OneToMany(mappedBy = "plot", cascade = CascadeType.ALL, orphanRemoval = true)
    List<PlotTask> plotTask;

    public enum PlotStatus {
        ACTIVE, CLOSED
    }
}
