package com.kaustubh.medrug;

public class appointment {
    private int patient;
    private int scheduled;
    private String comments;
    private String date;

    public appointment(int patient, int scheduled, String comments, String date) {
        this.patient = patient;
        this.scheduled = scheduled;
        this.comments = comments;
        this.date = date;
    }

    public int getPatient() {
        return patient;
    }

    public int getScheduled() {
        return scheduled;
    }

    public String getComments() {
        return comments;
    }

    public String getDate() {
        return date;
    }
}
