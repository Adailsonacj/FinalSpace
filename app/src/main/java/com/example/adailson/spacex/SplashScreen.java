package com.example.adailson.spacex;

import com.example.adailson.spacex.AndGraph.AGGameManager;
import com.example.adailson.spacex.AndGraph.AGInputManager;
import com.example.adailson.spacex.AndGraph.AGScene;
import com.example.adailson.spacex.AndGraph.AGScreenManager;
import com.example.adailson.spacex.AndGraph.AGSprite;
import com.example.adailson.spacex.AndGraph.AGTimer;

public class SplashScreen extends AGScene {

    AGTimer tempo = null;
    AGSprite spacex = null;

    public SplashScreen(AGGameManager gameManager){
        //toda cena tem internamente uma referencia
        // para o AGGameManager, para caso seja preciso
        // fazer uma alteração entre as cenas
        super(gameManager);
    }

    @Override
    public void init() {
        //Chamado toda vez que a cena for ativada
        //sempre que a cena for exibida

        tempo = new AGTimer(3000);

        spacex = createSprite(R.mipmap.logo,1,1);
        spacex.setScreenPercent(40,10);
        spacex.vrPosition.setXY(AGScreenManager.iScreenWidth/2, AGScreenManager.iScreenHeight/2);

        //Configura a cor de backgroud da cena
        setSceneBackgroundColor(1,1,1);
    }

    @Override
    public void restart() {
        //Chamado após um retorno de uma interrupção

    }

    @Override
    public void stop() {
        //Chamado quando uma interrupção ocorrer

    }

    @Override
    public void loop() {
        //Chamado N vezes por segundo

        tempo.update();

        if(tempo.isTimeEnded()){
            vrGameManager.setCurrentScene(1);
        }

        if(AGInputManager.vrTouchEvents.screenClicked()){
            vrGameManager.setCurrentScene(1);
        }

    }
}
