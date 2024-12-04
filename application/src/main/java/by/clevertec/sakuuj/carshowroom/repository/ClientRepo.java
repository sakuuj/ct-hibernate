package by.clevertec.sakuuj.carshowroom.repository;

import by.clevertec.sakuuj.carshowroom.domain.entity.Client;
import org.hibernate.Session;

import java.util.UUID;

public interface ClientRepo extends Repo<UUID, Client> {

    void addCarToClient(UUID carId, UUID clientId, Session session);

}
