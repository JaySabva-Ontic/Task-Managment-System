package org.jaysabva;

import org.jaysabva.controller.TaskContoller;
import org.jaysabva.controller.UserController;
import org.jaysabva.entity.*;

import java.time.LocalDateTime;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        UserController userController = new UserController();
        TaskContoller taskContoller = new TaskContoller();

        userController.registerUser("admin", "admin", "admin");
        userController.registerUser("user", "user", "user");


        Scanner scanner = new Scanner(System.in);

        while (true) {

            User loggedInUser = null;
            while (true) {
                try {
                    String[] loginInput = loginInput(scanner);
                    loggedInUser = userController.loginUser(loginInput[0], loginInput[1]);
                    if (loggedInUser != null) {
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

            if (loggedInUser.getRole().equals("admin")) {
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
                        String[] signUpInput;
                        String username;

                        switch (option) {
                            case 1:
                                signUpInput = signUpInput(scanner);
                                userController.registerUser(signUpInput[0], signUpInput[1], signUpInput[2]);

                                break;
                            case 2:
                                do {
                                    System.out.println("Enter username to update: ");
                                    username = scanner.nextLine();
                                } while (userController.getUser(username) == null);

                                signUpInput = signUpInput(scanner);

                                User user = new User(signUpInput[0], signUpInput[1], signUpInput[2]);

                                System.out.println(userController.updateUser(username, user));

                                break;
                            case 3:
                                System.out.println("Enter username to delete: ");
                                username = scanner.nextLine();

                                System.out.println(userController.deleteUserAdmin(username));

                                break;
                            case 4:
                                List<User> users = userController.getAllUsers();
                                for (User u : users) {
                                    System.out.println("Username: " + u.getUserName() + ", Role: " + u.getRole());
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
                                    taskContoller.addTask(createBugTask(scanner, loggedInUser.getUserName()));
                                    break;
                                case 2:
                                    taskContoller.addTask(createFeatureTask(scanner, loggedInUser.getUserName()));
                                    break;
                                case 3:
                                    taskContoller.addTask(createImprovementTask(scanner, loggedInUser.getUserName()));
                                    break;
                                default:
                                    System.out.println("Invalid task type. Please try again.");
                            }
                            break;
                        case 2:
                            System.out.println("Enter Task ID to update: ");
                            int taskId = Integer.parseInt(scanner.nextLine());
                            Task taskToUpdate = taskContoller.getTask((long) taskId);

                            if (taskToUpdate == null) {
                                System.out.println("Task with ID " + taskId + " not found.");
                                break;
                            }

                            taskContoller.updateTask(updateTask(taskToUpdate, scanner));
                            System.out.println("Task updated successfully!");

                            break;
                        case 3:
                            System.out.println("Enter Task ID to delete: ");
                            taskId = Integer.parseInt(scanner.nextLine());

                            System.out.println(taskContoller.deleteTask((long) taskId));
                            break;
                        case 4:
                            System.out.println("Enter Task ID to view: ");
                            taskId = Integer.parseInt(scanner.nextLine());

                            Task taskToView = taskContoller.getTask((long) taskId);
                            if (taskToView == null) {
                                System.out.println("Task with ID " + taskId + " not found.");
                            } else {
                                taskToView.viewTask();
                            }
                            break;
                        case 5:
                            List<Task> allTasks = taskContoller.getAllTasks();
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

    private static String[] loginInput(Scanner scanner) {
        System.out.println("Enter your username: ");
        String userName = scanner.nextLine();

        System.out.println("Enter your password: ");
        String password = scanner.nextLine();

        return new String[]{userName, password};
    }

    private static String[] signUpInput(Scanner scanner) {
        System.out.println("Enter your username: ");
        String userName = scanner.nextLine();

        System.out.println("Enter your password: ");
        String password = scanner.nextLine();

        System.out.println("Enter your role: ");
        String role = scanner.nextLine();

        return new String[]{userName, password, role};
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

    private static Task updateTask(Task task, Scanner scanner) {
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

        if (task instanceof BugTask) {
            BugTask bugTask = (BugTask) task;
            System.out.println("Enter severity: ");
            bugTask.setSeverity(scanner.nextLine());

            System.out.println("Enter step to reproduce: ");
            bugTask.setStepToReproduce(scanner.nextLine());
        } else if (task instanceof FeatureTask) {
            FeatureTask featureTask = (FeatureTask) task;
            System.out.println("Enter feature description: ");
            featureTask.setFeatureDescription(scanner.nextLine());

            System.out.println("Enter estimated effort: ");
            featureTask.setEstimatedEffort(scanner.nextLine());
        } else if (task instanceof ImprovementTask) {
            ImprovementTask improvementTask = (ImprovementTask) task;
            System.out.println("Enter current state: ");
            improvementTask.setCurrentState(scanner.nextLine());

            System.out.println("Enter proposed improvement: ");
            improvementTask.setProposedImprovement(scanner.nextLine());
        }

        return task;
    }
}