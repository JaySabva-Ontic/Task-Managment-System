package org.jaysabva;

public class FeatureTask extends Task {
    private String FeatureDescription;
    private String EstimatedEffort;

    public FeatureTask() {
        super();
    }

    public FeatureTask(String Title, String Description, String Status, String DueDate, String CreatedAt, String UpdatedAt, String Assignee, String CreatedBy, String FeatureDescription, String EstimatedEffort) {
        super(Title, Description, Status, DueDate, CreatedAt, UpdatedAt, Assignee, CreatedBy);
        this.FeatureDescription = FeatureDescription;
        this.EstimatedEffort = EstimatedEffort;
    }

    public String getFeatureDescription() {
        return FeatureDescription;
    }

    public void setFeatureDescription(String featureDescription) {
        FeatureDescription = featureDescription;
    }

    public String getEstimatedEffort() {
        return EstimatedEffort;
    }

    public void setEstimatedEffort(String estimatedEffort) {
        EstimatedEffort = estimatedEffort;
    }

    @Override
    public void viewTask() {

        super.viewTask();

        System.out.println("Feature Description: " + getFeatureDescription());
        System.out.println("Estimated Effort: " + getEstimatedEffort());
    }
}
