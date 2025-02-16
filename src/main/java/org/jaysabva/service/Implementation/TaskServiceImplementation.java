package org.jaysabva.service.Implementation;

import org.jaysabva.dto.*;
import org.jaysabva.entity.*;
import org.jaysabva.entity.elastic.BugTaskES;
import org.jaysabva.entity.elastic.FeatureTaskES;
import org.jaysabva.entity.elastic.ImprovementTaskES;
import org.jaysabva.entity.elastic.TaskES;
import org.jaysabva.repository.CustomFieldRepository;
//import org.jaysabva.repository.Implementation.UserRepositoryImplementation;
import org.jaysabva.repository.TaskRepository;
import org.jaysabva.repository.UserRepository;
import org.jaysabva.repository.elastic.TaskESRepository;
import org.jaysabva.service.TaskService;
import org.jaysabva.service.SequenceGeneratorService;
import org.jaysabva.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.RefreshPolicy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class TaskServiceImplementation implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final SequenceGeneratorService sequenceGeneratorService;
    private final TaskESRepository taskESRepository;
    private final CustomFieldRepository customFieldRepository;

    @Autowired
    public TaskServiceImplementation(TaskRepository taskRepository, UserRepository userRepository, SequenceGeneratorService sequenceGeneratorService, TaskESRepository taskESRepository, CustomFieldRepository customFieldRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.sequenceGeneratorService = sequenceGeneratorService;
        this.taskESRepository = taskESRepository;
        this.customFieldRepository = customFieldRepository;
    }

    @Override
    public Map<String, String> addTask(TaskDto task, boolean refresh) {
        try {
            if (!userRepository.existsUserByUserName(task.getAssignee())) {
                return Map.of("message", "Assignee does not exist");
            }

            if (!dynamicFieldValidation(task.getDynamicField())) {
                return Map.of("message", "Invalid dynamic field");
            }

            Task newTask;
            if (Objects.equals(task.getTaskType(), "BUG")) {
                newTask = new BugTask(task.getTitle(),  task.getDescription(), task.getStatus(), task.getStartDate(), task.getDueDate(), task.getCreatedAt(), task.getUpdatedAt(), task.getAssignee(), task.getCreatedBy(), ((BugTaskDto) task).getSeverity(), ((BugTaskDto) task).getStepToReproduce(), task.getTaskType(), task.getStoryPoints(), task.getDynamicField());
            } else if (Objects.equals(task.getTaskType(), "FEATURE")) {
                newTask = new FeatureTask(task.getTitle(), task.getDescription(), task.getStatus(), task.getStartDate(), task.getDueDate(), task.getCreatedAt(), task.getUpdatedAt(), task.getAssignee(), task.getCreatedBy(), ((FeatureTaskDto) task).getFeatureDescription(), ((FeatureTaskDto) task).getEstimatedEffort(), task.getTaskType(), task.getStoryPoints(), task.getDynamicField());
            } else if (Objects.equals(task.getTaskType(), "IMPROVEMENT")) {
                newTask = new ImprovementTask(task.getTitle(), task.getDescription(), task.getStatus(),task.getStartDate(),  task.getDueDate(), task.getCreatedAt(), task.getUpdatedAt(), task.getAssignee(), task.getCreatedBy(), ((ImprovementTaskDto) task).getProposedImprovement(), task.getTaskType(), task.getStoryPoints(), task.getDynamicField());
            } else {
                System.out.println("Invalid task type");
                return Map.of("message", "Invalid task type");
            }

            Long taskID = sequenceGeneratorService.generateSequence("taskCounter");
            newTask.setId(taskID);
            NestedField nestedField = new NestedField("N1", Collections.singletonList(new Check("C1", "C2")));
            newTask.setNestedField(nestedField);
            newTask.setNestedDynamicField(task.getNestedDynamicField());

            taskRepository.save(newTask);
            taskESRepository.save(newTask, (refresh) ? RefreshPolicy.IMMEDIATE : RefreshPolicy.NONE);

            return Map.of("message", "Task added successfully");
        } catch (Exception e) {
            return Map.of("message", "Error adding task");
        }
    }

    @Override
    public Map<String, String> updateTask(TaskDto task) {
        try {
            if (!taskRepository.existsById(task.getId())) {
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
                oldTask.setDynamicField(task.getDynamicField());
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
                taskESRepository.save(oldTask);
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
            taskESRepository.deleteById(id);
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

//    @Override
//    @Deprecated
//    public Map<Long, List<Task>> getTasksByStoryPointsRange(Long start, Long end) {
//        try {
//            List<Task> curr =  taskRepository.findByStoryPointsBetween(start - 1, end);
//            Map<Long, List<Task>> res = new HashMap<>();
//            for (Task task : curr) {
//                if (res.get(task.getStoryPoints()) == null) {
//                    res.put(task.getStoryPoints(), new ArrayList<>());
//                }
//                res.get(task.getStoryPoints()).add(task);
//            }
//
//            return res;
//        } catch (Exception e) {
//            return null;
//        }
//    }

    @Override
    public Map<Long, List<Task>> getTasksByStoryPointsRange(Long start, Long end) {
        try {
            List<Task> res = taskESRepository.findTasksByStoryPointsBetween(start, end);
            Map<Long, List<Task>> map = new HashMap<>();
            for (Task task : res) {
                if (map.get(task.getStoryPoints()) == null) {
                    map.put(task.getStoryPoints(), new ArrayList<>());
                }
                map.get(task.getStoryPoints()).add(task);
            }

            return map;
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

    @Override
    public List<Task> searchTask(String search) {
        try {
            List<Task> res = taskESRepository.universalSearch(search);
            return res;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Map<String, String> addCustomField(CustomFieldDto customFieldDto) {
        try {
            if (!userRepository.existsUserByUserName(customFieldDto.getCreatedBy())) {
                return Map.of("message", "User does not exist");
            }

            CustomField customField = new CustomField(customFieldDto.getName(), customFieldDto.getType(), customFieldDto.isEnabled(), customFieldDto.getCreatedBy());

            Long fieldID = sequenceGeneratorService.generateSequence("customFieldCounter");
            customField.setId(fieldID);
            customFieldRepository.save(customField);

            return Map.of("message", "Custom field added successfully");
        } catch (Exception e) {
            return Map.of("message", "Error adding custom field");
        }
    }

    @Override
    public Map<String, String> updateCustomField(Long id, String status) {
        try {
            if (!customFieldRepository.existsById(id)) {
                return Map.of("message", "Custom field does not exist");
            }

            synchronized (id) {
                CustomField customField = customFieldRepository.findById(id).get();
                customField.setEnabled(Boolean.parseBoolean(status));
                customFieldRepository.save(customField);
            }

            return Map.of("message", "Custom field updated successfully");
        } catch (Exception e) {
            return Map.of("message", "Error updating custom field");
        }
    }


    @Override
    public List<Task> searchTaskByDateRange(String startDate, String endDate) {
        try {
            List<Task> res = taskESRepository.findTaskByDateRange(startDate, endDate);
            return res;
        } catch (Exception e) {
            return null;
        }
    }

    private boolean dynamicFieldValidation(Map<Long, Object> dynamicField) {
        List<CustomField> customFields = customFieldRepository.findAllById(dynamicField.keySet());
        Map<Long, CustomField> customFieldMap = new HashMap<>();
        for (CustomField customField : customFields) {
            customFieldMap.put(customField.getId(), customField);
        }

        if (customFields.size() != dynamicField.size()) {
            return false;
        }

        for (Map.Entry<Long, Object> entry : dynamicField.entrySet()) {
            CustomField customField = customFieldMap.get(entry.getKey());
            if (!customField.isEnabled()) {
                return false;
            }

            if (!customField.getType().getClassType().equals(entry.getValue().getClass()) && !ValidationUtil.isNumber(customField.getType().getClassType(), entry.getValue()) && !ValidationUtil.isList(customField.getType().getClassType(), entry.getValue()) && !ValidationUtil.isDate(customField.getType().getClassType(), (String) entry.getValue())) {
                return false;
            }
        }
        return true;
    }

}
