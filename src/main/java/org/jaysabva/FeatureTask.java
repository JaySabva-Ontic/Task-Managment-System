package org.jaysabva;

public class FeatureTask extends Task {
    private String featureDescription;
    private String estimatedEffort;

    public FeatureTask() {
        super();
    }

    public FeatureTask(String title, String description, String status, String dueDate, String createdAt, String updatedAt, String assignee, String createdBy, String featureDescription, String estimatedEffort) {
        super(title, description, status, dueDate, createdAt, updatedAt, assignee, createdBy);
        this.featureDescription = featureDescription;
        this.estimatedEffort = estimatedEffort;
    }

    public String getFeatureDescription() {
        return featureDescription;
    }

    public void setFeatureDescription(String featureDescription) {
        this.featureDescription = featureDescription;
    }

    public String getEstimatedEffort() {
        return estimatedEffort;
    }

    public void setEstimatedEffort(String estimatedEffort) {
        this.estimatedEffort = estimatedEffort;
    }

    @Override
    public void viewTask() {

        super.viewTask();

        System.out.println("Feature Description: " + getFeatureDescription());
        System.out.println("Estimated Effort: " + getEstimatedEffort());
    }
}
