package com.api.nursery_system.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.nursery_system.dto.PlotDto;
import com.api.nursery_system.dto.PlotTaskDto;
import com.api.nursery_system.entity.Plot;
import com.api.nursery_system.entity.Plot.PlotStatus;
import com.api.nursery_system.entity.PlotTask;
import com.api.nursery_system.entity.PlotTask.TaskStatus;
import com.api.nursery_system.request.PlotRequest;
import com.api.nursery_system.request.PlotTaskRequest;
import com.api.nursery_system.response.ApiResponse;
import com.api.nursery_system.service.plot.PlotService;
import com.api.nursery_system.util.Constants;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/plots")
@RequiredArgsConstructor
public class PlotController {
    private final PlotService plotService;

    /**
     * Endpoint to create a new Plot.
     * Expects a valid PlotRequest in the request body.
     */
    @PostMapping
    public ResponseEntity<ApiResponse> createPlot(@Valid @RequestBody PlotRequest request) {
        try {
            // Generate a new plotId and set it into the request (assume PlotRequest has a
            // setPlotId method)

            Plot createdPlot = plotService.createPlot(request);
            PlotDto plotDto = PlotDto.from(createdPlot);
            return ResponseEntity.ok(new ApiResponse(Constants.SUCCESS_STATUS, "Plot created successfully", plotDto));
        } catch (Exception e) {
            // Handle exceptions gracefully and return a meaningful error message.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(Constants.ERROR_STATUS, "Failed to create plot: " + e.getMessage()));
        }
    }

