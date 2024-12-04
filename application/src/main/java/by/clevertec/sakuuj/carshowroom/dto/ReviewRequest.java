package by.clevertec.sakuuj.carshowroom.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ReviewRequest(
        String content,
        short rating,
        UUID carId,
        UUID clientId
) {
}
