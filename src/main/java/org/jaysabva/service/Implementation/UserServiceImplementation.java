package org.jaysabva.service.Implementation;

import org.jaysabva.entity.User;
import org.jaysabva.repository.Implementation.UserRepositoryImplementation;
import org.jaysabva.repository.UserRepository;
import org.jaysabva.service.UserService;

import java.util.List;

public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository = new UserRepositoryImplementation();

    @Override
    public String registerUser(String username, String password, String role) {
        try {
            if (userRepository.userExists(username)) {
                return "User already exists";
            } else {

                userRepository.signUp(username, password, role);
                return "User registered successfully";
            }
        } catch (Exception e) {
            return "Error registering user";
        }
    }

    @Override
    public User loginUser(String username, String password) {
        try {
            return userRepository.login(username, password);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String updateUser(String username, User user) {
        try {
            if (!userRepository.userExists(username)) {
                return "User doesn't exists";
            }
            if (userRepository.userExists(user.getUserName())) {
                return "User on that updated username already exists";
            }

            userRepository.updateUser(username, user);

            return "User updated successfully";
        } catch (Exception e) {
            return "Error updating user";
        }
    }

    @Override
    public String deleteUser(String username, String password) {
        try {
            if (userRepository.login(username, password) != null) {
                userRepository.deleteUser(username);
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
    public User getUser(String username) {
        try {
            return userRepository.getUser(username);
        } catch (Exception e) {
            return null;
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
