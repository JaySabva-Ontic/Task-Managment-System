package org.jaysabva.service;

import org.jaysabva.dto.UserDto;
import org.jaysabva.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    String registerUser(UserDto signUpInput);
    User loginUser(UserDto loginInput);
    String updateUser(String username, UserDto user);
    String deleteUser(UserDto user);
    String deleteUserByUsername(String username);
    User getUser(String username);
    List<User> getAllUsers();
    String deleteUserAdmin(String username);
}
