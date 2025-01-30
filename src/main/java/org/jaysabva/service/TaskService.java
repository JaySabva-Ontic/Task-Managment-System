package org.jaysabva.service;

import org.jaysabva.dto.TaskDto;
import org.jaysabva.entity.Task;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TaskService {
    Map<String, String> addTask(TaskDto task);
    Map<String, String> updateTask(Task task);
    Optional<Task> getTask(Long id);
    List<Task> getAllTasks();
    Map<String, String> deleteTask(Long id);
}
