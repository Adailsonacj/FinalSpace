package com.example.adailson.spacex;

import com.example.adailson.spacex.AndGraph.AGGameManager;
import com.example.adailson.spacex.AndGraph.AGInputManager;
import com.example.adailson.spacex.AndGraph.AGScene;
import com.example.adailson.spacex.AndGraph.AGScreenManager;
import com.example.adailson.spacex.AndGraph.AGSprite;

public class CenaCreditos extends AGScene {

    private AGSprite tigre = null;

    public CenaCreditos(AGGameManager gameManager) {
        super(gameManager);
    }

    @Override
    public void init() {
        setSceneBackgroundColor(0, 1, 0);

        tigre = createSprite(R.mipmap.tigre, 2, 4);
        tigre.setScreenPercent(30, 15);
        tigre.vrPosition.setXY(AGScreenManager.iScreenWidth / 2, AGScreenManager.iScreenHeight / 2);
        tigre.addAnimation(30, true, 0, 7);
        //tigre.iMirror = AGSprite.VERTICAL;
        //tigre.fAlpha = 0.3f;
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

        tigre.fAngle += 3;
    }
}
