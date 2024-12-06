package by.clevertec.sakuuj.carshowroom.dto;

import lombok.Builder;

@Builder
public record CategoryRequest(
        String name
) {
}
