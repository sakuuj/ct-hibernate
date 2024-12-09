package by.clevertec.sakuuj.carshowroom.mapper;

import by.clevertec.sakuuj.carshowroom.domain.entity.CarShowroom;
import by.clevertec.sakuuj.carshowroom.dto.CarShowroomRequest;
import by.clevertec.sakuuj.carshowroom.dto.CarShowroomResponse;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface CarShowroomMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cars", ignore = true)
    CarShowroom toEntity(CarShowroomRequest request);

    CarShowroomResponse toResponse(CarShowroom entity);

    @InheritConfiguration(name = "toEntity")
    void updateEntity(@MappingTarget CarShowroom entity, CarShowroomRequest updateInfo);
}
