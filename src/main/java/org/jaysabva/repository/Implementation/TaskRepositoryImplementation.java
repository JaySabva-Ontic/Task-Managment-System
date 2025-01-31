package org.jaysabva.repository.Implementation;

import org.jaysabva.dto.BugTaskDto;
import org.jaysabva.dto.FeatureTaskDto;
import org.jaysabva.dto.ImprovementTaskDto;
import org.jaysabva.dto.TaskDto;
import org.jaysabva.entity.BugTask;
import org.jaysabva.entity.FeatureTask;
import org.jaysabva.entity.ImprovementTask;
import org.jaysabva.entity.Task;
import org.jaysabva.repository.TaskRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public class TaskRepositoryImplementation implements TaskRepository {
    List<Task> tasks = new ArrayList<>();
    SortedMap<Long, Long> taskIdToIdx = new TreeMap<>();
    SortedMap<Long, Set<Long>> storyPointsToTasks = new TreeMap<>();

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
        tasks.add(task);
        taskIdToIdx.put(task.getId(), tasks.size() - 1L);
        if (storyPointsToTasks.get(task.getStoryPoints()) == null) {
            storyPointsToTasks.put(task.getStoryPoints(), new HashSet<>());
        }

        storyPointsToTasks.get(task.getStoryPoints()).add(task.getId());
    }

    @Override
    public void updateTask(TaskDto task) {
        Long idx = taskIdToIdx.get(task.getId());
        if (idx == null) {
            return;
        }
        storyPointsToTasks.get(tasks.get(idx.intValue()).getStoryPoints()).remove(task.getId());

        Task oldTask = tasks.get(idx.intValue());
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
        } else if (Objects.equals(task.getTaskType(), "FEATURE")) {
            ((FeatureTask) oldTask).setFeatureDescription(((FeatureTaskDto) task).getFeatureDescription());
            ((FeatureTask) oldTask).setEstimatedEffort(((FeatureTaskDto) task).getEstimatedEffort());
        } else if (Objects.equals(task.getTaskType(), "IMPROVEMENT")) {
            ((ImprovementTask) oldTask).setProposedImprovement(((ImprovementTaskDto) task).getProposedImprovement());
        } else {
            System.out.println("Invalid task type");
            return;
        }
        tasks.set(idx.intValue(), oldTask);
        if (storyPointsToTasks.get(oldTask.getStoryPoints()) == null) {
            storyPointsToTasks.put(oldTask.getStoryPoints(), new HashSet<>());
        }
        storyPointsToTasks.get(oldTask.getStoryPoints()).add(task.getId());
    }

    @Override
    public void deleteTask(Long id) {
        Long idx = taskIdToIdx.get(id);
        if (idx == null) {
            return;
        }

        tasks.set(idx.intValue(), null);
        taskIdToIdx.remove(id);
        storyPointsToTasks.get(tasks.get(idx.intValue()).getStoryPoints()).remove(id);
    }

    @Override
    public Optional<Task> getTask(Long id) {
        Long idx = taskIdToIdx.get(id);
        if (idx == null) {
            return Optional.empty();
        }

        return Optional.of(tasks.get(idx.intValue()));
    }

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    @Override
    public boolean isTaskExists(Long id) {
        return taskIdToIdx.get(id) != null;
    }

    @Override
    public List<Task> getTasksByStoryPoint(Long storyPoints) {
        Set<Long> taskIds = storyPointsToTasks.get(storyPoints);
        if (taskIds == null) {
            return new ArrayList<>();
        }

        List<Task> listOfTasks = new ArrayList<>();
        for (Long taskId : taskIds) {
            listOfTasks.add(tasks.get(taskIdToIdx.get(taskId).intValue()));
        }

        return listOfTasks;
    }

    @Override
    public Map<Long, List<Task>> getTasksByStoryPoints() {
        Map<Long, List<Task>> tasksByStoryPoints = new HashMap<>();
        for (Task task : tasks) {
            if (tasksByStoryPoints.get(task.getStoryPoints()) == null) {
                tasksByStoryPoints.put(task.getStoryPoints(), new ArrayList<>());
            }
            tasksByStoryPoints.get(task.getStoryPoints()).add(task);
        }

        return tasksByStoryPoints;
    }

    @Override
    public Map<Long, List<Task>> getTasksByStoryPointsRange(Long start, Long end) {
        Map<Long, List<Task>> tasksByStoryPoints = new HashMap<>();
        Map<Long, Set<Long>> subList = storyPointsToTasks.subMap(start, end);
        for (Map.Entry<Long, Set<Long>> entry : subList.entrySet()) {
            for (Long taskId : entry.getValue()) {
                if (tasksByStoryPoints.get(entry.getKey()) == null) {
                    tasksByStoryPoints.put(entry.getKey(), new ArrayList<>());
                }
                tasksByStoryPoints.get(entry.getKey()).add(tasks.get(taskIdToIdx.get(taskId).intValue()));
            }
        }
        return tasksByStoryPoints;
    }
}
