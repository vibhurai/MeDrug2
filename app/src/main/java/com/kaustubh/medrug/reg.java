package com.kaustubh.medrug;

public class reg {
    private String password;
    private String last_login;
    private Boolean is_superuser;
    private Boolean is_staff;
    private Boolean is_active;
    //    private String date_joined;
    private String email;
    private String first_name;
    private String last_name;
    private String snu_id;

    public reg( String password, String last_login, Boolean is_superuser, Boolean is_staff, Boolean is_active, String email, String first_name, String last_name, String snu_id) {
//        this.id = id;
        this.password = password;
        this.last_login = last_login;
        this.is_superuser = is_superuser;
        this.is_staff = is_staff;
        this.is_active = is_active;
//        date_joined = date_joined;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.snu_id = snu_id;
    }

//    public int getId() {
//        return id;
//    }

    public String getPassword() {
        return password;
    }

    public String getLast_login() {
        return last_login;
    }

    public Boolean getIs_superuser() {
        return is_superuser;
    }

    public Boolean getIs_staff() {
        return is_staff;
    }

    public Boolean getIs_active() {
        return is_active;
    }

//    public String getDate_joined() {
//        return date_joined;
//    }

    public String getEmail() {
        return email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getSnu_id() {
        return snu_id;
    }
}
