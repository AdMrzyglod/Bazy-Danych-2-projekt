package com.example.project.Controllers.GameViewBox;

import com.example.project.Controllers.GameViewBoxController;

public class ShopGameViewBox extends AbstractGameViewBox{

    public ShopGameViewBox(GameViewBoxController gameViewBoxController){
        super(gameViewBoxController);
    }

    @Override
    public void onBuyButtonClickOn() {
        if (!super.gameViewBoxController.isInCart()) {
            super.gameViewBoxController.setInCart(true);
            super.gameViewBoxController.setBuyButton("Cofnij");
            super.gameViewBoxController.getApp().getCart().addGame(super.gameViewBoxController.getGame(),this.gameViewBoxController.getQuantity());
        } else {
            super.gameViewBoxController.setInCart(false);
            super.gameViewBoxController.setBuyButton("Kup");
            super.gameViewBoxController.getApp().getCart().removeGame(super.gameViewBoxController.getGame());
        }
    }
}
