package com.vktech.healthtrackerapp;

public class LogItem {
    public String date;
    public int steps;
    public int water;
    public float sleep; // ✅ use float to match Storage.java
    public String notes;

    public LogItem(String date, int steps, int water, float sleep, String notes) {
        this.date = date;
        this.steps = steps;
        this.water = water;
        this.sleep = sleep;
        this.notes = notes;
    }
}
