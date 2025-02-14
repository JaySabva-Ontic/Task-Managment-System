package org.jaysabva.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@Document(collection = "tasks")
public class ImprovementTask extends Task {
    private String proposedImprovement;

    public ImprovementTask() {
        super();
    }

    public ImprovementTask(String title, String description, String status, LocalDateTime startDate, LocalDateTime dueDate, LocalDateTime createdAt, LocalDateTime updatedAt, String assignee, String createdBy, String proposedImprovement, String taskType, Long storyPoints, Map<Long, Object> dynamicField) {
        super(title, description, status, startDate, dueDate, createdAt, updatedAt, assignee, createdBy, taskType, storyPoints, dynamicField);
        this.proposedImprovement = proposedImprovement;
    }

    public String getProposedImprovement() {
        return proposedImprovement;
    }

    public void setProposedImprovement(String proposedImprovement) {
        this.proposedImprovement = proposedImprovement;
    }

    public void viewTask() {
        super.viewTask();

        System.out.println("Proposed Improvement: " + getProposedImprovement());
    }
}
