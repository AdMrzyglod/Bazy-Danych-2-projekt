package com.example.project.Logic;

import com.example.project.Logic.DatabaseClasses.Game;

import java.math.BigDecimal;
import java.util.*;

public class Cart{
    private HashMap<Game,Integer> gamesCart;

    public Cart(){
        this.gamesCart=new HashMap<>();
    }

    public void addGame(Game game,int quantity){
        this.gamesCart.put(game,quantity);
    }

    public void removeGame(Game game){
        gamesCart.remove(game);
    }

    public void clearCart() {
        gamesCart.clear();
    }

    public HashMap<Game, Integer> getGamesCart() {
        return gamesCart;
    }

    public BigDecimal getTotalPrice(){
        BigDecimal value=BigDecimal.valueOf(0);
        for (Map.Entry<Game, Integer> entry : gamesCart.entrySet()) {
            value=value.add(entry.getKey().getPrice().multiply(BigDecimal.valueOf(entry.getValue())));
        }

        return value;
    }

    public int getNumberOfGames(){
        return this.gamesCart.size();
    }

    public boolean isInCart(Game game){
        return this.getGamesCart().containsKey(game);
    }

}
