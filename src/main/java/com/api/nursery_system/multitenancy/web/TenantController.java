package com.api.nursery_system.multitenancy.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.nursery_system.multitenancy.context.TenantContextHolder;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("tenant")
public class TenantController {
    @GetMapping
    public String getTenant() {
        return "tenant Id " + TenantContextHolder.getTenantIdentifier();
    }
}
