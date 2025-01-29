package org.jaysabva.repository.Implementation;

import org.jaysabva.dto.UserDto;
import org.jaysabva.entity.User;
import org.jaysabva.repository.UserRepository;
import org.jaysabva.util.BCryptUtil;

import java.util.*;

public class UserRepositoryImplementation implements UserRepository {
    Map<String, User> users = new HashMap<>();

    private static UserRepositoryImplementation instance = null;
    private UserRepositoryImplementation() {
    }

    public static UserRepositoryImplementation getInstance() {
        if (instance == null) {
            instance = new UserRepositoryImplementation();
        }

        return instance;
    }

    @Override
    public void signUp(User user) {
        users.put(user.getUserName(), user);
    }

    @Override
    public boolean userExists(String username) {
        return users.containsKey(username);
    }

    @Override
    public Optional<User> login(UserDto loginInput) {
        String username = loginInput.username();
        String password = loginInput.password();

        User user = users.get(username);
        if (user != null && BCryptUtil.checkPassword(password, user.getPassword())) {
            return Optional.of(user);
        }

        return Optional.empty();
    }

    @Override
    public Optional<User> getUser(String userName) {
        return Optional.ofNullable(users.get(userName));
    }

    @Override
    public void updateUser(String username, UserDto user) {
        if (users.containsKey(username)) {
            User oldUser = users.get(username);
            oldUser.setUserName(user.username());
            oldUser.setPassword(user.password());
            oldUser.setRole(user.role());

            users.remove(username);
            users.put(user.username(), oldUser);
        }
    }

    @Override
    public void deleteUser(String username) {
        users.remove(username);
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }
}
