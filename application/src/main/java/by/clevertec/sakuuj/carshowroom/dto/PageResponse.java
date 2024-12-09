package by.clevertec.sakuuj.carshowroom.dto;

import lombok.Builder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

        return new PageResponseBuilder<T>().content(content)
                .pageSize(pageable.getPageSize())
                .pageNumber(pageable.getPageNumber());
    }

    public static <T> PageResponse<T> of(Page<T> page) {

        return PageResponse.builder(page.getContent(), page.getPageable())
                .build();
    }
}
