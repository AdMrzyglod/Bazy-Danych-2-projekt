package com.example.project.Logic;


import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Tournament_ID;

    private String name;
    private Timestamp startTournament;
    private Timestamp endTournament;
    private String description;
    private int userLimit;

    @OneToMany(mappedBy = "tournament")
    private List<UserTournament> userTournaments;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyUser companyUser;

    @ManyToOne
    @JoinColumn(name="game_id")
    private Game game;

    public Tournament(){
    }

    public Tournament(String name,Timestamp startTournament, Timestamp endTournament, String description, int userLimit, CompanyUser companyUser, Game game) {
        this.name=name;
        this.startTournament = startTournament;
        this.endTournament = endTournament;
        this.description = description;
        this.userLimit = userLimit;
        this.companyUser = companyUser;
        this.game = game;
        this.userTournaments= new ArrayList<UserTournament>();
    }

    public Timestamp getStartTournament() {
        return startTournament;
    }

    public void setStartTournament(Timestamp startTournament) {
        this.startTournament = startTournament;
    }

    public Timestamp getEndTournament() {
        return endTournament;
    }

    public void setEndTournament(Timestamp endTournament) {
        this.endTournament = endTournament;
    }

    public List<UserTournament> getUserTournaments() {
        return userTournaments;
    }

    public int getNumberOfPlayers(){
        return this.userTournaments.size();
    }



    public CompanyUser getCompanyUser() {
        return companyUser;
    }

    public Game getGame() {
        return game;
    }

    public int getTournament_ID() {
        return Tournament_ID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getUserLimit() {
        return userLimit;
    }

    public void addUserTournament(UserTournament userTournament){
        this.userTournaments.add(userTournament);
    }
}
