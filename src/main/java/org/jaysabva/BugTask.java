package org.jaysabva;

public class BugTask extends Task{
    private String severity;
    private String stepToReproduce;

    public BugTask() {
        super();
    }

    public BugTask(String title, String description, String status, String dueDate, String createdAt, String updatedAt, String assignee, String createdBy, String severity, String stepToReproduce) {
        super(title, description, status, dueDate, createdAt, updatedAt, assignee, createdBy);
        this.severity = severity;
        this.stepToReproduce = stepToReproduce;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getStepToReproduce() {
        return stepToReproduce;
    }

    public void setStepToReproduce(String stepToReproduce) {
        this.stepToReproduce = stepToReproduce;
    }

    @Override
    public void viewTask() {

        super.viewTask();

        System.out.println("Severity: " + getSeverity());
        System.out.println("Step to Reproduce: " + getStepToReproduce());
    }

}
