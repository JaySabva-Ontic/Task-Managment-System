package org.jaysabva.controller;

import org.jaysabva.dto.TaskDto;
import org.jaysabva.entity.Task;
import org.jaysabva.service.Implementation.TaskServiceImplementation;
import org.jaysabva.service.TaskService;

import java.util.List;
import java.util.Optional;

public class TaskController {
    private final TaskService taskService = new TaskServiceImplementation();

    public String addTask(TaskDto task) {
        return taskService.addTask(task);
    }

    public String updateTask(Task task) {
        return taskService.updateTask(task);
    }

    public Optional<Task> getTask(Long taskId) {
        return taskService.getTask(taskId);
    }

    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    public String deleteTask(Long taskId) {
        return taskService.deleteTask(taskId);
    }
}
