package org.jaysabva.controller;

import org.jaysabva.entity.User;
import org.jaysabva.service.Implementation.UserServiceImplementation;
import org.jaysabva.service.UserService;

import java.util.List;
import java.util.Map;

public class UserController {
    private final UserServiceImplementation userServiceImplementation = new UserServiceImplementation();

    public String registerUser(String username, String password, String role) {
        return userServiceImplementation.registerUser(username, password, role);
    }

    public User loginUser(String username, String password) {
        return userServiceImplementation.loginUser(username, password);
    }

    public String updateUser(String username, User user) {
        return userServiceImplementation.updateUser(username, user);
    }

    public String deleteUser(String username, String password) {
        return userServiceImplementation.deleteUser(username, password);
    }

    public User getUser(String username) {
        return userServiceImplementation.getUser(username);
    }

    public List<User> getAllUsers() {
        return userServiceImplementation.getAllUsers();
    }

    public String deleteUserAdmin(String username) {
        return userServiceImplementation.deleteUserAdmin(username);
    }
}
