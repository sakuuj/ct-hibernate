package by.clevertec.sakuuj.carshowroom.repository.common;

import lombok.Builder;

@Builder
public record Pageable(int size, int number) {
}
