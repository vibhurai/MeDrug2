package com.kaustubh.medrug;
public class historyItem {
    String Doctor;
    String Date;
    String Time;
    String Status;


    public historyItem(String Doctor, String Date, String time, String status){

        this.Date=Date;
        this.Doctor=Doctor;
        this.Time=time;
        this.Status=status;
    }

    String getDoctor() {
        return Doctor;
    }

    String getDate() { return Date;   }

    String getTime() {
        return Time;
    }

    String getStatus() {
        return Status;
    }

}
