package ru.tinkoff.edu.java.scrapper;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import ru.tinkoff.edu.java.scrapper.IntegrationEnvironment;

import java.io.File;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Arrays;
import java.util.List;

public class SimpleIntegrationEnvironmentTest extends IntegrationEnvironment {
    @Test
    public void testContainerRunningAndConnectionEstablished() {
        String url = POSTGRES_CONTAINER.getJdbcUrl();
        String user = DB_USER;
        String password = DB_PASSWORD;

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            Assertions.assertNotNull(connection, "Failed to establish a connection to the database");
        } catch (Exception e) {
            Assertions.fail("Exception: " + e.getMessage());
        }
    }
}
