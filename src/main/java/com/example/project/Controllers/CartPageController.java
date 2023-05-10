package com.example.project.Controllers;

import com.example.project.Controllers.GameViewBox.CartGameViewBox;
import com.example.project.Logic.Game;
import com.example.project.Logic.MainController.AppController;
import com.example.project.Logic.Option;
import com.example.project.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static java.lang.System.out;

public class CartPageController {
    private AppController app;
    private Stage stage;

    @FXML
    private Label totalPrice;

    @FXML
    protected void onShopButtonClick(ActionEvent e) throws IOException {
        app.homeLoad(new Stage());
        this.stage.close();
    }

    @FXML
    protected void onCartButtonClick(ActionEvent e) throws IOException {
        app.cartLoad(new Stage());
        this.stage.close();
    }

    @FXML
    protected void onLibraryButtonClick(ActionEvent e) throws IOException{
        app.libraryLoad(new Stage());
        this.stage.close();
    }

    @FXML
    protected void onClearButtonClick(ActionEvent e) throws IOException {
        if(this.app.getCart().getNumberOfGames()>0) {
            this.app.games.addAll(this.app.getCart().getGamesCart());
            this.app.getCart().clearCart();
            addToGameList(app.getCart().getGamesCart());
            setTotalPrice(this.app.getCart().getTotalPrice() + "");
        }
    }

    @FXML
    protected void onBuyGamesClick(ActionEvent e) throws IOException {
        float sumCart=this.app.getCart().getTotalPrice();
        if(this.app.getCart().getNumberOfGames()>0 && this.app.getUser().canBuy(sumCart)){
            //this.app.getUser().addUserGameList(this.app.getCart().getGamesCart());
            this.app.getCart().purchase(this.app.getUser());
            this.app.getUser().updateMoney(-sumCart);
            this.app.getCart().clearCart();
            setTotalPrice(this.app.getCart().getTotalPrice()+"");
            addToGameList(app.getCart().getGamesCart());
        }
        out.println(this.app.getUser().getMoney());
    }

    @FXML
    public void addToGameList(List<Game> games) throws IOException {
        ScrollPane scrollPane=(ScrollPane) stage.getScene().lookup("#gameList");
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPrefHeight(890);
        vbox.setPrefWidth(675);
        if(games.size()>0) {
            vbox.setStyle("-fx-background-color: #404040;");
        }
        else{
            vbox.setStyle("-fx-background-color: #404040; -fx-border-color: #202020; -fx-border-width: 5;");
            vbox.setAlignment(Pos.CENTER);
            Label label = new Label("Koszyk pusty");
            label.prefHeight(90);
            label.prefWidth(275);
            label.setStyle("-fx-font-size: 30px; -fx-text-fill: #252525; -fx-font-weight: BOLD;");
            vbox.getChildren().add(label);

        }

        for(Game game: games) {
            FXMLLoader fxml = new FXMLLoader(Main.class.getResource("game-view-box.fxml"));
            Scene scene = new Scene(fxml.load());
            GameViewBoxController gameViewBoxController=fxml.getController();
            gameViewBoxController.setApp(app);
            gameViewBoxController.setGame(game);
            gameViewBoxController.setBuyButton("Usuń");
            gameViewBoxController.setInCart(true);
            gameViewBoxController.setAbstractGameViewBox(new CartGameViewBox(gameViewBoxController));
            HBox hBox = (HBox) scene.lookup("#gameBox");
            ((Label)scene.lookup("#gameName")).setText(game.getGameName());
            ((Label)scene.lookup("#gameCategory")).setText(game.getCategory().getDisplayName());
            ((Label)scene.lookup("#gameSize")).setText(game.getSizeGB()+"");
            ((Label)scene.lookup("#gamePrice")).setText(game.getPrice()+"");
            ((Label)scene.lookup("#gamePolish")).setText(game.getIsPolish().getDisplayName());
            ((ImageView)scene.lookup("#gameImage")).setImage(new Image(new File(app.pathToImages+game.getImage()).getAbsolutePath()));
            vbox.getChildren().add(hBox);
        }
        scrollPane.setContent(vbox);
    }

    public void setApp(AppController app){
        this.app=app;
    }
    public void setStage(Stage stage){
        this.stage=stage;
    }

    private void closeStage(){
        this.stage.close();
    }

    public Label getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String text) {
        this.totalPrice.setText(text);
    }
}
