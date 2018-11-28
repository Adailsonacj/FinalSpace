package com.example.adailson.spacex;

import com.example.adailson.spacex.AndGraph.AGGameManager;
import com.example.adailson.spacex.AndGraph.AGInputManager;
import com.example.adailson.spacex.AndGraph.AGScene;
import com.example.adailson.spacex.AndGraph.AGScreenManager;
import com.example.adailson.spacex.AndGraph.AGSprite;

public class CenaCreditos extends AGScene {

    private AGSprite infoexit = null;
    private AGSprite infovir = null;
    private AGSprite infoshot = null;

    public CenaCreditos(AGGameManager gameManager) {
        super(gameManager);
    }

    @Override
    public void init() {
        setSceneBackgroundColor(0, 0, 0);

        infoexit = createSprite(R.mipmap.infoexit, 1, 1);
        infoexit.setScreenPercent(60, 20);
        infoexit.vrPosition.setXY(AGScreenManager.iScreenWidth / 2, AGScreenManager.iScreenHeight/ 6);

        infovir = createSprite(R.mipmap.infovir, 1, 1);
        infovir.setScreenPercent(30, 30);
        infovir.vrPosition.setXY(AGScreenManager.iScreenWidth / 2, AGScreenManager.iScreenHeight/ 2);

        infoshot = createSprite(R.mipmap.infoshot, 1, 1);
        infoshot.setScreenPercent(60, 20);
        infoshot.vrPosition.setXY(AGScreenManager.iScreenWidth / 2, AGScreenManager.iScreenHeight- AGScreenManager.iScreenHeight/ 5);
    }

    @Override
    public void restart() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void loop() {
        if (AGInputManager.vrTouchEvents.backButtonClicked()) {
            vrGameManager.setCurrentScene(1);
        }
        if (AGInputManager.vrTouchEvents.screenClicked()) {
            vrGameManager.setCurrentScene(1);
        }

    }
}
