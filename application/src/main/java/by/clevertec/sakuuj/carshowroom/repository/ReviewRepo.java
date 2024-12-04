package by.clevertec.sakuuj.carshowroom.repository;

import by.clevertec.sakuuj.carshowroom.domain.entity.Review;
import by.clevertec.sakuuj.carshowroom.repository.common.Pageable;
import org.hibernate.Session;
import org.hibernate.query.SortDirection;

import java.util.List;
import java.util.UUID;

public interface ReviewRepo extends Repo<UUID, Review> {

    List<Review> findAllBySearchTerms(
            Pageable pageable,
            SortDirection sortDirection,
            List<String> searchTerms,
            Session session
    );
}
