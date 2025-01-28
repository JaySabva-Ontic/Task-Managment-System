package org.jaysabva.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ImprovementTask extends Task {
    private String proposedImprovement;

    public ImprovementTask() {
        super();
    }

    public ImprovementTask(String title, String description, String status, LocalDateTime startDate, LocalDateTime dueDate, LocalDateTime createdAt, LocalDateTime updatedAt, String assignee, String createdBy, String proposedImprovement, String taskType) {
        super(title, description, status, startDate, dueDate, createdAt, updatedAt, assignee, createdBy, taskType);
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
