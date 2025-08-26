package com.vktech.healthtrackerapp;

public class LogEntry {

    private String date;
    private int steps;
    private int water;
    private float sleep;
    private String notes;
    private long id; // Added for unique identification

    public LogEntry(long id, String date, int steps, int water, float sleep, String notes) {
        this.id = id;
        this.date = date;
        this.steps = steps;
        this.water = water;
        this.sleep = sleep;
        this.notes = notes;
    }

    // Getters
    public long getId() { return id; }
    public String getDate() { return date; }
    public int getSteps() { return steps; }
    public int getWater() { return water; }
    public float getSleep() { return sleep; }
    public String getNotes() { return notes; }

    // Setters
    public void setDate(String date) { this.date = date; }
    public void setSteps(int steps) { this.steps = steps; }
    public void setWater(int water) { this.water = water; }
    public void setSleep(float sleep) { this.sleep = sleep; }
    public void setNotes(String notes) { this.notes = notes; }
}