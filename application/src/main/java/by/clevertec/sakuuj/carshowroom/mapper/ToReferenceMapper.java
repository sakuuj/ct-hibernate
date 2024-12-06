package by.clevertec.sakuuj.carshowroom.mapper;

import by.clevertec.sakuuj.carshowroom.domain.entity.Category;
import org.hibernate.Session;

public interface ToReferenceMapper {

    Category toCategory(short categoryId, Session session);
}
