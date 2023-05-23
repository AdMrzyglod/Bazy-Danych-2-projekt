package com.example.project.Logic;


import javax.persistence.*;

@Entity
public class UserTournament {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int UserTournament_ID;

    @ManyToOne
    @JoinColumn(name="tournament_id")
    private Tournament tournament;

    @ManyToOne
    @JoinColumn(name="user_id")
    private PlatformUser user;

    private String accessCode;

    public UserTournament(){
    }

    public UserTournament(Tournament tournament,PlatformUser user,String accessCode){
        this.tournament=tournament;
        this.user=user;
        this.accessCode=accessCode;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public PlatformUser getUser() {
        return user;
    }

    public void setUser(PlatformUser user) {
        this.user = user;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }
}
