package com.api.nursery_system.dto;

import java.time.LocalDateTime;

import com.api.nursery_system.entity.PlotTask;

import lombok.Data;

@Data
public class PlotTaskDto {
    private Long plotTaskId;
    private String plotId;
    private String taskTitle;
    private String taskDescription;
    private String assignTo;
    private String workerName;
    private LocalDateTime insertedAt;
    private String status; 
    private boolean isDeleted;

    public static PlotTaskDto from(PlotTask plotTask) {
        PlotTaskDto dto = new PlotTaskDto();
        dto.plotTaskId = plotTask.getPlotTaskId();
        dto.taskTitle = plotTask.getTaskTitle();
        dto.taskDescription = plotTask.getTaskDescription();
        dto.assignTo = plotTask.getAssignTo();
        dto.workerName = plotTask.getWorkerName();
        dto.insertedAt = plotTask.getInsertedAt();
        dto.status = plotTask.getStatus() != null ? plotTask.getStatus().name() : null;
        dto.isDeleted = plotTask.isDeleted();
        dto.plotId = plotTask.getPlot().getPlotId();
        return dto;
    }

}
