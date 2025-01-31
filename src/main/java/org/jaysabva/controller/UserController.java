package org.jaysabva.controller;

import org.jaysabva.dto.UserDto;
import org.jaysabva.entity.User;
import org.jaysabva.service.Implementation.UserServiceImplementation;
import org.jaysabva.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }

    @PostMapping("/register")
    public Map<String, String> registerUser(@RequestBody UserDto signUpInput) {
        return userService.registerUser(signUpInput);
    }

    @PostMapping("/login")
    public Optional<User> loginUser(@RequestBody UserDto loginInput) {
        return userService.loginUser(loginInput);
    }

    @PutMapping("/updateUser/{username}")
    public Map<String, String> updateUser(@PathVariable String username, @RequestBody UserDto user) {
        return userService.updateUser(username, user);
    }

    @PostMapping("/deleteUser")
    public Map<String, String> deleteUser(@RequestBody UserDto user) {
        return userService.deleteUser(user);
    }

    @GetMapping("/getUser/{username}")
    public Optional<User> getUser(@PathVariable String username) {
        return userService.getUser(username);
    }

    @GetMapping("/getAllUsers")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/deleteUserAdmin")
    public Map<String, String> deleteUserAdmin(@RequestBody Map<String, String> usernameInput) {
        return userService.deleteUserAdmin(usernameInput.get("username"));
    }
}
