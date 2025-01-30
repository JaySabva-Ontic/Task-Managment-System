package org.jaysabva.service;

import org.jaysabva.dto.UserDto;
import org.jaysabva.entity.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    Map<String, String> registerUser(UserDto signUpInput);
    Optional<User> loginUser(UserDto loginInput);
    Map<String, String> updateUser(String username, UserDto user);
    Map<String, String> deleteUser(UserDto user);
    String deleteUserByUsername(String username);
    Optional<User> getUser(String username);
    List<User> getAllUsers();
    Map<String, String> deleteUserAdmin(String username);
}
