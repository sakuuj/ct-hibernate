package by.clevertec.sakuuj.carshowroom.controller;

import by.clevertec.sakuuj.carshowroom.dto.CarShowroomRequest;
import by.clevertec.sakuuj.carshowroom.dto.CarShowroomResponse;
import by.clevertec.sakuuj.carshowroom.dto.PageResponse;
import by.clevertec.sakuuj.carshowroom.service.CarShowroomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/car-showrooms")
@RequiredArgsConstructor
public class CarShowroomController {

    private final CarShowroomService carShowroomService;

    @GetMapping
    public PageResponse<CarShowroomResponse> getAll(@PageableDefault Pageable pageable) {

        return carShowroomService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public CarShowroomResponse getById(@PathVariable(name = "id") UUID id) {

        return carShowroomService.findById(id);
    }

    @PostMapping
    public CarShowroomResponse create(@Valid @RequestBody CarShowroomRequest request) {

        return carShowroomService.create(request);
    }

    @PatchMapping("/{id}/add-car")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addCar(@PathVariable(name = "id") UUID id, @RequestBody UUID carId) {

        carShowroomService.addCarToCarShowroom(carId, id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable(name = "id") UUID id, @Valid @RequestBody CarShowroomRequest request) {

        carShowroomService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(name = "id") UUID id) {

        carShowroomService.deleteById(id);
    }
}
