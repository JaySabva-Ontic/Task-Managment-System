package org.jaysabva.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.jaysabva.dto.BugTaskDto;
import org.jaysabva.dto.UserDto;
import org.jaysabva.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserServiceTests1 {

    @Autowired
    private UserService userService;
    @Autowired
    private TaskService taskService;

    private void testRegisterUser(String username) {
        UserDto user = new UserDto(username, "Jay", "Sabva", username, "password", "admin");
        System.out.println(userService.registerUser(user));
    }
//
    private void updateUser(String username) {
        UserDto user = new UserDto(null, "Jfdksjnksjjldjsjkjay", "Sabva", null, "password", "admin");
        System.out.println(userService.updateUser(username, user));
    }


    @Test
    public void forloop() {
        for (int i = 0; i < 5; i++) {
            testRegisterUser(i + "a");
        }
        for (int i = 0; i < 5; i++) {
            updateUser(i + "a");
        }
    }

    @Test
    public void testtest() {
        String a = "3a";
        String b = "3a";
        int d = 3;
        String c = d + "a";
        String e = d + "a";

        anotherMethod(a,b,c, e);
    }

    private void anotherMethod(String a, String b, String c, String e) {
        System.out.println(a == b);
        System.out.println(a == c);
        System.out.println(a.equals(c));

        System.out.println(c == e);
        System.out.println(c.equals(e));

        System.out.println(c == c);
        System.out.println(c.equals(c));

        System.out.println(a.hashCode());
        System.out.println(b.hashCode());
        System.out.println(c.hashCode());
    }

    @RepeatedTest(1)
    public void testUpdateUserThread() {
        for (int i = 0; i < 5; i++) {
            testRegisterUser(String.valueOf(i + "a"));
        }

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            String username = finalI + "a";
            Thread t1 = new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Map<String, String> updateUserResponse = userService.updateUser(username, new UserDto(null, "1232312321313", null, null, "password", "admin"));
                System.out.println("updating user with username: " + username + " response: " + updateUserResponse.get("message"));
            });

            Thread t2 = new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Map<String, String> updateUserResponse = userService.updateUser(username, new UserDto(null, null, "Chan3123123ged12", null, "password", "admin"));
                System.out.println("updating user with username: " + username + " response: " + updateUserResponse.get("message"));
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
        System.out.println("Deleting all users");
        for (int i = 0; i < 5; i++) {
            userService.deleteUserAdmin(String.valueOf(i + "a"));
        }
    }

    @Test
    public void addTask() {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            Thread t1 = new Thread(() -> {
                String inputJson = "{\n" +
                        "    \"title\": \"BUG\",\n" +
                        "    \"description\": \"D\",\n" +
                        "    \"status\": \"PENDING\",\n" +
                        "    \"startDate\": \"2024-03-10T12:30:00\",\n" +
                        "    \"dueDate\": \"2024-03-10T12:30:00\",\n" +
                        "    \"createdAt\": \"2024-03-10T12:30:00\",\n" +
                        "    \"updatedAt\": \"2024-03-10T12:30:00\",\n" +
                        "    \"assignee\": \"JaySabva\",\n" +
                        "    \"createdBy\": \"admin\",\n" +
                        "    \"storyPoints\": \"1\",\n" +
                        "    \"severity\": \"HIGH\",\n" +
                        "    \"stepToReproduce\": [\n" +
                        "        \"Check\"\n" +
                        "    ],\n" +
                        "    \"taskType\": \"BUG\"\n" +
                        "}";

                System.out.println(finalI);
                try {
                    taskService.addTask(new ObjectMapper().registerModule(new JavaTimeModule()).readValue(inputJson, BugTaskDto.class));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            });

            threads.add(t1);
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
    }
}
