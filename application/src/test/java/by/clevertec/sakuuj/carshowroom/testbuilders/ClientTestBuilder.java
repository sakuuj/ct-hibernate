package by.clevertec.sakuuj.carshowroom.testbuilders;

import by.clevertec.sakuuj.carshowroom.domain.embeddable.ClientContact;
import by.clevertec.sakuuj.carshowroom.domain.entity.Car;
import by.clevertec.sakuuj.carshowroom.domain.entity.Client;
import by.clevertec.sakuuj.carshowroom.domain.entity.Review;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@With
@AllArgsConstructor
@NoArgsConstructor(staticName = "newInstance")
public class ClientTestBuilder {

    private UUID id = UUID.fromString("ef8e42d8-b911-4de2-b893-3be224fc49fb");

    private String name = "Andrew Johnson";

    private LocalDateTime registrationDate = LocalDateTime.parse("2021-11-30T11:12:13.111");

    private List<ClientContact> clientContacts = new ArrayList<>();

    private List<Review> reviews = new ArrayList<>();

    private Set<Car> cars = new HashSet<>();

    public Client build() {

        return Client.builder()
                .id(id)
                .name(name)
                .registrationDate(registrationDate)
                .clientContacts(clientContacts)
                .reviews(reviews)
                .cars(cars)
                .build();
    }
}
