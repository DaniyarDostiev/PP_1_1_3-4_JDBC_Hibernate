package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    private static final UserService userService = new UserServiceImpl();

    public static void main(String[] args) {
        userService.createUsersTable();
        userService.saveUser("Sasha", "Fomenko", (byte) 14);
        System.out.println("User с именем – Sasha добавлен в базу данных");
        userService.saveUser("Seva", "Fedotov", (byte) 15);
        System.out.println("User с именем – Seva добавлен в базу данных");
        userService.saveUser("Petya", "Kazakov", (byte) 16);
        System.out.println("User с именем – Petya добавлен в базу данных");
        userService.saveUser("Roma", "Rogov", (byte) 17);
        System.out.println("User с именем – Roma добавлен в базу данных");

        for (User user : userService.getAllUsers()) {
            System.out.println(user);
        }

//        userService.cleanUsersTable();
//
//        userService.dropUsersTable();
    }
}
