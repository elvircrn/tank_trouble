package com.elvircrn.TankTrouble.android;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import com.elvircrn.TankTrouble.android.Blue.BTManager;

public class Tenkici extends ApplicationAdapter  {

    //region old

	public static SpriteBatch batch;
    public static AssetManager manager;

    public static OrthographicCamera cam;
    public static FreeTypeFontGenerator generator;
    static boolean updateManager = false;

    //fonts
    public static BitmapFont font16;
    static String fontChars = "abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ 1234567890.:,; ' (!?) +-*/=";
    public static FreeTypeFontGenerator.FreeTypeFontParameter fontParameters;

    public static BitmapFont font_red;
    public static FreeTypeFontGenerator.FreeTypeFontParameter fontParametersRed;

    public static BitmapFont font_blue;
    public static FreeTypeFontGenerator.FreeTypeFontParameter fontParametersBlue;


    //MAIN MENU KLASA
    MainMenu glavni_meni;
    //OPTIONS
    Options options;
    VictoryScreen victory_s;
    MPMenu MULTIPLAYER_MENU;
    public static Music menu_music;
    public static Music game_music;
    public static boolean play_music=true;

    ParticleEffect pEffect;
    public static ParticleEffect explosion_effect_red;
    public static ParticleEffect explosion_effect_blue;
    public static ParticleEffect smoke_effect;
    //endregion


    //region Activity stuff

    public interface MyGameCallback {
        void onStartActivityServer();
        void onStartActivityClient();
    }

    // Local variable to hold the callback implementation
    public MyGameCallback myGameCallback;

    // ** Additional **
    // Setter for the callback
    public void setMyGameCallback(MyGameCallback callback) {
        myGameCallback = callback;
    }
    //endregion


	@Override
	public void create () {
        //Random
        RandomWrapper.init();

        //state
        StateManager.changeState(StateManager.State.MAINMENU);

        //asset manager init OK
        batch = new SpriteBatch();
        manager = new AssetManager();

        //fonts
        fontParameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameters.color = Color.BLACK;
        generator = new FreeTypeFontGenerator(Gdx.files.internal("data/ProFontWindows.ttf"));
        font16 = generator.generateFont(fontParameters);
        batch = new SpriteBatch();

        //TODO: FONT SCALING
        fontParametersRed = new FreeTypeFontGenerator.FreeTypeFontParameter();
        //Color red = new Color(1, 0, 0, 0.15f);
        Color red = new Color(1, 0, 0, 0.15f);
        fontParametersRed.color = red;
        fontParametersRed.size = 25;
        //fontParametersRed.size = (int)Graphics.scaleWidth(1f);
        font_red = generator.generateFont(fontParametersRed);

        fontParametersBlue = new FreeTypeFontGenerator.FreeTypeFontParameter();
        //Color blue = new Color(0, 0, 1, 0.15f);
        Color blue = new Color(0, 0, 1, 0.15f);
        fontParametersBlue.color = blue;
        fontParametersBlue.size = 25;
        //fontParametersBlue.size = (int)Graphics.scaleWidth(1f);
        font_blue = generator.generateFont(fontParametersBlue);

        //Resolution init OK
        Graphics.initValues();


        //camera init OK
        cam = new OrthographicCamera(Graphics.prefferedWidth, Graphics.prefferedHeight);
        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        cam.update();

        //game init
        GameMaster.createGame();

        //UI init
        com.elvircrn.TankTrouble.android.FPSCounter.create();
        glavni_meni = new MainMenu();
        glavni_meni.create();
        menu_music=Gdx.audio.newMusic(Gdx.files.internal("Music/Menu_ButtonMasher.mp3"));
        menu_music.setVolume(Options.MUSIC_VOLUME);
        menu_music.setLooping(true);

        //options
        options = new Options();
        options.create();
        //mpMENU
        MULTIPLAYER_MENU = new MPMenu();
        MULTIPLAYER_MENU.create();
        //victory
        victory_s = new VictoryScreen();
        victory_s.create();

        game_music = Gdx.audio.newMusic(Gdx.files.internal("Music/Game_ingamemusic.mp3"));
        game_music.setVolume(Options.MUSIC_VOLUME);
        game_music.setLooping(true);

        pEffect = new ParticleEffect();
        pEffect.load(Gdx.files.internal("ParticleEffects/MainMenuEffect.p"), Gdx.files.internal("ParticleEffects"));
        pEffect.setPosition(0, Graphics.prefferedHeight / 2);
        pEffect.start();

        explosion_effect_red = new ParticleEffect();
        explosion_effect_red.load(Gdx.files.internal("ParticleEffects/RedTankExplosion.p"), Gdx.files.internal("ParticleEffects"));

        explosion_effect_blue = new ParticleEffect();
        explosion_effect_blue.load(Gdx.files.internal("ParticleEffects/BlueTankExplosion.p"), Gdx.files.internal("ParticleEffects"));

        explosion_effect_red.scaleEffect(0.7f);
        explosion_effect_blue.scaleEffect(0.7f);

        smoke_effect = new ParticleEffect();
        smoke_effect.load(Gdx.files.internal("ParticleEffects/TankSmoke.p"), Gdx.files.internal("ParticleEffects"));
        //bluetooth
        BTManager.init();

        load();
    }

