package org.jaysabva.service.Implementation;

import org.jaysabva.entity.Task;
import org.jaysabva.repository.Implementation.TaskRepositoryImplementation;
import org.jaysabva.repository.TaskRepository;
import org.jaysabva.service.TaskService;

import java.util.List;

public class TaskServiceImplementation implements TaskService {

    private final TaskRepository taskRepository = new TaskRepositoryImplementation();

    @Override
    public String addTask(Task task) {
        try {
            taskRepository.addTask(task);
            return "Task added successfully";
        } catch (Exception e) {
            return "Error adding task";
        }
    }

    @Override
    public String updateTask(Task task) {
        try {
            if (!taskRepository.isTaskExists(task.getId())) {
                return "Task does not exist";
            }

            taskRepository.updateTask(task);
            return "Task updated successfully";
        } catch (Exception e) {
            return "Error updating task";
        }
    }

    @Override
    public Task getTask(Long id) {
        try {
            return taskRepository.getTask(id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Task> getAllTasks() {
        try {
            return taskRepository.getAllTasks();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String deleteTask(Long id) {
        try {
            if (!taskRepository.isTaskExists(id)) {
                return "Task does not exist";
            }

            taskRepository.deleteTask(id);
            return "Task deleted successfully";
        } catch (Exception e) {
            return "Error deleting task";
        }
    }
}
