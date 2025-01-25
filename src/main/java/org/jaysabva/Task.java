package org.jaysabva;

public abstract class Task {
    private static Long taskId = 1L;

    private Long Id;
    private String Title;
    private String Description;
    private String Status;
    private String DueDate;
    private String CreatedAt;
    private String UpdatedAt;
    private String Assignee;
    private String CreatedBy;

    public Task() {
        this.Id = taskId;
        incrTaskId();
    }

    public Task(String Title, String Description, String Status, String DueDate, String CreatedAt, String UpdatedAt, String Assignee, String CreatedBy) {
        this.Id = taskId;
        this.Title = Title;
        this.Description = Description;
        this.Status = Status;
        this.DueDate = DueDate;
        this.CreatedAt = CreatedAt;
        this.UpdatedAt = UpdatedAt;
        this.Assignee = Assignee;
        this.CreatedBy = CreatedBy;

        incrTaskId();
    }

    private static void incrTaskId() {
        taskId++;
    }

    public static Long getTaskId() {
        return taskId;
    }

    public static void setTaskId(Long taskId) {
        Task.taskId = taskId;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        this.Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getDueDate() {
        return DueDate;
    }

    public void setDueDate(String dueDate) {
        DueDate = dueDate;
    }

    public String getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        CreatedAt = createdAt;
    }

    public String getUpdatedAt() {
        return UpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        UpdatedAt = updatedAt;
    }

    public String getAssignee() {
        return Assignee;
    }

    public void setAssignee(String assignee) {
        Assignee = assignee;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
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

