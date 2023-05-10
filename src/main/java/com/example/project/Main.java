package com.example.project;

import com.example.project.Logic.MainController.AppController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main  {

    public static void main(String[] args) {
        Application.launch(AppController.class,args);
    }
}