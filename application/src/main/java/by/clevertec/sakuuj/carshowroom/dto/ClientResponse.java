package by.clevertec.sakuuj.carshowroom.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record ClientResponse(
        UUID id,
        String name,
        LocalDateTime registrationDate
) {
}
