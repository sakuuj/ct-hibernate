package by.clevertec.sakuuj.carshowroom.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CarShowroomRequest(

        @NotBlank
        String name,

        @NotBlank
        String address
) {
}
