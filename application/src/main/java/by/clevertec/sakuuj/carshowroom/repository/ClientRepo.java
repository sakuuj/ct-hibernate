package by.clevertec.sakuuj.carshowroom.repository;

import by.clevertec.sakuuj.carshowroom.domain.entity.Client;
import by.clevertec.sakuuj.carshowroom.repository.custom.ClientRepoCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClientRepo extends JpaRepository<Client, UUID>, ClientRepoCustom {

}
