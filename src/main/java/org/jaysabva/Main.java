package org.jaysabva;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<User> users = new ArrayList<>();
        users.add(createAdmin());
        users.add(createUser());

        Scanner scanner = new Scanner(System.in);

        User loggedInUser = null;
        while (true) {
//            System.out.println("Enter 1 to login, 2 to exit: ");
//            int option = scanner.nextInt();
//            scanner.nextLine();
//
//            if (option == 1) {
                loggedInUser = login(users, scanner);
                if (loggedInUser != null) {
                    System.out.println("Login successful!");
                    break;
                } else {
                    System.out.println("Login failed! Retry.");
                }
//            } else if (option == 2) {
//                System.exit(0);
//            }
        }

        if (loggedInUser.getRole().equals("admin")) {
            while (true) {
                System.out.print("Enter * to create a user, 2 to exit: ");
                char option = scanner.next().charAt(0);
                scanner.nextLine();

                if (option == '*') {
                    System.out.println(createUser(scanner, (long) users.size() + 1));
                } else if (option == '2') {
                    System.exit(0);
                    break;
                }
            }
        }

        List<Task> tasks = new ArrayList<>();
        while (true) {
            System.out.println("Enter 1 to create a bug task, 2 to create a feature task, 3 to create an improvement task, 4 to view tasks, 5 to exit: ");
            int option = Integer.parseInt(scanner.nextLine());

            if (option == 1) {
                tasks.add(createBugTask(scanner));
            } else if (option == 2) {
                tasks.add(createFeatureTask(scanner));
            } else if (option == 3) {
                tasks.add(createImprovementTask(scanner));
            } else if (option == 4) {
                for (Task task : tasks) {
                    task.viewTask();
                }
            } else if (option == 5) {
                System.exit(0);
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
    private static User login(List<User> users, Scanner scanner) {
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

    private static String createUser(Scanner scanner, Long userId) {
        User user = new User();

        user.setUserId(userId);

        System.out.println("Enter username: ");
        user.setUserName(scanner.nextLine());

        System.out.println("Enter password: ");
        user.setPassword(scanner.nextLine());

        System.out.println("Enter role: ");
        user.setRole(scanner.nextLine());

        return "User created successfully!";
    }

    private static Map<String, String> createTask(Scanner scanner) {
        Map<String, String> task = new HashMap<>();

        System.out.println("Enter title: ");
        task.put("title", scanner.nextLine());

        System.out.println("Enter description");
        task.put("description", scanner.nextLine());

        System.out.println("Enter status: ");
        task.put("status", scanner.nextLine());

        System.out.println("Enter due date: ");
        task.put("dueDate", scanner.nextLine());

        System.out.println("Enter created at: ");
        task.put("createdAt", scanner.nextLine());

        System.out.println("Enter updated at: ");
        task.put("updatedAt", scanner.nextLine());

        System.out.println("Enter assignee: ");
        task.put("assignee", scanner.nextLine());

        System.out.println("Enter created by: ");
        task.put("createdBy", scanner.nextLine());

        return task;
    }

    private static BugTask createBugTask(Scanner scanner) {
        BugTask bugTask = new BugTask();

        Map<String, String> task = createTask(scanner);
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

    private static FeatureTask createFeatureTask(Scanner scanner) {
        FeatureTask featureTask = new FeatureTask();

        Map<String, String> task = createTask(scanner);
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

    private static ImprovementTask createImprovementTask(Scanner scanner) {
        ImprovementTask improvementTask = new ImprovementTask();

        Map<String, String> task = createTask(scanner);
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
}