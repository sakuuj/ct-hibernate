package by.clevertec.sakuuj.carshowroom.mapper;

import by.clevertec.sakuuj.carshowroom.domain.entity.Car;
import by.clevertec.sakuuj.carshowroom.domain.entity.Category;
import by.clevertec.sakuuj.carshowroom.domain.entity.Client;

import java.util.UUID;

public interface ToReferenceMapper {

    Category toCategory(short categoryId);

    Car toCar(UUID carId);

    Client toClient(UUID clientId);
}
