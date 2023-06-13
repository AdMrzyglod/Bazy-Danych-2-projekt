package com.example.project.Logic.DatabaseClasses;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Version;

@Entity
public class IndividualUser extends PlatformUser{

    private String firstname;
    private String lastname;

    @Version
    @Column(name = "version", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private int version;

    public IndividualUser(){
    }

    public IndividualUser(String username, String password, String email, String firstname, String lastname) {
        super(username, password, email);
        this.firstname = firstname;
        this.lastname = lastname;
        this.version=0;
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
