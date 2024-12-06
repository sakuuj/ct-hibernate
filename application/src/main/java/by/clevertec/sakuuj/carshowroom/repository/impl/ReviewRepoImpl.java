package by.clevertec.sakuuj.carshowroom.repository.impl;

import by.clevertec.sakuuj.carshowroom.domain.entity.Review;
import by.clevertec.sakuuj.carshowroom.repository.ReviewRepo;
import by.clevertec.sakuuj.carshowroom.repository.common.Pageable;
import by.clevertec.sakuuj.carshowroom.repository.common.PageableUtils;
import org.hibernate.Session;
import org.hibernate.query.SelectionQuery;
import org.hibernate.query.SortDirection;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaPredicate;
import org.hibernate.query.criteria.JpaRoot;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ReviewRepoImpl extends SimpleRepo<UUID, Review> implements ReviewRepo {

    public ReviewRepoImpl() {
        super(Review.class, "id");
    }

    @Override
    public List<Review> findAllBySearchTerms(
            Pageable pageable,
            SortDirection sortDirection,
            List<String> searchTerms,
            Session session
    ) {
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

        query.orderBy(builder.sort(root.get("id"), sortDirection));
        SelectionQuery<Review> createdQuery = session.createSelectionQuery(query);

        PageableUtils.setPageable(createdQuery, pageable);

        return createdQuery.list();
    }
}
