package by.clevertec.sakuuj.carshowroom.testcontainers.postgres;

import by.clevertec.sakuuj.carshowroom.domain.entity.Car;
import by.clevertec.sakuuj.carshowroom.domain.entity.CarShowroom;
import by.clevertec.sakuuj.carshowroom.domain.entity.Category;
import by.clevertec.sakuuj.carshowroom.domain.entity.Client;
import by.clevertec.sakuuj.carshowroom.domain.entity.Review;
import by.clevertec.sakuuj.carshowroom.repository.liquibase.LiquibaseRunner;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TestSessionFactory {

    static {
        LiquibaseRunner.runLiquibase(
                PostgresSingletonContainerLauncher.getContainerJdbcUri(),
                PostgresSingletonContainerLauncher.USERNAME,
                PostgresSingletonContainerLauncher.PASSWORD
        );
    }

    private static final Configuration SESSION_FACTORY_CONFIG = new Configuration()
            .addAnnotatedClass(CarShowroom.class)
            .addAnnotatedClass(Client.class)
            .addAnnotatedClass(Car.class)
            .addAnnotatedClass(Review.class)
            .addAnnotatedClass(Category.class);

    private static volatile SessionFactory sessionFactoryInstance;

    private static final Lock SINGLETON_LOCK = new ReentrantLock();


    public static SessionFactory getInstance() {

        if (sessionFactoryInstance == null) {

            try {
                SINGLETON_LOCK.lock();

                if (sessionFactoryInstance != null) {
                    return sessionFactoryInstance;
                }

                sessionFactoryInstance = SESSION_FACTORY_CONFIG
                        .setProperty("jakarta.persistence.jdbc.url", PostgresSingletonContainerLauncher.getContainerJdbcUri())
                        .setProperty("jakarta.persistence.jdbc.user", PostgresSingletonContainerLauncher.USERNAME)
                        .setProperty("jakarta.persistence.jdbc.password", PostgresSingletonContainerLauncher.PASSWORD)
                        .buildSessionFactory();

            } finally {
                SINGLETON_LOCK.unlock();
            }
        }
        return sessionFactoryInstance;
    }

}