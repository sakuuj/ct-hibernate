package by.clevertec.sakuuj.carshowroom.dto;

import lombok.Builder;

@Builder
public record CarShowroomRequest(
        String name,
        String address
) {
}
