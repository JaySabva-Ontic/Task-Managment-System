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
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class TaskServiceImplementation implements TaskService {

    private final TaskRepository taskRepository = TaskRepositoryImplementation.getInstance();
    private final UserRepository userRepository = UserRepositoryImplementation.getInstance();

    @Override
    public Map<String, String> addTask(TaskDto task) {
        try {
            if (!userRepository.userExists(task.getAssignee())) {
                return Map.of("message", "Assignee does not exist");
            }

            Task newTask;
            if (Objects.equals(task.getTaskType(), "BUG")) {
                newTask = new BugTask(task.getTitle(),  task.getDescription(), task.getStatus(), task.getStartDate(), task.getDueDate(), task.getCreatedAt(), task.getUpdatedAt(), task.getAssignee(), task.getCreatedBy(), ((BugTaskDto) task).getSeverity(), ((BugTaskDto) task).getStepToReproduce(), task.getTaskType());
            } else if (Objects.equals(task.getTaskType(), "FEATURE")) {
                newTask = new FeatureTask(task.getTitle(), task.getDescription(), task.getStatus(), task.getStartDate(), task.getDueDate(), task.getCreatedAt(), task.getUpdatedAt(), task.getAssignee(), task.getCreatedBy(), ((FeatureTaskDto) task).getFeatureDescription(), ((FeatureTaskDto) task).getEstimatedEffort(), task.getTaskType());
            } else if (Objects.equals(task.getTaskType(), "IMPROVEMENT")) {
                newTask = new ImprovementTask(task.getTitle(), task.getDescription(), task.getStatus(),task.getStartDate(),  task.getDueDate(), task.getCreatedAt(), task.getUpdatedAt(), task.getAssignee(), task.getCreatedBy(), ((ImprovementTaskDto) task).getProposedImprovement(), task.getTaskType());
            } else {
                System.out.println("Invalid task type");
                return Map.of("message", "Invalid task type");
            }

            taskRepository.addTask(newTask);

            return Map.of("message", "Task added successfully");
        } catch (Exception e) {
            return Map.of("message", "Error adding task");
        }
    }

    @Override
    public Map<String, String> updateTask(Task task) {
        try {
            if (!taskRepository.isTaskExists(task.getId())) {
                return Map.of("message", "Task does not exist");
            }
            Task oldTask = taskRepository.getTask(task.getId()).get();
            taskRepository.updateTask(task);
            return Map.of("message", "Task updated successfully");
        } catch (Exception e) {
            return Map.of("message", "Error updating task");
        }
    }

    @Override
    public Optional<Task> getTask(Long id) {
        try {
            return taskRepository.getTask(id);
        } catch (Exception e) {
            return Optional.empty();
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
    public Map<String, String> deleteTask(Long id) {
        try {
            if (!taskRepository.isTaskExists(id)) {
                return Map.of("message", "Task does not exist");
            }

            taskRepository.deleteTask(id);
            return Map.of("message", "Task deleted successfully");
        } catch (Exception e) {
            return Map.of("message", "Error deleting task");
        }
    }
}
