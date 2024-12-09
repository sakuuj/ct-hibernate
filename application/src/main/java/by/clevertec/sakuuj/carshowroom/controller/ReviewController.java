package by.clevertec.sakuuj.carshowroom.controller;

import by.clevertec.sakuuj.carshowroom.dto.PageResponse;
import by.clevertec.sakuuj.carshowroom.dto.ReviewRequest;
import by.clevertec.sakuuj.carshowroom.dto.ReviewResponse;
import by.clevertec.sakuuj.carshowroom.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public PageResponse<ReviewResponse> getAll(@PageableDefault Pageable pageable) {

        return reviewService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ReviewResponse getById(@PathVariable(name = "id") UUID id) {

        return reviewService.findById(id);
    }

    @PostMapping
    public ReviewResponse create(@Valid @RequestBody ReviewRequest request) {

        return reviewService.create(request);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable(name = "id") UUID id, @Valid @RequestBody ReviewRequest request) {

        reviewService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(name = "id") UUID id) {

        reviewService.deleteById(id);
    }
}
