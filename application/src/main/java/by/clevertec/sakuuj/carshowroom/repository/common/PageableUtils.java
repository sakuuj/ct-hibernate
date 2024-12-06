package by.clevertec.sakuuj.carshowroom.repository.common;

import lombok.experimental.UtilityClass;
import org.hibernate.query.SelectionQuery;

@UtilityClass
public class PageableUtils {

    public static <T> void setPageable(SelectionQuery<T> createdQuery, Pageable pageable) {

        createdQuery.setFirstResult(pageable.number() * pageable.size());
        createdQuery.setMaxResults(pageable.size());
    }
}
