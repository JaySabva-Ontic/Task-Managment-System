package org.jaysabva.service;

import org.jaysabva.dto.UserDto;
import org.jaysabva.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    String registerUser(UserDto signUpInput);
    Optional<User> loginUser(UserDto loginInput);
    String updateUser(String username, UserDto user);
    String deleteUser(UserDto user);
    String deleteUserByUsername(String username);
    Optional<User> getUser(String username);
    List<User> getAllUsers();
    String deleteUserAdmin(String username);
}
