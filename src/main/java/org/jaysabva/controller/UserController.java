package org.jaysabva.controller;

import org.jaysabva.entity.User;
import org.jaysabva.service.Implementation.UserServiceImplementation;
import org.jaysabva.service.UserService;

import java.util.List;

public class UserController {
    private final UserService userService = new UserServiceImplementation();

    public String registerUser(String username, String password, String role) {
        return userService.registerUser(username, password, role);
    }

    public User loginUser(String username, String password) {
        return userService.loginUser(username, password);
    }

    public String updateUser(String username, User user) {
        return userService.updateUser(username, user);
    }

    public String deleteUser(String username, String password) {
        return userService.deleteUser(username, password);
    }

    public User getUser(String username) {
        return userService.getUser(username);
    }

    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    public String deleteUserAdmin(String username) {
        return userService.deleteUserAdmin(username);
    }
}
