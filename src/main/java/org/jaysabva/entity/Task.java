package org.jaysabva.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "task")
public abstract class Task {

    @Id
    private String id;
    private String title;
    private String description;
    private Status status;
    private LocalDateTime startDate;
    private LocalDateTime dueDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String assignee;
    private String createdBy;
    private TaskType taskType;
    private Long storyPoints;

    public Task() {
    }

    public Task(String title, String description, String status, LocalDateTime startDate, LocalDateTime dueDate, LocalDateTime createdAt, LocalDateTime updatedAt, String assignee, String createdBy, String taskType, Long storyPoints) {
        this.title = title;
        this.description = description;
        this.status = getStatus(status);
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.assignee = assignee;
        this.createdBy = createdBy;
        this.taskType = TaskType.valueOf(taskType);
        this.storyPoints = storyPoints;

    }

    private static Status getStatus(String status) {
        try {
            return Status.valueOf(status);
        } catch (IllegalArgumentException e) {
            return Status.PENDING;
        }
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status.toString();
    }

    public void setStatus(String status) {
        this.status = Status.valueOf(status);
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getTaskType() {
        return taskType.toString();
    }

    public void setTaskType(String taskType) {
        this.taskType = TaskType.valueOf(taskType);
    }

    public Long getStoryPoints() {
        return storyPoints;
    }

    public void setStoryPoints(Long storyPoints) {
        this.storyPoints = storyPoints;
    }

    public void viewTask() {
        System.out.println("Id: " + getId());
        System.out.println("Task Type: " + getTaskType());
        System.out.println("Title: " + getTitle());
        System.out.println("Description: " + getDescription());
        System.out.println("Story Points: " + getStoryPoints());
        System.out.println("Status: " + getStatus());
        System.out.println("Start Date: " + getStartDate());
        System.out.println("Due Date: " + getDueDate());
        System.out.println("Created At: " + getCreatedAt());
        System.out.println("Updated At: " + getUpdatedAt());
        System.out.println("Assignee: " + getAssignee());
        System.out.println("Created By: " + getCreatedBy());
    }
}

