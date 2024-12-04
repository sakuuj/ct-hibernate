package by.clevertec.sakuuj.carshowroom.service.impl;

import by.clevertec.sakuuj.carshowroom.domain.entity.CarShowroom;
import by.clevertec.sakuuj.carshowroom.dto.CarShowroomRequest;
import by.clevertec.sakuuj.carshowroom.dto.CarShowroomResponse;
import by.clevertec.sakuuj.carshowroom.dto.PageResponse;
import by.clevertec.sakuuj.carshowroom.exception.EntityNotFoundException;
import by.clevertec.sakuuj.carshowroom.mapper.CarShowroomMapper;
import by.clevertec.sakuuj.carshowroom.repository.CarRepo;
import by.clevertec.sakuuj.carshowroom.repository.CarShowroomRepo;
import by.clevertec.sakuuj.carshowroom.repository.common.Pageable;
import by.clevertec.sakuuj.carshowroom.service.CarShowroomService;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.query.SortDirection;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class CarShowroomServiceImpl implements CarShowroomService {

    private final CarShowroomMapper carShowroomMapper;

    private final CarRepo carRepo;
    private final CarShowroomRepo carShowroomRepo;

    private final SessionFactory sessionFactory;

    @Override
    public void addCarToCarShowroom(UUID carId, UUID carShowroomId) {

        sessionFactory.inTransaction(
                session -> carRepo.addCarToCarShowroom(carId, carShowroomId, session)
        );
    }

    @Override
    public PageResponse<CarShowroomResponse> findAll(Pageable pageable, SortDirection sortDirection) {

        List<CarShowroom> found = sessionFactory.fromTransaction(session -> carShowroomRepo.findAll(pageable, sortDirection, session));
        List<CarShowroomResponse> pageContent = found.stream()
                .map(carShowroomMapper::toResponse)
                .toList();

        return PageResponse.builder(pageContent, pageable).build();
    }

    @Override
    public Optional<CarShowroomResponse> findById(UUID id) {

        return sessionFactory.fromTransaction(session -> carShowroomRepo.findById(id, session))
                .map(carShowroomMapper::toResponse);
    }

    @Override
    public CarShowroomResponse create(CarShowroomRequest request) {

        CarShowroom savedCarShowroom = sessionFactory.fromTransaction(session -> {

            CarShowroom carShowroomToSave = carShowroomMapper.toEntity(request);
            return carShowroomRepo.create(carShowroomToSave, session);
        });

        return carShowroomMapper.toResponse(savedCarShowroom);
    }

    @Override
    public void update(UUID id, CarShowroomRequest carShowroomRequest) {

        sessionFactory.inTransaction(session -> {

            CarShowroom carShowroomToUpdate = carShowroomRepo.findById(id, session)
                    .orElseThrow(EntityNotFoundException::new);

            carShowroomMapper.updateEntity(carShowroomToUpdate, carShowroomRequest);
        });
    }

    @Override
    public void deleteById(UUID id) {

        sessionFactory.inTransaction(session -> carShowroomRepo.deleteById(id, session));
    }
}
