package by.clevertec.sakuuj.carshowroom.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

@Builder
public record CategoryRequest(

        @NotBlank @Length(min = 3)
        String name
) {
}
