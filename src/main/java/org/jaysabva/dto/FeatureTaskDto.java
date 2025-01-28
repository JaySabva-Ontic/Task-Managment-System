package org.jaysabva.dto;

import java.time.Duration;

public class FeatureTaskDto extends TaskDto{
    private String featureDescription;
    private Duration estimatedEffort;

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
}
