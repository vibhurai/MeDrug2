package com.kaustubh.medrug;
public class historyItem {
    String Doctor;
    String Date;
    String Time;
    String Status;
    int id;


    public historyItem(String Doctor, String Date, String time, String status, int id){

        this.Date=Date;
        this.Doctor=Doctor;
        this.Time=time;
        this.Status=status;
        this.id=id;
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

    int getId(){return id;}

}
