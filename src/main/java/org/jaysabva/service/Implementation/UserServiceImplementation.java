package org.jaysabva.service.Implementation;

import org.jaysabva.dto.UserDto;
import org.jaysabva.entity.User;
import org.jaysabva.repository.Implementation.UserRepositoryImplementation;
import org.jaysabva.repository.UserRepository;
import org.jaysabva.service.UserService;
import org.jaysabva.util.BCryptUtil;

import java.util.List;
import java.util.Optional;

public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository = UserRepositoryImplementation.getInstance();

    @Override
    public String registerUser(UserDto userDto) {
        try {
            if (userRepository.userExists(userDto.username())) {
                return "User already exists";
            } else {

                User user = new User(userDto.username(), BCryptUtil.hashPassword(userDto.password()), userDto.role());

                userRepository.signUp(user);
                return "User registered successfully";
            }
        } catch (Exception e) {
            return "Error registering user";
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
    public String updateUser(String username, UserDto user) {
        try {
            if (!userRepository.userExists(username)) {
                return "User doesn't exists";
            }
            if (userRepository.userExists(user.username())) {
                return "User on that updated username already exists";
            }

            userRepository.updateUser(username, user);

            return "User updated successfully";
        } catch (Exception e) {
            return "Error updating user";
        }
    }

    @Override
    public String deleteUser(UserDto loginInput) {
        try {
            if (userRepository.login(loginInput) != null) {
                userRepository.deleteUser(loginInput.username());
                return "User deleted successfully";
            } else {
                return "Invalid credentials";
            }
        } catch (Exception e) {
            return "Error deleting user";
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
    public String deleteUserAdmin(String username) {
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
}
