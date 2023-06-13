package com.example.project.Controllers;

import com.example.project.Logic.CategoryPanel;
import com.example.project.Logic.MainController.AppController;
import com.example.project.Logic.DatabaseClasses.Tournament;
import com.example.project.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class TournamentController {

    private AppController app;
    private Stage stage;

    private boolean disableButton=false;
    private boolean addlist=false;

    private CategoryPanel categoryPanel = new CategoryPanel();

    @FXML
    private Label userName;
    @FXML
    private Button createButton;
    @FXML
    private Button showButton;

    @FXML
    private Button shopButton;

    @FXML
    private ScrollPane tournamentList;

    @FXML
    private TextField mainCode;

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
    protected void onAvailableTournament(ActionEvent e) throws IOException {
        this.disableButton=false;
        this.addlist=false;
       addTournaments(this.app.provider.getAvailableTournaments(this.app.getUser().getUser_ID()));
    }

    @FXML
    protected void onInfoTournament(ActionEvent e) throws IOException {
        this.disableButton=true;
        this.addlist=false;
        addTournaments(this.app.provider.getUserTournaments(this.app.getUser().getUser_ID()));
    }

    @FXML
    protected void onShowButton(ActionEvent e) throws IOException {
        this.addlist=true;
        addTournaments(this.app.provider.getTournamentsCreatedByUser(this.app.getUser().getUser_ID()));
    }


    @FXML
    protected void OnRefresh(ActionEvent e) throws IOException {
        this.app.tournamentLoad(new Stage());
        closeStage();
    }

    @FXML
    protected void onCreateTournament(ActionEvent e) throws IOException {
        ScrollPane scrollPane=(ScrollPane) stage.getScene().lookup("#tournamentList");
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPrefHeight(890);
        vbox.setPrefWidth(675);
        vbox.setStyle("-fx-background-color: #404040;");
        FXMLLoader fxml = new FXMLLoader(Main.class.getResource("create-tournament-view-box.fxml"));
        Scene scene = new Scene(fxml.load());
        CreateTournamentViewBoxController CreateTournamentViewBoxController=fxml.getController();
        CreateTournamentViewBoxController.setApp(app);
        CreateTournamentViewBoxController.setTournamentController(this);

        HBox hBox = (HBox) scene.lookup("#firstBox");
        vbox.getChildren().add(hBox);
        scrollPane.setContent(vbox);
    }

    @FXML
    protected void setUserName(String user){
        userName.setText(user);
    }

    @FXML
    protected String getMainCode(){
        return this.mainCode.getText();
    }

    @FXML
    public void addTournaments(List<Tournament> tournaments) throws IOException {
        ScrollPane scrollPane=(ScrollPane) stage.getScene().lookup("#tournamentList");
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPrefHeight(890);
        vbox.setPrefWidth(675);
        vbox.setStyle("-fx-background-color: #404040;");

        for(Tournament tournament: tournaments){
            FXMLLoader fxml = new FXMLLoader(Main.class.getResource("tournament-view-box.fxml"));
            Scene scene = new Scene(fxml.load());
            TournamentViewBoxController tournamentViewBoxController=fxml.getController();
            tournamentViewBoxController.setApp(app);
            tournamentViewBoxController.setTournamentController(this);

            if(!disableButton) {
                tournamentViewBoxController.addTournament(tournament, "");
            }
            else{
                tournamentViewBoxController.addTournament(tournament, this.app.provider.getTournamentCode(this.app.getUser().getUser_ID(),tournament.getTournament_ID()));
                tournamentViewBoxController.setActionButton("Kod dostępu:");
            }

            if(addlist){
                tournamentViewBoxController.setActionButton();
            }

            HBox hBox = (HBox) scene.lookup("#firstBox");

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

    public void settingsForIndividual(){
        this.createButton.setDisable(true);
        this.createButton.setVisible(false);
        this.showButton.setDisable(true);
        this.showButton.setVisible(false);
    }

    public void setUserList(VBox vBox){
        ScrollPane scrollPane=(ScrollPane) stage.getScene().lookup("#tournamentList");
        scrollPane.setContent(vBox);
    }

}
