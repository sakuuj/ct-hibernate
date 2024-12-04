package by.clevertec.sakuuj.carshowroom.service;

import by.clevertec.sakuuj.carshowroom.dto.ClientRequest;
import by.clevertec.sakuuj.carshowroom.dto.ClientResponse;

import java.util.UUID;

public interface ClientService extends CRUDService<UUID, ClientRequest, ClientResponse> {

    void addCarToClient(UUID carId, UUID clientId);
}
