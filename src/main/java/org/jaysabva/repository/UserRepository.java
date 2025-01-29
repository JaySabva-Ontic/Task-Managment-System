package org.jaysabva.repository;

import org.jaysabva.dto.UserDto;
import org.jaysabva.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    void signUp(User user);
    boolean userExists(String username);
    Optional<User> login(UserDto loginInput);
    Optional<User> getUser(String userName);
    void updateUser(String username, UserDto user);
    void deleteUser(String username);
    List<User> getUsers();
}
