package com.example.project.Controllers;

import com.example.project.Controllers.GameViewBox.AbstractGameViewBox;
import com.example.project.Logic.Game;
import com.example.project.Logic.MainController.AppController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.IOException;

import static java.lang.System.out;

public class GameViewBoxController {

    private AppController app;
    private AbstractGameViewBox abstractGameViewBox;
    private Game game;
    private HBox gameBox;

    @FXML
    private Label gameName;

    @FXML
    private Button buyButton;
    @FXML
    private TextField quantity;
    @FXML
    private HBox textBox;
    @FXML
    private VBox textContainer;
    private boolean isInCart;

    @FXML
    protected void setGameName(String name){
        gameName.setText(name);
    }
    @FXML
    public String getBuyButtonText(){
        return this.buyButton.getText();
    }
    @FXML
    public Integer getQuantity(){
        return Integer.parseInt(this.quantity.getText());
    }
    @FXML
    public void setQuantity(int quantity){
        this.quantity.setText(quantity+"");
        this.quantity.setEditable(false);
    }
    @FXML
    public void setCodesRange(int numberOfCodes){
        this.quantity.setPromptText("1 - "+numberOfCodes);
    }


    public void setBuyButton(String buyButtonText){
        this.buyButton.setText(buyButtonText);
    }

    @FXML
    protected void onBuyButtonClickOn(ActionEvent e){
        this.abstractGameViewBox.onBuyButtonClickOn();
    }

    public void setApp(AppController app){
        this.app=app;
    }
    public void setGame(Game game){
        this.game=game;
    }
    public void disableTextBox(){
        this.textContainer.getChildren().remove(this.textBox);
    }

    public boolean isInCart() {
        return isInCart;
    }

    public void setInCart(boolean inCart) {
        isInCart = inCart;
    }

    public AppController getApp() {
        return app;
    }

    public Game getGame() {
        return game;
    }

    public void setAbstractGameViewBox(AbstractGameViewBox abstractGameViewBox) {
        this.abstractGameViewBox = abstractGameViewBox;
    }

    public void refreshPriceLabel(){
        this.getApp().getCartPageController().setTotalPrice(this.getApp().getCart().getTotalPrice()+"");
    }
}
