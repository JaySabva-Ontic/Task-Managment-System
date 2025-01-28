package org.jaysabva.repository.Implementation;

import org.jaysabva.entity.Task;
import org.jaysabva.repository.TaskRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskRepositoryImplementation implements TaskRepository {
    Map<Long, Task> tasks = new HashMap<>();

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
    public Task getTask(Long id) {
        return tasks.get(id);
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
