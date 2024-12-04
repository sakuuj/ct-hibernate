package by.clevertec.sakuuj.carshowroom.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record CarResponse(
        UUID id,
        String model,
        String make,
        short productionYear,
        BigDecimal price,
        CategoryResponse category,
        CarShowroomResponse carShowroom
) {
}
