package com.kaustubh.medrug;

// {
//         "id": 1,
//         "name": "Ishit Beswal",
//         "phone": 1234567890,
//         "speciality": "General Physician",
//         "picture": "https://devilish.pythonanywhere.com/doctors/CA-Paper-Figure-8.JPG",
//         "details": "MBBS from Udemy"
//         },
public class doctors {
    private int id;
    private String name;
    private long phone;
    private String speciality;
    private String picture;
    private String details;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getPhone() {
        return phone;
    }

    public String getSpeciality() {
        return speciality;
    }

    public String getPicture() {
        return picture;
    }

    public String getDetails() {
        return details;
    }
}
