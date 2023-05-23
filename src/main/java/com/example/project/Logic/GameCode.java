package com.example.project.Logic;


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

    @ManyToOne
    @JoinColumn(name="user_id")
    private PlatformUser user;

    @ManyToOne
    @JoinColumn(name = "orderdetails_id")
    private OrderDetails orderDetails;

    public GameCode() {
    }

    public GameCode(String accessCode,Game game) {
        this.accessCode = accessCode;
        this.used = false;
        this.game = game;
        game.addGameCode(this);
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

    public PlatformUser getUser() {
        return user;
    }

    public void setUser(PlatformUser user) {
        this.user = user;
        this.setUsed(true);
    }

    public OrderDetails getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
    }
}
