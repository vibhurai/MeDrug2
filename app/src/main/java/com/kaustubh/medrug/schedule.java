package com.kaustubh.medrug;

public class schedule {
    private int id;
    private String time;
    private String day;
    private int doctor_id;
    private boolean slot_booked;

    public boolean isSlot_booked() {
        return slot_booked;
    }

    public int getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public String getDay() {
        return day;
    }

    public int getDoctor_id() {
        return doctor_id;
    }
}