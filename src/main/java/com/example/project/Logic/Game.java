package com.example.project.Logic;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Game_ID;
    private String gameName;
    private int quantity;

    @OneToMany(mappedBy = "game")
    private List<GameCode> gameCodes;
    @ManyToOne
    @JoinColumn(name = "Category_ID")
    private Category category;
    private String image;
    private float price;
    private float sizeGB;


    private boolean isPolish;

    @OneToMany(mappedBy = "game")
    private List<OrderDetails> orderDetails;

    @OneToMany(mappedBy = "game")
    private List<Tournament> tournaments;
    public Game(){
    }

    public Game(String gameName,Category category,String image, float price, float sizeGB, boolean isPolish) {
        this.gameName = gameName;
        this.category = category;
        category.addGameToCategory(this);
        this.image=image;
        this.price = price;
        this.sizeGB = sizeGB;
        this.isPolish = isPolish;
        this.orderDetails = new ArrayList<OrderDetails>();
        this.gameCodes= new ArrayList<GameCode>();
        this.tournaments= new ArrayList<Tournament>();
    }

    public String getGameName() {
        return gameName;
    }

    public Category getCategory() {
        return category;
    }

    public float getPrice() {
        return price;
    }

    public float getSizeGB() {
        return sizeGB;
    }

    public boolean getIsPolish() {
        return isPolish;
    }
    public String getIsPolishString(){
        return Boolean.toString(this.isPolish);
    }

    public void setIsPolish(boolean isPolish) {
        this.isPolish = isPolish;
    }

    public String getImage() {
        return image;
    }
    public void addOrder(PlatformOrder platformOrder){
        //this.platformOrders.add(platformOrder);
    }

    public int getGame_ID() {
        return Game_ID;
    }

    public List<GameCode> getGameCodes() {
        return gameCodes;
    }

    public void addGameCode(GameCode gameCode) {
        this.gameCodes.add(gameCode);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Game game)) return false;
        return getGame_ID() == game.getGame_ID();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGame_ID());
    }
}
