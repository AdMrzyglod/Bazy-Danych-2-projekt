package com.example.project.Controllers;

import com.example.project.Logic.DatabaseClasses.Game;
import com.example.project.Logic.DatabaseClasses.PlatformUser;
import com.example.project.Logic.DatabaseClasses.Tournament;
import com.example.project.Logic.MainController.AppController;
import com.example.project.RandomCode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class TournamentViewBoxController {

    private AppController app;
    private TournamentController tournamentController;

    @FXML
    private HBox firstBox;
    @FXML
    private VBox secondBox;

    @FXML
    private Label tournamentNumber;
    @FXML
    private Label name;
    @FXML
    private Label start;
    @FXML
    private Label end;
    @FXML
    private Label company;
    @FXML
    private Label game;
    @FXML
    private Label maxPlayers;
    @FXML
    private Label numberPlayers;
    @FXML
    private TextArea description;
    @FXML
    private TextField tournamentCode;
    @FXML
    private Button actionButton;


    @FXML
    protected void onTakePart(ActionEvent e) throws IOException {

        Tournament tournament = this.app.provider.getTournamentByName(getName());
        Game game = tournament.getGame();

        if(tournament.getUserLimit()-tournament.getNumberOfPlayers()==0 || !this.app.provider.userOwnGame(this.app.getUser().getUsername(),game)){
            this.setTournamentCode("Problem z zapisem!");
        }
        else{
            this.app.provider.addUserToTournament(this.app.getUser().getUsername(),getName(), RandomCode.getString());
            this.tournamentController.OnRefresh(new ActionEvent());
        }
    }


    private void setTournamentNumber(String text){
        this.tournamentNumber.setText(text);
    }

    private void setName(String text){
        this.name.setText(text);
    }
    private void setStart(String text){
        this.start.setText(text);
    }
    private void setEnd(String text){
        this.end.setText(text);
    }
    private void setCompany(String text){
        this.company.setText(text);
    }
    private void setGame(String text){
        this.game.setText(text);
    }
    private void setMaxPlayers(String text){
        this.maxPlayers.setText(text);
    }
    private void setNumberPlayers(String text){
        this.numberPlayers.setText(text);
    }
    private void setDescription(String text){
        this.description.setText(text);
    }
    private void setTournamentCode(String text){
        this.tournamentCode.setText(text);
        this.tournamentCode.setVisible(true);
    }

    protected void setActionButton(String text){
        this.actionButton.setText(text);
        this.actionButton.setDisable(true);
    }

    protected void disableActionButton(){
        this.actionButton.setDisable(true);
        this.actionButton.setVisible(false);
    }

    private String getName(){
        return this.name.getText();
    }


    public void addTournament(Tournament tournament,String code){
        setTournamentNumber(tournament.getTournament_ID()+"");
        setName(tournament.getName());
        setStart(tournament.getStartTournament()+"");
        setEnd(tournament.getEndTournament()+"");
        setCompany(tournament.getCompanyUser().getUsername());
        setGame(tournament.getGame().getGameName());
        setMaxPlayers(tournament.getUserLimit()+"");
        setNumberPlayers(tournament.getNumberOfPlayers()+"");
        setDescription(tournament.getDescription());
        this.tournamentCode.setVisible(false);
        this.tournamentCode.setEditable(false);
        if(!code.equals("")){
            setTournamentCode(code);
        }
    }

    public void setApp(AppController app){
        this.app=app;
    }


    public AppController getApp() {
        return app;
    }

    public TournamentController getTournamentController() {
        return tournamentController;
    }

    public void setTournamentController(TournamentController tournamentController) {
        this.tournamentController = tournamentController;
    }

    public void setActionButton(){
        this.actionButton.setText("Lista");
        this.actionButton.setDisable(false);
        this.actionButton.setOnAction((ActionEvent e)->{


            List<PlatformUser> platformUsers = this.app.provider.getTournamentsAllUsers(Integer.parseInt(this.tournamentNumber.getText()));
            if(platformUsers.size()>0) {

                VBox vbox = new VBox();
                vbox.setSpacing(10);
                vbox.setPrefHeight(890);
                vbox.setPrefWidth(675);
                vbox.setStyle("-fx-background-color: #404040;");


                VBox titleBox = new VBox();
                titleBox.setPrefSize(660, 75);
                Label labelBox = new Label("  Numer Turnieju:      " + this.tournamentNumber.getText() + "      " + this.name.getText() + "  ");
                labelBox.setStyle("-fx-font-size: 24px; -fx-text-fill: #000056; -fx-font-weight: BOLD;");
                labelBox.setPrefSize(660, 75);
                titleBox.getChildren().add(labelBox);
                labelBox.setAlignment(Pos.CENTER);
                titleBox.setAlignment(Pos.CENTER);
                vbox.getChildren().add(titleBox);


                for (int i = 0; i < platformUsers.size(); i++) {

                    PlatformUser user = platformUsers.get(i);

                    VBox infoBox = new VBox();
                    infoBox.setPrefSize(660, 50);
                    Label infoLabel = new Label("   " + (i + 1) + "         " + user.getUsername() + "   " + this.app.provider.getCodeToTournament(Integer.parseInt(this.tournamentNumber.getText()),user.getUser_ID()));
                    infoLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: #000056; -fx-font-weight: BOLD;");
                    infoLabel.setPrefSize(660, 50);
                    infoLabel.setAlignment(Pos.CENTER);
                    infoBox.getChildren().add(infoLabel);
                    infoBox.setAlignment(Pos.CENTER);
                    vbox.getChildren().add(infoBox);
                }

                this.tournamentController.setUserList(vbox);
            }

        });
    }
}
