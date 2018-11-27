package com.example.adailson.spacex;

import com.example.adailson.spacex.AndGraph.AGGameManager;
import com.example.adailson.spacex.AndGraph.AGInputManager;
import com.example.adailson.spacex.AndGraph.AGScene;
import com.example.adailson.spacex.AndGraph.AGScreenManager;
import com.example.adailson.spacex.AndGraph.AGSprite;
import com.example.adailson.spacex.AndGraph.AGTimer;

import java.util.ArrayList;
import java.util.Random;


public class CenaGame extends AGScene {

    private AGSprite[] vetInimigos = null;
    private AGSprite[] vetInimigos2 = null;
    private AGSprite[] vetIestrela = null;
    private ArrayList<AGSprite> vetBullets = null;
    private AGTimer tempRevivier = null;

    private AGSprite jogador = null;
    private int vidas = 3;
    private int ladoAnimacao = 0;


    public CenaGame(AGGameManager gameManager) {
        super(gameManager);
    }

    @Override
    public void init() {
        setSceneBackgroundColor(0, 0, 0);
        vetBullets = new ArrayList<AGSprite>();


        //Instancia o array de navios e configura seus tamanhos e direcoes
        vetInimigos = new AGSprite[3];
        vetInimigos2 = new AGSprite[3];
        vetIestrela = new AGSprite[20];
        for (int iIndex = 0; iIndex < vetIestrela.length; iIndex++) {
            Random r = new Random();
            int numero = r.nextInt(AGScreenManager.iScreenWidth);
            vetIestrela[iIndex] = createSprite(R.mipmap.estrela, 1, 1);
            vetIestrela[iIndex].setScreenPercent(8, 5);
            vetIestrela[iIndex].vrPosition.setXY(numero, AGScreenManager.iScreenHeight + numero);
            vetIestrela[iIndex].vrDirection.fY = -1;
        }

        for (int iIndex = 0; iIndex < vetInimigos.length; iIndex++) {
            Random r = new Random();
            int numero = r.nextInt(AGScreenManager.iScreenWidth);
            vetInimigos[iIndex] = createSprite(R.mipmap.inimigo, 5, 1);
            vetInimigos[iIndex].setScreenPercent(16, 10);
            vetInimigos[iIndex].vrPosition.setXY(numero, AGScreenManager.iScreenHeight);
            //Nave esquerda
            vetInimigos[iIndex].addAnimation(10, false, 0, 1);
            //Nave explodindo
            vetInimigos[iIndex].addAnimation(10, false, 2, 4);
            vetInimigos[iIndex].setCurrentAnimation(0);
            vetInimigos[iIndex].iMirror = AGSprite.VERTICAL;


            vetInimigos2[iIndex] = createSprite(R.mipmap.inimigo2, 5, 1);
            vetInimigos2[iIndex].setScreenPercent(16, 10);
            vetInimigos2[iIndex].vrPosition.setXY(numero, AGScreenManager.iScreenHeight);
            //Nave esquerda
            vetInimigos2[iIndex].addAnimation(10, false, 0, 1);
            //Nave explodindo
            vetInimigos2[iIndex].addAnimation(10, false, 2, 4);
            vetInimigos2[iIndex].setCurrentAnimation(0);
            vetInimigos2[iIndex].iMirror = AGSprite.VERTICAL;
        }
        vetInimigos[0].vrDirection.fY = -1;
        vetInimigos[1].vrDirection.fY = -1;
        vetInimigos[2].vrDirection.fY = -1;

        vetInimigos2[0].vrDirection.fY = -1;
        vetInimigos2[1].vrDirection.fY = -1;
        vetInimigos2[2].vrDirection.fY = -1;

        /*
        vetInimigos[3].vrDirection.fY = -1;
        vetInimigos[4].vrDirection.fY = -1;
        vetInimigos[5].vrDirection.fY = -1;
        vetInimigos[6].vrDirection.fY = -1;
        vetInimigos[7].vrDirection.fY = -1;
        vetInimigos[8].vrDirection.fY = -1;
        vetInimigos[9].vrDirection.fY = -1;

        /*
        vetInimigos[0].vrDirection.fX = 0;
        vetInimigos[1].vrDirection.fX = 0;
        vetInimigos[2].vrDirection.fX = 0;
        vetInimigos[4].vrDirection.fX = 0;
        vetInimigos[5].vrDirection.fX = 0;
        vetInimigos[5].vrDirection.fX = 0;
        vetInimigos[6].vrDirection.fX = 0;
*/

        jogador = createSprite(R.mipmap.jogador, 4, 3);
        jogador.setScreenPercent(16, 10);
        jogador.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
        jogador.vrPosition.setY(jogador.getSpriteHeight() / 2);
        //nave parada
        jogador.addAnimation(10, true, 0, 3);
        //Nave esquerda
        jogador.addAnimation(10, false, 7, 9);
        //Nave direita
        jogador.addAnimation(10, false, 4, 6);


        tempRevivier = new AGTimer(1000);


    }

