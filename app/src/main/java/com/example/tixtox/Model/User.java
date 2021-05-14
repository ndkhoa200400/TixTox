package com.example.tixtox.Model;

public class User {
    public String fullname, email, phone, dob;
    public double point;

    public User(String fullname, String email) {
        this.fullname = fullname;
        this.email = email;
        this.point=0.0;
    }
    public User(String fullname, String email, String phone, String dob, double point) {
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.dob = dob;
        this.point = point;
    }

    public User(){
        this.point=0.0;
    }
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public void set(String fullname, String email)
    {
        this.fullname = fullname;
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
