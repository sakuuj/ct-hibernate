package by.clevertec.sakuuj.carshowroom.testcontainers.postgres;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

public abstract class PostgresSingletonContainerLauncher {

    static final String USERNAME = "postgres";
    static final String PASSWORD = "postgres";
    static final String DATABASE = "postgresXXX";

    static final GenericContainer<?> POSTGRES_CONTAINER;

    static {
        POSTGRES_CONTAINER = new GenericContainer<>("postgres:16.6-alpine3.20")
                .withEnv("POSTGRES_PASSWORD", PASSWORD)
                .withEnv("POSTGRES_USER", USERNAME)
                .withEnv("POSTGRES_DB", DATABASE)
                .withExposedPorts(5432)
                .waitingFor(Wait.forLogMessage(".*database system is ready to accept connections.*", 2));

        POSTGRES_CONTAINER.start();
    }

    static String getContainerJdbcUri() {
        return "jdbc:postgresql://" + POSTGRES_CONTAINER.getHost() + ":"
                + POSTGRES_CONTAINER.getFirstMappedPort()
                + "/" + DATABASE;
    }


//    @AfterEach
//    public void cleanDb() {
//
//        PostgresDBCleaner.truncateTables(SESSION_FACTORY);
//    }
}