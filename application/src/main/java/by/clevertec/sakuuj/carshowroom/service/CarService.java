package by.clevertec.sakuuj.carshowroom.service;

import by.clevertec.sakuuj.carshowroom.dto.CarRequest;
import by.clevertec.sakuuj.carshowroom.dto.CarResponse;
import by.clevertec.sakuuj.carshowroom.dto.PageResponse;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.UUID;

public interface CarService extends CRUDService<UUID, CarRequest, CarResponse> {
    PageResponse<CarResponse> findAllSortedByPrice(Pageable pageable);

    PageResponse<CarResponse> findAllByParams(
            String make,
            Short productionYear,
            Short categoryId,
            BigDecimal minPriceIncl,
            BigDecimal maxPriceIncl,
            Pageable pageable
    );
}
