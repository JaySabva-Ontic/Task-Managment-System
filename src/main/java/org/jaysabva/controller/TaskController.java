package org.jaysabva.controller;

import org.jaysabva.dto.CustomFieldDto;
import org.jaysabva.dto.TaskDto;
import org.jaysabva.entity.Task;
import org.jaysabva.service.Implementation.TaskServiceImplementation;
import org.jaysabva.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;
    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }

    @PostMapping("/addTask")
    public Map<String, String> addTask(@RequestBody TaskDto task, @RequestParam boolean refresh) {
        return taskService.addTask(task, refresh);
    }

    @PutMapping("/updateTask")
    public Map<String, String> updateTask(@RequestBody TaskDto task) {
        return taskService.updateTask(task);
    }

    @GetMapping("/getTask/{taskId}")
    public Optional<Task> getTask(@PathVariable Long taskId) {
        return taskService.getTask(taskId);
    }

    @GetMapping("/getAllTasks")
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @DeleteMapping("/deleteTask/{taskId}")
    public Map<String, String> deleteTask(@PathVariable Long taskId) {
        return taskService.deleteTask(taskId);
    }

    @GetMapping("/getTasksByStoryPoints/{storyPoints}")
    public List<Task> getTasksByStoryPoints(@PathVariable Long storyPoints) {
        return taskService.getTasksByStoryPoints(storyPoints);
    }

    @GetMapping("/getTasksByStoryPoints")
    public Map<Long, List<Task>> getTasksByStoryPoints() {
        return taskService.getTaskByStoryPoints();
    }

    @GetMapping("/getTasksByStoryPointsRange/")
    public Map<Long, List<Task>> getTasksByStoryPointsRange(@RequestParam("start") Long start, @RequestParam("end") Long end) {
        return taskService.getTasksByStoryPointsRange(start, end);
    }

    @GetMapping("/getTaskByTaskType/{taskType}")
    public List<Task> getTaskByTaskType(@PathVariable String taskType) {
        return taskService.getTaskByTaskType(taskType);
    }

    @GetMapping("/searchTask/")
    public List<Task> searchTask(@RequestParam String search) {
        return taskService.searchTask(search);
    }

    @PostMapping("/addCustomField")
    public Map<String, String> addCustomField(@RequestBody CustomFieldDto customField) {
        return taskService.addCustomField(customField);
    }

    @PostMapping("/updateCustomField/{id}")
    public Map<String, String> updateCustomField(@PathVariable Long id, @RequestBody String status) {
        return taskService.updateCustomField(id, status);
    }

    @GetMapping("/searchTaskByDateRange")
    public List<Task> searchTaskByDateRange(@RequestParam ("startDate") String startDate, @RequestParam("endDate") String endDate) {
        return taskService.searchTaskByDateRange(startDate, endDate);
    }
}
