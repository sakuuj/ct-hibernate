package by.clevertec.sakuuj.carshowroom.repository.impl;

import by.clevertec.sakuuj.carshowroom.domain.entity.Car;
import by.clevertec.sakuuj.carshowroom.domain.entity.CarShowroom;
import by.clevertec.sakuuj.carshowroom.exception.EntityNotFoundException;
import by.clevertec.sakuuj.carshowroom.repository.CarRepo;
import by.clevertec.sakuuj.carshowroom.repository.common.Pageable;
import by.clevertec.sakuuj.carshowroom.repository.common.PageableUtils;
import org.hibernate.Session;
import org.hibernate.query.SelectionQuery;
import org.hibernate.query.SortDirection;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaPredicate;
import org.hibernate.query.criteria.JpaRoot;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CarRepoImpl extends SimpleRepo<UUID, Car> implements CarRepo {

    public CarRepoImpl() {
        super(Car.class, "id");
    }

    @Override
    public void addCarToCarShowroom(UUID carId, UUID carShowroomId, Session session) {

        Car car = findById(carId, session).orElseThrow(EntityNotFoundException::new);
        CarShowroom carShowroomReference = session.getReference(CarShowroom.class, carShowroomId);

        car.setCarShowroom(carShowroomReference);
    }

    @Override
    public List<Car> findAllSortedByPrice(Pageable pageable, SortDirection sortDirection, Session session) {

        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();

        JpaCriteriaQuery<Car> query = builder.createQuery(Car.class);

        JpaRoot<Car> root = query.from(Car.class);

        query.select(root);
        query.orderBy(builder.sort(root.get("price"), sortDirection));

        SelectionQuery<Car> createdQuery = session.createSelectionQuery(query);

        PageableUtils.setPageable(createdQuery, pageable);

        return createdQuery.list();
    }

    @Override
    public List<Car> findAllByParams(
            String make,
            Short productionYear,
            Short categoryId,
            BigDecimal minPriceIncl,
            BigDecimal maxPriceIncl,
            Pageable pageable,
            SortDirection sortDirection,
            Session session
    ) {
        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();

        JpaCriteriaQuery<Car> query = builder.createQuery(Car.class);

        JpaRoot<Car> root = query.from(Car.class);

        List<JpaPredicate> predicates = new ArrayList<>();

        if (make != null) {
            predicates.add(builder.equal(root.get("make"), make));
        }

        if (productionYear != null) {
            predicates.add(builder.equal(root.get("productionYear"), productionYear));
        }

        if (categoryId != null) {
            predicates.add(builder.equal(root.get("category").get("id"), categoryId));
        }

        if (minPriceIncl != null) {
            predicates.add(builder.ge(root.get("price"), minPriceIncl));
        }

        if (maxPriceIncl != null) {
            predicates.add(builder.le(root.get("price"), maxPriceIncl));
        }

        if (!predicates.isEmpty()) {
            query.where(predicates.toArray(JpaPredicate[]::new));
        }

        query.select(root);
        query.orderBy(builder.sort(root.get("id"), sortDirection));

        SelectionQuery<Car> createdQuery = session.createSelectionQuery(query);
        PageableUtils.setPageable(createdQuery, pageable);

        return createdQuery.list();
    }

    @Override
    public List<Car> findAllWithCategoryAndCarShowroom(Pageable pageable, SortDirection sortDirection, Session session) {

        return findAllWithFetchGraph(pageable, sortDirection,  "Car.withCategory-withCarShowroom", session);
    }
}
