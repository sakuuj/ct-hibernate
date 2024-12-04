package by.clevertec.sakuuj.carshowroom.util;

import by.clevertec.sakuuj.carshowroom.repository.liquibase.LiquibaseRunner;
import by.clevertec.sakuuj.carshowroom.domain.entity.Car;
import by.clevertec.sakuuj.carshowroom.domain.entity.CarShowroom;
import by.clevertec.sakuuj.carshowroom.domain.entity.Category;
import by.clevertec.sakuuj.carshowroom.domain.entity.Client;
import by.clevertec.sakuuj.carshowroom.domain.entity.Review;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionFactoryConfigured {

    private static final Configuration SESSION_FACTORY_CONFIG = new Configuration()
            .addAnnotatedClass(CarShowroom.class)
            .addAnnotatedClass(Client.class)
            .addAnnotatedClass(Car.class)
            .addAnnotatedClass(Review.class)
            .addAnnotatedClass(Category.class);

    static {
        LiquibaseRunner.runLiquibase(
            SESSION_FACTORY_CONFIG.getProperty("jakarta.persistence.jdbc.url"),
            SESSION_FACTORY_CONFIG.getProperty("jakarta.persistence.jdbc.user"),
            SESSION_FACTORY_CONFIG.getProperty("jakarta.persistence.jdbc.password")
        );
    }

    private static final SessionFactory SESSION_FACTORY = new Configuration().buildSessionFactory();

    static  {
        Runtime.getRuntime().addShutdownHook(new Thread(SESSION_FACTORY::close));
    }

    public static SessionFactory getInstance() {

        return SESSION_FACTORY;
    }
}
