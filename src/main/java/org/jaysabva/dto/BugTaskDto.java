package org.jaysabva.dto;

import java.util.ArrayList;
import java.util.List;

public class BugTaskDto extends TaskDto{
    private String severity;
    private List<String> stepToReproduce = new ArrayList<>();

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
}
