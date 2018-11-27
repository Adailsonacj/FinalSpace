package com.example.adailson.spacex;
import android.os.Bundle;

import com.example.adailson.spacex.AndGraph.AGActivityGame;

public class MainActivity extends AGActivityGame {

    private SplashScreen abertura = null;
    private CenaMenu menu = null;
    private CenaCreditos creditos = null;
    private CenaGame game = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Inicializa o motor gráfico
        init(this, true);
        abertura = new SplashScreen(getGameManager());
        menu = new CenaMenu(getGameManager());
        creditos = new CenaCreditos(getGameManager());
        game = new CenaGame(getGameManager());

        getGameManager().addScene(abertura);
        getGameManager().addScene(menu);
        getGameManager().addScene(creditos);
        getGameManager().addScene(game);

    }
}
