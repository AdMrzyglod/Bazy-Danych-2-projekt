package com.example.project.Controllers;

import com.example.project.Logic.DatabaseClasses.Category;
import com.example.project.Logic.DatabaseClasses.Game;
import com.example.project.Logic.DatabaseClasses.GameCode;
import com.example.project.Logic.MainController.AppController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;

public class CreateGameViewBoxController {

    private AppController app;
    private EmployeeController employeeController;

    @FXML
    private HBox firstBox;
    @FXML
    private VBox secondBox;

    @FXML
    private TextField gamename;
    @FXML
    private TextField image;
    @FXML
    private TextField polish;
    @FXML
    private TextField price;
    @FXML
    private TextField size;
    @FXML
    private TextField category;
    @FXML
    private TextField name;
    @FXML
    private TextField code;
    @FXML
    private TextField categoryname;
    @FXML
    private TextField description;
    @FXML
    private TextField waringCode;
    @FXML
    private TextField waringCategory;
    @FXML
    private TextField waringGame;


    @FXML
    protected void onCreateGame(ActionEvent e) throws IOException, ParseException {

        Game game = getGame();
        if(game==null){
            this.setWaringGame("Problem !");
        }
        else{
            this.app.provider.addGame(game);
            this.employeeController.onCreate();
        }
    }

    @FXML
    protected void onCreateCode(ActionEvent e) throws IOException, ParseException {

        GameCode gameCode = getNewCode();
        if(gameCode==null){
            this.setWaringCode("Problem !");
        }
        else{
            this.app.provider.addGameCode(gameCode);
            this.employeeController.onCreate();
        }
    }

    @FXML
    protected void onCreateCategory(ActionEvent e) throws IOException, ParseException {

        Category gameCategory = getNewCategory();
        if(gameCategory==null){
            this.setWaringCategory("Problem !");
        }
        else{
            this.app.provider.addCategory(gameCategory);
            this.employeeController.onCreate();
        }
    }


    private void setWaringGame(String text){
        this.waringGame.setEditable(false);
        this.waringGame.setText(text);
    }

    private void setWaringCode(String text){
        this.waringCode.setEditable(false);
        this.waringCode.setText(text);
    }

    private void setWaringCategory(String text){
        this.waringCategory.setEditable(false);
        this.waringCategory.setText(text);
    }

    public String getGamename() {
        return gamename.getText();
    }

    public String getImage() {
        return image.getText();
    }

    public String getPolish() {
        return polish.getText();
    }

    public String getPrice() {
        return price.getText();
    }

    public String getSize() {
        return size.getText();
    }

    public String getCategory() {
        return category.getText();
    }

    public String getName() {
        return name.getText();
    }

    public String getCode() {
        return code.getText();
    }

    public String getCategoryname() {
        return categoryname.getText();
    }

    public String getDescription() {
        return description.getText();
    }

    public Game getGame() throws ParseException {
        if(this.app.provider.getGameByName(getGamename())!=null || this.app.provider.getCategory(getCategory())==null){
            return null;
        }
        return new Game(getGamename(),this.app.provider.getCategory(getCategory()),getImage(), BigDecimal.valueOf(Double.parseDouble(getPrice())),BigDecimal.valueOf(Double.parseDouble(getSize())),getPolish().equals("tak"));
    }

    public GameCode getNewCode() throws ParseException {
        if(this.app.provider.getGameCode(getCode())!=null || this.app.provider.getGameByName(getName())==null){
            return null;
        }
        return new GameCode(getCode(),this.app.provider.getGameByName(getName()));
    }

    public Category getNewCategory() throws ParseException {
        if(this.app.provider.getCategory(getCategoryname())!=null){
            return null;
        }
        return new Category(getCategoryname(),getDescription());
    }


    public void setApp(AppController app){
        this.app=app;
    }


    public AppController getApp() {
        return app;
    }

    public EmployeeController getEmployeeController() {
        return employeeController;
    }

    public void setEmployeeController(EmployeeController employeeController) {
        this.employeeController = employeeController;
    }
}
