package by.clevertec.sakuuj.carshowroom.repository.custom;

import java.util.UUID;

public interface ClientRepoCustom {

    void addCarToClient(UUID carId, UUID clientId);
}
