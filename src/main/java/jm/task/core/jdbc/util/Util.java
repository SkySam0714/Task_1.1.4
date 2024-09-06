package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public final class Util {
    private static final Logger LOGGER = LogManager.getLogger("Util");
    private static Connection connection = null;
    private static SessionFactory sessionFactory = null;
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123321";


    private Util(){}

    private static void connectToDB() {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement statement = connection.createStatement();
            statement.execute("CREATE SCHEMA IF NOT EXISTS user_storage");
            Util.connection = connection;
            LOGGER.info("connection established");
        } catch (SQLException e) {
            LOGGER.error("Connection Error", e);
            throw new RuntimeException(e);
        }
    }

    public static SessionFactory getSessionFactory(){
        if(sessionFactory != null) return sessionFactory;
        if(connection == null) connectToDB();


        Properties properties = new Properties();
        properties.put(Environment.DRIVER, "org.postgresql.Driver");
        properties.put(Environment.URL, URL);
        properties.put(Environment.USER, USER);
        properties.put(Environment.PASS, PASSWORD);

        Configuration configuration = new Configuration();
        configuration.setProperties(properties);
        configuration.addAnnotatedClass(User.class);

        sessionFactory = configuration.buildSessionFactory();
        return sessionFactory;

    }

    public static Connection getConnection(){
        if (connection == null) connectToDB();
        return connection;
    }

}
