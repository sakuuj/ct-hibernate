package by.clevertec.sakuuj.carshowroom.testcontainers.postgres;

import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;

@UtilityClass
public class PostgresDBCleaner {

    public static void truncateTables(SessionFactory sessionFactory) {

        sessionFactory.inTransaction(session -> {
            session.createNativeMutationQuery(
                    """
                            DO
                            $$BEGIN
                            EXECUTE  'TRUNCATE TABLE ' ||
                            (SELECT array_to_string(
                                (
                                SELECT ARRAY(SELECT table_name FROM information_schema.tables 
                                    WHERE table_schema = 'public' 
                                    AND table_name NOT LIKE 'databasechangelog%')
                                ), ','
                            ))::VARCHAR;
                            END$$
                            """).executeUpdate();
        });
    }
}
