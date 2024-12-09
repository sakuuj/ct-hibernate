package by.clevertec.sakuuj.carshowroom.repository.custom;

import by.clevertec.sakuuj.carshowroom.domain.entity.Car;
import by.clevertec.sakuuj.carshowroom.domain.entity.Client;
import by.clevertec.sakuuj.carshowroom.exception.EntityNotFoundException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ClientRepoCustomImpl implements ClientRepoCustom {

    private final EntityManager entityManager;

    @Override
    public void addCarToClient(UUID carId, UUID clientId) {

        Session session = entityManager.unwrap(Session.class);

        Client client = session.byId(Client.class)
                .loadOptional(clientId)
                .orElseThrow(EntityNotFoundException::new);

        Car carReference = session.getReference(Car.class, carId);

        client.getCars().add(carReference);
    }
}
