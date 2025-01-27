package org.jaysabva;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<User> users = new ArrayList<>();
        List<Task> tasks = new ArrayList<>();
        users.add(createAdmin());
        users.add(createUser());

        Scanner scanner = new Scanner(System.in);

        while (true) {

            User loggedInUser = null;
            while (true) {
                try {
                    loggedInUser = login(users, scanner);
                    if (loggedInUser != null) {
                        System.out.println("Login successful!");
                        break;
                    } else {
                        System.out.println("Login failed! Retry.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid option.");
                    scanner.nextLine();
                }
            }

            if (loggedInUser != null && loggedInUser.getRole().equals("admin")) {
                while (true) {
                    try {
                        System.out.print("1 - Create User\n" +
                                "2 - Update User\n" +
                                "3 - Delete User\n" +
                                "4 - Get All Users\n" +
                                "5 - Task Related\n");
//                                "6 - Logout\n");

                        int option = Integer.parseInt(scanner.nextLine());

                        boolean taskRelated = false;

                        switch (option) {
                            case 1:
                                users.add(createUser(scanner, (long) users.size() + 1));
                                break;
                            case 2:
                                System.out.println("Enter username to update: ");
                                String userName = scanner.nextLine();
                                User userToUpdate = null;
                                for (User user : users) {
                                    if (user.getUserName().equals(userName)) {
                                        userToUpdate = user;
                                        break;
                                    }
                                }

                                if (userToUpdate != null) {
                                    System.out.println(updateUser(users, userToUpdate, scanner));
                                } else {
                                    System.out.println("User with username " + userName + " not found.");
                                }
                                break;
                            case 3:
                                System.out.println("Enter username to delete: ");
                                userName = scanner.nextLine();
                                User userToDelete = null;
                                for (User user : users) {
                                    if (user.getUserName().equals(userName)) {
                                        userToDelete = user;
                                        break;
                                    }
                                }

                                if (userToDelete != null) {
                                    users.remove(userToDelete);
                                    System.out.println("User deleted successfully!");
                                } else {
                                    System.out.println("User with username " + userName + " not found.");
                                }
                                break;
                            case 4:
                                if (users.isEmpty()) {
                                    System.out.println("No users available.");
                                } else {
                                    for (User user : users) {
                                        System.out.println("User ID: " + user.getUserId() + ", Username: " + user.getUserName() + ", Role: " + user.getRole());
                                    }
                                }
                                break;
                            case 5:
                                taskRelated = true;
                                break;

                            default:
                                System.out.println("Invalid option. Please select a valid option.");
                        }
                        if (taskRelated == true) {
                            break;
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a valid option.");
                        scanner.nextLine();
                    }
                }
            }

            while (true) {
                try {
                    System.out.println("1 - Create Task\n" +
                            "2 - Update Task\n" +
                            "3 - Delete Task\n" +
                            "4 - View Task\n" +
                            "5 - View All Tasks\n" +
                            "6 - Logout");
                    int option = Integer.parseInt(scanner.nextLine());

                    switch (option) {
                        case 1:
                            System.out.println("1 - Bug Task\n" +
                                    "2 - Feature Task\n" +
                                    "3 - Improvement Task");
                            option = Integer.parseInt(scanner.nextLine());

                            switch (option) {
                                case 1:
                                    tasks.add(createBugTask(scanner, loggedInUser.getUserName()));
                                    break;
                                case 2:
                                    tasks.add(createFeatureTask(scanner, loggedInUser.getUserName()));
                                    break;
                                case 3:
                                    tasks.add(createImprovementTask(scanner, loggedInUser.getUserName()));
                                    break;
                                default:
                                    System.out.println("Invalid task type. Please try again.");
                            }
                            break;
                        case 2:
                            System.out.println("Enter Task ID to update: ");
                            int taskId = Integer.parseInt(scanner.nextLine());

                            Task taskToUpdate = null;
                            for (Task task : tasks) {
                                if (task.getId() == taskId) {
                                    taskToUpdate = task;
                                    break;
                                }
                            }

                            if (taskToUpdate != null) {
                                updateTask(taskToUpdate, scanner);
                            } else {
                                System.out.println("Task with ID " + taskId + " not found.");
                            }
                            break;
                        case 3:
                            System.out.println("Enter Task ID to delete: ");
                            taskId = Integer.parseInt(scanner.nextLine());

                            Task taskToDelete = null;
                            for (Task task : tasks) {
                                if (task.getId() == taskId) {
                                    taskToDelete = task;
                                    break;
                                }
                            }

                            if (taskToDelete != null) {
                                tasks.remove(taskToDelete);
                                System.out.println("Task deleted successfully!");
                            } else {
                                System.out.println("Task with ID " + taskId + " not found.");
                            }
                            break;
                        case 4:
                            System.out.println("Enter Task ID to view: ");
                            taskId = Integer.parseInt(scanner.nextLine());

                            Task taskToView = null;
                            for (Task task : tasks) {
                                if (task.getId() == taskId) {
                                    taskToView = task;
                                    break;
                                }
                            }

                            if (taskToView != null) {
                                taskToView.viewTask();
                            } else {
                                System.out.println("Task with ID " + taskId + " not found.");
                            }
                            break;
                        case 5:
                            if (tasks.isEmpty()) {
                                System.out.println("No tasks available.");
                            } else {
                                for (Task task : tasks) {
                                    task.viewTask();
                                }
                            }
                            break;
                        case 6:
                            System.out.println("Logging out...");
                            loggedInUser = null;
                            break;
                        default:
                            System.out.println("Invalid option. Please select a valid option.");
                    }

                    if (loggedInUser == null)
                        break;

                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    scanner.nextLine();
                }
            }
        }
    }

    private static User createAdmin() {
        User admin = new User();
        admin.setUserId(1L);
        admin.setUserName("admin");
        admin.setPassword("admin");
        admin.setRole("admin");
        return admin;
    }

    private static User createUser() {
        User user = new User();
        user.setUserId(2L);
        user.setUserName("user");
        user.setPassword("user");
        user.setRole("user");
        return user;
    }

    private static User login(List<User> users, Scanner scanner) throws InputMismatchException {
        System.out.println("Enter your username: ");
        String userName = scanner.nextLine();

        System.out.println("Enter your password: ");
        String password = scanner.nextLine();

        return authenticateUser(users, userName, password);
    }

    private static User authenticateUser(List<User> users, String userName, String password) {
        for (User user : users) {
            if (user.getUserName().equals(userName) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    private static User createUser(Scanner scanner, Long userId) {
        User user = new User();
        user.setUserId(userId);

        System.out.println("Enter username: ");
        user.setUserName(scanner.nextLine());

        System.out.println("Enter password: ");
        user.setPassword(scanner.nextLine());

        System.out.println("Enter role: ");
        user.setRole(scanner.nextLine());

        return user;
    }

    private static User updateUser(List<User> users, User user, Scanner scanner) {
        String username = "";
        do {
            System.out.println("Enter username: ");
            username = scanner.nextLine();
        } while (!isUsernameAvailable(users, username));

        user.setUserName(username);

        System.out.println("Enter password: ");
        user.setPassword(scanner.nextLine());

        System.out.println("Enter role: ");
        user.setRole(scanner.nextLine());

        return user;
    }

    private static boolean isUsernameAvailable(List<User> users, String username) {
        for (User user : users) {
            if (user.getUserName().equals(username)) {
                System.out.println("Username already exists. Please enter a different username.");
                return false;
            }
        }
        return true;
    }
    private static Map<String, String> createTask(Scanner scanner, String username) {
        Map<String, String> task = new HashMap<>();

        System.out.println("Enter title: ");
        task.put("title", scanner.nextLine());

        System.out.println("Enter description");
        task.put("description", scanner.nextLine());

        System.out.println("Enter status: ");
        task.put("status", scanner.nextLine());

        System.out.println("Enter due date: ");
        task.put("dueDate", scanner.nextLine());

//        System.out.println("Enter created at: ");
        task.put("createdAt", LocalDateTime.now().toString());

//        System.out.println("Enter updated at: ");
        task.put("updatedAt", LocalDateTime.now().toString());

        System.out.println("Enter assignee: ");
        task.put("assignee", scanner.nextLine());

//        System.out.println("Enter created by: ");
        task.put("createdBy", username);

        return task;
    }

    private static BugTask createBugTask(Scanner scanner, String username) {
        BugTask bugTask = new BugTask();
        Map<String, String> task = createTask(scanner, username);
        bugTask.setTitle(task.get("title"));
        bugTask.setDescription(task.get("description"));
        bugTask.setStatus(task.get("status"));
        bugTask.setDueDate(task.get("dueDate"));
        bugTask.setCreatedAt(task.get("createdAt"));
        bugTask.setUpdatedAt(task.get("updatedAt"));
        bugTask.setAssignee(task.get("assignee"));
        bugTask.setCreatedBy(task.get("createdBy"));

        System.out.println("Enter severity: ");
        bugTask.setSeverity(scanner.nextLine());

        System.out.println("Enter step to reproduce: ");
        bugTask.setStepToReproduce(scanner.nextLine());

        return bugTask;
    }

    private static FeatureTask createFeatureTask(Scanner scanner, String username) {
        FeatureTask featureTask = new FeatureTask();

        Map<String, String> task = createTask(scanner, username);
        featureTask.setTitle(task.get("title"));
        featureTask.setDescription(task.get("description"));
        featureTask.setStatus(task.get("status"));
        featureTask.setDueDate(task.get("dueDate"));
        featureTask.setCreatedAt(task.get("createdAt"));
        featureTask.setUpdatedAt(task.get("updatedAt"));
        featureTask.setAssignee(task.get("assignee"));
        featureTask.setCreatedBy(task.get("createdBy"));

        System.out.println("Enter feature description: ");
        featureTask.setFeatureDescription(scanner.nextLine());

        System.out.println("Enter estimated effort: ");
        featureTask.setEstimatedEffort(scanner.nextLine());

        return featureTask;
    }

    private static ImprovementTask createImprovementTask(Scanner scanner, String username) {
        ImprovementTask improvementTask = new ImprovementTask();

        Map<String, String> task = createTask(scanner, username);
        improvementTask.setTitle(task.get("title"));
        improvementTask.setDescription(task.get("description"));
        improvementTask.setStatus(task.get("status"));
        improvementTask.setDueDate(task.get("dueDate"));
        improvementTask.setCreatedAt(task.get("createdAt"));
        improvementTask.setUpdatedAt(task.get("updatedAt"));
        improvementTask.setAssignee(task.get("assignee"));
        improvementTask.setCreatedBy(task.get("createdBy"));

        System.out.println("Enter current state: ");
        improvementTask.setCurrentState(scanner.nextLine());

        System.out.println("Enter proposed improvement: ");
        improvementTask.setProposedImprovement(scanner.nextLine());

        return improvementTask;
    }

    private static void updateTask(Task task, Scanner scanner) {
        System.out.println("Enter title: ");
        task.setTitle(scanner.nextLine());

        System.out.println("Enter description");
        task.setDescription(scanner.nextLine());

        System.out.println("Enter status: ");
        task.setStatus(scanner.nextLine());

        System.out.println("Enter due date: ");
        task.setDueDate(scanner.nextLine());

//        System.out.println("Enter created at: ");
//        task.setCreatedAt(scanner.nextLine());

//        System.out.println("Enter updated at: ");
        task.setUpdatedAt(LocalDateTime.now().toString());

        System.out.println("Enter assignee: ");
        task.setAssignee(scanner.nextLine());

//        System.out.println("Enter created by: ");
//        task.setCreatedBy(scanner.nextLine());

        System.out.println("Task updated successfully!");
    }
}