package com.example.project.Controllers;

import com.example.project.Controllers.GameViewBox.LibraryGameViewBox;
import com.example.project.Logic.CategoryPanel;
import com.example.project.Logic.Filters.SpecializedFilter;
import com.example.project.Logic.Game;
import com.example.project.Logic.MainController.AppController;
import com.example.project.Logic.Payment;
import com.example.project.Logic.PlatformOrder;
import com.example.project.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

public class PaymentController {

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
    private TextField amount;
    @FXML
    private TextField title;
    @FXML
    private TextField date;
    @FXML
    private TextField accountNumber;
    @FXML
    private Label codeProblem;

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
    protected void OnRefresh(ActionEvent e) throws IOException {
        this.app.paymentLoad(new Stage());
        closeStage();
    }

    @FXML
    protected void onPaymentButton(ActionEvent e) throws ParseException, IOException {
        if(getDate().compareTo(new Timestamp(System.currentTimeMillis()))>0){
            this.setCodeProblem("Problem !");
        }
        else {
            this.app.provider.addPayment(this.app.getUser().getUsername(), new Payment(getAmount(), getAccountNumber(), getDate(), getTitle()));
            this.app.paymentLoad(new Stage());
            closeStage();
        }
    }

    @FXML
    protected void onOrderList(ActionEvent e) throws IOException {
        addAllOrders();
    }

    @FXML
    protected void onPaymentList(ActionEvent e) throws IOException {
        addAllPayments();
    }

    @FXML
    protected void setUserName(String user){
        userName.setText(user);
    }


    @FXML
    public void addAllOrders() throws IOException {
        ScrollPane scrollPane=(ScrollPane) stage.getScene().lookup("#orderList");
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPrefHeight(890);
        vbox.setPrefWidth(675);
        vbox.setStyle("-fx-background-color: #404040;");

        for(PlatformOrder order: this.app.provider.getUsersByName(this.app.getUser().getUsername()).getPlatformOrders()){
            FXMLLoader fxml = new FXMLLoader(Main.class.getResource("payment-view-box.fxml"));
            Scene scene = new Scene(fxml.load());
            PaymentViewBoxController paymentViewBoxController=fxml.getController();
            paymentViewBoxController.setApp(app);

            paymentViewBoxController.addOrderInfo(order.getOrder_ID());
            HBox hBox = (HBox) scene.lookup("#paymentBox");

            vbox.getChildren().add(hBox);
        }
        scrollPane.setContent(vbox);
    }

    @FXML
    public void addAllPayments() throws IOException {
        ScrollPane scrollPane=(ScrollPane) stage.getScene().lookup("#orderList");
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setPrefHeight(890);
        vbox.setPrefWidth(675);
        vbox.setStyle("-fx-background-color: #404040;");

        for(Payment payment: this.app.provider.getUsersByName(this.app.getUser().getUsername()).getPayments()){
            FXMLLoader fxml = new FXMLLoader(Main.class.getResource("payment-view-box.fxml"));
            Scene scene = new Scene(fxml.load());
            PaymentViewBoxController paymentViewBoxController=fxml.getController();
            paymentViewBoxController.setApp(app);

            paymentViewBoxController.addPaymentInfo(payment.getPayment_ID());
            HBox hBox = (HBox) scene.lookup("#paymentBox");

            vbox.getChildren().add(hBox);
        }
        scrollPane.setContent(vbox);
    }

    protected float getAmount(){
        return Float.parseFloat(this.amount.getText());
    }
    private String getTitle(){
        return this.title.getText();
    }
    private String getAccountNumber(){
        return this.accountNumber.getText();
    }
    private Timestamp getDate() throws ParseException {
        return this.app.provider.getTimeFromString(this.date.getText());
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

    protected void setCodeProblem(String text){
        this.codeProblem.setText(text);
    }

}
