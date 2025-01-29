package org.jaysabva.repository.Implementation;

import org.jaysabva.entity.Task;
import org.jaysabva.repository.TaskRepository;

import java.util.*;

public class TaskRepositoryImplementation implements TaskRepository {
    Map<Long, Task> tasks = new HashMap<>();

    private static TaskRepositoryImplementation instance = null;
    private TaskRepositoryImplementation() {
    }

    public static TaskRepositoryImplementation getInstance() {
        if (instance == null) {
            return new TaskRepositoryImplementation();
        }
        return instance;
    }

    @Override
    public void addTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void deleteTask(Long id) {
        tasks.remove(id);
    }

    @Override
    public Optional<Task> getTask(Long id) {
        return Optional.ofNullable(tasks.get(id));
    }

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public boolean isTaskExists(Long id) {
        return tasks.containsKey(id);
    }
}
