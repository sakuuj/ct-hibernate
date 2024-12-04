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
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

class SimpleRepoTest extends PostgresSingletonContainerLauncher {

    private final SimpleRepo<UUID, Review> simpleRepo = new SimpleRepo<>(
            Review.class,
            "id"
    ) {
    };

    private static final String TEST_FETCH_GRAPH_NAME = "Review.withClient-withCar";

    @Nested
    class findById {
        @Test
        void shouldFindReviewById() {

            // given
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
            Review review = ReviewTestBuilder.newInstance()
                    .withId(null)
                    .withCar(car)
                    .withClient(client)
                    .build();

            SESSION_FACTORY.inTransaction(session -> {
                        session.persist(category);
                        session.persist(car);
                        session.persist(client);
                        session.persist(review);
                    }
            );

            // when
            Review actualReview = SESSION_FACTORY.fromTransaction(session ->
                            simpleRepo.findById(review.getId(), session)
                    )
                    .orElseThrow(RuntimeException::new);

            // then
            assertThat(review.getId()).isNotNull();

            assertThat(actualReview).usingRecursiveComparison()
                    .ignoringFields(
                            "client",
                            "car"
                    )
                    .isEqualTo(review);
        }
    }

    @Nested
    class findByIdWithFetchGraph {
        @Test
        void shouldFindReviewByIdWithFetchGraph() {

            // given
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
            Review review = ReviewTestBuilder.newInstance()
                    .withId(null)
                    .withCar(car)
                    .withClient(client)
                    .build();

            SESSION_FACTORY.inTransaction(session -> {
                        session.persist(category);
                        session.persist(car);
                        session.persist(client);
                        session.persist(review);
                    }
            );

            // when
            Review actualReview = SESSION_FACTORY.fromTransaction(session ->
                            simpleRepo.findByIdWithFetchGraph(review.getId(), TEST_FETCH_GRAPH_NAME, session)
                    )
                    .orElseThrow(RuntimeException::new);

            // then
            assertThat(review.getId()).isNotNull();

            assertThat(actualReview).usingRecursiveComparison()
                    .ignoringFields(
                            "client.reviews",
                            "client.clientContacts",
                            "client.cars",
                            "car.reviews",
                            "car.category",
                            "car.carShowroom"
                    )
                    .isEqualTo(review);
        }
    }

    @Nested
    class findAll {

        // chance of random result to be correct
        // (in a sense that sort may not be applied) is (1/6)^5 < 0.013%
        @RepeatedTest(5)
        void shouldFindAllSortedAndPaginated() {

            // given
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

            Review reviewOne = ReviewTestBuilder.newInstance()
                    .withId(null)
                    .withCar(car)
                    .withClient(client)
                    .build();
            Review reviewTwo = ReviewTestBuilder.newInstance()
                    .withId(null)
                    .withCar(car)
                    .withClient(client)
                    .build();
            Review reviewThree = ReviewTestBuilder.newInstance()
                    .withId(null)
                    .withCar(car)
                    .withClient(client)
                    .build();

            SESSION_FACTORY.inTransaction(session -> {
                        session.persist(category);
                        session.persist(car);
                        session.persist(client);

                        session.persist(reviewOne);
                        session.persist(reviewTwo);
                        session.persist(reviewThree);
                    }
            );

            // when
            List<Review> firstPage = SESSION_FACTORY.fromTransaction(session ->
                    simpleRepo.findAll(
                            Pageable.builder()
                                    .number(0)
                                    .size(2)
                                    .build(),
                            SortDirection.ASCENDING,
                            session)
            );

            List<Review> secondPage = SESSION_FACTORY.fromTransaction(session ->
                    simpleRepo.findAll(
                            Pageable.builder()
                                    .number(1)
                                    .size(2)
                                    .build(),
                            SortDirection.ASCENDING,
                            session)
            );

            // then
            assertThat(firstPage).hasSize(2);
            assertThat(secondPage).hasSize(1);

            List<Review> reviews = new ArrayList<>(List.of(reviewOne, reviewTwo, reviewThree));
            reviews.sort(Comparator.comparing(r -> r.getId().toString()));

            assertThat(firstPage).containsExactlyElementsOf(reviews.subList(0, 2));
            assertThat(secondPage).containsExactlyElementsOf(reviews.subList(2, 3));
        }
    }

    @Nested
    class findAllWithFetchGraph {

