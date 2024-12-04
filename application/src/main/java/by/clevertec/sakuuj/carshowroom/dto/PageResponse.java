package by.clevertec.sakuuj.carshowroom.dto;

import by.clevertec.sakuuj.carshowroom.repository.common.Pageable;
import lombok.Builder;

import java.util.List;

@Builder
public record PageResponse<T>(
        List<T> content,
        int pageNumber,
        int pageSize
) {

    public static <T> PageResponseBuilder<T> builder(List<T> content) {

        return new PageResponseBuilder<T>().content(content);
    }

    public static <T> PageResponseBuilder<T> builder(List<T> content, Pageable pageable) {

        return new PageResponseBuilder<T>().content(content);
    }
}
