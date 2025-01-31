package org.jaysabva.repository;

import org.jaysabva.dto.TaskDto;
import org.jaysabva.entity.Task;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface TaskRepository {
    void addTask(Task task);
    void updateTask(TaskDto task);
    void deleteTask(Long id);
    Optional<Task> getTask(Long id);
    List<Task> getAllTasks();
    boolean isTaskExists(Long id);
    List<Task> getTasksByStoryPoint(Long storyPoints);
    Map<Long, List<Task>> getTasksByStoryPoints();
    Map<Long, List<Task>> getTasksByStoryPointsRange(Long start, Long end);
}
