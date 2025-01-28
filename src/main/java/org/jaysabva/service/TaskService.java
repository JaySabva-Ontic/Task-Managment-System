package org.jaysabva.service;

import org.jaysabva.dto.TaskDto;
import org.jaysabva.entity.Task;

import java.util.List;

public interface TaskService {
    String addTask(TaskDto task);
    String updateTask(Task task);
    Task getTask(Long id);
    List<Task> getAllTasks();
    String deleteTask(Long id);
}
