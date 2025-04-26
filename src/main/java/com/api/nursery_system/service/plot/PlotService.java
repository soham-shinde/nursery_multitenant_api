package com.api.nursery_system.service.plot;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.api.nursery_system.entity.Plot;
import com.api.nursery_system.entity.Plot.PlotStatus;
import com.api.nursery_system.entity.PlotTask;
import com.api.nursery_system.entity.PlotTask.TaskStatus;
import com.api.nursery_system.exception.ResourceNotFoundException;
import com.api.nursery_system.repository.PlotRepository;
import com.api.nursery_system.repository.PlotTaskRepository;
import com.api.nursery_system.request.PlotRequest;
import com.api.nursery_system.request.PlotTaskRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlotService implements IPlotService {
    private final PlotRepository plotRepository;
    private final PlotTaskRepository plotTaskRepository;

    public String generateNextPlotId() {
        // Retrieve the plot with the highest plotId.
        Optional<Plot> lastPlotOptional = plotRepository.findTopByOrderByPlotIdDesc();

        // If no plot exists yet, start with the first plotId.
        if (lastPlotOptional.isEmpty() || lastPlotOptional.get().getPlotId() == null) {
            return "PLT0001";
        }

        // Extract the last plotId and remove the "PLT" prefix.
        String lastPlotId = lastPlotOptional.get().getPlotId();
        int lastNumericPart = Integer.parseInt(lastPlotId.substring(3));

        // Increment the numeric part.
        int newNumericPart = lastNumericPart + 1;

        // Format the new plotId keeping the same format (e.g., PLT0001, PLT0002, etc.).
        return String.format("PLT%04d", newNumericPart);
    }

    @Override
    public Plot createPlot(PlotRequest request) {
        Plot newPlot = new Plot();
        newPlot.setPlotId(generateNextPlotId());
        newPlot.setPlotTitle(request.getPlotTitle());
        newPlot.setPlotDescription(request.getPlotDescription());
        newPlot.setInsertedAt(request.getInsertedAt());
        newPlot.setDeleted(false);
        newPlot.setStatus(request.getStatus());
        return plotRepository.save(newPlot);
    }

    @Override
    public PlotTask createPlotTask(PlotTaskRequest request) {
        PlotTask newPlotTask = new PlotTask();
        newPlotTask.setTaskTitle(request.getTaskTitle());
        newPlotTask.setTaskDescription(request.getTaskDescription());
        newPlotTask.setAssignTo(request.getAssignTo());
        newPlotTask.setWorkerName(request.getWorkerName());
        newPlotTask.setInsertedAt(request.getInsertedAt());
        newPlotTask.setStatus(request.getStatus());
        Plot taskPlot = plotRepository.findById(request.getPlotId())
                .orElseThrow(() -> new ResourceNotFoundException("Plot not Found"));
        newPlotTask.setPlot(taskPlot);

        return plotTaskRepository.save(newPlotTask);
    }

    @Override
    public void softDeletePlot(String plotId) {
        Plot plot = plotRepository.findById(plotId)
                .orElseThrow(() -> new RuntimeException("Plot not found with id: " + plotId));
        plot.setDeleted(true);
        plotRepository.save(plot);
    }

    @Override
    public void softDeletePlotTask(Long plotTaskId) {
        PlotTask plot = plotTaskRepository.findById(plotTaskId)
                .orElseThrow(() -> new RuntimeException("Plot not found with id: " + plotTaskId));
        plot.setDeleted(true);
        plotTaskRepository.save(plot);
    }

    @Override
    public Plot updatePlot(String plotId, PlotRequest updatedPlot) {
        Plot newUpdatePlot = plotRepository.findById(plotId)
                .orElseThrow(() -> new ResourceNotFoundException("Plot not found with id: " + plotId));
        newUpdatePlot.setPlotTitle(updatedPlot.getPlotTitle());
        newUpdatePlot.setPlotDescription(updatedPlot.getPlotDescription());
        return plotRepository.save(newUpdatePlot);
    }

    @Override
    public PlotTask updatePlotTask(Long plotTaskId, PlotTaskRequest updatedPlotTask) {
        PlotTask newPlotTask = plotTaskRepository.findById(plotTaskId)
                .orElseThrow(() -> new ResourceNotFoundException("Plot Task not found with id: " + plotTaskId));
        newPlotTask.setTaskTitle(updatedPlotTask.getTaskTitle());
        newPlotTask.setTaskDescription(updatedPlotTask.getTaskDescription());
        newPlotTask.setAssignTo(updatedPlotTask.getAssignTo());
        newPlotTask.setWorkerName(updatedPlotTask.getWorkerName());

        return plotTaskRepository.save(newPlotTask);
    }

    @Override
    public Plot updatePlotStatus(String plotId, PlotStatus status,LocalDateTime updatedAt, String updateDevice) {
        Plot newUpdatePlot = plotRepository.findById(plotId)
                .orElseThrow(() -> new ResourceNotFoundException("Plot not found with id: " + plotId));
        newUpdatePlot.setStatus(status);
        newUpdatePlot.setUpdatedAt(updatedAt);
        newUpdatePlot.setUpdatedDevice(updateDevice);
        return plotRepository.save(newUpdatePlot);
    }

    @Override
    public PlotTask updatePlotTaskStatus(Long plotTaskId, TaskStatus status,LocalDateTime updatedAt, String updateDevice) {
        PlotTask newPlotTask = plotTaskRepository.findById(plotTaskId)
                .orElseThrow(() -> new ResourceNotFoundException("Plot Task not found with id: " + plotTaskId));
        newPlotTask.setStatus(status);
        newPlotTask.setUpdatedAt(updatedAt);
        newPlotTask.setUpdatedDevice(updateDevice);

        return plotTaskRepository.save(newPlotTask);
    }

    @Override
    public List<Plot> getActivePlots() {
        return plotRepository.findByIsDeletedFalse();
    }

    @Override
    public Plot getActivePlotById(String plotId) {
        Plot plot = plotRepository.findById(plotId)
                .orElseThrow(() -> new ResourceNotFoundException("Plot not found with id: " + plotId));
        if (plot.isDeleted()) {
            throw new ResourceNotFoundException("Plot with id " + plotId + " is deleted.");
        }
        return plot;
    }

    @Override
    public List<PlotTask> getPlotTaskByPlotId(String plotId) {
        return plotTaskRepository.findByIsDeletedFalse();
    }

    @Override
    public List<Plot> getActiveAndClosedToday() {
        List<Plot> result = new ArrayList<>();

        // 1. All ACTIVE & not deleted
        result.addAll(plotRepository.findByStatusAndIsDeletedFalse(Plot.PlotStatus.ACTIVE));

        // 2. All CLOSED & not deleted & updated today
        LocalDateTime startOfToday = LocalDate.now().atStartOfDay();
        LocalDateTime endOfToday   = LocalDate.now().atTime(LocalTime.MAX);
        result.addAll(plotRepository.findClosedToday(startOfToday, endOfToday));

        return result;
    }

    @Override
    public List<Plot> getOlderClosedPlots() {
        // CLOSED & not deleted & updated before today
        LocalDateTime start = LocalDate.now().atStartOfDay();
        return plotRepository.findByStatusAndIsDeletedFalseAndUpdatedAtBefore(Plot.PlotStatus.CLOSED, start);
    }

}
