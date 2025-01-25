package org.jaysabva;

public class ImprovementTask extends Task {
    private String CurrentState;
    private String ProposedImprovement;

    public ImprovementTask() {
        super();
    }

    public ImprovementTask(String Title, String Description, String Status, String DueDate, String CreatedAt, String UpdatedAt, String Assignee, String CreatedBy, String CurrentState, String ProposedImprovment) {
        super(Title, Description, Status, DueDate, CreatedAt, UpdatedAt, Assignee, CreatedBy);
        this.CurrentState = CurrentState;
        this.ProposedImprovement = ProposedImprovment;
    }

    public String getCurrentState() {
        return CurrentState;
    }

    public void setCurrentState(String currentState) {
        CurrentState = currentState;
    }

    public String getProposedImprovement() {
        return ProposedImprovement;
    }

    public void setProposedImprovement(String proposedImprovement) {
        ProposedImprovement = proposedImprovement;
    }

    public void viewTask() {
        super.viewTask();

        System.out.println("Current State: " + getCurrentState());
        System.out.println("Proposed Improvement: " + getProposedImprovement());
    }
}
