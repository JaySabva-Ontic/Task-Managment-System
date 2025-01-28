package org.jaysabva.repository.Implementation;

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
    public void signUp(String username, String password, String role) {
        password = BCryptUtil.hashPassword(password);

        User user = new User(username, password, role);

        users.put(user.getUserName(), user);
    }

    @Override
    public boolean userExists(String username) {
        return users.containsKey(username);
    }

    @Override
    public User login(String username, String password) {
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
    public void updateUser(String username, User user) {
        if (users.containsKey(username)) {
            users.remove(username);
            signUp(user.getUserName(), user.getPassword(), user.getRole());
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
