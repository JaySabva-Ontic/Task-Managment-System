package org.jaysabva.repository;

import org.jaysabva.entity.Task;

import java.util.List;

public interface TaskRepository {
    void addTask(Task task);
    void updateTask(Task task);
    void deleteTask(Long id);
    Task getTask(Long id);
    List<Task> getAllTasks();
    boolean isTaskExists(Long id);
}
