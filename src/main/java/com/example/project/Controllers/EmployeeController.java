package com.example.project.Controllers;

import com.example.project.Logic.CategoryPanel;
import com.example.project.Logic.MainController.AppController;
import com.example.project.Logic.DatabaseClasses.Payment;
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
import java.util.ArrayList;
import java.util.List;

public class EmployeeController {

    private AppController app;
    private Stage stage;
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
    protected void onLogout(ActionEvent e) throws IOException {
        this.app.setUser(null);
        this.app.loginLoad(new Stage());
        closeStage();
    }

    @FXML
    protected void OnRefresh(ActionEvent e) throws IOException {
        this.app.employeeLoad(new Stage());
        closeStage();
    }


    @FXML
    public void onCreate() throws IOException {
        ScrollPane scrollPane=(ScrollPane) stage.getScene().lookup("#view");
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPrefHeight(950);
        vbox.setPrefWidth(675);
        vbox.setStyle("-fx-background-color: #404040;");
        FXMLLoader fxml = new FXMLLoader(Main.class.getResource("create-game-view-box.fxml"));
        Scene scene = new Scene(fxml.load());
        CreateGameViewBoxController createGameViewBoxController=fxml.getController();
        createGameViewBoxController.setApp(app);
        createGameViewBoxController.setEmployeeController(this);

        HBox hBox = (HBox) scene.lookup("#firstBox");
        vbox.getChildren().add(hBox);
        scrollPane.setContent(vbox);
    }

    @FXML
    protected void onPayment() throws IOException {
        ScrollPane scrollPane=(ScrollPane) stage.getScene().lookup("#view");
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPrefHeight(890);
        vbox.setPrefWidth(675);
        vbox.setStyle("-fx-background-color: #404040;");

        List<Payment> payments = this.app.provider.getAllNotVerifiedPayment();

        for(Payment payment: payments==null ? new ArrayList<Payment>():payments){
            FXMLLoader fxml = new FXMLLoader(Main.class.getResource("payment-view-box.fxml"));
            Scene scene = new Scene(fxml.load());
            PaymentViewBoxController paymentViewBoxController=fxml.getController();
            paymentViewBoxController.setApp(app);
            paymentViewBoxController.showVerifiedButton();

            paymentViewBoxController.addPaymentInfo(payment.getPayment_ID());
            HBox hBox = (HBox) scene.lookup("#paymentBox");

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


}
