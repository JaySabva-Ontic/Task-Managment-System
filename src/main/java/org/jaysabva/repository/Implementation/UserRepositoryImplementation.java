package org.jaysabva.repository.Implementation;

import lombok.Synchronized;
import org.jaysabva.dto.UserDto;
import org.jaysabva.entity.User;
import org.jaysabva.repository.UserRepository;
import org.jaysabva.util.BCryptUtil;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepositoryImplementation implements UserRepository {
    List<User> users = new ArrayList<>();
    Map<String, Long> userUsername = new HashMap<>();
    Map<String, Long> userPhoneno = new HashMap<>();

    private static UserRepositoryImplementation instance = null;
    public UserRepositoryImplementation() {
    }

    public static UserRepositoryImplementation getInstance() {
        if (instance == null) {
            instance = new UserRepositoryImplementation();
        }

        return instance;
    }

    @Override
    public boolean existsUserByUserName(String userName) {
        Long indexFromUsername = userUsername.get(userName);
        Long indexFromPhoneNo = userPhoneno.get(userName);

        return (indexFromUsername != null && indexFromUsername >= 0 && indexFromUsername < users.size() && users.get(indexFromUsername.intValue()) != null) ||
                (indexFromPhoneNo != null && indexFromPhoneNo >= 0 && indexFromPhoneNo < users.size() && users.get(indexFromPhoneNo.intValue()) != null);
    }

    @Override
    public boolean existsUserByPhoneno(String key) {
        Long indexFromUsername = userUsername.get(key);
        Long indexFromPhoneNo = userPhoneno.get(key);

        return (indexFromUsername != null && indexFromUsername >= 0 && indexFromUsername < users.size() && users.get(indexFromUsername.intValue()) != null) ||
                (indexFromPhoneNo != null && indexFromPhoneNo >= 0 && indexFromPhoneNo < users.size() && users.get(indexFromPhoneNo.intValue()) != null);
    }

    @Override
    public Optional<User> findByUserNameAndPassword(String key, String password) {
        String username = key;
        String phoneno = key;

        User user = null;

        Long usernameIndex = userUsername.get(username);
        if (usernameIndex != null && usernameIndex >= 0 && usernameIndex < users.size()) {
            user = users.get(usernameIndex.intValue());
        }

        if (user == null) {
            Long phoneNoIndex = userPhoneno.get(phoneno);
            if (phoneNoIndex != null && phoneNoIndex >= 0 && phoneNoIndex < users.size()) {
                user = users.get(phoneNoIndex.intValue());
            }
        }

        if (user != null && BCryptUtil.checkPassword(password, user.getPassword())) {
            return Optional.of(user);
        }

        return Optional.empty();
    }

    @Override
    public User save(User user) {
        users.add(user);
        //TODO: Handle the case where the user already exists
        return user;
    }

    @Override
    public List<User> findAll() {
        return users;
    }
    @Override
    public Optional<User> findByUserName(String key) {
        return Optional.ofNullable(userUsername.get(key)).isPresent() ? Optional.ofNullable(users.get(userUsername.get(key).intValue())) : Optional.ofNullable(users.get(userPhoneno.get(key).intValue()));
    }

    @Override
    public void deleteByUserName(String key) {
        if (userUsername.get(key) != null) {
            users.set(userUsername.get(key).intValue(), null);
        } else {
            users.set(userPhoneno.get(key).intValue(), null);
        }

        userUsername.remove(key);
        userPhoneno.remove(key);
    }

}
