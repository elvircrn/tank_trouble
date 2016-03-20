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

public class MainMenu {

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
    Texture naslov;

    Button multiplayerUp;
    Button multiplayerDown;
    Button optionsUp;
    Button optionsDown;

    ParticleEffect pEffect;

    public boolean bool_singleplayer=false;
    public boolean bool_multiplayer=false;
    public boolean bool_options=false;

    public void create () {

        background = new Texture("MainMenuData/background.png");
        naslov = new Texture("MainMenuData/naslov.png");
        font = new BitmapFont();
        font.setColor(Color.WHITE);

        screenW = Graphics.prefferedWidth;
        screenH = Graphics.prefferedHeight;
        margin=screenW*0.01f;
        bSize = 9*(screenW/100);
        new_height = 70*(screenW/100);
        new_width = (width*new_height)/height;
        koordx=screenW/2 - new_height/2;
        koordy=screenH/2 - new_width/2;

        multiplayerUp = new Button(203,true, koordx + new_height/2f, koordy-new_width-90 + new_width/2f, new_height, new_width,15);
        multiplayerDown = new Button(204,true, koordx + new_height/2f, koordy-new_width-90 + new_width/2f, new_height, new_width,16);


        optionsUp = new Button(205,true, margin + bSize/2f,margin + bSize/2f,bSize,bSize,17);
        optionsDown = new Button(206,true, margin + bSize/2f,margin + bSize/2f,bSize,bSize,18);

        load();
    }

    public void load(){

    }

    public void update(float deltaTime){
        multiplayerDown.update(Input.touchList);
        multiplayerUp.update(Input.touchList);
        optionsDown.update(Input.touchList);
        optionsUp.update(Input.touchList);

        if(optionsUp.isDown() && !optionsUp.justPressed()){
            bool_options=true;
        }
        if(optionsDown.justRaised() && Input.count() == 0){
            StateManager.changeState(StateManager.State.OPTIONS);
        }
        //
        if(multiplayerUp.isDown() && !multiplayerUp.justPressed()){
            bool_multiplayer=true;
        }
        if(multiplayerDown.justRaised() && Input.count() == 0){
            StateManager.changeState(StateManager.State.MP_MENU);
        }
    }

    public void render(SpriteBatch batch) {
        float deltaTime = Gdx.graphics.getDeltaTime();

        batch.draw(background, 0, 0, screenW, screenH);
        batch.draw(naslov, screenW/2 - naslov_width/2, screenH/3+ naslov_height/4, naslov_width, naslov_height);
        multiplayerUp.draw(batch);
        optionsUp.draw(batch);

        if(bool_options) {
            optionsDown.draw(batch);
            bool_options = false;
        }
        if(bool_multiplayer) {
            multiplayerDown.draw(batch);
            bool_multiplayer = false;
        }

    }


}
