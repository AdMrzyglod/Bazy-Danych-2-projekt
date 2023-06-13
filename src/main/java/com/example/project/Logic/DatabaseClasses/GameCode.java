package com.example.project.Logic.DatabaseClasses;


import javax.persistence.*;

@Entity
public class GameCode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int gameCode_ID;

    private String accessCode;
    private boolean used;

    @ManyToOne
    @JoinColumn(name="game_id")
    private Game game;

    @OneToOne(mappedBy = "gameCode")
    private OrderDetails orderDetails;

    @OneToOne(mappedBy = "gameCode")
    private ActiveCode activeCode;

    @Version
    @Column(name = "version", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private int version;



    public GameCode() {
    }

    public GameCode(String accessCode,Game game) {
        this.accessCode = accessCode;
        this.used = false;
        this.game = game;
        game.addGameCode(this);
        this.version=0;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public boolean isUsed() {
        return used;
    }

    public Game getGame() {
        return game;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public int getGameCode_ID() {
        return gameCode_ID;
    }

    public OrderDetails getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }

    public ActiveCode getActiveCode() {
        return activeCode;
    }

    public void setActiveCode(ActiveCode activeCode) {
        this.activeCode = activeCode;
        this.used=true;
    }
}

