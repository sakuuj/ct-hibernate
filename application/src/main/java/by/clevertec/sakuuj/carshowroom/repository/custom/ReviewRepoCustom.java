package by.clevertec.sakuuj.carshowroom.repository.custom;

import by.clevertec.sakuuj.carshowroom.domain.entity.Review;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewRepoCustom {

    List<Review> findAllBySearchTerms(
            Pageable pageable,
            List<String> searchTerms
    );
}
