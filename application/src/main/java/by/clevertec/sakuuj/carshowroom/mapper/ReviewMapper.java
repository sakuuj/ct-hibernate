package by.clevertec.sakuuj.carshowroom.mapper;

import by.clevertec.sakuuj.carshowroom.domain.entity.Review;
import by.clevertec.sakuuj.carshowroom.dto.ReviewRequest;
import by.clevertec.sakuuj.carshowroom.dto.ReviewResponse;
import org.hibernate.Session;
import org.mapstruct.Context;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(uses = ToReferenceMapperImpl.class)
public interface ReviewMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "car", source = "carId")
    @Mapping(target = "client", source = "clientId")
    Review toEntity(ReviewRequest request, @Context Session session);

    ReviewResponse toResponse(Review entity);

    @InheritConfiguration(name = "toEntity")
    void updateEntity(@MappingTarget Review entity, ReviewRequest updateInfo, @Context Session session);
}
