package org.jaysabva.entity;

import org.jaysabva.util.IgnoreMongo;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Dynamic;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@Document(collection = "tasks")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "tasks")
public class Task {

    @Id
    private Long id;

    @Field(type = FieldType.Text)
    private String title;

    @Field(type = FieldType.Text)
    private String description;

    private Status status;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime startDate;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime dueDate;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime createdAt;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
    private LocalDateTime updatedAt;

    private String assignee;
    private String createdBy;

    @Indexed
    private TaskType taskType;

    @Indexed
    private Long storyPoints;

    @IgnoreMongo
    private String elasticRelated;

    private NestedField nestedField;

    @Field(type = FieldType.Object, dynamic = Dynamic.STRICT)
    private Map<Long, Object> dynamicField;

    private Map<String, Object> nestedDynamicField;
    public Task() {
    }

    public Task(String title, String description, String status, LocalDateTime startDate, LocalDateTime dueDate, LocalDateTime createdAt, LocalDateTime updatedAt, String assignee, String createdBy, String taskType, Long storyPoints, Map<Long, Object> dynamicField) {
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
        this.dynamicField = dynamicField;
    }

    private static Status getStatus(String status) {
        try {
            return Status.valueOf(status);
        } catch (IllegalArgumentException e) {
            return Status.PENDING;
        }
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

    public String getElasticRelated() {
        return elasticRelated;
    }

    public void setElasticRelated(String elasticRelated) {
        this.elasticRelated = elasticRelated;
    }

    public NestedField getNestedField() {
        return nestedField;
    }

    public void setNestedField(NestedField nestedField) {
        this.nestedField = nestedField;
    }

    public Map<Long, Object> getDynamicField() {
        return dynamicField;
    }

    public void setDynamicField(Map<Long, Object> dynamicField) {
        this.dynamicField = dynamicField;
    }

    public Map<String, Object> getNestedDynamicField() {
        return nestedDynamicField;
    }

    public void setNestedDynamicField(Map<String, Object> nestedDynamicField) {
        this.nestedDynamicField = nestedDynamicField;
    }
}

