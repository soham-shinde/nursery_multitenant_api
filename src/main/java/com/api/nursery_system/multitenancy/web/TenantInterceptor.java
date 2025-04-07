package com.api.nursery_system.multitenancy.web;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.api.nursery_system.multitenancy.context.TenantContextHolder;
import com.api.nursery_system.multitenancy.context.resolvers.HttpHeaderTenantResolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TenantInterceptor implements HandlerInterceptor {

    private final HttpHeaderTenantResolver tenantResolver;
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        clear();
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        clear();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        var tenantId = tenantResolver.resolveTenantIdentifier(request);

        TenantContextHolder.setTenantIdentifier(tenantId);
        return true;
    }

    private void clear() {
        TenantContextHolder.clear();
    }

}
