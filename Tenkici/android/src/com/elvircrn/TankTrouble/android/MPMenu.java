/**
 * Created by M3 on 1.3.2016.

 public class MainMenu {




 }
 */
package com.elvircrn.TankTrouble.android;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MPMenu {

    SpriteBatch batch;
    private BitmapFont font;

    float screenW, screenH;
    float width = 128, height=1024;
    float new_height;
    float new_width;
    float koordx, koordy;
    //NW = 512 NH = 128
    float naslov_height=256;
    float naslov_width = 1024;
    float margin;
    float bSize;
    Texture background;

    Button hostUp;
    Button hostDn;
    Button joinUp;
    Button joinDn;
    Button optionsUp;
    Button optionsDown;
    Button localUp;
    Button localDn;
    Button back;

    ParticleEffect pEffect;

    public boolean bool_local=false;
    public boolean bool_host=false;
    public boolean bool_join=false;
    public boolean bool_options=false;

    public void create () {

        background = new Texture("MainMenuData/background.png");
        font = new BitmapFont();
        font.setColor(Color.WHITE);

        screenW = Graphics.prefferedWidth;
        screenH = Graphics.prefferedHeight;
        margin=screenW*0.01f;
        bSize = 9*(screenW/100);
        new_height = 70*(screenW/100);
        new_width = (width*new_height)/height;
        koordx=screenW/2 ;
        koordy=screenH/2 ;

        localUp = new Button(701,true, koordx, koordy +new_width+2*margin, new_height, new_width,21);
        localDn = new Button(702,true, koordx, koordy +new_width+2*margin, new_height, new_width,22);

        hostUp = new Button(703,true, koordx, koordy  , new_height, new_width,25);
        hostDn= new Button(704,true, koordx , koordy , new_height, new_width,26);

        joinUp = new Button(705,true, koordx, koordy-new_width-2*margin, new_height, new_width,23);
        joinDn = new Button(706,true, koordx, koordy-new_width-2*margin, new_height, new_width,24);

        optionsUp = new Button(205,true, margin + bSize/2f,margin + bSize/2f,bSize,bSize,17);
        optionsDown = new Button(206,true, margin + bSize/2f,margin + bSize/2f,bSize,bSize,18);

        back = new Button(601,true, margin+bSize/2f, screenH - margin - bSize+bSize/2f, bSize, bSize,9);
        load();
    }

    public void load(){

    }

    public void update(float deltaTime){
        joinUp.update(Input.touchList);
        joinDn.update(Input.touchList);
        hostUp.update(Input.touchList);
        hostDn.update(Input.touchList);
        localDn.update(Input.touchList);
        localUp.update(Input.touchList);
        optionsDown.update(Input.touchList);
        optionsUp.update(Input.touchList);
        back.update(Input.touchList);

        if(optionsUp.isDown() && !optionsUp.justPressed()){
            bool_options=true;
        }
        if(optionsDown.justRaised() && Input.count() == 0){
            StateManager.changeState(StateManager.State.OPTIONS);
        }
        //
        if(hostUp.isDown() && !hostUp.justPressed()){
            bool_host=true;
        }
        if(hostDn.justRaised() && Input.count() == 0){
            AndroidLauncher.tenkici.myGameCallback.onStartActivityServer();
        }
        //
        if(joinUp.isDown() && !joinUp.justPressed()){
            bool_join=true;
        }
        if(joinDn.justRaised() && Input.count() == 0){
            AndroidLauncher.tenkici.myGameCallback.onStartActivityClient();
        }
        if(localUp.isDown() && !localUp.justPressed()){
            bool_local=true;
        }
        if(localDn.justRaised() && Input.count() == 0){
            if (GameMaster.getMode() == GameMaster.Mode.CLIENT) {
                GameMaster.requestNewRound();
            }
            StateManager.changeState(StateManager.State.MULTIPLAYER);//
        }
        if(back.justPressed()){
            StateManager.changeState(StateManager.State.MAINMENU);
        }
    }

    public void render(SpriteBatch batch) {
        float deltaTime = Gdx.graphics.getDeltaTime();

        batch.draw(background, 0, 0, screenW, screenH);
        joinUp.draw(batch);
        hostUp.draw(batch);
        localUp.draw(batch);
        optionsUp.draw(batch);
        back.draw(batch);

        if(bool_local){
            localDn.draw(batch);
            bool_local=false;
        }
        if(bool_options) {
            optionsDown.draw(batch);
            bool_options = false;
        }
        if(bool_join) {
            joinDn.draw(batch);
            bool_join = false;
        }
        if(bool_host) {
            hostDn.draw(batch);
            bool_host=false;
        }

    }


}