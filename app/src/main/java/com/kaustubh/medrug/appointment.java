package com.kaustubh.medrug;

public class appointment {
    private int patient;
    private int scheduled;
    private int id;

    public int getId() {
        return id;
    }

    private String comments;
    private String date;
    private  String doctor;
    private  String time;
    private  String day;

    public String getDoctor() {
        return doctor;
    }

    public String getTime() {
        return time;
    }

    public String getDay() {
        return day;
    }

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