package org.jaysabva.service.Implementation;

import org.jaysabva.dto.BugTaskDto;
import org.jaysabva.dto.FeatureTaskDto;
import org.jaysabva.dto.ImprovementTaskDto;
import org.jaysabva.dto.TaskDto;
import org.jaysabva.entity.BugTask;
import org.jaysabva.entity.FeatureTask;
import org.jaysabva.entity.ImprovementTask;
import org.jaysabva.entity.Task;
//import org.jaysabva.repository.Implementation.UserRepositoryImplementation;
import org.jaysabva.repository.TaskRepository;
import org.jaysabva.repository.UserRepository;
import org.jaysabva.service.TaskService;
import org.jaysabva.service.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class TaskServiceImplementation implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    public TaskServiceImplementation(TaskRepository taskRepository, UserRepository userRepository, SequenceGeneratorService sequenceGeneratorService) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;
    }

    @Override
    public Map<String, String> addTask(TaskDto task) {
        try {
            if (!userRepository.existsUserByUserName(task.getAssignee())) {
                return Map.of("message", "Assignee does not exist");
            }

            Task newTask;
            if (Objects.equals(task.getTaskType(), "BUG")) {
                newTask = new BugTask(task.getTitle(),  task.getDescription(), task.getStatus(), task.getStartDate(), task.getDueDate(), task.getCreatedAt(), task.getUpdatedAt(), task.getAssignee(), task.getCreatedBy(), ((BugTaskDto) task).getSeverity(), ((BugTaskDto) task).getStepToReproduce(), task.getTaskType(), task.getStoryPoints());
            } else if (Objects.equals(task.getTaskType(), "FEATURE")) {
                newTask = new FeatureTask(task.getTitle(), task.getDescription(), task.getStatus(), task.getStartDate(), task.getDueDate(), task.getCreatedAt(), task.getUpdatedAt(), task.getAssignee(), task.getCreatedBy(), ((FeatureTaskDto) task).getFeatureDescription(), ((FeatureTaskDto) task).getEstimatedEffort(), task.getTaskType(), task.getStoryPoints());
            } else if (Objects.equals(task.getTaskType(), "IMPROVEMENT")) {
                newTask = new ImprovementTask(task.getTitle(), task.getDescription(), task.getStatus(),task.getStartDate(),  task.getDueDate(), task.getCreatedAt(), task.getUpdatedAt(), task.getAssignee(), task.getCreatedBy(), ((ImprovementTaskDto) task).getProposedImprovement(), task.getTaskType(), task.getStoryPoints());
            } else {
                System.out.println("Invalid task type");
                return Map.of("message", "Invalid task type");
            }

            newTask.setId(sequenceGeneratorService.generateSequence("taskCounter"));
            taskRepository.save(newTask);

            return Map.of("message", "Task added successfully");
        } catch (Exception e) {
            return Map.of("message", "Error adding task");
        }
    }

    @Override
    public Map<String, String> updateTask(TaskDto task) {
        try {
            if (!taskRepository.existsById(String.valueOf(task.getId()))) {
                return Map.of("message", "Task does not exist");
            }

            synchronized (task.getId()) {
                Task oldTask = taskRepository.findById(task.getId()).get();
                oldTask.setTitle(task.getTitle());
                oldTask.setDescription(task.getDescription());
                oldTask.setStatus(task.getStatus());
                oldTask.setStartDate(task.getStartDate());
                oldTask.setDueDate(task.getDueDate());
                oldTask.setUpdatedAt(LocalDateTime.now());
                oldTask.setAssignee(task.getAssignee());
                oldTask.setCreatedBy(task.getCreatedBy());
                oldTask.setStoryPoints(task.getStoryPoints());
                if (Objects.equals(task.getTaskType(), "BUG")) {
                    ((BugTask) oldTask).setSeverity(((BugTaskDto) task).getSeverity());
                    ((BugTask) oldTask).setStepToReproduce(((BugTaskDto) task).getStepToReproduce());
                } else if (task.getTaskType() == "FEATURE") {
                    ((FeatureTask) oldTask).setFeatureDescription(((FeatureTaskDto) task).getFeatureDescription());
                    ((FeatureTask) oldTask).setEstimatedEffort(((FeatureTaskDto) task).getEstimatedEffort());
                } else if (task.getTaskType() == "IMPROVEMENT") {
                    ((ImprovementTask) oldTask).setProposedImprovement(((ImprovementTaskDto) task).getProposedImprovement());
                } else {
                    System.out.println("Invalid task type");
                    return Map.of("message", "Invalid task type");
                }

                taskRepository.save(oldTask);
            }
            return Map.of("message", "Task updated successfully");
        } catch (Exception e) {
            return Map.of("message", "Error updating task");
        }
    }

    @Override
    public Optional<Task> getTask(Long id) {
        try {
            return taskRepository.findTaskById(id);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Task> getAllTasks() {
        try {
            return taskRepository.findAll();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Map<String, String> deleteTask(Long id) {
        try {
            if (!taskRepository.existsById(id)) {
                return Map.of("message", "Task does not exist");
            }

            taskRepository.deleteById(id);
            return Map.of("message", "Task deleted successfully");
        } catch (Exception e) {
            return Map.of("message", "Error deleting task");
        }
    }

    @Override
    public List<Task> getTasksByStoryPoints(Long storyPoints) {
        try {
            return taskRepository.findByStoryPoints(storyPoints);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Map<Long, List<Task>> getTaskByStoryPoints() {
        try {
            List<Task> curr = taskRepository.findAll();
            Map<Long, List<Task>> res = new HashMap<>();
            for (Task task : curr) {
                if (res.get(task.getStoryPoints()) == null) {
                    res.put(task.getStoryPoints(), new ArrayList<>());
                }
                res.get(task.getStoryPoints()).add(task);
            }

            return res;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Map<Long, List<Task>> getTasksByStoryPointsRange(Long start, Long end) {
        try {
            List<Task> curr =  taskRepository.findByStoryPointsBetween(start - 1, end);
            Map<Long, List<Task>> res = new HashMap<>();
            for (Task task : curr) {
                if (res.get(task.getStoryPoints()) == null) {
                    res.put(task.getStoryPoints(), new ArrayList<>());
                }
                res.get(task.getStoryPoints()).add(task);
            }

            return res;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Task> getTaskByTaskType(String taskType) {
        try {
            return taskRepository.findByTaskType(taskType);
        } catch (Exception e) {
            return null;
        }
    }
}