    public void load() {
        manager.load("data/joystick.png", Texture.class);
        manager.load("data/joystickOuter.png", Texture.class);
        manager.load("data/Inner.png", Texture.class);
        manager.load("data/tankBlue.png", Texture.class);
        manager.load("data/tankRed.png", Texture.class);
        manager.load("data/tile.png", Texture.class);
        manager.load("data/bullet.png", Texture.class);
        manager.load("data/wall.png", Texture.class);
        manager.load("OptionsData/musicOn.png",Texture.class);
        manager.load("OptionsData/musicOff.png", Texture.class);
        manager.load("MainMenuData/soundOn.png", Texture.class);
        manager.load("MainMenuData/soundOff.png", Texture.class);
        manager.load("OptionsData/plus.png",Texture.class);
        manager.load("OptionsData/minus.png",Texture.class);
        manager.load("OptionsData/vibrateUp.png",Texture.class);
        manager.load("OptionsData/vibrateDown.png",Texture.class);
        manager.load("OptionsData/back.png",Texture.class);
        manager.load("MainMenuData/spUp.png",Texture.class);
        manager.load("MainMenuData/spDn.png",Texture.class);
        manager.load("MainMenuData/mpUp.png",Texture.class);
        manager.load("MainMenuData/mpDn.png",Texture.class);
        manager.load("MainMenuData/gearDn.png",Texture.class);
        manager.load("MainMenuData/gearUp.png",Texture.class);
        manager.load("VictoryData/mainmenubtn.png", Texture.class);
        manager.load("VictoryData/rematchbtn.png", Texture.class);
        manager.load("OptionsData/hp.png", Texture.class);

        manager.load("MPMenuData/localUp.png", Texture.class);
        manager.load("MPMenuData/localDn.png", Texture.class);
        manager.load("MPMenuData/joinUp.png", Texture.class);
        manager.load("MPMenuData/joinDn.png", Texture.class);
        manager.load("MPMenuData/hostUp.png", Texture.class);
        manager.load("MPMenuData/hostDn.png", Texture.class);
    }

    public void done() {
        TankManager.get(0).setTexture(manager.get("data/tankRed.png", Texture.class));
        TankManager.get(1).setTexture(manager.get("data/tankBlue.png", Texture.class));

        BTManager.clientButton.setTexture(manager.get("data/joystick.png", Texture.class));
        BTManager.serverButton.setTexture(manager.get("data/joystick.png", Texture.class));
        BTManager.syncButton.setTexture(manager.get("data/joystick.png", Texture.class));

        Level.tileTexture = manager.get("data/tile.png", Texture.class);
        Level.wallTexture = manager.get("data/wall.png", Texture.class);

        ExplosionManager.texture = manager.get("data/wall.png", Texture.class);

        Bullet.setTexture(manager.get("data/bullet.png", Texture.class));

        JoystickManager.get(0).setTexture(manager.get("data/joystickOuter.png", Texture.class), manager.get("data/Inner.png", Texture.class));
        JoystickManager.get(1).setTexture(manager.get("data/joystickOuter.png", Texture.class), manager.get("data/Inner.png", Texture.class));

        ExplosionManager.texture = manager.get("data/wall.png", Texture.class);
        Bullet.setTexture(manager.get("data/bullet.png", Texture.class));

        ButtonManager.load(27);
    }

