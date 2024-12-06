package by.clevertec.sakuuj.carshowroom.repository;

import by.clevertec.sakuuj.carshowroom.domain.entity.Car;
import by.clevertec.sakuuj.carshowroom.repository.common.Pageable;
import org.hibernate.Session;
import org.hibernate.query.SortDirection;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface CarRepo extends Repo<UUID, Car> {

    void addCarToCarShowroom(UUID carId, UUID carShowroomId, Session session);

    List<Car> findAllSortedByPrice(Pageable pageable, SortDirection sortDirection, Session session);

    List<Car> findAllByParams(
            String make,
            Short productionYear,
            Short categoryId,
            BigDecimal minPriceIncl,
            BigDecimal maxPriceIncl,
            Pageable pageable,
            SortDirection sortDirection,
            Session session
    );

    List<Car> findAllWithCategoryAndCarShowroom(Pageable pageable, SortDirection sortDirection, Session session);
}
