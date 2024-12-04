package by.clevertec.sakuuj.carshowroom.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record CarShowroomResponse(
        UUID id,
        String name,
        String address
) {
}