    public void update(float deltaTime) {

     /*   Gdx.app.log("WIDTH: ",Float.toString(Graphics.prefferedWidth));
        Gdx.app.log("HEIGHT: ", Float.toString(Graphics.prefferedHeight));*/

        //don't touch
        Input.update();

        //ZA WIN SCREEN

        if (StateManager.getCurrentState() == StateManager.State.MULTIPLAYER) {
            GameMaster.points_red = 0;
            GameMaster.points_blue=0;
            GameMaster.update(deltaTime);

            if(play_music == true){
                menu_music.stop();
                game_music.play();
            }
            else{
                menu_music.stop();
                game_music.stop();
            }
        }
        else if (StateManager.getCurrentState() == StateManager.State.MAINMENU) {
            if(play_music == true){
                menu_music.play();
                game_music.stop();
            }
            else{
                menu_music.stop();
                game_music.stop();
            }
            glavni_meni.update(deltaTime);


            // if(glavni_meni.sound_button_off_show == true){ menu_music.stop(); game_music.stop(); }
        }
        else if (StateManager.getCurrentState() == StateManager.State.SINGLEPLAYER) {
            if(play_music){
                menu_music.stop();
                game_music.play();
            }
            else{
                menu_music.stop();
                game_music.stop();
            }
        }
        else if (StateManager.getCurrentState() == StateManager.State.OPTIONS) {
            //Gdx.app.log("", "u tenkici options state update");
            if(play_music){
                menu_music.play();
                game_music.stop();
            }
            else{
                menu_music.stop();
                game_music.stop();
            }
            options.update(deltaTime);
        }
        else if(StateManager.getCurrentState() == StateManager.State.VICTORY_SCREEN){

            victory_s.update(deltaTime);
        }
        else if(StateManager.getCurrentState()== StateManager.State.MP_MENU){
            MULTIPLAYER_MENU.update(deltaTime);
        }
    }

	@Override
	public void render () {
        if (!updateManager) {
            updateManager = manager.update();
            if (updateManager)
                    done();
            return;
        }
        cam.update();
        batch.setProjectionMatrix(cam.combined);

        batch.totalRenderCalls = 0;

        float deltaTime = Gdx.graphics.getDeltaTime();

        update(deltaTime);

        Gdx.gl.glClearColor(255, 255, 255, 255);
        //Gdx.gl.glClearColor((float)34/255,(float)139/255,(float)34/255,255);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.enableBlending();

        batch.begin();

        if (StateManager.getCurrentState() == StateManager.State.MULTIPLAYER) {
            GameMaster.draw(batch);
            explosion_effect_red.draw(batch);
            explosion_effect_red.update(deltaTime);
            explosion_effect_blue.draw(batch);
            explosion_effect_blue.update(deltaTime);
            smoke_effect.draw(batch);
            smoke_effect.update(deltaTime);
        }
        else if (StateManager.getCurrentState() == StateManager.State.MAINMENU) {
            glavni_meni.render(batch);
            pEffect.draw(batch);
            pEffect.update(deltaTime);
        }
        else if (StateManager.getCurrentState() == StateManager.State.OPTIONS) {

            options.render(batch);
            //options.update(deltaTime);
        }
        else if(StateManager.getCurrentState() == StateManager.State.VICTORY_SCREEN){
            victory_s.render(batch);
        }
        else if(StateManager.getCurrentState()== StateManager.State.MP_MENU){
            MULTIPLAYER_MENU.render(batch);
        }

        batch.end();

        int calls = batch.totalRenderCalls;
    }
}
/*
684.6667 115.333336
115.33331 334.66666
 */
