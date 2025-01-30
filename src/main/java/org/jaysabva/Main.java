package org.jaysabva;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.jaysabva.controller.TaskController;
import org.jaysabva.controller.UserController;
import org.jaysabva.dto.*;
import org.jaysabva.entity.*;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class Main {
    public static UserController userController = new UserController();
    public static TaskController taskController = new TaskController();

    public static void main(String[] args) {

        userController.registerUser(new UserDto("admin", "admin", "admin"));
        userController.registerUser(new UserDto("user", "user", "user"));


        Scanner scanner = new Scanner(System.in);

        while (true) {

            Optional<User> loggedInUser;
            while (true) {
                try {
                    UserDto loginInput = loginInput(scanner);
                    loggedInUser = userController.loginUser(loginInput);
                    if (loggedInUser.isPresent()) {
                        System.out.println("Login successful!");
                        break;
                    } else {
                        System.out.println("Invalid credentials. Please try again.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid option.");
                    scanner.nextLine();
                }
            }

            if (loggedInUser.get().getRole().equals("admin")) {
                while (true) {
                    try {
                        System.out.print("""
                                1 - Create User
                                2 - Update User
                                3 - Delete User
                                4 - Get All Users
                                5 - Task Related
                                """);
//                                "6 - Logout\n");

                        int option = Integer.parseInt(scanner.nextLine());

                        boolean taskRelated = false;
                        UserDto signUpInput;
                        String username;

                        switch (option) {
                            case 1:
                                signUpInput = signUpInput(scanner);
                                Map<String, String> response = userController.registerUser(signUpInput);
                                System.out.println(response.get("message"));
                                break;
                            case 2:
                                do {
                                    System.out.println("Enter username to update: ");
                                    username = scanner.nextLine();
                                } while (userController.getUser(username).isEmpty());

                                signUpInput = signUpInput(scanner);

                                System.out.println(userController.updateUser(username, signUpInput).get("message"));

                                break;
                            case 3:
                                System.out.println("Enter username to delete: ");
                                username = scanner.nextLine();

                                System.out.println(userController.deleteUserAdmin(username).get("message"));

                                break;
                            case 4:
                                List<User> users = userController.getAllUsers();
                                for (User u : users) {
                                    System.out.println("UserID: " + u.getUserId() + " Username: " + u.getUserName() + ", Role: " + u.getRole());
                                }
                                break;
                            case 5:
                                taskRelated = true;
                                break;

                            default:
                                System.out.println("Invalid option. Please select a valid option.");
                        }
                        if (taskRelated) {
                            break;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a valid option.");
                        scanner.nextLine();
                    } catch (Exception e) {
                        scanner.nextLine();
                    }
                }
            }

            while (true) {
                try {
                    System.out.println("""
                            1 - Create Task
                            2 - Update Task
                            3 - Delete Task
                            4 - View Task
                            5 - View All Tasks
                            6 - Logout""");
                    int option = Integer.parseInt(scanner.nextLine());
                    long taskId;
                    switch (option) {
                        case 1:
                            System.out.println("""
                                    1 - Bug Task
                                    2 - Feature Task
                                    3 - Improvement Task""");
                            option = Integer.parseInt(scanner.nextLine());

                            String username = loggedInUser.map(User::getUserName).orElseThrow(() -> new RuntimeException("User not found"));
                            switch (option) {
                                case 1:
                                    System.out.println(taskController.addTask(createBugTask(scanner, username)).get("message"));
                                    break;
                                case 2:
                                    System.out.println(taskController.addTask(createFeatureTask(scanner, username)).get("message"));
                                    break;
                                case 3:
                                    System.out.println(taskController.addTask(createImprovementTask(scanner, username)).get("message"));
                                    break;
                                default:
                                    System.out.println("Invalid task type. Please try again.");
                            }
                            break;
                        case 2:
                            System.out.println("Enter Task ID to update: ");
                            taskId = Long.parseLong(scanner.nextLine());
                            Optional<Task> taskToUpdate = taskController.getTask(taskId);

                            if (taskToUpdate.isEmpty()) {
                                System.out.println("Task with ID " + taskId + " not found.");
                                break;
                            }

                            Task task1 = taskToUpdate.get();
                            taskController.updateTask(updateTask(task1, scanner));
                            System.out.println("Task updated successfully!");

                            break;
                        case 3:
                            System.out.println("Enter Task ID to delete: ");
                            taskId = Long.parseLong(scanner.nextLine());

                            System.out.println(taskController.deleteTask(taskId).get("message"));
                            break;
                        case 4:
                            System.out.println("Enter Task ID to view: ");
                            taskId = Long.parseLong(scanner.nextLine());

                            Optional<Task> taskToView = taskController.getTask(taskId);
                            if (taskToView.isEmpty()) {
                                System.out.println("Task with ID " + taskId + " not found.");
                            } else {
                                taskToView.get().viewTask();
                            }
                            break;
                        case 5:
                            List<Task> allTasks = taskController.getAllTasks();
                            if (allTasks == null) {
                                System.out.println("No tasks found.");
                            } else {
                                for (Task task : allTasks) {
                                    System.out.println("--------------------");
                                    task.viewTask();
                                }
                                System.out.println("--------------------");
                            }
                            break;
                        case 6:
                            System.out.println("Logging out...");
                            loggedInUser = Optional.empty();
                            break;
                        default:
                            System.out.println("Invalid option. Please select a valid option.");
                    }

                    if (loggedInUser.isEmpty())
                        break;

                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    scanner.nextLine();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private static String jsonInput(Scanner scanner) {
        System.out.println("Enter json");
        return scanner.nextLine();
    }
    private static UserDto loginInput(Scanner scanner) {
//        take json input from command line using jackson
        System.out.println("Enter username and password json");
        String inputJson = jsonInput(scanner);
        try {
            return new ObjectMapper().registerModule(new JavaTimeModule()).readValue(inputJson, UserDto.class);
        } catch (IOException e) {
            return loginInput(scanner);
        }

    }

    private static UserDto signUpInput(Scanner scanner) {
        System.out.println("Enter username, password, role json");
        String inputJson = jsonInput(scanner);

        try {
            return new ObjectMapper().registerModule(new JavaTimeModule()).readValue(inputJson, UserDto.class);
        } catch (IOException e) {
            return signUpInput(scanner);
        }
    }

    private static String getStatus(int status) {
        return switch (status) {
            case 1 -> "PENDING";
            case 2 -> "IN_PROGRESS";
            case 3 -> "COMPLETED";
            case 4 -> "CANCELLED";
            default -> "PENDING";
        };
    }
    private static Map<String, Object> createTask(Scanner scanner, String username) {
        Map<String, Object> task = new HashMap<>();

        System.out.println("Enter title: ");
        task.put("title", scanner.nextLine());

        System.out.println("Enter description");
        task.put("description", scanner.nextLine());

        System.out.println("Enter status: 1.PENDING 2.IN_PROGRESS 3.COMPLETED 4.CANCELLED");
        int status = Integer.parseInt(scanner.nextLine());
        task.put("status", getStatus(status));

        System.out.println("Enter start date: (yyyy-mm-dd");
        LocalDateTime startDate = LocalDateTime.parse(scanner.nextLine() + "T00:00:00");
        task.put("startDate", startDate);

        System.out.println("Enter due date: (yyyy-mm-dd)");
        LocalDateTime dueDate = LocalDateTime.parse(scanner.nextLine() + "T00:00:00");
        task.put("dueDate", dueDate);

//        System.out.println("Enter created at: ");
        task.put("createdAt", LocalDateTime.now());

//        System.out.println("Enter updated at: ");
        task.put("updatedAt", LocalDateTime.now());

        while (true) {
            System.out.println("Enter assignee: ");
            String assignee = scanner.nextLine();
            if (userController.getUser(assignee).isPresent()) {
                task.put("assignee", assignee);
                break;
            } else {
                System.out.println("User does not exist. Please enter a valid username.");
            }
        }

        task.put("createdBy", username);

        return task;
    }

    private static <T> void fieldAssignment (Map<String, Object> task, TaskDto taskType) {
        taskType.setTitle((String) task.get("title"));
        taskType.setDescription((String) task.get("description"));
        taskType.setStatus((String) task.get("status"));
//        taskType.setStartDate((LocalDateTime) task.get("startDate"));
//        taskType.setDueDate((LocalDateTime) task.get("dueDate"));
//        taskType.setCreatedAt((LocalDateTime) task.get("createdAt"));
//        taskType.setUpdatedAt((LocalDateTime) task.get("updatedAt"));
        taskType.setAssignee((String) task.get("assignee"));
        taskType.setCreatedBy((String) task.get("createdBy"));
        taskType.setTaskType((String) task.get("taskType"));
    }

    private static BugTaskDto createBugTask(Scanner scanner, String username) throws IOException {
        String inputJson = jsonInput(scanner);
        System.out.println(inputJson);
        try {
            return new ObjectMapper().registerModule(new JavaTimeModule()).readValue(inputJson, BugTaskDto.class);
        } catch (IOException e) {
            return createBugTask(scanner, username);
        }
    }

    private static FeatureTaskDto createFeatureTask(Scanner scanner, String username) throws IOException {
        String inputJson = jsonInput(scanner);
        System.out.println(inputJson);
        try {
            return new ObjectMapper().registerModule(new JavaTimeModule()).readValue(inputJson, FeatureTaskDto.class);
        } catch (IOException e) {
            return createFeatureTask(scanner, username);
        }
    }

    private static ImprovementTaskDto createImprovementTask(Scanner scanner, String username) throws IOException {
        String inputJson = jsonInput(scanner);
        System.out.println(inputJson);
        try {
            return new ObjectMapper().registerModule(new JavaTimeModule()).readValue(inputJson, ImprovementTaskDto.class);
        } catch (IOException e) {
            return createImprovementTask(scanner, username);
        }
    }

    private static Task updateTask(Task task, Scanner scanner) {
        System.out.println("Enter title: ");
        task.setTitle(scanner.nextLine());

        System.out.println("Enter description");
        task.setDescription(scanner.nextLine());

        System.out.println("Enter status: ");
        task.setStatus(scanner.nextLine());

        System.out.println("Enter start date: (yyyy-mm-dd)");
        LocalDateTime startDate = LocalDateTime.parse(scanner.nextLine() + "T00:00:00");
        task.setStartDate(startDate);

        System.out.println("Enter due date: (yyyy-mm-dd)");
        LocalDateTime dueDate = LocalDateTime.parse(scanner.nextLine() + "T00:00:00");
        task.setDueDate(dueDate);

        task.setUpdatedAt(LocalDateTime.now());

        while (true) {
            System.out.println("Enter assignee: ");
            String assignee = scanner.nextLine();
            if (userController.getUser(assignee).isPresent()) {
                task.setAssignee(assignee);
                break;
            } else {
                System.out.println("User does not exist. Please enter a valid username.");
            }
        }

        if (task instanceof BugTask bugTask) {
            System.out.println("Enter severity: ");
            bugTask.setSeverity(scanner.nextLine());

            System.out.println("Enter step to reproduce: (q: to exit)");
            String input;
            long cnt = 0;
            while (true) {
                System.out.println("Step " + ++cnt + ": ");
                input = scanner.nextLine();

                if (input.equals("q:")) {
                    break;
                }

                if (input.isBlank()) {
                    continue;
                }

                bugTask.getStepToReproduce().add(input);
            }
        } else if (task instanceof FeatureTask featureTask) {
            System.out.println("Enter feature description: ");
            featureTask.setFeatureDescription(scanner.nextLine());

            System.out.println("Enter estimated effort: (hours)");
            featureTask.setEstimatedEffort(Duration.parse("PT" + scanner.nextLine() + "H"));
        } else if (task instanceof ImprovementTask improvementTask) {
            System.out.println("Enter proposed improvement: ");
            improvementTask.setProposedImprovement(scanner.nextLine());
        }

        return task;
    }
}