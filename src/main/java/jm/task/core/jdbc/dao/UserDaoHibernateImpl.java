package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private static final Logger LOGGER = LogManager.getLogger("Dao");
    private static final String CREATE_TABLE = """
                                            CREATE TABLE IF NOT EXISTS user_storage.users(
                                            id SERIAL8 PRIMARY KEY,
                                            name varchar(30),
                                            last_name varchar(30),
                                            age int2);
                                            """;

    public UserDaoHibernateImpl() {
    }


    @Override
    public void createUsersTable() {
        try(Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery(CREATE_TABLE).executeUpdate();
            session.getTransaction().commit();
            LOGGER.info("Table created");
        }

    }

    @Override
    public void dropUsersTable() {
        try(Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS user_storage.users").executeUpdate();
            session.getTransaction().commit();
            LOGGER.info("Table deleted");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(new User(name,lastName,age));
            session.getTransaction().commit();
            LOGGER.info("User с именем – " + name + " добавлен в базу данных");
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            User user = new User(); user.setId(id);
            session.delete(user);
            session.getTransaction().commit();
            LOGGER.info("User with id: " + id + " - was removed from database");
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().openSession()) {
            return session.createQuery("SELECT user FROM User user", User.class).getResultList();
        }
    }

    @Override
    public void cleanUsersTable() {
        try(Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createSQLQuery("TRUNCATE user_storage.users RESTART IDENTITY").executeUpdate();
            session.getTransaction().commit();
            LOGGER.info("Table cleared");
        }

    }
}
