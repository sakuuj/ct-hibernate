package by.clevertec.sakuuj.carshowroom.repository.impl;

import by.clevertec.sakuuj.carshowroom.domain.entity.Car;
import by.clevertec.sakuuj.carshowroom.domain.entity.Client;
import by.clevertec.sakuuj.carshowroom.exception.EntityNotFoundException;
import by.clevertec.sakuuj.carshowroom.repository.ClientRepo;
import by.clevertec.sakuuj.carshowroom.util.SessionFactoryConfigured;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Set;
import java.util.UUID;

public class ClientRepoImpl extends SimpleRepo<UUID, Client> implements ClientRepo {

    public ClientRepoImpl() {
        super(Client.class, "id");
    }

    @Override
    public void addCarToClient(UUID carId, UUID clientId, Session session) {

        Client client = findById(clientId, session).orElseThrow(EntityNotFoundException::new);
        Car carReference = session.getReference(Car.class, carId);

        client.getCars().add(carReference);
    }
}
