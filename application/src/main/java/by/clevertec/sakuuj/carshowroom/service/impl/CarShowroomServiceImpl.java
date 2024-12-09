package by.clevertec.sakuuj.carshowroom.service.impl;

import by.clevertec.sakuuj.carshowroom.domain.entity.CarShowroom;
import by.clevertec.sakuuj.carshowroom.dto.CarShowroomRequest;
import by.clevertec.sakuuj.carshowroom.dto.CarShowroomResponse;
import by.clevertec.sakuuj.carshowroom.dto.PageResponse;
import by.clevertec.sakuuj.carshowroom.exception.EntityNotFoundException;
import by.clevertec.sakuuj.carshowroom.mapper.CarShowroomMapper;
import by.clevertec.sakuuj.carshowroom.repository.CarRepo;
import by.clevertec.sakuuj.carshowroom.repository.CarShowroomRepo;
import by.clevertec.sakuuj.carshowroom.service.CarShowroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CarShowroomServiceImpl implements CarShowroomService {

    private final CarShowroomMapper carShowroomMapper;

    private final CarRepo carRepo;
    private final CarShowroomRepo carShowroomRepo;

    @Override
    public void addCarToCarShowroom(UUID carId, UUID carShowroomId) {

        carRepo.addCarToCarShowroom(carId, carShowroomId);
    }

    @Override
    public PageResponse<CarShowroomResponse> findAll(Pageable pageable) {

        Page<CarShowroomResponse> foundPage = carShowroomRepo.findAll(pageable)
                .map(carShowroomMapper::toResponse);

        return PageResponse.of(foundPage);
    }

    @Override
    public CarShowroomResponse findById(UUID id) {

        return carShowroomRepo.findById(id).map(carShowroomMapper::toResponse)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public CarShowroomResponse create(CarShowroomRequest request) {

        CarShowroom carShowroomToSave = carShowroomMapper.toEntity(request);
        CarShowroom saved = carShowroomRepo.save(carShowroomToSave);

        return carShowroomMapper.toResponse(saved);
    }

    @Override
    public void update(UUID id, CarShowroomRequest carShowroomRequest) {

        CarShowroom carShowroomToUpdate = carShowroomRepo.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        carShowroomMapper.updateEntity(carShowroomToUpdate, carShowroomRequest);
    }

    @Override
    public void deleteById(UUID id) {

        carShowroomRepo.deleteById(id);
    }
}
