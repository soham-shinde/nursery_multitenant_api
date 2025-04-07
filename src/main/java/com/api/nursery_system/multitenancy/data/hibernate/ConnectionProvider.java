package com.api.nursery_system.multitenancy.data.hibernate;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.cfg.AvailableSettings;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class ConnectionProvider implements MultiTenantConnectionProvider<String>, HibernatePropertiesCustomizer {

    private final DataSource dataSource;

    @Override
    public Connection getAnyConnection() throws SQLException {
        return getConnection("public");
    }

    @Override
    public Connection getConnection(String arg0) throws SQLException {
        var connection = dataSource.getConnection();
        connection.setSchema(arg0);
        return connection;
    }

    @Override
    public void releaseAnyConnection(Connection arg0) throws SQLException {
        arg0.close();
    }

    @Override
    public void releaseConnection(String arg0, Connection arg1) throws SQLException {
        arg1.setSchema("public");
        arg1.close();
    }

    @Override
    public boolean isUnwrappableAs(Class<?> arg0) {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> arg0) {
        throw new UnsupportedOperationException("Unimplemented method 'unwrap'.");
    }

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put(AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER, this);
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return true;
    }

}
