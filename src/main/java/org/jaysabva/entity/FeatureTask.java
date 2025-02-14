package org.jaysabva.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;

@Document(collection = "tasks")
public class FeatureTask extends Task {
    private String featureDescription;
    private Duration estimatedEffort;

    public FeatureTask() {
        super();
    }

    public FeatureTask(String title, String description, String status,LocalDateTime startDate, LocalDateTime dueDate, LocalDateTime createdAt, LocalDateTime updatedAt, String assignee, String createdBy, String featureDescription, Duration estimatedEffort, String taskType, Long storyPoints, Map<Long, Object> dynamicField) {
        super(title, description, status, startDate, dueDate, createdAt, updatedAt, assignee, createdBy, taskType, storyPoints, dynamicField);
        this.featureDescription = featureDescription;
        this.estimatedEffort = estimatedEffort;
    }

    public String getFeatureDescription() {
        return featureDescription;
    }

    public void setFeatureDescription(String featureDescription) {
        this.featureDescription = featureDescription;
    }

    public Duration getEstimatedEffort() {
        return estimatedEffort;
    }

    public void setEstimatedEffort(Duration estimatedEffort) {
        this.estimatedEffort = estimatedEffort;
    }

    @Override
    public void viewTask() {

        super.viewTask();

        System.out.println("Feature Description: " + getFeatureDescription());
        System.out.println("Estimated Effort: " + getEstimatedEffort().toHours() + " hours");
    }
}
