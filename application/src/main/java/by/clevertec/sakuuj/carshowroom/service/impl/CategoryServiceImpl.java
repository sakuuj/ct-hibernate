package by.clevertec.sakuuj.carshowroom.service.impl;

import by.clevertec.sakuuj.carshowroom.domain.entity.Category;
import by.clevertec.sakuuj.carshowroom.dto.CategoryRequest;
import by.clevertec.sakuuj.carshowroom.dto.CategoryResponse;
import by.clevertec.sakuuj.carshowroom.dto.PageResponse;
import by.clevertec.sakuuj.carshowroom.exception.EntityNotFoundException;
import by.clevertec.sakuuj.carshowroom.mapper.CategoryMapper;
import by.clevertec.sakuuj.carshowroom.repository.CategoryRepo;
import by.clevertec.sakuuj.carshowroom.repository.common.Pageable;
import by.clevertec.sakuuj.carshowroom.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.query.SortDirection;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    private final CategoryRepo categoryRepo;

    private final SessionFactory sessionFactory;


    @Override
    public PageResponse<CategoryResponse> findAll(Pageable pageable, SortDirection sortDirection) {

        List<Category> found = sessionFactory.fromTransaction(session -> categoryRepo.findAll(pageable, sortDirection, session));
        List<CategoryResponse> pageContent = found.stream()
                .map(categoryMapper::toResponse)
                .toList();

        return PageResponse.builder(pageContent, pageable).build();
    }

    @Override
    public Optional<CategoryResponse> findById(Short id) {

        return sessionFactory.fromTransaction(session -> categoryRepo.findById(id, session))
                .map(categoryMapper::toResponse);
    }

    @Override
    public CategoryResponse create(CategoryRequest request) {

        Category savedCategory = sessionFactory.fromTransaction(session -> {

            Category categoryToSave = categoryMapper.toEntity(request);
            return categoryRepo.create(categoryToSave, session);
        });

        return categoryMapper.toResponse(savedCategory);
    }

    @Override
    public void update(Short id, CategoryRequest request) {

        sessionFactory.inTransaction(session -> {

            Category categoryToUpdate = categoryRepo.findById(id, session)
                    .orElseThrow(EntityNotFoundException::new);

            categoryMapper.updateEntity(categoryToUpdate, request);
        });
    }

    @Override
    public void deleteById(Short id) {

        sessionFactory.inTransaction(session -> categoryRepo.deleteById(id, session));
    }
}
