package org.jaysabva.entity;

import java.util.concurrent.atomic.AtomicLong;

public abstract class Task {
    private static AtomicLong taskId = new AtomicLong(1);

    private Long id;
    private String title;
    private String description;
    private String status;
    private String dueDate;
    private String createdAt;
    private String updatedAt;
    private String assignee;
    private String createdBy;

    public Task() {
        this.id = taskId.longValue();
        incrTaskId();
    }

    public Task(String title, String description, String status, String dueDate, String createdAt, String updatedAt, String assignee, String createdBy) {
        this.id = taskId.longValue();
        this.title = title;
        this.description = description;
        this.status = status;
        this.dueDate = dueDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.assignee = assignee;
        this.createdBy = createdBy;

        incrTaskId();
    }

    private static void incrTaskId() {
        taskId.incrementAndGet();
    }

    public static Long getTaskId() {
        return taskId.longValue();
    }

    public static void setTaskId(Long taskId) {
        Task.taskId = new AtomicLong(taskId);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
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

    public void viewTask() {
        System.out.println("Id: " + getId());
        System.out.println("Title: " + getTitle());
        System.out.println("Description: " + getDescription());
        System.out.println("Status: " + getStatus());
        System.out.println("Due Date: " + getDueDate());
        System.out.println("Created At: " + getCreatedAt());
        System.out.println("Updated At: " + getUpdatedAt());
        System.out.println("Assignee: " + getAssignee());
        System.out.println("Created By: " + getCreatedBy());
    }
}

