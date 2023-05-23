package com.example.project.Logic.Filters;

import com.example.project.Logic.Game;
import com.example.project.Logic.Option;

import java.util.ArrayList;
import java.util.List;

public class SpecializedFilter {

    public GameFilter gameFilter=new GameFilter();

    public void refreshFilter(){
        this.gameFilter=new GameFilter();
    }

    public boolean correctCategory(Game game){
        return gameFilter.getCategory().contains(game.getCategory());
    }
    public boolean correctPrice(Game game){
        return game.getPrice()>=gameFilter.getPrice()[0] && game.getPrice()<=gameFilter.getPrice()[1];
    }
    public boolean correctSize(Game game){
        return game.getSizeGB()>= gameFilter.getSizeGB()[0] && game.getSizeGB()<=gameFilter.getSizeGB()[1];
    }
    public boolean correctPolish(Game game){
        if(gameFilter.getIsPolish()== Option.BOTH){
            return true;
        }
        //return gameFilter.getIs()==game.getIsPolish();
        return false;
    }

    public boolean checkGame(Game game){

        if(this.gameFilter.getCategory().size()>0 && !this.correctCategory(game)){
            return false;
        }
        if(!this.correctPrice(game) || !this.correctSize(game) || !correctPolish(game)){
            return false;
        }
        return true;
    }

    public List<Game> getCorrectGames(List<Game> gameList){
        List<Game> games = new ArrayList<Game>();

        for(Game game: gameList){
            if(checkGame(game)){
                games.add(game);
            }
        }
        return games;
    }
}
