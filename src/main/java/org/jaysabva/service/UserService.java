package org.jaysabva.service;

import org.jaysabva.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    String registerUser(String username, String password, String role);
    User loginUser(String username, String password);
    String updateUser(String username, User user);
    String deleteUser(String username, String password);
    String deleteUserByUsername(String username);
    User getUser(String username);
    List<User> getAllUsers();
    String deleteUserAdmin(String username);
}
