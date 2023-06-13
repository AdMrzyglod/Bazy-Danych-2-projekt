package com.example.project.Logic.DatabaseClasses;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Game_ID;
    private String gameName;
    private String image;
    private BigDecimal price;
    private BigDecimal sizeGB;
    private boolean isPolish;

    @OneToMany(mappedBy = "game")
    private List<GameCode> gameCodes;
    @ManyToOne
    @JoinColumn(name = "Category_ID")
    private Category category;
    @OneToMany(mappedBy = "game")
    private List<Tournament> tournaments;

    @Version
    @Column(name = "version", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private int version;

    public Game(){
    }

    public Game(String gameName,Category category,String image, BigDecimal price, BigDecimal sizeGB, boolean isPolish) {
        this.gameName = gameName;
        this.category = category;
        category.addGameToCategory(this);
        this.image=image;
        this.price = price;
        this.sizeGB = sizeGB;
        this.isPolish = isPolish;
        this.gameCodes= new ArrayList<GameCode>();
        this.tournaments= new ArrayList<Tournament>();
        this.version=0;
    }

    public String getGameName() {
        return gameName;
    }

    public Category getCategory() {
        return category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getSizeGB() {
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
