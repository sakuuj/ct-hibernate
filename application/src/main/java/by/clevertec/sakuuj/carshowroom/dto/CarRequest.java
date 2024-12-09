package by.clevertec.sakuuj.carshowroom.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CarRequest(

        @NotBlank
        String model,

        @NotBlank
        String make,

        @Min(1900)
        short productionYear,

        @Positive
        BigDecimal price,

        short categoryId
) {
}
