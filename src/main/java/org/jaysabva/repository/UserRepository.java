package org.jaysabva.repository;

import org.jaysabva.entity.User;

import java.util.List;

public interface UserRepository {
    void signUp(String username, String password, String role);
    boolean userExists(String username);
    User login(String username, String password);
    User getUser(String userName);
    void updateUser(String username, User user);
    void deleteUser(String username);
    List<User> getUsers();
}
