package com.example.project.Controllers.GameViewBox;

import com.example.project.Controllers.GameViewBoxController;

public class LibraryGameViewBox extends AbstractGameViewBox{

    public LibraryGameViewBox(GameViewBoxController gameViewBoxController){
        super(gameViewBoxController);
    }

    @Override
    public void onBuyButtonClickOn() {
        if (super.gameViewBoxController.getBuyButtonText().equals("Włącz")) {
            super.gameViewBoxController.setBuyButton("Wyłącz");
        } else {
            super.gameViewBoxController.setBuyButton("Włącz");
        }
    }
}
