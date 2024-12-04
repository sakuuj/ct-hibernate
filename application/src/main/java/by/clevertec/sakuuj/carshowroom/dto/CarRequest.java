package by.clevertec.sakuuj.carshowroom.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CarRequest(
    String model,
    String make,
    short productionYear,
    BigDecimal price,
    short categoryId
) {
}
