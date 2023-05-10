package com.example.project.Controllers;

import com.example.project.Logic.MainController.AppController;
import com.example.project.Logic.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {

    private AppController app;
    private Stage stage;

    @FXML
    private Button registerOnButton;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField password1;
    @FXML
    private TextField firstname;
    @FXML
    private TextField lastname;
    @FXML
    private TextField email;
    @FXML
    private Label waring;

    @FXML
    protected void onRegisterOnButtonClick(ActionEvent e) throws IOException {

        if(!getPassword().equals(getPassword1())){
            waring.setText("Niepoprawne hasło");
        }
        else if(app.userInDataBase(getUsername())){
            waring.setText("Użytkownik istnieje");
        }
        else{
            User user= new User(getFirstname(),getLastname(),getUsername(),getPassword(),getEmail());
            app.setUsers(user);

            app.loginLoad(new Stage());
            closeStage();
        }


    }

    @FXML
    protected void onReturn(ActionEvent e) throws IOException {

        this.app.loginLoad(new Stage());
        closeStage();
    }
    private void closeStage(){
        this.stage.close();
    }

    public String getPassword(){
        return password.getText();
    }

    public String getUsername() {
        return username.getText();
    }

    public String getPassword1() {
        return password1.getText();
    }

    public String getFirstname() {
        return firstname.getText();
    }

    public String getLastname() {
        return lastname.getText();
    }

    public String getEmail() {
        return email.getText();
    }

    public void setApp(AppController app){
        this.app=app;
    }
    public void setStage(Stage stage){
        this.stage=stage;
    }

}
