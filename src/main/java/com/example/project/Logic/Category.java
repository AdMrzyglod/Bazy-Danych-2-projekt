package com.example.project.Logic;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Category_ID;
    private String categoryName;

    private String description;

    @OneToMany(mappedBy = "category")
    private List<Game> games;

    public Category(){
    }
    public Category(String categoryName,String description){

        this.categoryName=categoryName;
        this.description=description;
        this.games= new ArrayList<>();
    }
    public String getCategoryName() {
        return categoryName;
    }

    public int getCategoryID() {
        return Category_ID;
    }

    public String getDescription() {
        return description;
    }

    public List<Game> getGames() {
        return games;
    }

    public void addGameToCategory(Game game){
        this.games.add(game);
    }
}
