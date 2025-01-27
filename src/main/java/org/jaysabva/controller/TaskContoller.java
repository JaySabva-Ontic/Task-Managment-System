package org.jaysabva.controller;

import org.jaysabva.entity.Task;
import org.jaysabva.service.Implementation.TaskServiceImplementation;

import java.util.List;

public class TaskContoller {
    private final TaskServiceImplementation taskServiceImplementation = new TaskServiceImplementation();

    public String addTask(Task task) {
        return taskServiceImplementation.addTask(task);
    }

    public String updateTask(Task task) {
        return taskServiceImplementation.updateTask(task);
    }

    public Task getTask(Long taskId) {
        return taskServiceImplementation.getTask(taskId);
    }

    public List<Task> getAllTasks() {
        return taskServiceImplementation.getAllTasks();
    }

    public String deleteTask(Long taskId) {
        return taskServiceImplementation.deleteTask(taskId);
    }
}
