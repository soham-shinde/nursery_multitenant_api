package com.api.nursery_system.request;

import java.time.LocalDateTime;

import com.api.nursery_system.entity.PlotTask.TaskStatus;

import lombok.Data;

@Data
public class PlotTaskRequest {

    private String plotId;

    private String taskTitle;

    private String taskDescription;

    private String assignTo;

    private String workerName;

    private LocalDateTime insertedAt;

    private TaskStatus status;

}
