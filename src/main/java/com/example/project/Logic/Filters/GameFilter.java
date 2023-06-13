package com.example.project.Logic.Filters;

import com.example.project.Logic.DatabaseClasses.Game;
import com.example.project.Logic.Option;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class GameFilter {

    private List<String> category;
    private BigDecimal price[];
    private BigDecimal sizeGB[];
    private Option isPolish;

    public GameFilter(){
        this.price=new BigDecimal[2];
        this.price[0]=BigDecimal.valueOf(0);
        this.price[1]=BigDecimal.valueOf(10000);
        this.sizeGB=new BigDecimal[2];
        this.sizeGB[0]=BigDecimal.valueOf(0);
        this.sizeGB[1]=BigDecimal.valueOf(10000);
        this.isPolish=Option.BOTH;
        this.category=new ArrayList<String>();
    }

    public List<String> getCategory() {
        return category;
    }

    public void addCategory(String category){
        this.category.add(category);
    }
    public void removeCategory(String category){
        this.category.remove(category);
    }

    public BigDecimal[] getPrice() {
        return price;
    }

    public void setPrice(BigDecimal from,BigDecimal to) {
        this.price[0]=from;
        this.price[1]=to;
    }
    public void setPriceFrom(BigDecimal from){
        this.price[0]=from;
    }
    public void setPriceTo(BigDecimal to){
        this.price[1]=to;
    }

    public BigDecimal[] getSizeGB() {
        return sizeGB;
    }

    public void setSizeGB(BigDecimal from, BigDecimal to) {
        this.sizeGB[0]=from;
        this.sizeGB[1]=to;
    }

    public void setSizeGBFrom(BigDecimal from){
        this.sizeGB[0]=from;
    }

    public void setSizeGBTo(BigDecimal to){
        this.sizeGB[1]=to;
    }

    public Option getIsPolish() {
        return isPolish;
    }

    public void setIsPolish(Option isPolish) {
        this.isPolish = isPolish;
    }

    boolean isTake(Game game){
        return true;
    }
}
