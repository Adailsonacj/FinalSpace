package com.example.adailson.spacex;

import com.example.adailson.spacex.AndGraph.AGGameManager;
import com.example.adailson.spacex.AndGraph.AGInputManager;
import com.example.adailson.spacex.AndGraph.AGScene;
import com.example.adailson.spacex.AndGraph.AGScreenManager;
import com.example.adailson.spacex.AndGraph.AGSoundManager;
import com.example.adailson.spacex.AndGraph.AGSprite;

public class CenaGameOver extends AGScene {

    private AGSprite menu = null;
    private AGSprite menuButton = null;
    private AGSprite reiniciarButton = null;
    private int codSom;
    private AGSprite[] vetScore = null;
    public static int score = 0;

    public CenaGameOver(AGGameManager pManager) {
        super(pManager);
    }

    @Override
    public void init() {
        setSceneBackgroundColor(0, 0, 0);
        menu = createSprite(R.mipmap.logo, 1, 1);
        menu.setScreenPercent(80, 20);
        menu.vrPosition.setXY(AGScreenManager.iScreenWidth / 2, AGScreenManager.iScreenHeight - AGScreenManager.iScreenHeight / 4);

        menuButton = createSprite(R.mipmap.menu, 1, 1);
        menuButton.setScreenPercent(16, 10);
        menuButton.vrPosition.setXY(AGScreenManager.iScreenWidth / 2, AGScreenManager.iScreenHeight / 4);

        reiniciarButton = createSprite(R.mipmap.voltar, 1, 1);
        reiniciarButton.setScreenPercent(32, 20);
        reiniciarButton.vrPosition.setXY(AGScreenManager.iScreenWidth / 2, AGScreenManager.iScreenHeight / 2);
        codSom = AGSoundManager.vrSoundEffects.loadSoundEffect("toc.wav");

        vetScore = new AGSprite[6];
        for (int iIndex = vetScore.length - 1; iIndex >= 0; iIndex--) {
            vetScore[iIndex] = createSprite(R.mipmap.placar, 4, 4);
            vetScore[iIndex].setScreenPercent(8, 8);
            vetScore[iIndex].vrPosition.setXY(AGScreenManager.iScreenWidth/4*3 - iIndex * vetScore[iIndex].getSpriteWidth() - 10, AGScreenManager.iScreenHeight - vetScore[iIndex].getSpriteHeight() / 2);
            vetScore[iIndex].bAutoRender = false;

            for (int jIndex = 0; jIndex < 10; jIndex++) {
                vetScore[iIndex].addAnimation(1, false, jIndex);
            }
        }
    }

    @Override
    public void restart() {

    }

    @Override
    public void stop() {

    }

    public void render() {
        super.render();

        for (AGSprite digit : vetScore) {
            digit.render();
        }
    }

    @Override
    public void loop() {
        updateScore();
        if (AGInputManager.vrTouchEvents.screenClicked()) {
            if (menuButton.collide(AGInputManager.vrTouchEvents.getLastPosition())) {
                AGSoundManager.vrSoundEffects.play(codSom);
                vrGameManager.setCurrentScene(1);

                return;
            }
            if (reiniciarButton.collide(AGInputManager.vrTouchEvents.getLastPosition())) {
                AGSoundManager.vrSoundEffects.play(codSom);
                vrGameManager.setCurrentScene(3);
                return;
            }
        }
    }

    //Metodo utilizado para atualizar o placar
    private void updateScore() {

        //score = 100;

        vetScore[0].setCurrentAnimation((int) score % 10);
        vetScore[1].setCurrentAnimation(((int) score % 100) / 10);
        vetScore[2].setCurrentAnimation(((int) score % 1000) / 100);
        vetScore[3].setCurrentAnimation(((int) score % 10000) / 1000);
        vetScore[4].setCurrentAnimation(((int) score % 100000) / 10000);
        vetScore[5].setCurrentAnimation((int) score / 100000);
    }
}
