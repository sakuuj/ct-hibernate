package by.clevertec.sakuuj.carshowroom.service.impl;

import by.clevertec.sakuuj.carshowroom.domain.entity.Category;
import by.clevertec.sakuuj.carshowroom.dto.CategoryRequest;
import by.clevertec.sakuuj.carshowroom.dto.CategoryResponse;
import by.clevertec.sakuuj.carshowroom.dto.PageResponse;
import by.clevertec.sakuuj.carshowroom.exception.EntityNotFoundException;
import by.clevertec.sakuuj.carshowroom.mapper.CategoryMapper;
import by.clevertec.sakuuj.carshowroom.repository.CategoryRepo;
import by.clevertec.sakuuj.carshowroom.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    private final CategoryRepo categoryRepo;


    @Override
    public PageResponse<CategoryResponse> findAll(Pageable pageable) {

        Page<CategoryResponse> foundPage = categoryRepo.findAll(pageable)
                .map(categoryMapper::toResponse);

        return PageResponse.of(foundPage);
    }

    @Override
    public CategoryResponse findById(Short id) {

        return categoryRepo.findById(id).map(categoryMapper::toResponse)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public CategoryResponse create(CategoryRequest request) {

        Category categoryToSave = categoryMapper.toEntity(request);
        Category saved = categoryRepo.save(categoryToSave);

        return categoryMapper.toResponse(saved);
    }

    @Override
    public void update(Short id, CategoryRequest request) {

        Category categoryToUpdate = categoryRepo.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        categoryMapper.updateEntity(categoryToUpdate, request);
    }

    @Override
    public void deleteById(Short id) {

        categoryRepo.deleteById(id);
    }
}
