package com.kaustubh.medrug;

public class login {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public login(String email_id, String password) {
        this.email = email_id;
        this.password = password;
    }
}
