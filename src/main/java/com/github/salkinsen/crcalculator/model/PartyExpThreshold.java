package com.github.salkinsen.crcalculator.model;

public class PartyExpThreshold {

    private int easy;
    private int medium;
    private int hard;
    private int deadly;

    public PartyExpThreshold(int easy, int medium, int hard, int deadly) {
        this.easy = easy;
        this.medium = medium;
        this.hard = hard;
        this.deadly = deadly;
    }

    public int getEasy() {
        return easy;
    }

    public void setEasy(int easy) {
        this.easy = easy;
    }

    public int getMedium() {
        return medium;
    }

    public void setMedium(int medium) {
        this.medium = medium;
    }

    public int getHard() {
        return hard;
    }

    public void setHard(int hard) {
        this.hard = hard;
    }

    public int getDeadly() {
        return deadly;
    }

    public void setDeadly(int deadly) {
        this.deadly = deadly;
    }
}
