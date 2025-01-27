package org.jaysabva.repository;

import org.jaysabva.entity.User;
import org.jaysabva.util.BCryptUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepository {
    Map<String, User> users;

    public UserRepository() {
        users = new HashMap<>();
    }

    public void signUp(String username, String password, String role) {
        password = BCryptUtil.hashPassword(password);

        User user = new User(username, password, role);

        users.put(user.getUserName(), user);
    }

    public boolean userExists(String username) {
        return users.containsKey(username);
    }

    public boolean login(String username, String password) {
        if (users.containsKey(username) && BCryptUtil.checkPassword(password, users.get(username).getPassword())) {
            return true;
        }

        return false;
    }

    public User getUser(String userName) {
        return users.get(userName);
    }

    public void updateUser(String username, User user) {
        if (users.containsKey(username)) {
            users.remove(username);
            signUp(user.getUserName(), user.getPassword(), user.getRole());
        }
    }

    public void deleteUser(String username) {
        users.remove(username);
    }

    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }
}
