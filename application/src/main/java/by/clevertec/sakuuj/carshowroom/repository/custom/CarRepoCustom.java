package by.clevertec.sakuuj.carshowroom.repository.custom;

import by.clevertec.sakuuj.carshowroom.domain.entity.Car;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface CarRepoCustom {

    void addCarToCarShowroom(UUID carId, UUID carShowroomId);

    List<Car> findAllByParams(
            String make,
            Short productionYear,
            Short categoryId,
            BigDecimal minPriceIncl,
            BigDecimal maxPriceIncl,
            Pageable pageable
    );
}
