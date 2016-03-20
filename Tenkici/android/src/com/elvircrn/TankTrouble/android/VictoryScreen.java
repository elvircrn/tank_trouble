package com.elvircrn.TankTrouble.android;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Vedo on 06.03.2016..
 */
public class VictoryScreen {
    SpriteBatch batch;
    Texture bgBlue;
    Texture bgRed;
    Texture redWins;
    Texture blueWins;
    float screenW,screenH;
    float winnerW,winnerH;
    float winnerX,winnerY;
    float mainmenuX,mainmenuY;
    float mainmenuW,mainmenuH;
    Button mainmenu;
    Button rematch;
    public void create() {
        screenH = Graphics.prefferedHeight;
        screenW = Graphics.prefferedWidth;

        winnerH=screenW*0.75f;
        winnerW=winnerH*0.5f;

        winnerX = screenW/2 - winnerH/2;
        winnerY = screenH - winnerW +30;

        mainmenuH=screenW*0.7f;
        mainmenuW=mainmenuH/8f;
        mainmenuX = screenW/2;
        mainmenuY = screenH/2;

        bgBlue = new Texture("VictoryData/RedWinner.png");
        bgRed = new Texture("VictoryData/BlueWinner.png");
        redWins = new Texture("VictoryData/winnerRed.png");
        blueWins = new Texture("VictoryData/winnerBlue.png");
        mainmenu = new Button(501,true,mainmenuX,mainmenuY-40,mainmenuH,mainmenuW,19);
        rematch = new Button(502,true,mainmenuX,mainmenuY-mainmenuW-50,mainmenuH,mainmenuW,20);
    }

    public void update(float deltaTime){
        mainmenu.update(Input.touchList);
        rematch.update(Input.touchList);

        if(mainmenu.isDown() && !mainmenu.justPressed()){
        }
        if(mainmenu.justRaised() && Input.count() == 0){
            StateManager.changeState(StateManager.State.MAINMENU);
        }

        if(rematch.isDown() && !rematch.justPressed()){
        }
        if(rematch.justRaised() && Input.count() == 0){
            GameMaster.points_red = 0;
            GameMaster.points_blue=0;
            GameMaster.initNewRound();
            StateManager.changeState(StateManager.State.MULTIPLAYER);
        }
    }

    public void render(SpriteBatch batch){

        if(GameMaster.points_blue == GameMaster.max_points){
            batch.draw(bgRed,0,0, screenW, screenH);
            batch.draw(blueWins,winnerX,winnerY,winnerH,winnerW);
        }
        if(GameMaster.points_red == GameMaster.max_points) {
            batch.draw(bgBlue,0,0, screenW, screenH);
            batch.draw(redWins,winnerX,winnerY,winnerH,winnerW);
        }
        mainmenu.draw(batch);
        rematch.draw(batch);
    }

}

/*
package com.elvircrn.TankTrouble.android;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Vedo on 06.03.2016..

public class VictoryScreen {

    Texture againtxt;
    Texture menutxt;
    Texture background1, background2;

    float screenW, screenH;

    Button MainMenuBtn;
    Button RematchBtn;

    float width = 128;
    float height = 512;
    float new_height, new_width;
    float koordx, koordy;

    public void create() {

        screenW = Graphics.prefferedWidth;
        screenH = Graphics.prefferedHeight;

        againtxt = new Texture("data/tankRed.png");
        menutxt = new Texture("data/tankBlue.png");

        background1 = new Texture("VictoryData/BlueWinner.png");

        background2 = new Texture("VictoryData/RedWinner.png");

        new_height = 35*(screenW/100);
        new_width = (width*new_height)/height;

        koordx=screenW/2 - new_height/2;
        koordy=screenH/2 - new_width/2;

        MainMenuBtn = new Button(500, true, koordx + new_height + new_height/6, koordy-new_width + new_width/2f, new_height, new_width, 19);
        RematchBtn = new Button(501, true, screenW / 2 - 160, koordy-new_width + new_width/2f, new_height, new_width, 20);


    }
    public void update(float deltaTime){

        MainMenuBtn.update(Input.touchList);
        RematchBtn.update(Input.touchList);

        if(MainMenuBtn.justPressed()){
            StateManager.changeState(StateManager.State.MAINMENU);
        }

        if(RematchBtn.justPressed()){
            GameMaster.initNewRound();
            StateManager.changeState(StateManager.State.MULTIPLAYER);
        }

    }

    public void render(SpriteBatch batch){

        if(GameMaster.points_blue == GameMaster.max_points){
            batch.draw(background1, 0,0, screenW, screenH);
        }
        if(GameMaster.points_red == GameMaster.max_points){
            batch.draw(background2, 0,0, screenW, screenH);
        }

        RematchBtn.draw(batch);
        MainMenuBtn.draw(batch);


    }

}
*/