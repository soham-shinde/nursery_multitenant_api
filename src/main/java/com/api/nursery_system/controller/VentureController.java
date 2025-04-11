package com.api.nursery_system.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.nursery_system.entity.Venture;
import com.api.nursery_system.response.ApiResponse;
import com.api.nursery_system.service.venture.IVentureService;
import com.api.nursery_system.util.Constants;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/ventures")
@RequiredArgsConstructor
public class VentureController {

    private final IVentureService ventureService;

    // Create Venture
    @PostMapping
    public ResponseEntity<ApiResponse> createVenture(@Valid @RequestBody Venture request) {
        Venture venture = ventureService.createVenture(request);
        return ResponseEntity.ok(new ApiResponse(Constants.SUCCESS_STATUS, "Venture created", venture));
    }

    // Update Venture
    @PutMapping("/{ventureId}")
    public ResponseEntity<ApiResponse> updateVenture(@PathVariable Long ventureId,
            @Valid @RequestBody Venture request) {
        Venture venture = ventureService.updateVenture(ventureId, request);
        return ResponseEntity.ok(new ApiResponse(Constants.SUCCESS_STATUS, "Venture updated", venture));
    }

    // Delete Venture
    @DeleteMapping("/{ventureId}")
    public ResponseEntity<ApiResponse> deleteVenture(@PathVariable Long ventureId) {
        ventureService.deleteVenture(ventureId);
        return ResponseEntity.ok(new ApiResponse(Constants.SUCCESS_STATUS, "Venture deleted"));
    }

    // Get Venture by ID
    @GetMapping("/{ventureId}")
    public ResponseEntity<ApiResponse> getVentureById(@PathVariable Long ventureId) {
        Venture venture = ventureService.getVentureById(ventureId);
        return ResponseEntity.ok(new ApiResponse(Constants.SUCCESS_STATUS, venture));
    }

    // Get All Ventures
    @GetMapping
    public ResponseEntity<ApiResponse> getAllVentures() {

        return ResponseEntity.ok(new ApiResponse(Constants.SUCCESS_STATUS, ventureService.getAllVentures()));
    }
}
