package com.api.nursery_system.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.api.nursery_system.entity.Plot;

import lombok.Data;

@Data
public class PlotDto {
    private String plotId;
    private String plotTitle;
    private String plotDescription;
    private LocalDateTime insertedAt;
    private LocalDateTime updatedAt;
    private String updatedDevice;
    private boolean isDeleted;
    private String status; // Using String for simplicity
    private List<PlotTaskDto> plotTasks;

    public static PlotDto from(Plot plot) {
        PlotDto dto = new PlotDto();
        dto.plotId = plot.getPlotId();
        dto.plotTitle = plot.getPlotTitle();
        dto.plotDescription = plot.getPlotDescription();
        dto.insertedAt = plot.getInsertedAt();
        dto.updatedAt = plot.getUpdatedAt();
        dto.updatedDevice = plot.getUpdatedDevice();
        dto.isDeleted = plot.isDeleted();
        dto.status = plot.getStatus() != null ? plot.getStatus().name() : null;
        if (plot.getPlotTask() != null) {
            dto.plotTasks = plot.getPlotTask()
                                 .stream()
                                 .map(PlotTaskDto::from)
                                 .toList();
        }
        return dto;
    }
}
