package com.example.adailson.spacex;

import com.example.adailson.spacex.AndGraph.AGGameManager;
import com.example.adailson.spacex.AndGraph.AGInputManager;
import com.example.adailson.spacex.AndGraph.AGScene;
import com.example.adailson.spacex.AndGraph.AGScreenManager;
import com.example.adailson.spacex.AndGraph.AGSoundManager;
import com.example.adailson.spacex.AndGraph.AGSprite;

public class CenaMenu extends AGScene {

    private AGSprite menu = null;
    private AGSprite play = null;
    private AGSprite duvida = null;
    private AGSprite sair = null;
    private int i = 0;
    private int codSom;

    public CenaMenu(AGGameManager gameManager) {
        super(gameManager);
    }

    @Override
    public void init() {

        menu = createSprite(R.mipmap.logo, 1, 1);
        menu.setScreenPercent(80, 20);
        menu.vrPosition.setXY(AGScreenManager.iScreenWidth / 2, AGScreenManager.iScreenHeight / 2 + 700);

        play = createSprite(R.mipmap.play, 1, 1);
        play.setScreenPercent(32, 20);
        play.vrPosition.setXY(AGScreenManager.iScreenWidth / 2, AGScreenManager.iScreenHeight / 2 - 450);

        duvida = createSprite(R.mipmap.ask, 1, 1);
        duvida.setScreenPercent(16,10);
        duvida.vrPosition.setXY(AGScreenManager.iScreenWidth / 4 * 3, AGScreenManager.iScreenHeight / 2 - 450);

        sair = createSprite(R.mipmap.exit, 1, 1);
        sair.setScreenPercent(16,10);
        sair.vrPosition.setXY(AGScreenManager.iScreenWidth / 4, AGScreenManager.iScreenHeight / 2 - 450);
        setSceneBackgroundColor(1, 1, 1);

        AGSoundManager.vrMusic.loadMusic("musica.mp3", true);
        AGSoundManager.vrMusic.play();

        codSom = AGSoundManager.vrSoundEffects.loadSoundEffect("toc.wav");
    }

    @Override
    public void restart() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void loop() {

        //Verifca se a tela foi clicada
        if(AGInputManager.vrTouchEvents.screenClicked()){
            if(play.collide(AGInputManager.vrTouchEvents.getLastPosition())){
                AGSoundManager.vrSoundEffects.play(codSom);
                vrGameManager.setCurrentScene(3);

                return;
            }
            if(duvida.collide(AGInputManager.vrTouchEvents.getLastPosition())){
                AGSoundManager.vrSoundEffects.play(codSom);
                vrGameManager.setCurrentScene(2);
                return;
            }
            if(sair.collide(AGInputManager.vrTouchEvents.getLastPosition())){
                AGSoundManager.vrSoundEffects.play(codSom);
                vrGameManager.vrActivity.finish();
                return;
            }
        }
    }
}
