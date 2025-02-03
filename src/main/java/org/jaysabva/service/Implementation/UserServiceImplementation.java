package org.jaysabva.service.Implementation;

import org.jaysabva.dto.UserDto;
import org.jaysabva.entity.User;
import org.jaysabva.repository.UserRepository;
import org.jaysabva.service.UserService;
import org.jaysabva.util.BCryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    @Autowired
    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Map<String, String> registerUser(UserDto userDto) {
        try {
            if (userRepository.existsUserByUserName(userDto.getUsername()) || userRepository.existsUserByPhoneno(userDto.getPhoneno())) {
                return Map.of("message", "User already exists");
            } else if ((userDto.getUsername() == null || userDto.getPhoneno() == null|| userDto.getPassword() == null || userDto.getRole() == null)) {
                return Map.of("message", "Please fill in all fields");
            } else {

                User user = new User(userDto.getUsername(), userDto.getFirstName(), userDto.getLastName(),  userDto.getPhoneno(), BCryptUtil.hashPassword(userDto.getPassword()), userDto.getRole());

                userRepository.save(user);
                return Map.of("message", "User registered successfully");
            }
        } catch (Exception e) {
            return Map.of("message", "Error registering user");
        }
    }

    @Override
    public Optional<User> loginUser(UserDto loginInput) {
        try {
            User user = userRepository.findByUserName(loginInput.getUsername());
            if (BCryptUtil.checkPassword(loginInput.getPassword(), user.getPassword())) {
                return Optional.ofNullable(user);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Map<String, String> updateUser(String username, UserDto user) {
        try {
            if (!userRepository.existsUserByUserName(username)) {
                return Map.of("message", "User does not exist");
            }
            if (userRepository.existsUserByUserName(user.getUsername())) {
                return Map.of("message", "Username already exists");
            }

            User oldUser = userRepository.findByUserName(username);
            oldUser.setUserName(user.getUsername());
            oldUser.setFirstName(user.getFirstName());
            oldUser.setLastName(user.getLastName());
            oldUser.setRole(user.getRole());
            oldUser.setPhoneno(user.getPhoneno());
            oldUser.setPassword(BCryptUtil.hashPassword(user.getPassword()));


            userRepository.save(oldUser);

            return Map.of("message", "User updated successfully");
        } catch (Exception e) {
            System.out.println(e);
            return Map.of("message", "Error updating user");
        }
    }

//    @Override
//    public Map<String, String> updateUser1(String username, UserDto user) {
//        Thread thread1 = new Thread(() -> {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            user.setUsername("abcd123");
//            userRepository.updateUser(username, user);
//        });
//
//        Thread thread2 = new Thread(() -> {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            user.setPhoneno("123123123");
//            userRepository.updateUser(username, user);
//        });
//
//        thread1.start();
//        thread2.start();
//    }

//    @Override
//    public Map<String, String> deleteUser(UserDto loginInput) {
//        try {
//            if (userRepository.findBYUserNameAndPassword(loginInput).isPresent()) {
//                userRepository.deleteUser(loginInput.getUsername());
//                return Map.of("message", "User deleted successfully");
//            } else {
//                return Map.of("message", "User does not exist");
//            }
//        } catch (Exception e) {
//            return Map.of("message", "Error deleting user");
//        }
//    }

    @Override
    public String deleteUserByUsername(String username) {
        try {
            if (userRepository.existsUserByUserName(username)) {
                userRepository.deleteByUserName(username);
                return "User deleted successfully";
            } else {
                return "User does not exist";
            }
        } catch (Exception e) {
            return "Error deleting user";
        }
    }
    @Override
    public Optional<User> getUser(String username) {
        try {
            return Optional.ofNullable(userRepository.findByUserName(username));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Map<String, String> deleteUserAdmin(String username) {
        try {
            if (userRepository.existsUserByUserName(username)) {
                userRepository.deleteByUserName(username);
                return Map.of("message", "User deleted successfully");
            } else {
                return Map.of("message", "User does not exist");
            }
        } catch (Exception e) {
            return Map.of("message", "Error deleting user");
        }
    }
}
