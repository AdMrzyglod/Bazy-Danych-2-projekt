package com.example.project.Logic;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;


@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int UserID;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
    private String email;
    private float money;

    @ManyToMany(mappedBy = "User")
    private List<Game> userGames;

    @OneToMany(mappedBy = "User")
    private List<Order> orders= new ArrayList<Order>();;

    public User(String firstname, String lastname, String username, String password, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.userGames=new ArrayList<Game>();
        this.money=0;
    }

    public User(String username) {
        this.username = username;
        this.money=0;
        this.userGames=new ArrayList<Game>();
        setMoney(10000);
    }

    public User() {

    }


    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void addUserGame(Game game){
        this.userGames.add(game);
    }

    public void addUserGameList(List<Game> games){
        this.userGames.addAll(games);
        for(Game game:games){
            game.addUserToGames(this);
        }
    }

    public List<Game> getUserGames(){
        return this.userGames;
    }

    public float getMoney(){
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public void updateMoney(float cash){
        out.println(cash+ "  "+money+"  " + (money+cash));
        this.money+=cash;
        out.println(money);
    }

    public boolean canBuy(float cash){
        return this.money-cash>0 ? true : false;
    }

    public void addOrder(Order order){
        this.orders.add(order);
    }
}
