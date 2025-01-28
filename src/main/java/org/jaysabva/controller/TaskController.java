package org.jaysabva.controller;

import org.jaysabva.entity.Task;
import org.jaysabva.service.Implementation.TaskServiceImplementation;
import org.jaysabva.service.TaskService;

import java.util.List;

public class TaskController {
    private final TaskService taskService = new TaskServiceImplementation();

    public String addTask(Task task) {
        return taskService.addTask(task);
    }

    public String updateTask(Task task) {
        return taskService.updateTask(task);
    }

    public Task getTask(Long taskId) {
        return taskService.getTask(taskId);
    }

    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    public String deleteTask(Long taskId) {
        return taskService.deleteTask(taskId);
    }
}
