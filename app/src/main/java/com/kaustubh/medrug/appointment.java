package com.kaustubh.medrug;

public class appointment {
    private int id;
    private String comments;
    private String date;
    private int patient;
    private int  doctor;
    private int scheduled;

    public appointment(int  id, String comments, String date, int patient, int doctor, int scheduled) {
        this.id = id;
        this.comments = comments;
        this.date = date;
        this.patient = patient;
        this.doctor = doctor;
        this.scheduled = scheduled;
    }

    public int getId() {
        return id;
    }

    public String getComments() {
        return comments;
    }

    public String getDate() {
        return date;
    }

    public int getPatient() {
        return patient;
    }

    public int getDoctor() {
        return doctor;
    }

    public int getScheduled() {
        return scheduled;
    }
}
