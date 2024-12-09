package by.clevertec.sakuuj.carshowroom.service.impl;

import by.clevertec.sakuuj.carshowroom.domain.entity.Review;
import by.clevertec.sakuuj.carshowroom.dto.PageResponse;
import by.clevertec.sakuuj.carshowroom.dto.ReviewRequest;
import by.clevertec.sakuuj.carshowroom.dto.ReviewResponse;
import by.clevertec.sakuuj.carshowroom.exception.EntityNotFoundException;
import by.clevertec.sakuuj.carshowroom.mapper.ReviewMapper;
import by.clevertec.sakuuj.carshowroom.repository.ReviewRepo;
import by.clevertec.sakuuj.carshowroom.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewMapper reviewMapper;

    private final ReviewRepo reviewRepo;

    @Override
    public PageResponse<ReviewResponse> findAll(Pageable pageable) {

        Page<ReviewResponse> foundPage = reviewRepo.findAll(pageable)
                .map(reviewMapper::toResponse);

        return PageResponse.of(foundPage);
    }

    @Override
    public ReviewResponse findById(UUID id) {

        return reviewRepo.findById(id).map(reviewMapper::toResponse)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public ReviewResponse create(ReviewRequest request) {

        Review reviewToSave = reviewMapper.toEntity(request);
        Review saved = reviewRepo.save(reviewToSave);


        return reviewMapper.toResponse(saved);
    }

    @Override
    public void update(UUID id, ReviewRequest request) {

        Review reviewToUpdate = reviewRepo.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        reviewMapper.updateEntity(reviewToUpdate, request);
    }

    @Override
    public void deleteById(UUID id) {

        reviewRepo.deleteById(id);
    }

    @Override
    public PageResponse<ReviewResponse> findAllBySearchTerms(Pageable pageable, List<String> searchTerms) {
        List<ReviewResponse> content = reviewRepo.findAllBySearchTerms(pageable, searchTerms)
                .stream()
                .map(reviewMapper::toResponse)
                .toList();

        return PageResponse.builder(content, pageable).build();
    }
}
