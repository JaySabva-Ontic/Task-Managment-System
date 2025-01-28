package org.jaysabva.entity;

import java.time.Duration;
import java.time.LocalDateTime;

public class FeatureTask extends Task {
    private String featureDescription;
    private Duration estimatedEffort;

    public FeatureTask() {
        super();
    }

    public FeatureTask(String title, String description, String status, LocalDateTime dueDate, LocalDateTime createdAt, LocalDateTime updatedAt, String assignee, String createdBy, String featureDescription, Duration estimatedEffort, String taskType) {
        super(title, description, status, dueDate, createdAt, updatedAt, assignee, createdBy, taskType);
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
        System.out.println("Estimated Effort: " + getEstimatedEffort());
    }
}
