package com.example.project.Logic.DatabaseClasses;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Category_ID;
    private String categoryName;

    private String description;

    @OneToMany(mappedBy = "category")
    private List<Game> games;

    @Version
    @Column(name = "version", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private int version;


    public Category(){
    }
    public Category(String categoryName,String description){

        this.categoryName=categoryName;
        this.description=description;
        this.games= new ArrayList<>();
        this.version=0;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category category)) return false;
        return Category_ID == category.Category_ID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(Category_ID);
    }
}
