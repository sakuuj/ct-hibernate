package by.clevertec.sakuuj.carshowroom.repository;

import by.clevertec.sakuuj.carshowroom.domain.entity.Review;
import by.clevertec.sakuuj.carshowroom.repository.custom.ReviewRepoCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReviewRepo extends JpaRepository<Review, UUID>, ReviewRepoCustom {
}