    @Override
    public void restart() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void loop() {
        /*
        if (AGInputManager.vrTouchEvents.screenClicked()) {
            //vrGameManager.setCurrentScene(1);
            animacao++;
            if (animacao > 2) {
                animacao = 0;
            }
            gato.setCurrentAnimation(animacao);
            return;
        }
        */

        handleCannon();
        updateInimigos();
        updateInimigos2();
        updateEstrela();
        xDasBolas();
        setAnimacao();

        shot();
        coliInimigoJogador();
        coliTiroInimigo();
        updateBalas();
        reviveInimigo();
        stopPartita();

        voltar();
    }

    private void voltar() {
        if (AGInputManager.vrTouchEvents.backButtonClicked()) {
            vrGameManager.setCurrentScene(1);
        }
    }

    //Atualiza o movimeto dos inimigos
    private void updateInimigos() {
        for (AGSprite inimigos : vetInimigos) {
            inimigos.vrPosition.setY(inimigos.vrPosition.fY + inimigos.vrDirection.fY * 10);


            if (inimigos.vrPosition.fY < 0) {
                Random r = new Random();
                inimigos.bRecycled = true;
                int numero = r.nextInt(AGScreenManager.iScreenWidth);
                inimigos.vrPosition.fX = numero;
                inimigos.vrPosition.fY = AGScreenManager.iScreenHeight;
            }

        }
    }

    private void updateInimigos2() {
        for (AGSprite inimigos : vetInimigos2) {
            inimigos.vrPosition.setY(inimigos.vrPosition.fY + inimigos.vrDirection.fY * 10);


            if (inimigos.vrPosition.fY < 0) {
                Random r = new Random();
                inimigos.bRecycled = true;
                int numero = r.nextInt(AGScreenManager.iScreenWidth);
                inimigos.vrPosition.fX = numero;
                inimigos.vrPosition.fY = AGScreenManager.iScreenHeight;
            }

        }
    }

    private void updateEstrela() {
        for (AGSprite estrela : vetIestrela) {
            estrela.vrPosition.setY(estrela.vrPosition.fY + estrela.vrDirection.fY * 10);

            if (estrela.vrPosition.fY < 0) {
                Random r = new Random();
                estrela.bRecycled = true;
                int numero = r.nextInt(AGScreenManager.iScreenWidth);
                estrela.vrPosition.fX = numero;
                estrela.vrPosition.fY = AGScreenManager.iScreenHeight + numero;
            }
        }
    }

    private void xDasBolas() {
        for (AGSprite bola : vetInimigos) {

            bola.vrPosition.setX(bola.vrPosition.fX + bola.vrDirection.fX * 10);


            if (bola.vrDirection.fX == 1 && bola.vrPosition.fX > AGScreenManager.iScreenWidth) {
                bola.vrDirection.fX = -1;
            }
            if (bola.vrDirection.fX == -1 && bola.vrPosition.fX < 0) {
                bola.vrDirection.fX = 1;
            }

        }

    }

    private void shot() {
        if (jogador.bVisible) {
            //Se a tela foi clicada efetua um novo tiro
            if (AGInputManager.vrTouchEvents.screenClicked()) {

                AGSprite newBullet = null;

                //Verifica se e possivel reciclar a bala
                for (AGSprite bala : vetBullets) {
                    if (bala.bRecycled) {
                        newBullet = bala;
                        bala.bRecycled = false;
                        bala.bVisible = true;
                        break;
                    }
                }

                //Cria uma nova bala
                if (newBullet == null) {
                    newBullet = createSprite(R.mipmap.bala, 1, 1);
                    newBullet.setScreenPercent(1, 4);
                    vetBullets.add(newBullet);
                }

                //Configura a bala com a posicao do canhao
                newBullet.vrPosition.setX(jogador.vrPosition.getX());
                newBullet.vrPosition.setY(jogador.vrPosition.getY());
            }
        }
    }

