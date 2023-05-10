package com.example.project.Controllers;

import com.example.project.Controllers.GameViewBox.LibraryGameViewBox;
import com.example.project.Controllers.GameViewBox.ShopGameViewBox;
import com.example.project.Logic.Category;
import com.example.project.Logic.CategoryPanel;
import com.example.project.Logic.Filters.SpecializedFilter;
import com.example.project.Logic.Game;
import com.example.project.Logic.MainController.AppController;
import com.example.project.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public class LibraryController {

    private AppController app;
    private Stage stage;

    private CategoryPanel categoryPanel = new CategoryPanel();

    @FXML
    private Label userName;

    @FXML
    private Button shopButton;

    @FXML
    private ScrollPane gameList;

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
    protected void setUserName(String user){
        userName.setText(user);
    }

    @FXML
    public void addToGameList(List<Game> games) throws IOException {
        ScrollPane scrollPane=(ScrollPane) stage.getScene().lookup("#gameList");
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPrefHeight(890);
        vbox.setPrefWidth(675);
        vbox.setStyle("-fx-background-color: #404040;");

        for(Game game: games) {
            FXMLLoader fxml = new FXMLLoader(Main.class.getResource("game-view-box.fxml"));
            Scene scene = new Scene(fxml.load());
            GameViewBoxController gameViewBoxController=fxml.getController();
            gameViewBoxController.setApp(app);
            gameViewBoxController.setGame(game);
            gameViewBoxController.setInCart(false);
            gameViewBoxController.setAbstractGameViewBox(new LibraryGameViewBox(gameViewBoxController));
            HBox hBox = (HBox) scene.lookup("#gameBox");
            ((Button)scene.lookup("#buyButton")).setText("Włącz");
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

    @FXML
    public void applyFilters(ActionEvent e) throws IOException {
        this.categoryPanel.setFilters();
        this.addToGameList(this.getSpecializedFilter().getCorrectGames(this.app.getUser().getUserGames()));
    }

    @FXML
    public void setCategoryPanel() throws IOException {
        ScrollPane scrollPane=(ScrollPane) stage.getScene().lookup("#categoryList");

        scrollPane.setContent(categoryPanel.createVBOX(new ArrayList<Category>(EnumSet.allOf(Category.class))));
    }

    @FXML
    private void resetFilters(ActionEvent e) throws IOException {
        setCategoryPanel();
        this.addToGameList(this.getSpecializedFilter().getCorrectGames(this.app.getUser().getUserGames()));
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

    public SpecializedFilter getSpecializedFilter() {
        return categoryPanel.specializedFilter;
    }
}
