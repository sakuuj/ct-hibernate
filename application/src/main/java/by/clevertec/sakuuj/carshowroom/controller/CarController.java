package by.clevertec.sakuuj.carshowroom.controller;

import by.clevertec.sakuuj.carshowroom.dto.CarRequest;
import by.clevertec.sakuuj.carshowroom.dto.CarResponse;
import by.clevertec.sakuuj.carshowroom.dto.PageResponse;
import by.clevertec.sakuuj.carshowroom.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping
    public PageResponse<CarResponse> getAll(
            @PageableDefault Pageable pageable,
            @RequestParam(value = "make", required = false) String make,
            @RequestParam(value = "productionYear", required = false) Short productionYear,
            @RequestParam(value = "categoryId", required = false) Short categoryId,
            @RequestParam(value = "minPriceIncl", required = false) BigDecimal minPriceIncl,
            @RequestParam(value = "maxPriceIncl", required = false) BigDecimal maxPriceIncl
    ) {

        return carService.findAllByParams(
                make,
                productionYear,
                categoryId,
                minPriceIncl,
                maxPriceIncl,
                pageable
        );
    }

    @GetMapping("/{id}")
    public CarResponse getById(@PathVariable(name = "id") UUID id) {

        return carService.findById(id);
    }

    @PostMapping
    public CarResponse create(@Valid @RequestBody CarRequest carRequest) {

        return carService.create(carRequest);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable(name = "id") UUID id, @Valid @RequestBody CarRequest carRequest) {

        carService.update(id, carRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(name = "id") UUID id) {

        carService.deleteById(id);
    }
}
