package org.jaysabva.entity;

import org.jaysabva.util.IgnoreMongo;

public class Check {
    private String w1;
    private String w2;

    public Check() {
    }
    public Check(String c1, String c2) {
        this.w1 = c1;
        this.w2 = c2;
    }

    public String getW1() {
        return w1;
    }

    public void setW1(String w1) {
        this.w1 = w1;
    }

    public String getW2() {
        return w2;
    }

    public void setW2(String w2) {
        this.w2 = w2;
    }
}