    /**
     * Endpoint to create a new PlotTask.
     * Expects a valid PlotTaskRequest in the request body.
     */
    @PostMapping("/tasks")
    public ResponseEntity<ApiResponse> createPlotTask(@Valid @RequestBody PlotTaskRequest request) {
        try {
            PlotTask createdTask = plotService.createPlotTask(request);
            PlotTaskDto taskDto = PlotTaskDto.from(createdTask);
            return ResponseEntity
                    .ok(new ApiResponse(Constants.SUCCESS_STATUS, "Plot task created successfully", taskDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(Constants.ERROR_STATUS, "Failed to create plot task: " + e.getMessage()));
        }
    }

    /**
     * Endpoint to update an existing Plot.
     */
    @PutMapping("/{plotId}")
    public ResponseEntity<ApiResponse> updatePlot(@PathVariable String plotId,
            @Valid @RequestBody PlotRequest request) {
        try {
            Plot updatedPlot = plotService.updatePlot(plotId, request);
            PlotDto plotDto = PlotDto.from(updatedPlot);
            return ResponseEntity.ok(new ApiResponse(Constants.SUCCESS_STATUS, "Plot updated successfully", plotDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(Constants.ERROR_STATUS, "Failed to update plot: " + e.getMessage()));
        }
    }

    /**
     * Endpoint to update the status of an existing Plot.
     */
    @PutMapping("/{plotId}/status")
    public ResponseEntity<ApiResponse> updatePlotStatus(@PathVariable String plotId,
            @RequestParam("status") PlotStatus status, @RequestParam("datetime") LocalDateTime updatedAt,
            @RequestParam("device") String updateDevice) {
        try {
            // Here, convert the provided status String to the PlotStatus enum as needed.
            Plot updatedPlot = plotService.updatePlotStatus(plotId, status, updatedAt, updateDevice);
            PlotDto plotDto = PlotDto.from(updatedPlot);
            return ResponseEntity
                    .ok(new ApiResponse(Constants.SUCCESS_STATUS, "Plot status updated successfully", plotDto));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(Constants.ERROR_STATUS, "Invalid plot status provided."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(Constants.ERROR_STATUS, "Failed to update plot status: " + e.getMessage()));
        }
    }

    /**
     * Endpoint to soft-delete a Plot.
     */
    @DeleteMapping("/{plotId}")
    public ResponseEntity<ApiResponse> softDeletePlot(@PathVariable String plotId) {
        try {
            plotService.softDeletePlot(plotId);
            return ResponseEntity.ok(new ApiResponse(Constants.SUCCESS_STATUS, "Plot deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(Constants.ERROR_STATUS, "Failed to delete plot: " + e.getMessage()));
        }
    }

    /**
     * Endpoint to update an existing PlotTask.
     */
    @PutMapping("/tasks/{plotTaskId}")
    public ResponseEntity<ApiResponse> updatePlotTask(@PathVariable Long plotTaskId,
            @Valid @RequestBody PlotTaskRequest request) {
        try {
            PlotTask updatedTask = plotService.updatePlotTask(plotTaskId, request);
            PlotTaskDto taskDto = PlotTaskDto.from(updatedTask);
            return ResponseEntity
                    .ok(new ApiResponse(Constants.SUCCESS_STATUS, "Plot task updated successfully", taskDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(Constants.ERROR_STATUS, "Failed to update plot task: " + e.getMessage()));
        }
    }

    /**
     * Endpoint to update the status of an existing PlotTask.
     */
    @PutMapping("/tasks/{plotTaskId}/status")
    public ResponseEntity<ApiResponse> updatePlotTaskStatus(@PathVariable Long plotTaskId,
            @RequestParam("status") TaskStatus status, @RequestParam("datetime") LocalDateTime updatedAt,
            @RequestParam("device") String updateDevice) {
        try {
            // Convert the status String to the TaskStatus enum as needed.
            PlotTask updatedTask = plotService.updatePlotTaskStatus(plotTaskId, status, updatedAt, updateDevice);
            PlotTaskDto taskDto = PlotTaskDto.from(updatedTask);
            return ResponseEntity
                    .ok(new ApiResponse(Constants.SUCCESS_STATUS, "Plot task status updated successfully", taskDto));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(Constants.ERROR_STATUS, "Invalid task status provided."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(Constants.ERROR_STATUS,
                            "Failed to update plot task status: " + e.getMessage()));
        }
    }

    /**
     * Endpoint to soft-delete a PlotTask.
     */
    @DeleteMapping("/tasks/{plotTaskId}")
    public ResponseEntity<ApiResponse> softDeletePlotTask(@PathVariable Long plotTaskId) {
        try {
            plotService.softDeletePlotTask(plotTaskId);
            return ResponseEntity.ok(new ApiResponse(Constants.SUCCESS_STATUS, "Plot task deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(Constants.ERROR_STATUS, "Failed to delete plot task: " + e.getMessage()));
        }
    }

    /**
     * Endpoint to retrieve all active (non-deleted) Plots.
     */
    @GetMapping("/active")
    public ResponseEntity<ApiResponse> getActivePlots() {
        try {
            List<Plot> plots = plotService.getActivePlots();
            List<PlotDto> plotDtos = plots.stream().map(PlotDto::from).toList();
            return ResponseEntity.ok(new ApiResponse(Constants.SUCCESS_STATUS, plotDtos));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(Constants.ERROR_STATUS, "Failed to fetch active plots: " + e.getMessage()));
        }
    }

    /**
     * Endpoint to retrieve a specific active Plot by its ID.
     */
    @GetMapping("/{plotId}")
    public ResponseEntity<ApiResponse> getActivePlotById(@PathVariable String plotId) {
        try {
            Plot plot = plotService.getActivePlotById(plotId);
            PlotDto plotDto = PlotDto.from(plot);
            return ResponseEntity.ok(new ApiResponse(Constants.SUCCESS_STATUS, plotDto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(Constants.ERROR_STATUS, "Plot not found: " + e.getMessage()));
        }
    }

    /**
     * Endpoint to retrieve PlotTasks associated with a specific Plot.
     */
    @GetMapping("/{plotId}/tasks")
    public ResponseEntity<ApiResponse> getPlotTasksByPlotId(@PathVariable String plotId) {
        try {
            List<PlotTask> tasks = plotService.getPlotTaskByPlotId(plotId);
            List<PlotTaskDto> taskDtos = tasks.stream().map(PlotTaskDto::from).toList();
            return ResponseEntity.ok(new ApiResponse(Constants.SUCCESS_STATUS, taskDtos));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(Constants.ERROR_STATUS, "Failed to fetch plot tasks: " + e.getMessage()));
        }
    }

    // Exception handler for validation errors
    @ExceptionHandler
    public ResponseEntity<ApiResponse> handleValidationExceptions(Exception ex) {
        return ResponseEntity.badRequest()
                .body(new ApiResponse(Constants.ERROR_STATUS, "Validation error: " + ex.getMessage()));
    }

    @GetMapping("/active-or-closed-today")
    public ResponseEntity<ApiResponse> getActiveAndRecentlyClosed() {
        List<Plot> plots = plotService.getActiveAndRecentlyClosed();
        return ResponseEntity.ok(new ApiResponse(Constants.SUCCESS_STATUS, plots.stream().map(PlotDto::from).toList()));
    }

    /**
     * GET /api/plots/closed-before-today
     * returns all CLOSED plots updated before today
     */
    @GetMapping("/closed-before-today")
    public ResponseEntity<ApiResponse> getOlderClosedPlots() {
        List<Plot> plots = plotService.getOlderClosedPlots();
        return ResponseEntity.ok(new ApiResponse(Constants.SUCCESS_STATUS, plots.stream().map(PlotDto::from).toList()));
    }
}
