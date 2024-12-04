package by.clevertec.sakuuj.carshowroom.testbuilders;

import by.clevertec.sakuuj.carshowroom.domain.entity.Car;
import by.clevertec.sakuuj.carshowroom.domain.entity.Category;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.ArrayList;
import java.util.List;

@With
@AllArgsConstructor
@NoArgsConstructor(staticName = "newInstance")
public class CategoryTestBuilder {

    private short id = 1;

    private String name = "Седан";

    private List<Car> cars = new ArrayList<>();

    public Category build() {

        return Category.builder()
                .id(id)
                .name(name)
                .cars(cars)
                .build();
    }
}
