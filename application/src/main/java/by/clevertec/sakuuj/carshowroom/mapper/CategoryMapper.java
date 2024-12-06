package by.clevertec.sakuuj.carshowroom.mapper;

import by.clevertec.sakuuj.carshowroom.domain.entity.Category;
import by.clevertec.sakuuj.carshowroom.dto.CategoryRequest;
import by.clevertec.sakuuj.carshowroom.dto.CategoryResponse;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface CategoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cars", ignore = true)
    Category toEntity(CategoryRequest request);

    CategoryResponse toResponse(Category entity);

    @InheritConfiguration(name = "toEntity")
    void updateEntity(@MappingTarget Category entity, CategoryRequest updateInfo);
}
