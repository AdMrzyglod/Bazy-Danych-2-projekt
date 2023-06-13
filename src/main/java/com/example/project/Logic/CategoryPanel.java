package com.example.project.Logic;

import com.example.project.Logic.DatabaseClasses.Category;
import com.example.project.Logic.Filters.SpecializedFilter;
import com.example.project.Main;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class CategoryPanel {

    public SpecializedFilter specializedFilter = new SpecializedFilter();

    TextField fromSize;
    TextField toSize;
    TextField fromPrice;
    TextField toPrice;




    public VBox createVBOX(List<Category> categories) throws IOException {

        VBox vBox = new VBox();
        vBox.setPrefWidth(268);
        vBox.setPrefHeight(560);
        vBox.setStyle("-fx-background-color: #505050;");

        vBox.getChildren().addAll(addCategories(categories),addPriceInterval(),addSizeGBInterval(),addPolishOption());

        this.specializedFilter.refreshFilter();

        return vBox;
    }

    private VBox addCategories(List<Category> categories) throws IOException {
        FXMLLoader fxml = new FXMLLoader(Main.class.getResource("view-container.fxml"));
        Scene scene = new Scene(fxml.load());
        VBox panel = (VBox) scene.lookup("#container");
        panel.prefHeight(60+50*categories.size());
        panel.setMinHeight(60+50*categories.size());
        ((Label) scene.lookup("#name")).setText("Rodzaj:");

        for(Category category: categories){
            HBox box = new HBox();
            box.setAlignment(Pos.CENTER);
            box.setPrefHeight(50);
            box.setPrefWidth(250);
            CheckBox checkBox = new CheckBox(category.getCategoryName());
            checkBox.setOnAction((actionEvent) -> {
                if(checkBox.isSelected()){
                    specializedFilter.gameFilter.addCategory(checkBox.getText());
                }
                else{
                    specializedFilter.gameFilter.removeCategory(checkBox.getText());
                }

            });
            checkBox.setPrefHeight(50);
            checkBox.setPrefWidth(200);
            checkBox.setAlignment(Pos.CENTER);
            checkBox.setStyle("-fx-text-fill: #202020; -fx-font-size: 18px;");
            box.getChildren().add(checkBox);
            panel.getChildren().add(box);
        }
        return panel;
    }

    private VBox addPolishOption() throws IOException {
        FXMLLoader fxml = new FXMLLoader(Main.class.getResource("view-container.fxml"));
        Scene scene = new Scene(fxml.load());
        VBox panel = (VBox) scene.lookup("#container");
        panel.prefHeight(100);
        panel.maxHeight(100);
        ((Label) scene.lookup("#name")).setText("PL Wersja:");

        ToggleGroup toggleGroup = new ToggleGroup();
        RadioButton radioButton1= new RadioButton(Option.YES.getDisplayName());
        RadioButton radioButton2= new RadioButton(Option.NO.getDisplayName());
        RadioButton radioButton3= new RadioButton(Option.BOTH.getDisplayName());
        radioButton1.setStyle("-fx-text-fill: #202020;-fx-font-size: 15px;");
        radioButton2.setStyle("-fx-text-fill: #202020;-fx-font-size: 15px;");
        radioButton3.setStyle("-fx-text-fill: #202020;-fx-font-size: 15px;");
        radioButton1.setOnAction((actionEvent) -> {
            this.specializedFilter.gameFilter.setIsPolish(Option.fromString(radioButton1.getText()));
        });
        radioButton2.setOnAction((actionEvent) -> {
            this.specializedFilter.gameFilter.setIsPolish(Option.fromString(radioButton2.getText()));
        });
        radioButton3.setOnAction((actionEvent) -> {
            this.specializedFilter.gameFilter.setIsPolish(Option.fromString(radioButton3.getText()));
        });
        radioButton1.setToggleGroup(toggleGroup);
        radioButton2.setToggleGroup(toggleGroup);
        radioButton3.setToggleGroup(toggleGroup);
        radioButton3.setSelected(true);

        HBox box = new HBox();
        box.setPrefHeight(100);
        box.setPrefWidth(250);
        box.setAlignment(Pos.CENTER);

        VBox box1= new VBox(radioButton1);
        box1.setPrefHeight(80);
        box1.setPrefWidth(80);
        box1.setAlignment(Pos.CENTER);
        VBox box2= new VBox(radioButton2);
        box2.setPrefHeight(80);
        box2.setPrefWidth(80);
        box2.setAlignment(Pos.CENTER);
        VBox box3= new VBox(radioButton3);
        box3.setPrefHeight(80);
        box3.setPrefWidth(80);
        box3.setAlignment(Pos.CENTER);

        box.getChildren().addAll(box1,box2,box3);
        panel.getChildren().add(box);

        return panel;
    }

    public VBox addPriceInterval() throws IOException {
        FXMLLoader fxml = new FXMLLoader(Main.class.getResource("number-container.fxml"));
        Scene scene = new Scene(fxml.load());
        VBox panel = (VBox) scene.lookup("#container");
        ((Label) scene.lookup("#name")).setText("Cena:");
        fromPrice =(TextField)scene.lookup("#from");
        toPrice =(TextField)scene.lookup("#to");

        fromPrice.setOnAction((actionEvent) -> {
            this.specializedFilter.gameFilter.setPriceFrom(BigDecimal.valueOf(Double.parseDouble(fromPrice.getText())));
        });
        toPrice.setOnAction((actionEvent) -> {
            this.specializedFilter.gameFilter.setPriceTo(BigDecimal.valueOf(Double.parseDouble(toPrice.getText())));
        });

        return panel;
    }

    public VBox addSizeGBInterval() throws IOException {
        FXMLLoader fxml = new FXMLLoader(Main.class.getResource("number-container.fxml"));
        Scene scene = new Scene(fxml.load());
        VBox panel = (VBox) scene.lookup("#container");
        ((Label) scene.lookup("#name")).setText("Rozmiar GB:");
        fromSize =(TextField)scene.lookup("#from");
        toSize =(TextField)scene.lookup("#to");

        fromSize.setOnAction((actionEvent) -> {
            this.specializedFilter.gameFilter.setSizeGBFrom(BigDecimal.valueOf(Double.parseDouble(fromSize.getText())));
        });
        toSize.setOnAction((actionEvent) -> {
            this.specializedFilter.gameFilter.setSizeGBTo(BigDecimal.valueOf(Double.parseDouble(toPrice.getText())));
        });

        return panel;
    }

    public void setFilters(){
        this.specializedFilter.gameFilter.setPrice(BigDecimal.valueOf(Double.parseDouble(fromPrice.getText())),BigDecimal.valueOf(Double.parseDouble(toPrice.getText())));
        this.specializedFilter.gameFilter.setSizeGB(BigDecimal.valueOf(Double.parseDouble(fromSize.getText())),BigDecimal.valueOf(Double.parseDouble(toSize.getText())));

    }
}
