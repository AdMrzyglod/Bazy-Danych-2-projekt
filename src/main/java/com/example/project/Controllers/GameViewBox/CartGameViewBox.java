package com.example.project.Controllers.GameViewBox;

import com.example.project.Controllers.GameViewBoxController;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class CartGameViewBox extends AbstractGameViewBox{

    public CartGameViewBox(GameViewBoxController gameViewBoxController){
        super(gameViewBoxController);
    }

    @Override
    public void onBuyButtonClickOn() {
        super.gameViewBoxController.getApp().getCart().removeGame(super.gameViewBoxController.getGame());
        try {
            super.gameViewBoxController.getApp().getCartPageController().addToGameList(super.gameViewBoxController.getApp().getCart().getGamesCart());
            super.gameViewBoxController.refreshPriceLabel();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
