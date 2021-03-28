package com.example.tixtox.Model;

public class User {
    public String fullname, email;


    public User(String fullname, String email) {
        this.fullname = fullname;
        this.email = email;
    }
    public User(){

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

    public void set(String fullname, String email)
    {
        this.fullname = fullname;
        this.email = email;
    }
}
