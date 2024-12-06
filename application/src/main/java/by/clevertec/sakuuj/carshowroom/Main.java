package by.clevertec.sakuuj.carshowroom;

import by.clevertec.sakuuj.carshowroom.util.SessionFactoryConfigured;
import org.hibernate.SessionFactory;

public class Main {

    public static void main(String[] args) {

        SessionFactory sessionFactory = SessionFactoryConfigured.getInstance();
    }
}
