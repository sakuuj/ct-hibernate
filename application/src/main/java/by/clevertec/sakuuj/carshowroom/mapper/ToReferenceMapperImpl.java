package by.clevertec.sakuuj.carshowroom.mapper;


import by.clevertec.sakuuj.carshowroom.domain.entity.Car;
import by.clevertec.sakuuj.carshowroom.domain.entity.Category;
import by.clevertec.sakuuj.carshowroom.domain.entity.Client;
import org.hibernate.Session;
import org.mapstruct.Context;

import java.util.UUID;

public class ToReferenceMapperImpl implements ToReferenceMapper {

    public Category toCategory(short categoryId, @Context Session session) {

        return session.getReference(Category.class, categoryId);
    }

    public Car toCar(UUID carId, @Context Session session) {

        return session.getReference(Car.class, carId);
    }

    public Client toClient(UUID clientId, @Context Session session) {

        return session.getReference(Client.class, clientId);
    }
}
