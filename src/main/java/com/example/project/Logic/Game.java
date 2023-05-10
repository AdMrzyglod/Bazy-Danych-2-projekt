package com.example.project.Logic;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int GameID;
    private String gameName;
    private Category category;
    private String image;
    private float price;
    private float sizeGB;
    private Option isPolish;

    @ManyToMany(mappedBy = "Games")
    private List<User> userList;

    @ManyToMany(mappedBy = "Games")
    private List<Order> orders;

    public Game(){
    }

    public Game(String gameName, Category category,String image, float price, float sizeGB, Option isPolish) {
        this.gameName = gameName;
        this.category = category;
        this.image=image;
        this.price = price;
        this.sizeGB = sizeGB;
        this.isPolish = isPolish;
        this.userList= new ArrayList<User>();
        this.orders= new ArrayList<Order>();
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

    public Option getIsPolish() {
        return isPolish;
    }

    public void setIsPolish(Option isPolish) {
        this.isPolish = isPolish;
    }

    public String getImage() {
        return image;
    }
    public void addOrder(Order order){
        this.orders.add(order);
    }
    public void addUserToGames(User user){
        this.userList.add(user);
    }
}
