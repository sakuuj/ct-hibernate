package by.clevertec.sakuuj.carshowroom.mapper;

import by.clevertec.sakuuj.carshowroom.domain.entity.Client;
import by.clevertec.sakuuj.carshowroom.dto.ClientRequest;
import by.clevertec.sakuuj.carshowroom.dto.ClientResponse;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface ClientMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cars", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "clientContacts", ignore = true)
    @Mapping(target = "registrationDate", ignore = true)
    Client toEntity(ClientRequest request);

    ClientResponse toResponse(Client client);

    @InheritConfiguration(name = "toEntity")
    void updateEntity(@MappingTarget Client entity, ClientRequest updateInfo);

}
