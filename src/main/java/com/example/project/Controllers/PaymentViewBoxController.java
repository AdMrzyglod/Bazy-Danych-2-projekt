package com.example.project.Controllers;

import com.example.project.Logic.DatabaseClasses.GameCode;
import com.example.project.Logic.DatabaseClasses.OrderDetails;
import com.example.project.Logic.DatabaseClasses.Payment;
import com.example.project.Logic.DatabaseClasses.PlatformOrder;
import com.example.project.Logic.MainController.AppController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentViewBoxController {

    private AppController app;

    @FXML
    private HBox paymentBox;
    @FXML
    private VBox secondPaymentBox;

    @FXML
    private Label orderNumber;

    @FXML
    private Label orderDate;
    @FXML
    private Label orderName;
    @FXML
    private Button verifiedButton;


    public void setOrderNumber(String number){
        this.orderNumber.setText(number);
    }
    public void setOrderDate(Timestamp timestamp){
        this.orderDate.setText(timestamp+"");
    }
    public void setOrderName(String text){
        this.orderName.setText(text);
    }

    public void addOrderInfo(int OrderID){
        PlatformOrder order =  app.provider.getOrder(OrderID);
        setOrderNumber(order.getOrder_ID()+"");
        setOrderDate(order.getOrderDate());
        int height= 75;


        for(OrderDetails detail :order.getOrderDetails()){

            List<GameCode> codes = this.app.provider.getCodesFromDetails(detail.getOrderDetails_ID());
            VBox container = new VBox();
            this.paymentBox.setHgrow(container, Priority.ALWAYS);
            container.setPrefSize(660, 75+50*codes.size());
            height+=75+50*codes.size();
            TextField label = new TextField("       Gra:  " +detail.getGameCode().getGame().getGameName()+"         Cena:  "+detail.getCodePrice());
            label.setStyle("-fx-font-size: 20px; -fx-text-fill: #000056; -fx-font-weight: BOLD;-fx-background-color:#404040;");
            label.setPrefSize(660,75);
            label.setEditable(false);
            label.setAlignment(Pos.CENTER);
            container.getChildren().add(label);

            for(GameCode code: codes){
                TextField codeLabel = new TextField("  Kod:     "+code.getAccessCode()+"      Użyto:     "+ (code.isUsed()==true ? "Tak" : "Nie"));
                codeLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: #000056; -fx-font-weight: BOLD;-fx-background-color:#404040;");
                codeLabel.setPrefSize(660,50);
                codeLabel.setEditable(false);
                codeLabel.setAlignment(Pos.CENTER);
                container.getChildren().add(codeLabel);
            }

            this.secondPaymentBox.getChildren().add(container);

        }
        this.paymentBox.setPrefHeight(height);

        this.secondPaymentBox.setPrefHeight(height);

    }

    public void addPaymentInfo(int paymentID){
        Payment payment =  app.provider.getPayment(paymentID);

        setOrderName("Numer wpłaty:");
        setOrderNumber(payment.getPayment_ID()+"");
        setOrderDate(payment.getDate());
        int height= 75;

        HashMap<String,String> set = new HashMap<>();
        set.put("Tytuł wpłaty:",payment.getTitle());
        set.put("Kwota:",payment.getAmount()+"");
        set.put("Numer konta:",payment.getBankAccountNumber());
        set.put("Zweryfikowano:",payment.isVerified()+"");

        for(Map.Entry<String,String> entry: set.entrySet()){

            VBox container = new VBox();
            this.paymentBox.setHgrow(container, Priority.ALWAYS);
            container.setPrefSize(660, 50);
            height+=50;
            TextField label = new TextField( "     "+entry.getKey()+"      "+entry.getValue()+"     ");
            label.setStyle("-fx-font-size: 20px; -fx-text-fill: #000056; -fx-font-weight: BOLD;-fx-background-color:#404040;");
            label.setPrefSize(660,50);
            label.setEditable(false);
            label.setAlignment(Pos.CENTER);
            container.getChildren().add(label);

            this.secondPaymentBox.getChildren().add(container);

        }
        this.paymentBox.setPrefHeight(height);

        this.secondPaymentBox.setPrefHeight(height);

    }

    public void setApp(AppController app){
        this.app=app;
    }


    public AppController getApp() {
        return app;
    }


    @FXML
    protected void onAccept(ActionEvent e){
        this.app.provider.verifiedPayment(Integer.parseInt(this.orderNumber.getText()));
        this.verifiedButton.setText("Ok");
        this.verifiedButton.setDisable(true);
    }

    public void showVerifiedButton(){
        this.verifiedButton.setDisable(false);
        this.verifiedButton.setVisible(true);
    }
}
