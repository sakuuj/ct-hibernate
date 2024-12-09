package by.clevertec.sakuuj.carshowroom.service.impl;

import by.clevertec.sakuuj.carshowroom.domain.entity.Car;
import by.clevertec.sakuuj.carshowroom.dto.CarRequest;
import by.clevertec.sakuuj.carshowroom.dto.CarResponse;
import by.clevertec.sakuuj.carshowroom.dto.PageResponse;
import by.clevertec.sakuuj.carshowroom.exception.EntityNotFoundException;
import by.clevertec.sakuuj.carshowroom.mapper.CarMapper;
import by.clevertec.sakuuj.carshowroom.repository.CarRepo;
import by.clevertec.sakuuj.carshowroom.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarMapper carMapper;

    private final CarRepo carRepo;

    @Transactional(readOnly = true)
    @Override
    public PageResponse<CarResponse> findAll(Pageable pageable) {

        Page<Car> found = carRepo.findAll(pageable);
        List<CarResponse> pageContent = found.stream()
                .map(carMapper::toResponse)
                .toList();

        return PageResponse.builder(pageContent, pageable).build();
    }

    @Transactional(readOnly = true)
    @Override
    public CarResponse findById(UUID id) {

        return carRepo.findById(id).map(carMapper::toResponse)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public CarResponse create(CarRequest request) {

        Car carToSave = carMapper.toEntity(request);
        Car savedCar = carRepo.save(carToSave);

        return carMapper.toResponse(savedCar);
    }

    @Override
    public void update(UUID id, CarRequest carRequest) {

        Car carToUpdate = carRepo.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        carMapper.updateEntity(carToUpdate, carRequest);

    }

    @Override
    public void deleteById(UUID id) {

        carRepo.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<CarResponse> findAllSortedByPrice(Pageable pageable) {

        PageRequest pageRequest = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by("price").ascending()
        );

        Page<CarResponse> page = carRepo.findAll(pageRequest).map(carMapper::toResponse);

        return PageResponse.of(page);
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponse<CarResponse> findAllByParams(String make, Short productionYear, Short categoryId, BigDecimal minPriceIncl, BigDecimal maxPriceIncl, Pageable pageable) {

        List<CarResponse> found = carRepo.findAllByParams(
                        make,
                        productionYear,
                        categoryId,
                        minPriceIncl,
                        maxPriceIncl,
                        pageable
                ).stream()
                .map(carMapper::toResponse)
                .toList();

        return PageResponse.builder(found, pageable)
                .build();
    }
}
