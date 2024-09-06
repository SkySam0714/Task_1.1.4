package jm.task.core.jdbc.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class Util {
    private static final Logger LOGGER = LogManager.getLogger("Util");
    private static Connection connection = null;
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

    public static Connection getConnection(){
        if (connection == null) connectToDB();
        return connection;
    }

}
