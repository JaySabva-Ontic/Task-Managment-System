package org.jaysabva.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "task")
public class BugTask extends Task {
    private String severity;
    private List<String> stepToReproduce = new ArrayList<>();

    public BugTask() {
        super();
    }

    public BugTask(String title, String description, String status, LocalDateTime startDate, LocalDateTime dueDate, LocalDateTime createdAt, LocalDateTime updatedAt, String assignee, String createdBy, String severity, List<String> stepToReproduce, String taskType, Long storyPoints) {
        super(title, description, status, startDate, dueDate, createdAt, updatedAt, assignee, createdBy, taskType, storyPoints);
        this.severity = severity;
        this.stepToReproduce = stepToReproduce;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public List<String> getStepToReproduce() {
        return stepToReproduce;
    }

    public void setStepToReproduce(List<String> stepToReproduce) {
        this.stepToReproduce = stepToReproduce;
    }

    @Override
    public void viewTask() {

        super.viewTask();

        System.out.println("Severity: " + getSeverity());
        System.out.println("Step to Reproduce: " + getStepToReproduce());
    }

}
