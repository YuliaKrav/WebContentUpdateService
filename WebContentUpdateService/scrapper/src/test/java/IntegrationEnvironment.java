import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.testcontainers.containers.PostgreSQLContainer;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;

public abstract class IntegrationEnvironment {
    static final String DB_NAME =  "scrapper";
    static final String DB_USER = "user";
    static final String DB_PASSWORD = "password";
    static final PostgreSQLContainer<?> POSTGRES_CONTAINER;

    static {
        POSTGRES_CONTAINER = new PostgreSQLContainer<>("postgres:14")
                .withDatabaseName(DB_NAME)
                .withUsername(DB_USER)
                .withPassword(DB_PASSWORD);
        POSTGRES_CONTAINER.start();

        try {
            runLiquibaseMigration(POSTGRES_CONTAINER.getJdbcUrl(), "user", "password");
        } catch (Exception e) {
            throw new RuntimeException("Failed to run Liquibase migration", e);
        }
    }


    private static void runLiquibaseMigration(String url, String user, String password) throws Exception {
        String migrationsPath = "./migrations/master.xml";
//        String migrationsPath = Paths.get(".")
//                .resolve("migrations")
//                .resolve("master.xml")
//                .toString();

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            DatabaseConnection dbConnection = new JdbcConnection(connection);
            Database database = new PostgresDatabase();
            database.setConnection(dbConnection);

            Liquibase liquibase = new Liquibase(migrationsPath, new ClassLoaderResourceAccessor(), database);
            liquibase.update(new Contexts());
        }
    }
}

