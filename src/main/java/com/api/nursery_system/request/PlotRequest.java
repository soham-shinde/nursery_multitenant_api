package com.api.nursery_system.request;

import java.time.LocalDateTime;
import com.api.nursery_system.entity.Plot.PlotStatus;

import lombok.Data;

@Data
public class PlotRequest {
 
    private String plotTitle;
    private String plotDescription;

    private LocalDateTime insertedAt;

    private boolean isDeleted;
    private PlotStatus status;

}
