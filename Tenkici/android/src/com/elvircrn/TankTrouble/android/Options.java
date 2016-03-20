package com.elvircrn.TankTrouble.android;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Created by Kerim on 02.03.2016..
 */
public class Options {
    SpriteBatch batch;
    Texture background;
    Texture musicUpTxt,musicDownTxt,backTxt,soundUpTxt,soundDownTxt,vibrateUpTxt,vibrateDownTxt,plusTxt,minusTxt;
    Button back;
    Button musicUp, musicDown;
    Button soundUp, soundDown;
    Button vibrateUp, vibrateDown;
    Button plusHp,plusRound,plusHeight,plusWidth;
    Button minusHp,minusRound,minusHeight,minusWidth;
    float screenW,screenH;
    float bSize,margin;
    boolean bool_musicUp = true;
    boolean bool_musicDown = false;
    float musicX, musicY;
    float xP,yP;
    int brojacMusic=0, brojacSound=0, brojacVibrate=0;

    public static int MW = 5;
    public static int MH = 8;

    public static float SOUND_VOLUME = 1.0f;
    public static float MUSIC_VOLUME = 0.5f;

    //TEXT
    public static FreeTypeFontGenerator generator;
    public static BitmapFont font16;
    static String fontChars = "abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ 1234567890.:,; ' (!?) +-*/=";
    public static FreeTypeFontGenerator.FreeTypeFontParameter fontParameters;
    int dole = 6;
    int dolehp = 6;
    int doleMh = 6;
    int doleMw = 6;

    Texture text1;
    Texture text2;
    Texture text3;
    Texture text4;

    public void create(){

        //FONT
        fontParameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameters.color = Color.WHITE;
        generator = new FreeTypeFontGenerator(Gdx.files.internal("data/ProFontWindows.ttf"));
        font16 = generator.generateFont(fontParameters);
        font16.scale(3f);


        screenW=Graphics.prefferedWidth;
        screenH=Graphics.prefferedHeight;
        bSize = 13f*(screenW/100);
        margin = screenW*0.01f;

        //musicX = bSize/2f + (28f*screenW)/100f;
        musicX = (screenW - 5f*bSize - 4f*margin)/2f+bSize/2f;
        musicY = (screenH - 3f*bSize -2f*margin)/2f +2f*bSize + 2f*margin+bSize/2f;
        background = new Texture("MainMenuData/background.png");

        back = new Button(601,true, margin+bSize/2f, screenH - margin - bSize+bSize/2f, bSize, bSize,9);
        musicUp = new Button(602,true, musicX, musicY, bSize, bSize,1);
        musicDown = new Button(603,true, musicX, musicY, bSize, bSize,2);
        soundUp = new Button(604,true,musicX, musicY-bSize-margin, bSize, bSize,3);
        soundDown = new Button(605,true,musicX, musicY-bSize-margin, bSize, bSize,4);
        vibrateUp = new Button(606,true, musicX, musicY-2f*bSize-2f*margin, bSize, bSize,7);
        vibrateDown = new Button(607,true,musicX, musicY-2f*bSize-2f*margin, bSize, bSize,8);

        plusHp = new Button(608,true,musicX + bSize + margin, musicY, bSize, bSize,5);
        plusRound = new Button(609,true,musicX + 2f*bSize + 2f*margin, musicY, bSize, bSize,5);
        plusHeight = new Button(610,true,musicX + 3f*bSize + 3f*margin, musicY, bSize, bSize,5);
        plusWidth = new Button(611,true,musicX + 4f*bSize + 4f*margin, musicY, bSize, bSize,5);

        minusHp = new Button(612,true,musicX + bSize + margin, musicY -2f*bSize-2f*margin, bSize, bSize,6);
        minusRound = new Button(613,true,musicX + 2f*bSize + 2f*margin, musicY -2f*bSize-2f*margin, bSize, bSize,6);
        minusHeight = new Button(614,true,musicX + 3f*bSize + 3f*margin, musicY -2f*bSize-2f*margin, bSize, bSize,6);
        minusWidth = new Button(615,true,musicX + 4f*bSize + 4f*margin, musicY -2f*bSize-2f*margin, bSize, bSize,6);

        text1 = new Texture("OptionsData/hp.png");
        text2 = new Texture("OptionsData/ofKills.png");
        text3 = new Texture("OptionsData/mapWidth.png");
        text4 = new Texture("OptionsData/mapHeight.png");

     /*   text1 = new Texture("OptionsData/hptext.png");
        text2 = new Texture("OptionsData/ftnptext.png");
        text3 = new Texture("OptionsData/mptext.png");
        text4 = new Texture("OptionsData/mwtext.png");
*/
    }

