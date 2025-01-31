package org.jaysabva.service;

import org.jaysabva.dto.UserDto;
import org.jaysabva.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserService userService;

    private void testRegisterUser(String username) {
        UserDto user = new UserDto(username, "Jay", "Sabva", username, "password", "admin");
        System.out.println(userService.registerUser(user));
    }
//
    @RepeatedTest(10)
    public void testUpdateUserThread() {
        for (int i = 0; i < 5; i++) {
            testRegisterUser(String.valueOf(i + "a"));
        }
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            Thread t1 = new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("updating user with username: " + (finalI + "a") + " response: " + userService.updateUser(String.valueOf(finalI + "a"), new UserDto( null, "1232312321313", null, null, "password", "admin")).get("message"));
            });

            int finalI1 = i;
            Thread t2 = new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("updating user with username: " + (finalI + "a") + " response: " + userService.updateUser(String.valueOf(finalI1 + "a"), new UserDto(null, null, "Chan3123123ged12", null, "password", "admin")).get("message"));
            });

            threads.add(t1);
            threads.add(t2);
        }

        for (Thread t : threads) {
            t.start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        for (int i = 0; i < 5; i++) {
            User user = userService.getUser(String.valueOf(i + "a")).get();
            assertEquals("1232312321313", user.getFirstName());
            assertEquals("Chan3123123ged12", user.getLastName());
        }
        System.out.println(userService.getAllUsers());

    }
    @AfterEach
    public void deletUser() {
        for (int i = 0; i < 5; i++) {
            userService.deleteUserAdmin(String.valueOf(i + "a"));
        }
    }
}
