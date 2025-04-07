package com.api.nursery_system.service.plot;

import java.util.List;

import com.api.nursery_system.entity.Plot;
import com.api.nursery_system.entity.PlotTask;
import com.api.nursery_system.request.PlotRequest;
import com.api.nursery_system.request.PlotTaskRequest;

public interface IPlotService {
    Plot createPlot(PlotRequest plot);

    PlotTask createPlotTask(PlotTaskRequest plotTask);

    void softDeletePlot(String plotId);

    void softDeletePlotTask(Long plotTaskId);

    Plot updatePlot(String plotId,PlotRequest updatedPlot);

    PlotTask updatePlotTask(Long plotTaskId,PlotTaskRequest updatedPlotTask);

    Plot updatePlotStatus(String plotId, Plot.PlotStatus status);

    PlotTask updatePlotTaskStatus(Long taskId, PlotTask.TaskStatus status);

    List<Plot> getActivePlots();

    Plot getActivePlotById(String plotId);

    List<PlotTask> getPlotTaskByPlotId(String plotId);

}