    public void update(float deltaTime) {
        //Input.update();
        musicUp.update(Input.touchList);
        musicDown.update(Input.touchList);
        soundUp.update(Input.touchList);
        soundDown.update(Input.touchList);
        vibrateDown.update(Input.touchList);
        vibrateUp.update(Input.touchList);
        back.update(Input.touchList);
        plusWidth.update(Input.touchList);
        plusHp.update(Input.touchList);
        plusHeight.update(Input.touchList);
        plusRound.update(Input.touchList);
        minusHp.update(Input.touchList);
        minusHeight.update(Input.touchList);
        minusWidth.update(Input.touchList);
        minusRound.update(Input.touchList);

        //HP
        if(plusHp.justPressed()){
            GameMaster.hp++;
        }
        if(minusHp.justPressed()){
            GameMaster.hp--;
            if(GameMaster.hp <= 1) GameMaster.hp = 1;
        }
        if(GameMaster.hp >= 10) dolehp = 3;
        if(GameMaster.hp < 10) dolehp = 6;

        if(GameMaster.max_points >= 10 && GameMaster.hp < 10){
            dolehp = 6;
        }
        else if(GameMaster.max_points < 10 && GameMaster.hp < 10) {
            dolehp = 6;
        }
        else{
            dolehp = 3;
        }

        //MAXPOINTS
        if(plusRound.justPressed()){
            GameMaster.max_points++;
        }
        if(minusRound.justPressed()){
            GameMaster.max_points--;
            if(GameMaster.max_points <= 2) GameMaster.max_points = 2;
        }
        if(GameMaster.max_points >= 10) dole = 3;
        if(GameMaster.max_points < 10) dole = 6;

        //MAPHEIGHT
        if(plusHeight.justPressed()){
            MH++;
            if(MH >= 8) MH = 8;
        }
        if(minusHeight.justPressed()){
            MH--;
            if(MH <= 5) MH = 5;
        }
        if(MH >= 10) doleMh = 3;
        if(MH < 10) doleMh = 6;


        //MAPWIDTH
        if(plusWidth.justPressed()){
            MW++;
            if(MW >= 8) MW = 8;
        }
        if(minusWidth.justPressed()){
            MW--;
            if(MW <= 5) MW = 5;
        }
        if(MW >= 10) doleMw = 3;
        if(MW < 10) doleMw = 6;


        if(musicUp.justPressed()){
            brojacMusic++;

        }
        if(soundUp.justPressed()){
            brojacSound++;
        }
        if(vibrateUp.justPressed()){
            brojacVibrate++;

        }
        if(back.justPressed()){
            StateManager.changeState(StateManager.State.MAINMENU);
        }

    }

    public void render(SpriteBatch batch) {
        batch.draw(background,0,0,screenW,screenH);
        musicUp.draw(batch);
        plusHeight.draw(batch);
        plusRound.draw(batch);
        plusHp.draw(batch);
        plusWidth.draw(batch);
        minusHp.draw(batch);
        minusRound.draw(batch);
        minusHeight.draw(batch);
        minusWidth.draw(batch);
        vibrateUp.draw(batch);
        soundUp.draw(batch);
        back.draw(batch);
        if (brojacMusic%2 != 0) {
            musicDown.draw(batch);
            GameMaster.play_explode=false;
            Tank.play_shot=false;
        }
        else{
            musicUp.draw(batch);
            GameMaster.play_explode=true;
            Tank.play_shot=true;
        }

        if (brojacSound%2 != 0) {
            soundDown.draw(batch);
            Tenkici.play_music = false;
        }
        else{
            soundUp.draw(batch);
            Tenkici.play_music = true;
        }

        if (brojacVibrate%2 != 0) {
            vibrateDown.draw(batch);
            GameMaster.vibrate=false;
             Gdx.input.cancelVibrate();
        }
        else{
            GameMaster.vibrate=true;
            vibrateUp.draw(batch);
            Gdx.input.vibrate(500);
        }

        //plusRound.width za sve jer je svaki plus istih dimenzija
        //MAXPOINTS
        //Y size musicY-bSize-margin

        //font16.draw(batch, Integer.toString(GameMaster.max_points), musicX + 2f * bSize + 2f * margin - plusRound.width / dole, plusRound.height * 3 - plusRound.height / 5);
        font16.draw(batch, Integer.toString(GameMaster.max_points), musicX + 2f * bSize + 2f * margin - plusRound.width / dole, musicY-bSize-margin+plusRound.height/4);

        batch.draw(text2, musicX + 2f*bSize + 2f*margin - plusRound.width/2, musicY+bSize/2, plusRound.width, plusRound.height);
        //HP
        font16.draw(batch, Integer.toString(GameMaster.hp), musicX + bSize + margin - plusRound.width/dolehp, musicY-bSize-margin+plusRound.height/4);
        batch.draw(text1, musicX + bSize/2 + margin + plusRound.width/4, musicY+bSize/2, plusRound.width/2, plusRound.height/2);
        //HEIGHT
        font16.draw(batch, Integer.toString(MH), musicX + 3f*bSize + 3f*margin - plusRound.width/doleMh, musicY-bSize-margin+plusRound.height/4);
        batch.draw(text3, musicX + 3f*bSize + 3f*margin - plusRound.width/2, musicY+bSize/2, plusRound.width, plusRound.height);
        //WIDTH
        font16.draw(batch, Integer.toString(MW), musicX + 4f*bSize + 4f*margin - plusRound.width/doleMw, musicY-bSize-margin+plusRound.height/4);
        batch.draw(text4, musicX + 4f*bSize + 4f*margin - plusRound.width/2, musicY+bSize/2, plusRound.width, plusRound.height);
    }
}
