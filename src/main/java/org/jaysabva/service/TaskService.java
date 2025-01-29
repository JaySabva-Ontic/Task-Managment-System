package org.jaysabva.service;

import org.jaysabva.dto.TaskDto;
import org.jaysabva.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    String addTask(TaskDto task);
    String updateTask(Task task);
    Optional<Task> getTask(Long id);
    List<Task> getAllTasks();
    String deleteTask(Long id);
}
