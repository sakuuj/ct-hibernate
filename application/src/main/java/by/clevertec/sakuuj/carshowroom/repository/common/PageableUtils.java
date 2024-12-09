package by.clevertec.sakuuj.carshowroom.repository.common;

import lombok.experimental.UtilityClass;
import org.hibernate.query.SelectionQuery;
import org.hibernate.query.SortDirection;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@UtilityClass
public class PageableUtils {

    public static <T> void setPageable(SelectionQuery<T> createdQuery, Pageable pageable) {

        createdQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        createdQuery.setMaxResults(pageable.getPageSize());
    }


    public static SortDirection getSortDirection(Pageable pageable, String attributeName) {
        SortDirection sortDirection = SortDirection.ASCENDING;

        Sort.Order idSortOrder = pageable.getSort().getOrderFor(attributeName);
        if (idSortOrder != null && idSortOrder.isDescending()) {

            sortDirection = SortDirection.DESCENDING;
        }
        return sortDirection;
    }
}
