package org.jaysabva.controller;

import org.jaysabva.dto.TaskDto;
import org.jaysabva.entity.Task;
import org.jaysabva.service.Implementation.TaskServiceImplementation;
import org.jaysabva.service.TaskService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TaskController {
    private final TaskService taskService = new TaskServiceImplementation();

    public Map<String, String> addTask(TaskDto task) {
        return taskService.addTask(task);
    }

    public Map<String, String> updateTask(Task task) {
        return taskService.updateTask(task);
    }

    public Optional<Task> getTask(Long taskId) {
        return taskService.getTask(taskId);
    }

    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    public Map<String, String> deleteTask(Long taskId) {
        return taskService.deleteTask(taskId);
    }
}
