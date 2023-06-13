package com.example.project.Logic.DatabaseClasses;


import javax.persistence.*;

@Entity
public class ActiveCode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ActiveCode_ID;

    @OneToOne
    @JoinColumn(name = "GameCode_ID")
    private GameCode gameCode;

    @ManyToOne
    @JoinColumn(name="user_id")
    private PlatformUser user;

    @Version
    @Column(name = "version", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private int version;


    public ActiveCode() {
    }

    public ActiveCode(PlatformUser platformUser, GameCode gameCode) {
        this.gameCode=gameCode;
        this.gameCode.setActiveCode(this);
        this.user=platformUser;
        this.user.addActiveCode(this);
        this.version=0;
    }

    public int getActiveCode_ID() {
        return ActiveCode_ID;
    }

    public GameCode getGameCode() {
        return gameCode;
    }

    public PlatformUser getUser() {
        return user;
    }
}
