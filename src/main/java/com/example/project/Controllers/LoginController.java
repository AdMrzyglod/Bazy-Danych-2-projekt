package com.example.project.Controllers;

import com.example.project.Logic.MainController.AppController;
import com.example.project.Logic.User;
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

import static java.lang.System.out;

public class LoginController {

    private AppController app;
    private Stage stage;

    @FXML
    private Button logOnButton;
    @FXML
    private Label waring;

    @FXML
    protected void onLogOnButtonClick(ActionEvent e) throws IOException {

        if(app.checkUser(getUserName(),getPassword())) {

            app.setUser(new User(getUserName()));
            app.homeLoad(new Stage());

            closeStage();
        }
        else{
            waring.setText("Niepoprawne dane");
        }
    }

    @FXML
    protected void onRegister(ActionEvent e) throws IOException{
        app.registerLoad(new Stage());
        closeStage();
    }
    private void closeStage(){
        this.stage.close();
    }
    public String getUserName(){
        Scene scene = stage.getScene();
        String userName=((TextField)scene.lookup("#username")).getText();
        return userName;
    }

    public String getPassword(){
        Scene scene = stage.getScene();
        String password=((TextField)scene.lookup("#password")).getText();
        return password;
    }

    public void setApp(AppController app){
        this.app=app;
    }
    public void setStage(Stage stage){
        this.stage=stage;
    }

}
