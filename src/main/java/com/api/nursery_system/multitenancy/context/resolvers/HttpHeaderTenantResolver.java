package com.api.nursery_system.multitenancy.context.resolvers;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class HttpHeaderTenantResolver implements TenantResolver<HttpServletRequest> {
    private static final String TENANT_HEADER = "X-TenantId";

    @Override
    public String resolveTenantIdentifier(HttpServletRequest request) {
        return request.getHeader(TENANT_HEADER);
    }

}
