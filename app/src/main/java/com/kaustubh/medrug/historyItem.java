package com.kaustubh.medrug;
public class historyItem {
    String Doctor;
    String Date;
    String Time;
    String Status;
    int id;
    long eventid;


    public historyItem(String Doctor, String Date, String time, String status, int id,long eventid){

        this.Date=Date;
        this.Doctor=Doctor;
        this.Time=time;
        this.Status=status;
        this.id=id;
        this.eventid=eventid;
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

    public long getEventid() {
        return eventid;
    }
}
