package by.clevertec.sakuuj.carshowroom.repository;

import by.clevertec.sakuuj.carshowroom.repository.common.Pageable;
import org.hibernate.Session;
import org.hibernate.query.SortDirection;

import java.util.List;
import java.util.Optional;

public interface Repo<I, T> {

    List<T> findAll(Pageable pageable, SortDirection sortDirection, Session session);

    Optional<T> findById(I id, Session session);

    T create(T entity, Session session);

    void deleteById(I id, Session session);
}
