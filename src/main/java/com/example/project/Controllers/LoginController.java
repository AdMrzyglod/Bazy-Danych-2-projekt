package com.example.project.Controllers;

import com.example.project.Logic.MainController.AppController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    private AppController app;
    private Stage stage;

    @FXML
    private Button logOnButton;
    @FXML
    private Label waring;

    @FXML
    protected void onLogOnButtonClick(ActionEvent e) throws IOException {

        if(this.app.provider.loginAuthorization(getUserName(),getPassword())) {

            app.setUser(this.app.provider.getUsersByName(getUserName()));

            app.setCompany(this.app.provider.getIsCompanyUser(this.app.getUser().getUser_ID()));

            app.homeLoad(new Stage());

            closeStage();
        }
        else if(this.app.provider.employeeLoginAuthorization(getUserName(),getPassword())){

            app.setEmployee(this.app.provider.getEmployee(getUserName()));

            app.employeeLoad(new Stage());

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
