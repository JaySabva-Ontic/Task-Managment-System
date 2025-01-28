package org.jaysabva.dto;

public class ImprovementTaskDto extends TaskDto{
    private String proposedImprovement;

    public String getProposedImprovement() {
        return proposedImprovement;
    }

    public void setProposedImprovement(String proposedImprovement) {
        this.proposedImprovement = proposedImprovement;
    }
}
