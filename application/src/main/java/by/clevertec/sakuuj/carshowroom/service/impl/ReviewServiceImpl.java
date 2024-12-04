package by.clevertec.sakuuj.carshowroom.service.impl;

import by.clevertec.sakuuj.carshowroom.domain.entity.Review;
import by.clevertec.sakuuj.carshowroom.dto.PageResponse;
import by.clevertec.sakuuj.carshowroom.dto.ReviewRequest;
import by.clevertec.sakuuj.carshowroom.dto.ReviewResponse;
import by.clevertec.sakuuj.carshowroom.exception.EntityNotFoundException;
import by.clevertec.sakuuj.carshowroom.mapper.ReviewMapper;
import by.clevertec.sakuuj.carshowroom.repository.ReviewRepo;
import by.clevertec.sakuuj.carshowroom.repository.common.Pageable;
import by.clevertec.sakuuj.carshowroom.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.query.SortDirection;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewMapper reviewMapper;

    private final ReviewRepo reviewRepo;

    private final SessionFactory sessionFactory;

    @Override
    public PageResponse<ReviewResponse> findAll(Pageable pageable, SortDirection sortDirection) {

        List<Review> found = sessionFactory.fromTransaction(session -> reviewRepo.findAll(pageable, sortDirection, session));
        List<ReviewResponse> pageContent = found.stream()
                .map(reviewMapper::toResponse)
                .toList();

        return PageResponse.builder(pageContent, pageable).build();
    }

    @Override
    public Optional<ReviewResponse> findById(UUID id) {

        return sessionFactory.fromTransaction(session -> reviewRepo.findById(id, session))
                .map(reviewMapper::toResponse);
    }

    @Override
    public ReviewResponse create(ReviewRequest request) {

        Review savedReview = sessionFactory.fromTransaction(session -> {

            Review reviewToSave = reviewMapper.toEntity(request, session);
            return reviewRepo.create(reviewToSave, session);
        });

        return reviewMapper.toResponse(savedReview);
    }

    @Override
    public void update(UUID id, ReviewRequest request) {

        sessionFactory.inTransaction(session -> {

            Review reviewToUpdate = reviewRepo.findById(id, session)
                    .orElseThrow(EntityNotFoundException::new);

            reviewMapper.updateEntity(reviewToUpdate, request, session);
        });
    }

    @Override
    public void deleteById(UUID id) {

        sessionFactory.inTransaction(session -> reviewRepo.deleteById(id, session));
    }
}
