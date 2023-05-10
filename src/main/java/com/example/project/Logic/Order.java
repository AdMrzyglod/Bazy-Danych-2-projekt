package com.example.project.Logic;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int OrderID;
    @ManyToMany(mappedBy = "Orders")
    private List<Game> games = new ArrayList<Game>();

    @ManyToOne
    private User user;

    public Order(){
    }

    public Order(List<Game> games,User user){
        addGamesToOrder(games);
        this.user=user;
        user.addOrder(this);
        user.addUserGameList(this.games);
    }
    public void addGamesToOrder(List<Game> games){
        this.games.addAll(games);
        for(Game game: games){
            game.addOrder(this);
        }
    }

}
