package by.clevertec.sakuuj.carshowroom.repository.liquibase;

import by.clevertec.sakuuj.carshowroom.repository.impl.SimpleRepo;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.DirectoryResourceAccessor;
import lombok.experimental.UtilityClass;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@UtilityClass
public class LiquibaseRunner {

    public static final String CLASSPATH_DIRECTORY = "database-init";
    public static final String PATH_TO_CHANGELOG = "liquibase/changelog_root.yml";

    public static void runLiquibase(String jdbcUrl, String username, String password) {

        Connection c = null;
        try {
            c = DriverManager.getConnection(jdbcUrl, username, password);
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(c));

            Path outerDir = Path.of(SimpleRepo.class.getClassLoader().getResource(CLASSPATH_DIRECTORY).getPath());

            Liquibase liquibase = new Liquibase(
                    PATH_TO_CHANGELOG,
                    new DirectoryResourceAccessor(outerDir),
                    database
            );
            liquibase.update();

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (c != null) {
                try {
                    c.rollback();
                    c.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
