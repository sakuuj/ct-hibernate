package by.clevertec.sakuuj.carshowroom.repository.custom;

import by.clevertec.sakuuj.carshowroom.domain.entity.Review;
import by.clevertec.sakuuj.carshowroom.repository.common.PageableUtils;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.SelectionQuery;
import org.hibernate.query.SortDirection;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaPredicate;
import org.hibernate.query.criteria.JpaRoot;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepoCustomImpl implements ReviewRepoCustom {

    private final EntityManager entityManager;

    @Override
    public List<Review> findAllBySearchTerms(
            Pageable pageable,
            List<String> searchTerms
    ) {
        Session session = entityManager.unwrap(Session.class);

        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();

        JpaCriteriaQuery<Review> query = builder.createQuery(Review.class);
        JpaRoot<Review> root = query.from(Review.class);

        if (!searchTerms.isEmpty()) {

            List<JpaPredicate> predicates = new ArrayList<>();
            searchTerms.forEach(searchTerm -> {

                JpaPredicate jpaPredicate = builder.like(root.get("content"), "%" + searchTerm + "%");
                predicates.add(jpaPredicate);
            });

            query.where(predicates.toArray(JpaPredicate[]::new));
        }

        SortDirection sortDirection = PageableUtils.getSortDirection(pageable, "id");
        query.orderBy(builder.sort(root.get("id"), sortDirection));
        SelectionQuery<Review> createdQuery = session.createSelectionQuery(query);

        PageableUtils.setPageable(createdQuery, pageable);

        return createdQuery.list();
    }
}
