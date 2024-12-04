package by.clevertec.sakuuj.carshowroom.testbuilders;

import by.clevertec.sakuuj.carshowroom.domain.entity.Car;
import by.clevertec.sakuuj.carshowroom.domain.entity.CarShowroom;
import by.clevertec.sakuuj.carshowroom.domain.entity.Category;
import by.clevertec.sakuuj.carshowroom.domain.entity.Review;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@With
@AllArgsConstructor
@NoArgsConstructor(staticName = "newInstance")
public class CarTestBuilder {

    private UUID id = UUID.fromString("0059200c-8d93-4eac-9e54-a5fdcd4e5880");

    private String model = "Civic X";

    private String make = "Honda";

    private short productionYear = 2017;

    private BigDecimal price = BigDecimal.valueOf(13500_00, 2);

    private Category category = null;

    private CarShowroom carShowroom = null;

    private List<Review> reviews = new ArrayList<>();

    public Car build() {

        return Car.builder()
                .id(id)
                .model(model)
                .make(make)
                .productionYear(productionYear)
                .price(price)
                .category(category)
                .carShowroom(carShowroom)
                .reviews(reviews)
                .build();
    }
}
