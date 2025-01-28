package org.jaysabva.repository;

import org.jaysabva.dto.UserDto;
import org.jaysabva.entity.User;

import java.util.List;

public interface UserRepository {
    void signUp(User user);
    boolean userExists(String username);
    User login(UserDto loginInput);
    User getUser(String userName);
    void updateUser(String username, UserDto user);
    void deleteUser(String username);
    List<User> getUsers();
}