        @RepeatedTest(5)
        void shouldFindAllSortedAndPaginated() {

            // given
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

            Review reviewOne = ReviewTestBuilder.newInstance()
                    .withId(null)
                    .withCar(car)
                    .withClient(client)
                    .build();
            Review reviewTwo = ReviewTestBuilder.newInstance()
                    .withId(null)
                    .withCar(car)
                    .withClient(client)
                    .build();
            Review reviewThree = ReviewTestBuilder.newInstance()
                    .withId(null)
                    .withCar(car)
                    .withClient(client)
                    .build();

            SESSION_FACTORY.inTransaction(session -> {
                        session.persist(category);
                        session.persist(car);
                        session.persist(client);

                        session.persist(reviewOne);
                        session.persist(reviewTwo);
                        session.persist(reviewThree);
                    }
            );

            // when
            List<Review> firstPage = SESSION_FACTORY.fromTransaction(session ->
                    simpleRepo.findAllWithFetchGraph(
                            Pageable.builder()
                                    .number(0)
                                    .size(2)
                                    .build(),
                            SortDirection.ASCENDING,
                            TEST_FETCH_GRAPH_NAME,
                            session)
            );

            List<Review> secondPage = SESSION_FACTORY.fromTransaction(session ->
                    simpleRepo.findAllWithFetchGraph(
                            Pageable.builder()
                                    .number(1)
                                    .size(2)
                                    .build(),
                            SortDirection.ASCENDING,
                            TEST_FETCH_GRAPH_NAME,
                            session)
            );

            // then
            assertThat(firstPage).hasSize(2);
            assertThat(secondPage).hasSize(1);

            List<Review> reviews = new ArrayList<>(List.of(reviewOne, reviewTwo, reviewThree));
            reviews.sort(Comparator.comparing(r -> r.getId().toString()));
            assertThat(firstPage).containsExactlyElementsOf(reviews.subList(0, 2));
            assertThat(secondPage).containsExactlyElementsOf(reviews.subList(2, 3));

            Consumer<Review> assertThatReviewsAreInitializedByFetchGraph = review -> {

                assertThat(review.getClient()).usingRecursiveComparison()
                        .ignoringFields(
                                "reviews",
                                "clientContacts",
                                "cars"
                        )
                        .isEqualTo(client);

                assertThat(review.getCar()).usingRecursiveComparison()
                        .ignoringFields(
                                "reviews",
                                "category",
                                "carShowroom"
                        )
                        .isEqualTo(car);
            };

            firstPage.forEach(assertThatReviewsAreInitializedByFetchGraph);
            secondPage.forEach(assertThatReviewsAreInitializedByFetchGraph);
        }
    }

    @Nested
    class create {

        @Test
        void shouldCreateReview() {

            // given
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

            SESSION_FACTORY.inTransaction(session -> {
                session.persist(category);
                session.persist(car);
                session.persist(client);
            });

            Review expectedReview = ReviewTestBuilder.newInstance()
                    .withId(null)
                    .withCar(car)
                    .withClient(client)
                    .build();

            // when
            Review createdReview = SESSION_FACTORY.fromTransaction(session ->
                    simpleRepo.create(expectedReview, session)
            );

            Review actualReview = SESSION_FACTORY.fromTransaction(
                    session -> session.find(Review.class, expectedReview.getId())
            );

            // then
            assertThat(actualReview).usingRecursiveComparison()
                    .ignoringFields("client")
                    .ignoringFields("car")
                    .isEqualTo(createdReview);

            assertThat(actualReview).usingRecursiveComparison()
                    .ignoringFields("id")
                    .ignoringFields("client")
                    .ignoringFields("car")
                    .isEqualTo(expectedReview);

            assertThat(actualReview.getId()).isNotNull();
        }
    }

    @Nested
    class deleteById {

        @Test
        void shouldDeleteReview() {

            // given
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

            SESSION_FACTORY.inTransaction(session -> {
                session.persist(category);
                session.persist(car);
                session.persist(client);
            });

            Review sourceReview = ReviewTestBuilder.newInstance()
                    .withId(null)
                    .withContent("cont1")
                    .withRating((short) 4)
                    .withCar(car)
                    .withClient(client)
                    .build();

            UUID createdId = SESSION_FACTORY.fromTransaction(
                    session -> {

                        session.persist(sourceReview);
                        session.flush();

                        Review result = session.find(Review.class, sourceReview.getId());

                        return result.getId();
                    }
            );

            // when
            SESSION_FACTORY.inTransaction(session -> simpleRepo.deleteById(createdId, session));

            Review deletedReview = SESSION_FACTORY.fromTransaction(session -> session.find(Review.class, createdId));

            // then
            assertThat(deletedReview).isNull();
        }
    }
}
