package org.jaysabva.service.Implementation;

import org.jaysabva.dto.UserDto;
import org.jaysabva.entity.User;
import org.jaysabva.repository.Implementation.UserRepositoryImplementation;
import org.jaysabva.repository.UserRepository;
import org.jaysabva.service.UserService;
import org.jaysabva.util.BCryptUtil;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository = UserRepositoryImplementation.getInstance();

    @Override
    public Map<String, String> registerUser(UserDto userDto) {
        try {
            if (userRepository.userExists(userDto.getUsername()) || userRepository.userExists(userDto.getPhoneno())) {
                return Map.of("message", "User already exists");
            } else if ((userDto.getUsername() == null || userDto.getPhoneno() == null|| userDto.getPassword() == null || userDto.getRole() == null)) {
                return Map.of("message", "Please fill in all fields");
            } else {

                User user = new User(userDto.getUsername(), userDto.getPhoneno(), BCryptUtil.hashPassword(userDto.getPassword()), userDto.getRole());

                userRepository.signUp(user);
                return Map.of("message", "User registered successfully");
            }
        } catch (Exception e) {
            return Map.of("message", "Error registering user");
        }
    }

    @Override
    public Optional<User> loginUser(UserDto loginInput) {
        try {
            return userRepository.login(loginInput);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Map<String, String> updateUser(String username, UserDto user) {
        try {
            if (!userRepository.userExists(username)) {
                return Map.of("message", "User does not exist");
            }
            if (userRepository.userExists(user.getUsername())) {
                return Map.of("message", "Username already exists");
            }

            userRepository.updateUser(username, user);

            return Map.of("message", "User updated successfully");
        } catch (Exception e) {
            return Map.of("message", "Error updating user");
        }
    }

    @Override
    public Map<String, String> deleteUser(UserDto loginInput) {
        try {
            if (userRepository.login(loginInput).isPresent()) {
                userRepository.deleteUser(loginInput.getUsername());
                return Map.of("message", "User deleted successfully");
            } else {
                return Map.of("message", "User does not exist");
            }
        } catch (Exception e) {
            return Map.of("message", "Error deleting user");
        }
    }

    @Override
    public String deleteUserByUsername(String username) {
        try {
            if (userRepository.userExists(username)) {
                userRepository.deleteUser(username);
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
            return userRepository.getUser(username);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try {
            return userRepository.getUsers();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Map<String, String> deleteUserAdmin(String username) {
        try {
            if (userRepository.userExists(username)) {
                userRepository.deleteUser(username);
                return Map.of("message", "User deleted successfully");
            } else {
                return Map.of("message", "User does not exist");
            }
        } catch (Exception e) {
            return Map.of("message", "Error deleting user");
        }
    }
}
