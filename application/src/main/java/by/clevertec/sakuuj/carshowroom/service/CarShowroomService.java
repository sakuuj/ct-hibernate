package by.clevertec.sakuuj.carshowroom.service;

import by.clevertec.sakuuj.carshowroom.dto.CarShowroomRequest;
import by.clevertec.sakuuj.carshowroom.dto.CarShowroomResponse;

import java.util.UUID;

public interface CarShowroomService extends CRUDService<UUID, CarShowroomRequest, CarShowroomResponse> {

    void addCarToCarShowroom(UUID carId, UUID carShowroomId);
}
