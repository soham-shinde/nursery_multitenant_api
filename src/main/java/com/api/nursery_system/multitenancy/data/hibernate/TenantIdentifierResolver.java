package com.api.nursery_system.multitenancy.data.hibernate;

import java.util.Map;
import java.util.Objects;

import org.hibernate.cfg.MultiTenancySettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

import com.api.nursery_system.multitenancy.context.TenantContextHolder;

@Component
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver<String>, HibernatePropertiesCustomizer {

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
       hibernateProperties.put(MultiTenancySettings.MULTI_TENANT_IDENTIFIER_RESOLVER, this);
    }

    @Override
    public String resolveCurrentTenantIdentifier() {
       return Objects.requireNonNullElse(TenantContextHolder.getTenantIdentifier(), "public");
    }  

    @Override
    public  boolean validateExistingCurrentSessions() {
      return false;
        
    }
    
}
