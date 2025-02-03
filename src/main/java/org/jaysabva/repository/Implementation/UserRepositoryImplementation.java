//package org.jaysabva.repository.Implementation;
//
//import lombok.Synchronized;
//import org.jaysabva.dto.UserDto;
//import org.jaysabva.entity.User;
//import org.jaysabva.repository.UserRepository;
//import org.jaysabva.util.BCryptUtil;
//import org.springframework.stereotype.Repository;
//
//import java.util.*;
//
//@Repository
//public class UserRepositoryImplementation implements UserRepository {
//    List<User> users = new ArrayList<>();
//    Map<String, Long> userUsername = new HashMap<>();
//    Map<String, Long> userPhoneno = new HashMap<>();
//
//    private static UserRepositoryImplementation instance = null;
//    public UserRepositoryImplementation() {
//    }
//
//    public static UserRepositoryImplementation getInstance() {
//        if (instance == null) {
//            instance = new UserRepositoryImplementation();
//        }
//
//        return instance;
//    }
//
//    @Override
//    public void signUp(User user) {
//        users.add(user);
//        userUsername.put(user.getUserName(), users.size() - 1L);
//        userPhoneno.put(user.getPhoneno(), users.size() - 1L);
//    }
//
//    @Override
//    public boolean userExists(String key) {
//        Long indexFromUsername = userUsername.get(key);
//        Long indexFromPhoneNo = userPhoneno.get(key);
//
//        return (indexFromUsername != null && indexFromUsername >= 0 && indexFromUsername < users.size() && users.get(indexFromUsername.intValue()) != null) ||
//                (indexFromPhoneNo != null && indexFromPhoneNo >= 0 && indexFromPhoneNo < users.size() && users.get(indexFromPhoneNo.intValue()) != null);
//    }
//
//    @Override
//    public Optional<User> login(UserDto loginInput) {
//        String username = loginInput.getUsername();
//        String password = loginInput.getPassword();
//        String phoneno = loginInput.getPhoneno();
//
//        User user = null;
//
//        Long usernameIndex = userUsername.get(username);
//        if (usernameIndex != null && usernameIndex >= 0 && usernameIndex < users.size()) {
//            user = users.get(usernameIndex.intValue());
//        }
//
//        if (user == null) {
//            Long phoneNoIndex = userPhoneno.get(phoneno);
//            if (phoneNoIndex != null && phoneNoIndex >= 0 && phoneNoIndex < users.size()) {
//                user = users.get(phoneNoIndex.intValue());
//            }
//        }
//
//        if (user != null && BCryptUtil.checkPassword(password, user.getPassword())) {
//            return Optional.of(user);
//        }
//
//        return Optional.empty();
//    }
//
//    @Override
//    public Optional<User> getUser(String key) {
//        return Optional.ofNullable(userUsername.get(key)).isPresent() ? Optional.ofNullable(users.get(userUsername.get(key).intValue())) : Optional.ofNullable(users.get(userPhoneno.get(key).intValue()));
//    }
//
//    @Override
//    public void updateUser(String key, UserDto user) {
////        User oldUser = userUsername.get(key) != null ? userUsername.get(key) : userPhoneno.get(key);
//
//        int idx = userUsername.get(key) != null ? userUsername.get(key).intValue() : userPhoneno.get(key).intValue();
//        User oldUser = users.get(idx);
//        synchronized (oldUser.getUserId()) {
//            String username = oldUser.getUserName();
//            String phoneno = oldUser.getPhoneno();
//            if (oldUser != null) {
//
//                oldUser.setUserName(user.getUsername() != null ? user.getUsername() : oldUser.getUserName());
//                oldUser.setFirstName(user.getFirstName() != null ? user.getFirstName() : oldUser.getFirstName());
//                oldUser.setLastName(user.getLastName() != null ? user.getLastName() : oldUser.getLastName());
//                oldUser.setPhoneno(user.getPhoneno() != null ? user.getPhoneno() : oldUser.getPhoneno());
//                oldUser.setPassword(user.getPassword() != null ? BCryptUtil.hashPassword(user.getPassword()) : oldUser.getPassword());
//                oldUser.setRole(user.getRole() != null ? user.getRole() : oldUser.getRole());
//
//                userUsername.remove(username);
//                userUsername.put(oldUser.getUserName(), (long) idx);
//                userPhoneno.remove(phoneno);
//                userPhoneno.put(oldUser.getPhoneno(), (long) idx);
//            }
//
//        }
//    }
//
//    @Override
//    public void deleteUser(String key) {
//        if (userUsername.get(key) != null) {
//            users.set(userUsername.get(key).intValue(), null);
//        } else {
//            users.set(userPhoneno.get(key).intValue(), null);
//        }
//
//        userUsername.remove(key);
//        userPhoneno.remove(key);
//    }
//
//    @Override
//    public List<User> getUsers() {
//        return new ArrayList<>(users);
//    }
//}
