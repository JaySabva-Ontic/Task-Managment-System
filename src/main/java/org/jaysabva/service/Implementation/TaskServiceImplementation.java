package org.jaysabva.service.Implementation;

import org.jaysabva.dto.BugTaskDto;
import org.jaysabva.dto.FeatureTaskDto;
import org.jaysabva.dto.ImprovementTaskDto;
import org.jaysabva.dto.TaskDto;
import org.jaysabva.entity.BugTask;
import org.jaysabva.entity.FeatureTask;
import org.jaysabva.entity.ImprovementTask;
import org.jaysabva.entity.Task;
import org.jaysabva.repository.Implementation.TaskRepositoryImplementation;
import org.jaysabva.repository.Implementation.UserRepositoryImplementation;
import org.jaysabva.repository.TaskRepository;
import org.jaysabva.repository.UserRepository;
import org.jaysabva.service.TaskService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class TaskServiceImplementation implements TaskService {

    private final TaskRepository taskRepository = new TaskRepositoryImplementation();
    private final UserRepository userRepository = new UserRepositoryImplementation();

    @Override
    public String addTask(TaskDto task) {
        try {

            if (userRepository.getUser(task.getAssignee()) == null) {
                return "Assignee does not exist";
            }

            Task newTask;
            if (Objects.equals(task.getTaskType(), "Bug")) {
                newTask = new BugTask(task.getTitle(),  task.getDescription(), task.getStatus(), task.getStartDate(), task.getDueDate(), task.getCreatedAt(), task.getUpdatedAt(), task.getAssignee(), task.getCreatedBy(), ((BugTaskDto) task).getSeverity(), ((BugTaskDto) task).getStepToReproduce(), task.getTaskType());
            } else if (Objects.equals(task.getTaskType(), "Feature")) {
                newTask = new FeatureTask(task.getTitle(), task.getDescription(), task.getStatus(), task.getStartDate(), task.getDueDate(), task.getCreatedAt(), task.getUpdatedAt(), task.getAssignee(), task.getCreatedBy(), ((FeatureTaskDto) task).getFeatureDescription(), ((FeatureTaskDto) task).getEstimatedEffort(), task.getTaskType());
            } else if (Objects.equals(task.getTaskType(), "Improvement")) {
                newTask = new ImprovementTask(task.getTitle(), task.getDescription(), task.getStatus(),task.getStartDate(),  task.getDueDate(), task.getCreatedAt(), task.getUpdatedAt(), task.getAssignee(), task.getCreatedBy(), ((ImprovementTaskDto) task).getProposedImprovement(), task.getTaskType());
            } else {
                return "Invalid task type";
            }

            taskRepository.addTask(newTask);

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
