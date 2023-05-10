package com.example.project.Logic;

import java.util.ArrayList;
import java.util.List;

public class Cart{
    private List<Game> gamesCart;

    public Cart(){
        this.gamesCart=new ArrayList<Game>();
    }

    public void addGame(Game game){
        gamesCart.add(game);
    }

    public void removeGame(Game game){
        gamesCart.remove(game);
    }

    public void clearCart() {
        gamesCart.clear();
    }

    public List<Game> getGamesCart() {
        return this.gamesCart;
    }

    public float getTotalPrice(){
        float value=0;
        for(Game game: this.getGamesCart()){
            value+=game.getPrice();
        }
        return value;
    }

    public int getNumberOfGames(){
        return this.gamesCart.size();
    }

    public void purchase(User user){
        Order order=new Order(this.getGamesCart(),user);
    }
}
