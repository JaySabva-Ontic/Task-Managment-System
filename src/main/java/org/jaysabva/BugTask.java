package org.jaysabva;

public class BugTask extends Task{
    private String Severity;
    private String StepToReproduce;

    public BugTask() {
        super();
    }

    public BugTask(String Title, String Description, String Status, String DueDate, String CreatedAt, String UpdatedAt, String Assignee, String CreatedBy, String Severity, String StepToReproduce) {
        super(Title, Description, Status, DueDate, CreatedAt, UpdatedAt, Assignee, CreatedBy);
        this.Severity = Severity;
        this.StepToReproduce = StepToReproduce;
    }

    public String getSeverity() {
        return Severity;
    }

    public void setSeverity(String severity) {
        Severity = severity;
    }

    public String getStepToReproduce() {
        return StepToReproduce;
    }

    public void setStepToReproduce(String stepToReproduce) {
        StepToReproduce = stepToReproduce;
    }

    @Override
    public void viewTask() {

        super.viewTask();

        System.out.println("Severity: " + getSeverity());
        System.out.println("Step to Reproduce: " + getStepToReproduce());
    }

}
