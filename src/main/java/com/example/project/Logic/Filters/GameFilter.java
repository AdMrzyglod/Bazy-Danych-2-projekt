package com.example.project.Logic.Filters;

import com.example.project.Logic.Category;
import com.example.project.Logic.Game;
import com.example.project.Logic.Option;

import java.util.ArrayList;
import java.util.List;

public class GameFilter {

    private List<Category> category;
    private float price[];
    private float sizeGB[];
    private Option isPolish;

    public GameFilter(){
        this.price=new float[2];
        this.price[0]=0;
        this.price[1]=10000;
        this.sizeGB=new float[2];
        this.sizeGB[0]=0;
        this.sizeGB[1]=10000;
        this.isPolish=Option.BOTH;
        this.category=new ArrayList<Category>();
    }

    public List<Category> getCategory() {
        return category;
    }

    public void addCategory(Category category){
        this.category.add(category);
    }
    public void removeCategory(Category category){
        this.category.remove(category);
    }

    public float[] getPrice() {
        return price;
    }

    public void setPrice(float from,float to) {
        this.price[0]=from;
        this.price[1]=to;
    }
    public void setPriceFrom(float from){
        this.price[0]=from;
    }
    public void setPriceTo(float to){
        this.price[1]=to;
    }

    public float[] getSizeGB() {
        return sizeGB;
    }

    public void setSizeGB(float from, float to) {
        this.sizeGB[0]=from;
        this.sizeGB[1]=to;
    }

    public void setSizeGBFrom(float from){
        this.sizeGB[0]=from;
    }

    public void setSizeGBTo(float to){
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
