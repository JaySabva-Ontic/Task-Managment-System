package org.jaysabva.service;

import org.jaysabva.dto.TaskDto;
import org.jaysabva.entity.Task;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public interface TaskService {
    Map<String, String> addTask(TaskDto task);
    Map<String, String> updateTask(TaskDto task);
    Optional<Task> getTask(Long id);
    List<Task> getAllTasks();
    Map<String, String> deleteTask(Long id);
    List<Task> getTasksByStoryPoints(Long storyPoints);
    Map<Long, List<Task>> getTaskByStoryPoints();
    Map<Long, List<Task>> getTasksByStoryPointsRange(Long start, Long end);
}
