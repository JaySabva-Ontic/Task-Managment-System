package org.jaysabva.repository.Implementation;

import org.jaysabva.dto.UserDto;
import org.jaysabva.entity.User;
import org.jaysabva.repository.UserRepository;
import org.jaysabva.util.BCryptUtil;

import java.util.*;

public class UserRepositoryImplementation implements UserRepository {
    Map<String, User> user_username = new HashMap<>();
    Map<String, User> user_phoneno = new HashMap<>();

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
        user_username.put(user.getUserName(), user);
        user_phoneno.put(user.getPhoneno(), user);
    }

    @Override
    public boolean userExists(String Key) {
        return user_username.containsKey(Key) || user_phoneno.containsKey(Key);
    }

    @Override
    public Optional<User> login(UserDto loginInput) {
        String username = loginInput.getUsername();
        String password = loginInput.getPassword();
        String phoneno = loginInput.getPhoneno();

        User user = user_username.get(username) != null ? user_username.get(username) : user_phoneno.get(phoneno);
        if (user != null && BCryptUtil.checkPassword(password, user.getPassword())) {
            return Optional.of(user);
        }

        return Optional.empty();
    }

    @Override
    public Optional<User> getUser(String Key) {
        return Optional.ofNullable(user_username.get(Key)).isPresent() ? Optional.ofNullable(user_username.get(Key)) : Optional.ofNullable(user_phoneno.get(Key));
    }

    @Override
    public void updateUser(String Key, UserDto user) {
        User oldUser = user_username.get(Key) != null ? user_username.get(Key) : user_phoneno.get(Key);
        if (oldUser != null) {
            user_username.remove(oldUser.getUserName());
            user_phoneno.remove(oldUser.getPhoneno());

            oldUser.setUserName(user.getUsername() != null ? user.getUsername() : oldUser.getUserName());
            oldUser.setPhoneno(user.getPhoneno() != null ? user.getPhoneno() : oldUser.getPhoneno());
            oldUser.setPassword(user.getPassword() != null ? BCryptUtil.hashPassword(user.getPassword()) : oldUser.getPassword());
            oldUser.setRole(user.getRole() != null ? user.getRole() : oldUser.getRole());

            user_username.put(user.getUsername(), oldUser);
            user_phoneno.put(user.getPhoneno(), oldUser);
        }
    }

    @Override
    public void deleteUser(String Key) {
        user_username.remove(Key);
        user_phoneno.remove(Key);
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(user_username.values());
    }
}
