package by.clevertec.sakuuj.carshowroom.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ReviewResponse(
        UUID id,
        String content,
        short rating,
        CarResponse car,
        ClientResponse client
) {
}
