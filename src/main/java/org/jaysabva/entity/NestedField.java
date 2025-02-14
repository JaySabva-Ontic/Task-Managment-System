package org.jaysabva.entity;

import java.util.List;

public class NestedField {
    private String f1;
    private List<Check> f2;

    public NestedField() {
    }

    public NestedField(String f1, List<Check> f2) {
        this.f1 = f1;
        this.f2 = f2;
    }

    public String getF1() {
        return f1;
    }

    public void setF1(String f1) {
        this.f1 = f1;
    }

    public List<Check> getF2() {
        return f2;
    }

    public void setF2(List<Check> f2) {
        this.f2 = f2;
    }
}
