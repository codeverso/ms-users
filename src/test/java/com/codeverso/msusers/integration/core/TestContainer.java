package com.codeverso.msusers.integration.core;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class TestContainer implements BeforeAllCallback {
    private static final AtomicBoolean containerStarted = new AtomicBoolean(false);

    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer("postgres:14.5")
            .withDatabaseName("users")
            .withUsername("admin")
            .withPassword("root");

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        if (!containerStarted.get()) {
            postgreSQLContainer.setPortBindings(List.of("5432:5432"));
            postgreSQLContainer.start();

            containerStarted.set(true);
        }
    }
}
