package com.example.adailson.spacex;

import com.example.adailson.spacex.AndGraph.AGGameManager;
import com.example.adailson.spacex.AndGraph.AGInputManager;
import com.example.adailson.spacex.AndGraph.AGScene;
import com.example.adailson.spacex.AndGraph.AGScreenManager;
import com.example.adailson.spacex.AndGraph.AGSoundManager;
import com.example.adailson.spacex.AndGraph.AGSprite;
import com.example.adailson.spacex.AndGraph.AGTimer;

import java.util.ArrayList;
import java.util.Random;


public class CenaGame extends AGScene {

    private AGSprite[] vetInimigos = null;
    private AGSprite[] vetInimigos2 = null;
    private AGSprite[] vetIestrela = null;
    private AGSprite[] vetMeteoro = null;
    private ArrayList<AGSprite> vetExplosions = null;
    private ArrayList<AGSprite> vetExplosions2 = null;
    private ArrayList<AGSprite> vetBullets = null;
    private AGTimer tempRevivier = null;
    private AGTimer tempExplosao = null;
    private AGSprite jogador = null;
    private AGSprite vida = null;
    private int vidas = 3;
    private int ladoAnimacao = 0;
    private int pontuacao = 0;
    private int somShot;
    private int somVida;
    private int somXplosion;


    public CenaGame(AGGameManager gameManager) {
        super(gameManager);
    }

    @Override
    public void init() {
        setSceneBackgroundColor(0, 0, 0);

        AGSoundManager.vrMusic.loadMusic("mega.mp3", true);
        AGSoundManager.vrMusic.play();

        vetBullets = new ArrayList<AGSprite>();

        //Instancia o array de navios e configura seus tamanhos e direcoes
        vetInimigos = new AGSprite[3];
        vetInimigos2 = new AGSprite[1];
        vetIestrela = new AGSprite[20];
        vetMeteoro = new AGSprite[4];

        for (int iIndex = 0; iIndex < vetMeteoro.length; iIndex++) {
            Random r = new Random();
            int numero = r.nextInt(AGScreenManager.iScreenWidth);
            vetMeteoro[iIndex] = createSprite(R.mipmap.meteoro, 4, 1);
            vetMeteoro[iIndex].setScreenPercent(16, 10);
            vetMeteoro[iIndex].vrPosition.setXY(numero, AGScreenManager.iScreenHeight + numero);
            vetMeteoro[iIndex].vrDirection.fY = -1;
            vetMeteoro[iIndex].addAnimation(10, true, 0, 3);
            vetMeteoro[iIndex].setCurrentAnimation(0);
        }

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
            for (int i = 0; i < vetInimigos.length; i++) {

            }
            //if (iIndex == 0) {
            vetInimigos[iIndex].setScreenPercent(16, 10);
            vetInimigos[iIndex].vrPosition.setXY(numero, AGScreenManager.iScreenHeight + numero);
            //}
            /*
            else {

                if (AGScreenManager.iScreenHeight + numero > vetInimigos[iIndex - 1].vrPosition.getY() + vetInimigos[iIndex - 1].iFrameHeight && AGScreenManager.iScreenHeight + numero < vetInimigos[iIndex - 1].vrPosition.getY() - vetInimigos[iIndex - 1].iFrameHeight) {
                    if (numero > vetInimigos[iIndex - 1].vrPosition.getX() + vetInimigos[iIndex - 1].iFrameWidth && numero < vetInimigos[iIndex - 1].vrPosition.getX() - vetInimigos[iIndex - 1].iFrameWidth) {
                        vetInimigos[iIndex].setScreenPercent(16, 10);
                        vetInimigos[iIndex].vrPosition.setXY(numero, AGScreenManager.iScreenHeight + numero);
                    }
                }
            }
            */
            //parada
            vetInimigos[iIndex].addAnimation(10, false, 0, 1);
            vetInimigos[iIndex].setCurrentAnimation(0);
            vetInimigos[iIndex].iMirror = AGSprite.VERTICAL;
        }
        for (
                int iIndex = 0;
                iIndex < vetInimigos2.length; iIndex++)

        {
            Random r = new Random();

            int numero1 = r.nextInt(AGScreenManager.iScreenWidth);

            vetInimigos2[iIndex] = createSprite(R.mipmap.inimigo2, 5, 1);
            vetInimigos2[iIndex].setScreenPercent(16, 10);
            vetInimigos2[iIndex].vrPosition.setXY(numero1, AGScreenManager.iScreenHeight + numero1);
            //parada
            vetInimigos2[iIndex].addAnimation(10, false, 0, 1);
            //Nave explodindo
            vetInimigos2[iIndex].addAnimation(10, true, 2, 4);
            vetInimigos2[iIndex].setCurrentAnimation(0);
            vetInimigos2[iIndex].iMirror = AGSprite.VERTICAL;
        }