    //Atualiza o movimento das balas
    private void updateBalas() {
        for (AGSprite bullet : vetBullets) {
            if (bullet.bVisible) {
                bullet.vrPosition.fY += 10;
                if (bullet.vrPosition.fY >= AGScreenManager.iScreenHeight + bullet.getSpriteHeight() / 2) {
                    bullet.bRecycled = true;
                    bullet.bVisible = false;
                }
            }
        }
    }

    private void handleCannon() {
        if (AGInputManager.vrAccelerometer.getAccelX() < 0.50f && AGInputManager.vrAccelerometer.getAccelX() > -0.50f) {
            ladoAnimacao = 0;
        }
        if (AGInputManager.vrAccelerometer.getAccelX() > 0.50f) {
            ladoAnimacao = 2;
            jogador.vrPosition.setX(jogador.vrPosition.getX() + 8.0f);
        } else if (AGInputManager.vrAccelerometer.getAccelX() < -0.50f) {
            ladoAnimacao = 1;
            jogador.vrPosition.setX(jogador.vrPosition.getX() - 8.0f);
        }
        if (AGInputManager.vrAccelerometer.getAccelY() > 0.50f) {
            jogador.vrPosition.setY(jogador.vrPosition.getY() + 8.0f);
        } else if (AGInputManager.vrAccelerometer.getAccelY() < 0.50f) {
            jogador.vrPosition.setY(jogador.vrPosition.getY() - 8.0f);
        }

        if (jogador.vrPosition.getX() < jogador.getSpriteWidth() / 2) {
            jogador.vrPosition.setX(jogador.getSpriteWidth() / 2);
        }

        if (jogador.vrPosition.getX() > AGScreenManager.iScreenWidth - jogador.getSpriteWidth() / 2) {
            jogador.vrPosition.setX(AGScreenManager.iScreenWidth - jogador.getSpriteWidth() / 2);
        }
        if (jogador.vrPosition.getY() < jogador.getSpriteHeight() / 2) {
            jogador.vrPosition.setY(jogador.getSpriteHeight() / 2);
        }

        if (jogador.vrPosition.getY() > AGScreenManager.iScreenHeight - jogador.getSpriteHeight() / 2) {
            jogador.vrPosition.setY(AGScreenManager.iScreenHeight - jogador.getSpriteHeight() / 2);
        }
    }

    private void coliTiroInimigo() {
        for (AGSprite balas : vetBullets) {
            for (AGSprite inimigos : vetInimigos) {
                if (balas.bVisible && balas.collide(inimigos)) {
                    balas.bVisible = false;
                    balas.bRecycled = true;

                    if (inimigos.vrDirection.fY == 1) {
                        inimigos.vrDirection.fY = -1;
                        inimigos.vrPosition.fY = AGScreenManager.iScreenHeight + inimigos.getSpriteHeight() / 2;
                    } else {
                        inimigos.vrDirection.fY = -1;
                        inimigos.vrPosition.fY = AGScreenManager.iScreenHeight;
                        Random r = new Random();
                        int numero = r.nextInt(AGScreenManager.iScreenWidth);
                        inimigos.vrPosition.fX = numero;

                    }

                    break;
                }
            }
        }
    }

    private void coliInimigoJogador() {
        for (AGSprite inimigo : vetInimigos) {
            if (inimigo.bVisible && inimigo.collide(jogador)) {
                jogador.bVisible = false;
                tempRevivier.update();
                vidas--;


            }


        }
    }

    private void reviveInimigo() {
        if (vidas > 0 && !jogador.bVisible) {
            //if (tempRevivier.isTimeEnded() && !jogador.bVisible) {
            jogador.vrPosition.setX(AGScreenManager.iScreenWidth / 2);
            jogador.vrPosition.setY(AGScreenManager.iScreenWidth - AGScreenManager.iScreenWidth / 4);
            jogador.bVisible = true;
            //}
        }
    }

    private void stopPartita() {
        if (vidas == 0 && !jogador.bVisible) {
            vidas = 3;
            vrGameManager.setCurrentScene(1);

        }
    }

    private void setAnimacao() {
        jogador.setCurrentAnimation(ladoAnimacao);
    }
}
