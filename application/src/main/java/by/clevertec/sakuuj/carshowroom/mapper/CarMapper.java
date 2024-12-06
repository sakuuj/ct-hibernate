package by.clevertec.sakuuj.carshowroom.mapper;

import by.clevertec.sakuuj.carshowroom.domain.entity.Car;
import by.clevertec.sakuuj.carshowroom.dto.CarRequest;
import by.clevertec.sakuuj.carshowroom.dto.CarResponse;
import org.hibernate.Session;
import org.mapstruct.Context;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(uses = {
        CategoryMapper.class,
        CarShowroomMapper.class,
        ToReferenceMapperImpl.class,
})
public interface CarMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "carShowroom", ignore = true)
    @Mapping(target = "category", source = "request.categoryId")
    Car toEntity(CarRequest request, @Context Session session);

    CarResponse toResponse(Car entity);

    @InheritConfiguration(name = "toEntity")
    void updateEntity(@MappingTarget Car entity, CarRequest updateInfo, @Context Session session);
}
