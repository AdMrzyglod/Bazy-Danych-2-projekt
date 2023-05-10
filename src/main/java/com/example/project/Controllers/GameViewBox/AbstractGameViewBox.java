package com.example.project.Controllers.GameViewBox;

import com.example.project.Controllers.GameViewBoxController;

public abstract class AbstractGameViewBox {

    protected GameViewBoxController gameViewBoxController;

    public AbstractGameViewBox(GameViewBoxController gameViewBoxController){
        this.gameViewBoxController=gameViewBoxController;
    }

    public abstract void onBuyButtonClickOn();
}
