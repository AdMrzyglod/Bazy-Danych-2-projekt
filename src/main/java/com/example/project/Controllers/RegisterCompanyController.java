package com.example.project.Controllers;

import com.example.project.Logic.DatabaseClasses.CompanyUser;
import com.example.project.Logic.MainController.AppController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterCompanyController {

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
    private TextField companyName;
    @FXML
    private TextField city;
    @FXML
    private TextField street;
    @FXML
    private TextField phone;
    @FXML
    private TextField email;
    @FXML
    private TextField country;
    @FXML
    private Label waring;
    @FXML
    private ToggleGroup toggle;

    @FXML
    protected void onRegisterOnButtonClick(ActionEvent e) throws IOException {
        System.out.println(getUsername());

        if(!getPassword().equals(getPassword1())){
            waring.setText("Niepoprawne hasło");
        }
        else if(app.provider.userExists(getUsername())){
            waring.setText("Użytkownik istnieje");
        }
        else{
            this.app.provider.addCompanyUser(new CompanyUser(getUsername(),getPassword(),getEmail(),getCompanyName(),getCity(),getStreet(),getPhone(),getCountry()));

            app.loginLoad(new Stage());
            closeStage();
        }


    }

    @FXML
    protected void onReturn(ActionEvent e) throws IOException {

        this.app.loginLoad(new Stage());
        closeStage();
    }

    @FXML
    protected void onIndividual(ActionEvent e) throws IOException {
        this.app.registerLoad(new Stage());
        closeStage();
    }

    @FXML
    protected void onCompany(ActionEvent e) throws IOException {
        this.app.registerCompanyLoad(new Stage());
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

    public String getCompanyName() {
        return companyName.getText();
    }

    public String getCountry(){
        return country.getText();
    }

    public String getCity() {
        return city.getText();
    }

    public String getStreet() {
        return street.getText();
    }

    public String getPhone() {
        return phone.getText();
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
