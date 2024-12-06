package by.clevertec.sakuuj.carshowroom.dto;

import lombok.Builder;

@Builder
public record CategoryResponse(
        short id,
        String name
) {
}
