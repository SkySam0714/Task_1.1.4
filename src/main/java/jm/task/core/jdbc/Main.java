package jm.task.core.jdbc;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.saveUser("Tom", "Ford", (byte) 44);
        userService.saveUser("Bob", "Ford", (byte) 32);
        userService.saveUser("Mike", "Frost", (byte) 21);
        userService.saveUser("Lily", "North", (byte) 18);
        List<User> users = userService.getAllUsers();
        users.forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();

    }
}
