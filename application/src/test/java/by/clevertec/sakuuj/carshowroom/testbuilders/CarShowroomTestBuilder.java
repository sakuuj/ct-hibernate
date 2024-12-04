package by.clevertec.sakuuj.carshowroom.testbuilders;

import by.clevertec.sakuuj.carshowroom.domain.entity.Car;
import by.clevertec.sakuuj.carshowroom.domain.entity.CarShowroom;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@With
@AllArgsConstructor
@NoArgsConstructor(staticName = "newInstance")
public class CarShowroomTestBuilder {

    private UUID id = UUID.fromString("9980269b-0d89-4e82-abc6-21390aa89e0c");

    private String name = "TEXAS SHOWROOM 9";

    private String address = "Dallas, TX, USA";

    private List<Car> cars = new ArrayList<>();

    public CarShowroom build() {

        return CarShowroom.builder()
                .id(id)
                .name(name)
                .address(address)
                .cars(cars)
                .build();
    }
}
