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
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class MainMenu {

    SpriteBatch batch;
    Texture slikica_single;
    Texture slikica_multi;

    private BitmapFont font;

    float screenW, screenH;
    float width = 128, height=1024;

    Rectangle rect;

    float new_height;
    float new_width;
    float koordx, koordy;
    float od_y=150;

    SimpleButton button_singleplayer;
    SimpleButton button_multiplayer;
    Texture slikica_single_down_txt;
    Texture slikica_multi_down_txt;
    boolean slikica_single_down_show=false;
    boolean slikica_multi_down_show=false;

    Texture naslov;

    SimpleButton button_options;
    Texture options_button_up_txt;
    Texture options_button_down_txt;
    boolean options_button_up_show = false;
    boolean options_button_down_show = false;
    float options_koordx;
    float options_koordy;
    float options_height;
    float options_width;

    public void create () {
        batch = new SpriteBatch();
	/*	img = new Texture("sp.jpg");
		img2 = new Texture("mp.jpg");*/
        font = new BitmapFont();
        font.setColor(Color.WHITE);

        screenW = Graphics.prefferedWidth;
        screenH = Graphics.prefferedHeight;
        new_height = 70*(screenW/100);
        new_width = (width*new_height)/height;
        koordx=screenW/2 - new_height/2;
        koordy=screenH-new_width - od_y;

        slikica_single = new Texture("MainMenuData/spUp.png");
        slikica_single_down_txt = new Texture("MainMenuData/spDn.png");
        slikica_multi = new Texture("MainMenuData/mpUp.png");
        slikica_multi_down_txt = new Texture("MainMenuData/mpDn.png");

        button_singleplayer = new SimpleButton(slikica_single, koordx, koordy, new_height, new_width);

        koordy = screenH-new_width-new_width-od_y-20;

        button_multiplayer = new SimpleButton(slikica_multi, koordx, koordy, new_height, new_width);

        naslov = new Texture("MainMenuData/naslov.png");

        options_button_up_txt = new Texture("MainMenuData/gearUp.png");
        options_button_down_txt = new Texture("MainMenuData/gearDn.png");

        options_koordx = screenW-50;
        options_koordy = 0;
        options_width = 10*(screenW/100);
        options_height = (50*options_width)/50;

        button_options = new SimpleButton(options_button_up_txt, options_koordx, options_koordy, options_width, options_height);

        load();
    }

    public void load(){

    }
    float x_pressed;
    float y_pressed;
    public void update(float deltaTime){
        for (int i = 0; i < 7; i++) {
            if (Gdx.input.isTouched(i)) {
                float locX = (float)Gdx.input.getX(i);
                float locY = (float)Gdx.input.getY(i);

                x_pressed = locX;
                y_pressed = screenH - locY;

                if(button_singleplayer.checkIfClicked(x_pressed, y_pressed) == true){
                    slikica_single_down_show=true;
                }
                else{
                    slikica_single_down_show=false;
                }

                if(button_multiplayer.checkIfClicked(x_pressed, y_pressed) == true){
                    slikica_multi_down_show=true;
                }
                else{
                    slikica_multi_down_show=false;
                }

                if(button_options.checkIfClicked(x_pressed, y_pressed) == true){
                    options_button_down_show = true;
                }
                else{
                    options_button_down_show=false;
                }

            }
        }

    }

    public boolean isMultiPlayer_Clicked(){
        if(slikica_multi_down_show == true) return true;
        else return false;
    }

    public boolean isSinglePlayer_Clicked(){
        if(slikica_single_down_show == true) return true;
        else return false;
    }

    public boolean isOptions_Clicked(){
        if(options_button_down_show == true) return true;
        else return false;
    }

    public void render(SpriteBatch batch) {
        float deltaTime = Gdx.graphics.getDeltaTime();

        update(deltaTime);

        button_singleplayer.update(batch, x_pressed, y_pressed);
        button_multiplayer.update(batch, x_pressed, y_pressed);

        new_height = 70*(screenW/100);
        new_width = (width*new_height)/height;

        if(slikica_single_down_show==true){
            batch.draw(slikica_single_down_txt, koordx, screenH-new_width - od_y, new_height, new_width);
        }

        if(slikica_multi_down_show==true){
            batch.draw(slikica_multi_down_txt, koordx, koordy, new_height, new_width);
        }

        new_height = 70*(screenW/100);
        new_width = (naslov.getHeight() * new_height) / naslov.getWidth();

        batch.draw(naslov, koordx, screenH - naslov.getHeight()+15, new_height, new_width);

        button_options.update(batch, x_pressed, y_pressed);

        if(options_button_down_show==true){
            batch.draw(options_button_down_txt, options_koordx, options_koordy, options_width, options_height);
        }
    }


}
