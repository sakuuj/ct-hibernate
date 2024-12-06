package by.clevertec.sakuuj.carshowroom.repository.impl;

import by.clevertec.sakuuj.carshowroom.domain.entity.Car;
import by.clevertec.sakuuj.carshowroom.domain.entity.Category;
import by.clevertec.sakuuj.carshowroom.domain.entity.Client;
import by.clevertec.sakuuj.carshowroom.domain.entity.Review;
import by.clevertec.sakuuj.carshowroom.repository.common.Pageable;
import by.clevertec.sakuuj.carshowroom.testbuilders.CarTestBuilder;
import by.clevertec.sakuuj.carshowroom.testbuilders.CategoryTestBuilder;
import by.clevertec.sakuuj.carshowroom.testbuilders.ClientTestBuilder;
import by.clevertec.sakuuj.carshowroom.testbuilders.ReviewTestBuilder;
import by.clevertec.sakuuj.carshowroom.testcontainers.postgres.PostgresSingletonContainerLauncher;
import org.hibernate.query.SortDirection;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ReviewRepoImplTest extends PostgresSingletonContainerLauncher {

    ReviewRepoImpl reviewRepo = new ReviewRepoImpl();

    @Test
    void shouldFindBySearchTerms() {

        Category category = CategoryTestBuilder.newInstance()
                .withId((short) 0)
                .build();

        Car car = CarTestBuilder.newInstance()
                .withId(null)
                .withCategory(category)
                .build();

        Client client = ClientTestBuilder.newInstance()
                .withId(null)
                .build();

        ReviewTestBuilder reviewTestBuilder = ReviewTestBuilder.newInstance()
                .withId(null)
                .withCar(car)
                .withClient(client);

        Review reviewOne = reviewTestBuilder
                .withContent("one")
                .build();
        Review reviewTwo = reviewTestBuilder
                .withContent("one two")
                .build();
        Review reviewThree= reviewTestBuilder
                .withContent("one three two")
                .build();

        SESSION_FACTORY.inTransaction(session -> {
            session.persist(category);
            session.persist(car);
            session.persist(client);

            session.persist(reviewOne);
            session.persist(reviewTwo);
            session.persist(reviewThree);
        });

        List<String> searchTerms = List.of("one", "two");

        // when
        List<Review> foundReviews = SESSION_FACTORY.fromTransaction(session ->
                reviewRepo.findAllBySearchTerms(
                        Pageable.builder().size(10).number(0).build(),
                        SortDirection.ASCENDING,
                        searchTerms,
                        session
                )
        );

        // then
        assertThat(foundReviews).containsExactlyInAnyOrder(reviewTwo, reviewThree);
    }

}