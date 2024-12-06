package by.clevertec.sakuuj.carshowroom.service;

import by.clevertec.sakuuj.carshowroom.dto.PageResponse;
import by.clevertec.sakuuj.carshowroom.repository.common.Pageable;
import org.hibernate.query.SortDirection;

import java.util.Optional;

public interface CRUDService<ID, Req, Resp> {

    PageResponse<Resp> findAll(Pageable pageable, SortDirection sortDirection);

    Optional<Resp> findById(ID id);

    Resp create(Req request);

    void update(ID id, Req req);

    void deleteById(ID id);

}
