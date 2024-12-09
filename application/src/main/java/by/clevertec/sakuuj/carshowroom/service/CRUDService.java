package by.clevertec.sakuuj.carshowroom.service;

import by.clevertec.sakuuj.carshowroom.dto.PageResponse;
import org.springframework.data.domain.Pageable;

public interface CRUDService<ID, Req, Resp> {

    PageResponse<Resp> findAll(Pageable pageable);

    Resp findById(ID id);

    Resp create(Req request);

    void update(ID id, Req req);

    void deleteById(ID id);

}
