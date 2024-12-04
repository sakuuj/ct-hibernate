package by.clevertec.sakuuj.carshowroom.repository.impl;

import by.clevertec.sakuuj.carshowroom.domain.entity.Car;
import by.clevertec.sakuuj.carshowroom.domain.entity.Category;
import by.clevertec.sakuuj.carshowroom.domain.entity.Client;
import by.clevertec.sakuuj.carshowroom.testbuilders.CarTestBuilder;
import by.clevertec.sakuuj.carshowroom.testbuilders.CategoryTestBuilder;
import by.clevertec.sakuuj.carshowroom.testbuilders.ClientTestBuilder;
import by.clevertec.sakuuj.carshowroom.testcontainers.postgres.PostgresSingletonContainerLauncher;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ClientRepoImplTest extends PostgresSingletonContainerLauncher {

    private final ClientRepoImpl clientRepo = new ClientRepoImpl();

    @Test
    void shouldAddCarToClient() {

        // given
        Category category = CategoryTestBuilder.newInstance()
                .withId((short) 0)
                .build();
        Client client = ClientTestBuilder.newInstance()
                .withId(null)
                .build();

        CarTestBuilder carTestBuilder = CarTestBuilder.newInstance()
                .withId(null)
                .withCategory(category);

        Car carOne = carTestBuilder.build();
        Car carTwo = carTestBuilder.build();
        Car carToAdd = carTestBuilder.build();

        SESSION_FACTORY.inTransaction(session -> {
            session.persist(category);
            session.persist(client);

            session.persist(carOne);
            session.persist(carTwo);
            session.persist(carToAdd);

            client.getCars().add(carOne);
            client.getCars().add(carTwo);
        });

        System.out.println("DB FILLED WITH DATA");

        // when
        SESSION_FACTORY.inTransaction(session ->
                clientRepo.addCarToClient(carToAdd.getId(), client.getId(), session)
        );
        System.out.println("CAR WAS ADDED");

        // then
        SESSION_FACTORY.inTransaction(session -> {

            Client updatedClient = session.find(Client.class, client.getId());

            assertThat(updatedClient.getCars()).containsExactlyInAnyOrder(carOne, carTwo, carToAdd);
        });
    }

}