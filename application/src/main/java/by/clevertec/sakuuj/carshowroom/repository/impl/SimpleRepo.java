package by.clevertec.sakuuj.carshowroom.repository.impl;

import by.clevertec.sakuuj.carshowroom.repository.Repo;
import by.clevertec.sakuuj.carshowroom.repository.common.Pageable;
import by.clevertec.sakuuj.carshowroom.repository.common.PageableUtils;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.graph.RootGraph;
import org.hibernate.jpa.SpecHints;
import org.hibernate.query.SelectionQuery;
import org.hibernate.query.SortDirection;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaRoot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public abstract class SimpleRepo<I, T> implements Repo<I, T> {

    private final Class<T> entityClass;
    private final String orderByAttributeName;

    @Override
    public List<T> findAll(Pageable pageable, SortDirection sortDirection, Session session) {

        return findAllWithFetchGraph(pageable, sortDirection, null, session);
    }

    public List<T> findAllWithFetchGraph(Pageable pageable, SortDirection sortDirection, String fetchGraphName, Session session) {

        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();

        JpaCriteriaQuery<T> query = builder.createQuery(entityClass);
        JpaRoot<T> root = query.from(entityClass);

        query.select(root);
        query.orderBy(builder.sort(root.get(orderByAttributeName), sortDirection));

        SelectionQuery<T> createdQuery = session.createSelectionQuery(query);
        PageableUtils.setPageable(createdQuery, pageable);

        if (fetchGraphName != null) {
            RootGraph<?> entityGraph = session.getEntityGraph(fetchGraphName);
            createdQuery.setHint(SpecHints.HINT_SPEC_FETCH_GRAPH, entityGraph);
        }

        return createdQuery.list();
    }

    @Override
    public Optional<T> findById(I id, Session session) {

        return findByIdWithFetchGraph(id, null, session);
    }

    public Optional<T> findByIdWithFetchGraph(I id, String fetchGraphName, Session session) {

        Map<String, Object> properties = null;
        if (fetchGraphName != null) {
            RootGraph<?> entityGraph = session.getEntityGraph(fetchGraphName);
            properties = new HashMap<>();
            properties.put(SpecHints.HINT_SPEC_FETCH_GRAPH, entityGraph);
        }

        return Optional.ofNullable(session.find(entityClass, id, properties));
    }

    @Override
    public T create(T entity, Session session) {

        session.persist(entity);
        return entity;
    }

    @Override
    public void deleteById(I id, Session session) {

        T entityToRemoveRef = session.getReference(entityClass, id);
        session.remove(entityToRemoveRef);
    }
}
