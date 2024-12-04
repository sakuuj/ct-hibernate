package by.clevertec.sakuuj.carshowroom.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ClientRequest(
        String name
) {
}
