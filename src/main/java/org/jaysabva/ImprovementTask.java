package org.jaysabva;

public class ImprovementTask extends Task {
    private String currentState;
    private String proposedImprovement;

    public ImprovementTask() {
        super();
    }

    public ImprovementTask(String title, String description, String status, String dueDate, String createdAt, String updatedAt, String assignee, String createdBy, String currentState, String proposedImprovement) {
        super(title, description, status, dueDate, createdAt, updatedAt, assignee, createdBy);
        this.currentState = currentState;
        this.proposedImprovement = proposedImprovement;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public String getProposedImprovement() {
        return proposedImprovement;
    }

    public void setProposedImprovement(String proposedImprovement) {
        this.proposedImprovement = proposedImprovement;
    }

    public void viewTask() {
        super.viewTask();

        System.out.println("Current State: " + getCurrentState());
        System.out.println("Proposed Improvement: " + getProposedImprovement());
    }
}
