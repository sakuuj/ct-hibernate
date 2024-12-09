package by.clevertec.sakuuj.carshowroom.mapper;


import by.clevertec.sakuuj.carshowroom.domain.entity.Car;
import by.clevertec.sakuuj.carshowroom.domain.entity.Category;
import by.clevertec.sakuuj.carshowroom.domain.entity.Client;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRED)
public class ToReferenceMapperImpl implements ToReferenceMapper {

    private final EntityManager entityManager;

    public Category toCategory(short categoryId) {

        Session session = entityManager.unwrap(Session.class);

        return session.getReference(Category.class, categoryId);
    }

    public Car toCar(UUID carId) {

        Session session = entityManager.unwrap(Session.class);

        return session.getReference(Car.class, carId);
    }

    public Client toClient(UUID clientId) {

        Session session = entityManager.unwrap(Session.class);

        return session.getReference(Client.class, clientId);
    }
}
