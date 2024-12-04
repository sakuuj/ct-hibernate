package by.clevertec.sakuuj.carshowroom.service;

import by.clevertec.sakuuj.carshowroom.dto.CarRequest;
import by.clevertec.sakuuj.carshowroom.dto.CarResponse;

import java.util.UUID;

public interface CarService extends CRUDService<UUID, CarRequest, CarResponse> {
}
