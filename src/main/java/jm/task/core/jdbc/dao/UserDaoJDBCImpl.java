package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private static final Logger LOGGER = LogManager.getLogger("Dao");

    private static final String CREATE_TABLE = """
                                            CREATE TABLE IF NOT EXISTS user_storage.users(
                                            id SERIAL8 PRIMARY KEY,
                                            name varchar(30),
                                            last_name varchar(30),
                                            age int2);
                                            """;

    private static final String DELETE_TABLE = "DROP TABLE IF EXISTS user_storage.users;";

    private static final String ADD_USER = """
                                            INSERT INTO user_storage.users(name, last_name, age)
                                            VALUES (?, ?, ?);
                                            """;

    private static final String CLEAR_TABLE = "TRUNCATE user_storage.users RESTART IDENTITY;";

    private static final String GET_ALL_USERS = "SELECT * FROM user_storage.users;";

    private static final String DELETE_USER_BY_ID = "DELETE FROM user_storage.users WHERE id = ?;";

    public void createUsersTable() {
        try(Statement statement = Util.getConnection().createStatement()) {
            statement.execute(CREATE_TABLE);
            LOGGER.info("Table created");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try(Statement statement = Util.getConnection().createStatement()) {
            statement.execute(DELETE_TABLE);
            LOGGER.info("Table deleted");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try(PreparedStatement preparedStatement = Util.getConnection().prepareStatement(ADD_USER)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();
            LOGGER.info("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try(PreparedStatement preparedStatement = Util.getConnection().prepareStatement(DELETE_USER_BY_ID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            LOGGER.info("User with id: " + id + " - was added to database");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try(Statement statement = Util.getConnection().createStatement()) {
            ResultSet allUsers = statement.executeQuery(GET_ALL_USERS);
            while (allUsers.next()){
                User user = new User(allUsers.getString("name"), allUsers.getString("last_name"), allUsers.getByte("age"));
                user.setId(allUsers.getLong("id"));
                users.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        try(Statement statement = Util.getConnection().createStatement()) {
            statement.execute(CLEAR_TABLE);
            LOGGER.info("Table cleared");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
