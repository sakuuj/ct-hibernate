package by.clevertec.sakuuj.carshowroom.service.impl;

import by.clevertec.sakuuj.carshowroom.domain.entity.Car;
import by.clevertec.sakuuj.carshowroom.dto.CarRequest;
import by.clevertec.sakuuj.carshowroom.dto.CarResponse;
import by.clevertec.sakuuj.carshowroom.dto.PageResponse;
import by.clevertec.sakuuj.carshowroom.exception.EntityNotFoundException;
import by.clevertec.sakuuj.carshowroom.mapper.CarMapper;
import by.clevertec.sakuuj.carshowroom.repository.CarRepo;
import by.clevertec.sakuuj.carshowroom.repository.common.Pageable;
import by.clevertec.sakuuj.carshowroom.service.CarService;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.query.SortDirection;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarMapper carMapper;

    private final CarRepo carRepo;

    private final SessionFactory sessionFactory;

    @Override
    public PageResponse<CarResponse> findAll(Pageable pageable, SortDirection sortDirection) {

        List<Car> found = sessionFactory.fromTransaction(session -> carRepo.findAll(pageable, sortDirection, session));
        List<CarResponse> pageContent = found.stream()
                .map(carMapper::toResponse)
                .toList();

        return PageResponse.builder(pageContent, pageable).build();
    }

    @Override
    public Optional<CarResponse> findById(UUID id) {

        return sessionFactory.fromTransaction(session -> carRepo.findById(id, session))
                .map(carMapper::toResponse);
    }

    @Override
    public CarResponse create(CarRequest request) {

        Car savedCar = sessionFactory.fromTransaction(session -> {

            Car carToSave = carMapper.toEntity(request, session);
            return carRepo.create(carToSave, session);
        });

        return carMapper.toResponse(savedCar);
    }

    @Override
    public void update(UUID id, CarRequest carRequest) {

        sessionFactory.inTransaction(session -> {

            Car carToUpdate = carRepo.findById(id, session)
                    .orElseThrow(EntityNotFoundException::new);

            carMapper.updateEntity(carToUpdate, carRequest, session);
        });
    }

    @Override
    public void deleteById(UUID id) {

        sessionFactory.inTransaction(session -> carRepo.deleteById(id, session));
    }
}
