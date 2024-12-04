package by.clevertec.sakuuj.carshowroom.service;

import by.clevertec.sakuuj.carshowroom.dto.ReviewRequest;
import by.clevertec.sakuuj.carshowroom.dto.ReviewResponse;

import java.util.UUID;

public interface ReviewService extends CRUDService<UUID, ReviewRequest, ReviewResponse> {

}
