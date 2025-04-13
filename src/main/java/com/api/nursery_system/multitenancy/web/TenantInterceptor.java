package com.api.nursery_system.multitenancy.web;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.api.nursery_system.multitenancy.context.TenantContextHolder;
import com.api.nursery_system.multitenancy.context.resolvers.HttpHeaderTenantResolver;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TenantInterceptor extends OncePerRequestFilter {

    private final HttpHeaderTenantResolver tenantResolver;

    private void clear() {
        TenantContextHolder.clear();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        tenantResolver.resolveTenantIdentifier(request);
        try {
            filterChain.doFilter(request, response);
        } finally {
            clear();
        }
    }


}
