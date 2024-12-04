package by.clevertec.sakuuj.carshowroom.testbuilders;

import by.clevertec.sakuuj.carshowroom.domain.entity.Car;
import by.clevertec.sakuuj.carshowroom.domain.entity.Client;
import by.clevertec.sakuuj.carshowroom.domain.entity.Review;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.UUID;

@With
@AllArgsConstructor
@NoArgsConstructor(staticName = "newInstance")
public class ReviewTestBuilder {

    private UUID id = UUID.fromString("826b0a49-3e35-451b-8155-f141473d4880");

    private String content = "my unbiased review is : it is cool";

    private short rating = 10;

    private Car car = null;

    private Client client = null;

    public Review build() {

        return Review.builder()
                .id(id)
                .content(content)
                .rating(rating)
                .car(car)
                .client(client)
                .build();
    }
}
