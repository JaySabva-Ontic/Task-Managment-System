package org.jaysabva.controller;

import org.jaysabva.dto.UserDto;
import org.jaysabva.entity.User;
import org.jaysabva.service.Implementation.UserServiceImplementation;
import org.jaysabva.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserController {
    private final UserService userService = new UserServiceImplementation();

    public Map<String, String> registerUser(UserDto signUpInput) {
        return userService.registerUser(signUpInput);
    }

    public Optional<User> loginUser(UserDto loginInput) {
        return userService.loginUser(loginInput);
    }

    public Map<String, String> updateUser(String username, UserDto user) {
        return userService.updateUser(username, user);
    }

    public Map<String, String> deleteUser(UserDto user) {
        return userService.deleteUser(user);
    }

    public Optional<User> getUser(String username) {
        return userService.getUser(username);
    }

    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    public Map<String, String> deleteUserAdmin(String username) {
        return userService.deleteUserAdmin(username);
    }
}
