package com.example.project.Logic;


import javax.persistence.Entity;

@Entity
public class IndividualUser extends PlatformUser{

    private String firstname;
    private String lastname;

    public IndividualUser(){
    }

    public IndividualUser(String username, String password, String email, String firstname, String lastname) {
        super(username, password, email);
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
