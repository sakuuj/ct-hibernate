package by.clevertec.sakuuj.carshowroom.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

import java.util.UUID;

@Builder
public record ReviewRequest(

        @NotBlank
        String content,

        @PositiveOrZero
        short rating,

        @NotNull
        UUID carId,

        @NotNull
        UUID clientId
) {
}
