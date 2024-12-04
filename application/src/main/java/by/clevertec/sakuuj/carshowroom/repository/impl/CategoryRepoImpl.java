package by.clevertec.sakuuj.carshowroom.repository.impl;

import by.clevertec.sakuuj.carshowroom.domain.entity.Category;
import by.clevertec.sakuuj.carshowroom.repository.CategoryRepo;
import by.clevertec.sakuuj.carshowroom.util.SessionFactoryConfigured;
import org.hibernate.SessionFactory;

public class CategoryRepoImpl extends SimpleRepo<Short, Category> implements CategoryRepo {

    public CategoryRepoImpl() {
        super(Category.class, "id");
    }
}
