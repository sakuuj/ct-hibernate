package by.clevertec.sakuuj.carshowroom.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record ClientRequest(

        @NotBlank
        String name
) {
}
