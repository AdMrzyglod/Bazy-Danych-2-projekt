package com.example.project.Controllers;

import com.example.project.Logic.CompanyUser;
import com.example.project.Logic.Game;
import com.example.project.Logic.MainController.AppController;
import com.example.project.Logic.Tournament;
import com.example.project.RandomCode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.text.ParseException;

public class CreateTournamentViewBoxController {

    private AppController app;
    private TournamentController tournamentController;

    @FXML
    private HBox firstBox;
    @FXML
    private VBox secondBox;

    @FXML
    private TextField name;
    @FXML
    private TextField start;
    @FXML
    private TextField end;
    @FXML
    private TextField game;
    @FXML
    private TextField limit;
    @FXML
    private TextArea description;
    @FXML
    private TextField waring;
    @FXML
    private Button actionButton;


    @FXML
    protected void onTournamentCreate(ActionEvent e) throws IOException, ParseException {

        Tournament tournament = getTournament();
        if(tournament==null){
            this.setWaring("Problem !");
        }
        else{
            this.app.provider.addTournament(tournament);
            this.tournamentController.OnRefresh(new ActionEvent());
        }
    }


    private void setWaring(String text){
        this.waring.setEditable(false);
        this.waring.setText(text);
    }

    private String getName(){
        return this.name.getText();
    }

    private String getStart(){
        return this.start.getText();
    }
    private String getEnd(){
        return this.end.getText();
    }
    private String getGame(){
        return this.game.getText();
    }
    private String getLimit(){
        return this.limit.getText();
    }
    private String getDescription(){
        return this.description.getText();
    }



    public Tournament getTournament() throws ParseException {
        if(this.app.provider.getGameByName(getGame())==null){
            return null;
        }
        return new Tournament(getName(),this.app.provider.getTimeFromString(getStart()),this.app.provider.getTimeFromString(getEnd()),getDescription(),Integer.parseInt(getLimit()),(CompanyUser) this.app.provider.getUsersByName(this.app.getUser().getUsername()),this.app.provider.getGameByName(getGame()));
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
}
