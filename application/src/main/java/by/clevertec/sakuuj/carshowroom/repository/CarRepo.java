package by.clevertec.sakuuj.carshowroom.repository;

import by.clevertec.sakuuj.carshowroom.domain.entity.Car;
import by.clevertec.sakuuj.carshowroom.repository.custom.CarRepoCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CarRepo extends JpaRepository<Car, UUID>, CarRepoCustom {
}
