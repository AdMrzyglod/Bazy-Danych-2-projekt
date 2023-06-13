package com.example.project.Controllers;

import com.example.project.Controllers.GameViewBox.ShopGameViewBox;
import com.example.project.Logic.CategoryPanel;
import com.example.project.Logic.Filters.SpecializedFilter;
import com.example.project.Logic.DatabaseClasses.Game;
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
import java.util.List;

public class HomeController {

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
    protected void onPaymentButtonClick(ActionEvent e) throws IOException {
        app.paymentLoad(new Stage());
        this.stage.close();
    }

    @FXML
    protected void onTournamentButtonClick(ActionEvent e) throws IOException {
        app.tournamentLoad(new Stage());
        this.stage.close();
    }

    @FXML
    protected void onLogout(ActionEvent e) throws IOException {
        this.app.setUser(null);
        this.app.loginLoad(new Stage());
        closeStage();
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
            int numberOfCodes= this.app.provider.getFreeCodes(game).size();
            if(numberOfCodes==0 || this.app.getCart().isInCart(game)){
                continue;
            }
            FXMLLoader fxml = new FXMLLoader(Main.class.getResource("game-view-box.fxml"));
            Scene scene = new Scene(fxml.load());
            GameViewBoxController gameViewBoxController=fxml.getController();
            gameViewBoxController.setApp(app);
            gameViewBoxController.setGame(game);
            gameViewBoxController.setInCart(false);
            gameViewBoxController.setAbstractGameViewBox(new ShopGameViewBox(gameViewBoxController));
            HBox hBox = (HBox) scene.lookup("#gameBox");
            gameViewBoxController.setCodesRange(numberOfCodes);
            ((Label)scene.lookup("#gameName")).setText(game.getGameName());
            ((Label)scene.lookup("#gameCategory")).setText(game.getCategory().getCategoryName());
            ((Label)scene.lookup("#gameSize")).setText(game.getSizeGB()+"");
            ((Label)scene.lookup("#gamePrice")).setText(game.getPrice()+"");
            ((Label)scene.lookup("#gamePolish")).setText(game.getIsPolishString());
            ((ImageView)scene.lookup("#gameImage")).setImage(new Image(new File(app.pathToImages+game.getImage()).getAbsolutePath()));
            vbox.getChildren().add(hBox);
        }
        scrollPane.setContent(vbox);
    }

    @FXML
    public void applyFilters(ActionEvent e) throws IOException {
        this.categoryPanel.setFilters();
        this.addToGameList(this.getSpecializedFilter().getCorrectGames(this.app.provider.getGames()));
    }

    @FXML
    public void setCategoryPanel() throws IOException {
        ScrollPane scrollPane=(ScrollPane) stage.getScene().lookup("#categoryList");

        scrollPane.setContent(categoryPanel.createVBOX(this.app.provider.getAllCategories()));
    }

    @FXML
    private void resetFilters(ActionEvent e) throws IOException {
        setCategoryPanel();
        addToGameList(this.app.provider.getGames());
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
