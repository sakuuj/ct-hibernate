package by.clevertec.sakuuj.carshowroom.repository.impl;

import by.clevertec.sakuuj.carshowroom.domain.entity.CarShowroom;
import by.clevertec.sakuuj.carshowroom.repository.CarShowroomRepo;
import by.clevertec.sakuuj.carshowroom.util.SessionFactoryConfigured;
import org.hibernate.SessionFactory;

import java.util.UUID;

public class CarShowroomRepoImpl extends SimpleRepo<UUID, CarShowroom> implements CarShowroomRepo {

    public CarShowroomRepoImpl() {
        super(CarShowroom.class, "id");
    }

}
