package by.clevertec.sakuuj.carshowroom.repository;

import by.clevertec.sakuuj.carshowroom.domain.entity.CarShowroom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CarShowroomRepo extends JpaRepository<CarShowroom, UUID> {

}
