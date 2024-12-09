package by.clevertec.sakuuj.carshowroom.repository.custom;

import by.clevertec.sakuuj.carshowroom.domain.entity.Car;
import by.clevertec.sakuuj.carshowroom.domain.entity.CarShowroom;
import by.clevertec.sakuuj.carshowroom.exception.EntityNotFoundException;
import by.clevertec.sakuuj.carshowroom.repository.common.PageableUtils;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.SelectionQuery;
import org.hibernate.query.SortDirection;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaPredicate;
import org.hibernate.query.criteria.JpaRoot;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class CarRepoCustomImpl implements CarRepoCustom {

    private final EntityManager entityManager;


    @Override
    public void addCarToCarShowroom(UUID carId, UUID carShowroomId) {

        Session session = entityManager.unwrap(Session.class);

        Car car = session.byId(Car.class)
                .loadOptional(carId)
                .orElseThrow(EntityNotFoundException::new);
        CarShowroom carShowroomReference = session.getReference(CarShowroom.class, carShowroomId);

        car.setCarShowroom(carShowroomReference);
    }

    @Override
    public List<Car> findAllByParams(
            String make,
            Short productionYear,
            Short categoryId,
            BigDecimal minPriceIncl,
            BigDecimal maxPriceIncl,
            Pageable pageable
    ) {
        Session session = entityManager.unwrap(Session.class);

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

        SortDirection sortDirection = PageableUtils.getSortDirection(pageable, "id");

        query.orderBy(builder.sort(root.get("id"), sortDirection));

        SelectionQuery<Car> createdQuery = session.createSelectionQuery(query);
        PageableUtils.setPageable(createdQuery, pageable);

        return createdQuery.list();
    }

}
