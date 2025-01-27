package org.jaysabva.repository;

import org.jaysabva.entity.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskRepository {
    Map<Integer, Task> tasks;

    public TaskRepository() {
        tasks = new HashMap<>();
    }

    public void addTask(Task task) {
        tasks.put(task.getId().intValue(), task);
    }

    public void updateTask(Task task) {
        tasks.put(task.getId().intValue(), task);
    }

    public void deleteTask(Long id) {
        tasks.remove(id.intValue());
    }

    public Task getTask(Long id) {
        return tasks.get(id.intValue());
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public boolean isTaskExists(Long id) {
        return tasks.containsKey(id.intValue());
    }
}
