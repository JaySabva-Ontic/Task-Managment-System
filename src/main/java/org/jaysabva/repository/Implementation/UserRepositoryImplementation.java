package org.jaysabva.repository.Implementation;

import org.jaysabva.dto.UserDto;
import org.jaysabva.entity.User;
import org.jaysabva.repository.UserRepository;
import org.jaysabva.util.BCryptUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepositoryImplementation implements UserRepository {
    Map<String, User> users = new HashMap<>();

    @Override
    public void signUp(User user) {
        users.put(user.getUserName(), user);
    }

    @Override
    public boolean userExists(String username) {
        return users.containsKey(username);
    }

    @Override
    public User login(UserDto loginInput) {
        String username = loginInput.username();
        String password = loginInput.password();

        User user = users.get(username);
        if (user != null && BCryptUtil.checkPassword(password, user.getPassword())) {
            return user;
        }

        return null;
    }

    @Override
    public User getUser(String userName) {
        return users.get(userName);
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
