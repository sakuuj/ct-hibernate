package by.clevertec.sakuuj.carshowroom.repository;

import by.clevertec.sakuuj.carshowroom.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Short> {
}