        vetInimigos[0].vrDirection.fY = -1;
        vetInimigos[1].vrDirection.fY = -1;
        vetInimigos[2].vrDirection.fY = -1;

        vetInimigos2[0].vrDirection.fY = -1;

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

        tempRevivier = new

                AGTimer(1000);

        tempExplosao = new

                AGTimer(500);

        vetExplosions = new ArrayList<AGSprite>();
        vetExplosions2 = new ArrayList<AGSprite>();

        somShot = AGSoundManager.vrSoundEffects.loadSoundEffect("shot.wav");
        somVida = AGSoundManager.vrSoundEffects.loadSoundEffect("stop.wav");
        somXplosion = AGSoundManager.vrSoundEffects.loadSoundEffect("xplosion.wav");

        vida = createSprite(R.mipmap.vida, 3, 3);
        vida.setScreenPercent(8, 5);
        vida.vrPosition.setX(AGScreenManager.iScreenWidth - AGScreenManager.iScreenWidth / 4);
        vida.vrPosition.setY(AGScreenManager.iScreenHeight - vida.iFrameHeight / 4);
        //1
        vida.addAnimation(10, true, 0, 1);
        //2
        vida.addAnimation(10, false, 2, 3);
        //3
        vida.addAnimation(10, false, 4, 5);
        vida.setCurrentAnimation(3);
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
            voltar;
        }
        */

        moveJogador();
        updateInimigos();
        updateExplosions();
        updateExplosions2();
        updateInimigos2();
        updateEstrela();
        updateMeteoro();
        xDasBolas();
        setAnimacao();
        verVida();

        shot();
        coliInimigoJogador();
        coliInimigo2Jogador();
        coliMeteoroJogador();
        inimigo1Atingido();
        inimigo2Atingido();
        meteoroAtingido();
        updateBalas();
        reviveInimigo();
        stopPartita();

        voltar();
    }

    private void voltar() {
        if (AGInputManager.vrTouchEvents.backButtonClicked()) {
            vrGameManager.setCurrentScene(1);
            vidas = 3;
            pontuacao = 0;
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

    private void updateMeteoro() {
        for (AGSprite meteoro : vetMeteoro) {
            meteoro.vrPosition.setY(meteoro.vrPosition.fY + meteoro.vrDirection.fY * 10);

            if (meteoro.vrPosition.fY < 0) {
                Random r = new Random();
                meteoro.bRecycled = true;
                int numero = r.nextInt(AGScreenManager.iScreenWidth);
                meteoro.vrPosition.fX = numero;
                meteoro.vrPosition.fY = AGScreenManager.iScreenHeight + numero;
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
                AGSoundManager.vrSoundEffects.play(somShot);

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
                    newBullet = createSprite(R.mipmap.shot, 1, 1);
                    newBullet.setScreenPercent(1, 4);
                    vetBullets.add(newBullet);
                }

                newBullet.vrPosition.setX(jogador.vrPosition.getX());
                newBullet.vrPosition.setY(jogador.vrPosition.getY() + jogador.iFrameHeight / 4);
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

    private void moveJogador() {
        if (AGInputManager.vrAccelerometer.getAccelX() < 1 && AGInputManager.vrAccelerometer.getAccelX() > -0.50f) {
            ladoAnimacao = 0;
        }
        if (AGInputManager.vrAccelerometer.getAccelX() > 1) {
            ladoAnimacao = 2;
            jogador.vrPosition.setX(jogador.vrPosition.getX() + 8.0f);
        } else if (AGInputManager.vrAccelerometer.getAccelX() < -0.50f) {
            ladoAnimacao = 1;
            jogador.vrPosition.setX(jogador.vrPosition.getX() - 8.0f);
        }
        if (AGInputManager.vrAccelerometer.getAccelY() > 1) {
            jogador.vrPosition.setY(jogador.vrPosition.getY() + 8.0f);
        } else if (AGInputManager.vrAccelerometer.getAccelY() < 1) {
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

    private void meteoroAtingido() {
        for (AGSprite balas : vetBullets) {
            for (AGSprite pedra : vetMeteoro) {
                if (balas.bVisible && balas.collide(pedra)) {

                    balas.bVisible = false;
                    balas.bRecycled = true;

                    break;
                }
            }
        }
    }

    private void inimigo1Atingido() {
        for (AGSprite balas : vetBullets) {
            for (AGSprite inimigos : vetInimigos) {
                if (balas.bVisible && balas.collide(inimigos)) {
                    pontuacao += 1;

                    balas.bVisible = false;
                    balas.bRecycled = true;
                    createExplosion(inimigos.vrPosition.getX(), inimigos.vrPosition.getY());
                    inimigos.setCurrentAnimation(0);
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

    private void inimigo2Atingido() {
        for (AGSprite balas : vetBullets) {
            for (AGSprite inimigos : vetInimigos2) {
                if (balas.bVisible && balas.collide(inimigos)) {
                    pontuacao += 5;

                    balas.bVisible = false;
                    balas.bRecycled = true;
                    createExplosion2(inimigos.vrPosition.getX(), inimigos.vrPosition.getY());
                    inimigos.setCurrentAnimation(0);
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

    //Metodo utilizado para criar uma explosao no lugar da colisao entre bala e navio
    private void createExplosion(float posX, float posY) {
        //Verifica se e possivel reciclar uma explosao
        for (AGSprite explosion : vetExplosions) {
            if (!explosion.bVisible) {
                AGSoundManager.vrSoundEffects.play(somXplosion);
                explosion.vrPosition.setXY(posX, posY);
                explosion.restartAnimation();
                explosion.bVisible = true;
                return;
            }
        }

        AGSprite newExplosion = createSprite(R.mipmap.inimigo, 5, 1);
        newExplosion.setScreenPercent(16, 10);
        newExplosion.iMirror = AGSprite.VERTICAL;
        newExplosion.addAnimation(20, false, 2, 4);
        newExplosion.setCurrentAnimation(0);
        newExplosion.vrPosition.setXY(posX, posY);
        vetExplosions.add(newExplosion);
    }

    private void createExplosion2(float posX, float posY) {
        //Verifica se e possivel reciclar uma explosao
        //Verifica se e possivel reciclar uma explosao
        for (AGSprite explosion : vetExplosions2) {
            if (!explosion.bVisible) {
                AGSoundManager.vrSoundEffects.play(somXplosion);
                explosion.vrPosition.setXY(posX, posY);
                explosion.restartAnimation();
                explosion.bVisible = true;
                return;
            }
        }

        AGSprite newExplosion = createSprite(R.mipmap.inimigo2, 5, 1);
        newExplosion.setScreenPercent(16, 10);
        newExplosion.iMirror = AGSprite.VERTICAL;
        newExplosion.addAnimation(20, false, 2, 4);
        newExplosion.setCurrentAnimation(0);
        newExplosion.vrPosition.setXY(posX, posY);
        vetExplosions2.add(newExplosion);
    }

    //Metodo utilizado para atualizar o estado das explosoes
    private void updateExplosions() {
        for (AGSprite explosion : vetExplosions) {
            if (explosion.bVisible && explosion.getCurrentAnimation().isAnimationEnded()) {
                explosion.bVisible = false;
            }
        }
    }

    //Metodo utilizado para atualizar o estado das explosoes
    private void updateExplosions2() {
        for (AGSprite explosion : vetExplosions2) {
            if (explosion.bVisible && explosion.getCurrentAnimation().isAnimationEnded()) {
                explosion.bVisible = false;
            }
        }
    }

    private void coliInimigoJogador() {
        for (AGSprite inimigo : vetInimigos) {
            if (inimigo.bVisible && inimigo.collide(jogador)) {
                AGSoundManager.vrSoundEffects.play(somVida);
                jogador.bVisible = false;
                tempRevivier.update();
                vidas--;
            }
        }
    }

    private void coliInimigo2Jogador() {
        for (AGSprite inimigo : vetInimigos2) {
            if (inimigo.bVisible && inimigo.collide(jogador)) {
                AGSoundManager.vrSoundEffects.play(somVida);
                jogador.bVisible = false;
                tempRevivier.update();
                vidas--;
            }
        }
    }

    private void coliMeteoroJogador() {
        for (AGSprite inimigo : vetMeteoro) {
            if (inimigo.bVisible && inimigo.collide(jogador)) {
                AGSoundManager.vrSoundEffects.play(somVida);
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

            CenaGameOver.score = pontuacao;
            AGSoundManager.vrMusic.stop();
            vrGameManager.setCurrentScene(4);
            pontuacao = 0;
            vidas = 3;
        }
    }

    private void setAnimacao() {
        jogador.setCurrentAnimation(ladoAnimacao);
    }

    private void verVida() {
        if (vidas == 3) {
            vida.setCurrentAnimation(3);
        }
        if (vidas == 2) {
            vida.setCurrentAnimation(2);
        }
        if (vidas == 1) {
            vida.setCurrentAnimation(1);
        }
    }
}
