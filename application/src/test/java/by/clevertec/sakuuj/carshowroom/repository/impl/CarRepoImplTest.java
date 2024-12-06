package by.clevertec.sakuuj.carshowroom.repository.impl;

import by.clevertec.sakuuj.carshowroom.domain.entity.Car;
import by.clevertec.sakuuj.carshowroom.domain.entity.CarShowroom;
import by.clevertec.sakuuj.carshowroom.domain.entity.Category;
import by.clevertec.sakuuj.carshowroom.repository.common.Pageable;
import by.clevertec.sakuuj.carshowroom.testbuilders.CarShowroomTestBuilder;
import by.clevertec.sakuuj.carshowroom.testbuilders.CarTestBuilder;
import by.clevertec.sakuuj.carshowroom.testbuilders.CategoryTestBuilder;
import by.clevertec.sakuuj.carshowroom.testcontainers.postgres.PostgresSingletonContainerLauncher;
import org.hibernate.Hibernate;
import org.hibernate.query.SortDirection;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CarRepoImplTest extends PostgresSingletonContainerLauncher {

    private final CarRepoImpl carRepo = new CarRepoImpl();

    @Test
    void shouldAddCarToCarShowroom() {

        // given
        Category category = CategoryTestBuilder.newInstance()
                .withId((short) 0)
                .build();

        CarShowroom carShowroom = CarShowroomTestBuilder.newInstance()
                .withId(null)
                .build();

        CarTestBuilder carTestBuilder = CarTestBuilder.newInstance()
                .withId(null)
                .withCategory(category);

        Car car = carTestBuilder.build();


        SESSION_FACTORY.inTransaction(session -> {
            session.persist(category);
            session.persist(car);
            session.persist(carShowroom);
        });

        System.out.println("DB FILLED WITH DATA");

        // when
        SESSION_FACTORY.inTransaction(session ->
                carRepo.addCarToCarShowroom(car.getId(), carShowroom.getId(), session)
        );
        System.out.println("CAR WAS ADDED");

        // then
        SESSION_FACTORY.inTransaction(session -> {

            CarShowroom updatedCarshowroom = session.find(CarShowroom.class, carShowroom.getId());

            assertThat(updatedCarshowroom.getCars()).containsExactlyInAnyOrder(car);
        });
    }

    @Test
    void shouldFindByParams() {

        // given
        Category category = CategoryTestBuilder.newInstance()
                .withId((short) 0)
                .build();

        CarTestBuilder carTestBuilder = CarTestBuilder.newInstance()
                .withId(null)
                .withCategory(category);
        Car car = carTestBuilder.build();

        SESSION_FACTORY.inTransaction(session -> {
            session.persist(category);
            session.persist(car);
        });

        // when
        List<Car> foundCars = SESSION_FACTORY.fromTransaction(session -> carRepo.findAllByParams(
                        car.getMake(),
                        car.getProductionYear(),
                        category.getId(),
                        car.getPrice(),
                        car.getPrice(),
                        Pageable.builder().number(0).size(10).build(),
                        SortDirection.ASCENDING,
                        session
                )
        );

        assertThat(foundCars).hasSize(1);
        assertThat(foundCars.getFirst()).isEqualTo(car);
    }

    @Test
    void shouldFindAllSortedByPrice() {

        // given
        Category category = CategoryTestBuilder.newInstance()
                .withId((short) 0)
                .build();

        CarTestBuilder carTestBuilder = CarTestBuilder.newInstance()
                .withId(null)
                .withCategory(category);
        Car carOne = carTestBuilder
                .withPrice(BigDecimal.valueOf(1000_00, 2))
                .build();
        Car carTwo = carTestBuilder
                .withPrice(BigDecimal.valueOf(2000_00, 2))
                .build();
        Car carThree = carTestBuilder
                .withPrice(BigDecimal.valueOf(3000_00, 2))
                .build();

        SESSION_FACTORY.inTransaction(session -> {
            session.persist(category);
            session.persist(carOne);
            session.persist(carTwo);
            session.persist(carThree);
        });

        // when
        List<Car> foundCars = SESSION_FACTORY.fromTransaction(session -> carRepo.findAllSortedByPrice(
                Pageable.builder().number(0).size(10).build(),
                SortDirection.DESCENDING,
                session)
        );

        // then
        assertThat(foundCars).containsExactly(carThree, carTwo, carOne);
    }

    @Test
    void shouldFindAllWithEntityGraph() {

        // given
        Category category = CategoryTestBuilder.newInstance()
                .withId((short) 0)
                .build();

        CarShowroom carShowroom = CarShowroomTestBuilder.newInstance()
                .withId(null)
                .build();

        CarTestBuilder carTestBuilder = CarTestBuilder.newInstance()
                .withId(null)
                .withCategory(category)
                .withCarShowroom(carShowroom);

        Car carOne = carTestBuilder.build();
        Car carTwo = carTestBuilder.build();

        SESSION_FACTORY.inTransaction(session -> {
            session.persist(carShowroom);
            session.persist(category);

            session.persist(carOne);
            session.persist(carTwo);
        });

        // when
        List<Car> foundCars = SESSION_FACTORY.fromTransaction(session -> carRepo.findAllWithCategoryAndCarShowroom(
                        Pageable.builder().number(0).size(10).build(),
                        SortDirection.DESCENDING,
                        session
                )
        );

        assertThat(foundCars)
                .hasSize(2)
                .allMatch(car ->
                        Hibernate.isInitialized(car.getCategory()) && Hibernate.isInitialized(car.getCarShowroom())
                );
    }
}