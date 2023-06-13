package com.example.project.Logic.DatabaseClasses;


import com.example.project.Logic.DatabaseClasses.PlatformUser;
import com.example.project.Logic.DatabaseClasses.Tournament;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Version;
import java.util.ArrayList;
import java.util.List;

@Entity
public class CompanyUser extends PlatformUser {

    private String companyName;
    private String country;
    private String city;
    private String street;
    private String phone;

    @OneToMany(mappedBy = "companyUser")
    private List<Tournament> tournaments;

    @Version
    private int version=0;

    public CompanyUser(){
    }

    public CompanyUser(String username, String password, String email, String companyName, String city, String street, String phone,String country) {
        super(username, password, email);
        this.companyName = companyName;
        this.country=country;
        this.city = city;
        this.street = street;
        this.phone = phone;
        this.tournaments= new ArrayList<Tournament>();
        this.version=0;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Tournament> getTournaments() {
        return tournaments;
    }

    public void addTournament(Tournament tournament){
        this.tournaments.add(tournament);
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
