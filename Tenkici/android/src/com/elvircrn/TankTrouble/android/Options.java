package com.elvircrn.TankTrouble.android;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Kerim on 02.03.2016..
 */
public class Options {
    SpriteBatch batch;
    Texture background;
    float screenW=Graphics.prefferedWidth, screenH=Graphics.prefferedHeight;
    SimpleButton back_btn;
    Texture back_txt;
    float backDim=screenW*0.1f;
    float margin = screenW * 0.01f;
    float x_pressed,y_pressed;

    public void create(){

        background = new Texture("MainMenuData/background.png");

    }
    public boolean isBackClicked = false;
    public void update(float deltaTime) {

        /*
        for (int i = 0; i < Input.count(); i++) {

            x_pressed = Input.get(i).x;
            y_pressed = Input.get(i).y;
            if(x_pressed > 0){

                isBackClicked = true;
            }
            else {
                isBackClicked = false;
            }
        }
*/
    }

    public void render(SpriteBatch batch) {

        float deltaTime = Gdx.graphics.getDeltaTime();

        update(deltaTime);

        batch.draw(background, 0, 0,screenW,screenH);

    }
}