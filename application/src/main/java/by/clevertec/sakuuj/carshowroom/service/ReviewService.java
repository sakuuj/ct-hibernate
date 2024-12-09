package by.clevertec.sakuuj.carshowroom.service;

import by.clevertec.sakuuj.carshowroom.dto.PageResponse;
import by.clevertec.sakuuj.carshowroom.dto.ReviewRequest;
import by.clevertec.sakuuj.carshowroom.dto.ReviewResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ReviewService extends CRUDService<UUID, ReviewRequest, ReviewResponse> {

    PageResponse<ReviewResponse> findAllBySearchTerms(
            Pageable pageable,
            List<String> searchTerms
    );
}
