package com.elvircrn.TankTrouble.android;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.elvircrn.TankTrouble.android.Blue.BTManager;

public class Tenkici extends ApplicationAdapter  {
	static SpriteBatch batch;
    static AssetManager manager;

    public static OrthographicCamera cam;
    public static FreeTypeFontGenerator generator;
    static boolean updateManager = false;

    //fonts
    public static BitmapFont font16;
    static String fontChars = "abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ 1234567890.:,; ' (!?) +-*/=";
    public static FreeTypeFontGenerator.FreeTypeFontParameter fontParameters;


    //MAIN MENU KLASA
    MainMenu glavni_meni;

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
        StateManager.changeState(StateManager.State.MULTIPLAYER);

        //asset manager init OK
        batch = new SpriteBatch();
        manager = new AssetManager();

        //fonts
        fontParameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameters.color = Color.BLACK;
        generator = new FreeTypeFontGenerator(Gdx.files.internal("data/ProFontWindows.ttf"));
        font16 = generator.generateFont(fontParameters);
        batch = new SpriteBatch();
        manager = new AssetManager();

        //Resolution init OK
        Graphics.initValues();

        //camera init OK
        cam = new OrthographicCamera(Graphics.prefferedWidth, Graphics.prefferedHeight);
        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        cam.update();

        //game init
        GameMaster.createGame();

        //UI init
        FPSCounter.create();
        glavni_meni = new MainMenu();
        glavni_meni.create();

        //bluetooth
        BTManager.init();

        load();
    }

    public void load() {
        manager.load("data/joystick.png", Texture.class);
        manager.load("data/tankBlue.png", Texture.class);
        manager.load("data/tankRed.png", Texture.class);
        manager.load("data/tile.png", Texture.class);
        manager.load("data/wall.png", Texture.class);
        manager.load("data/bullet.png", Texture.class);
    }

    public void done() {
        TankManager.get(0).setTexture(manager.get("data/tankRed.png", Texture.class));
        TankManager.get(1).setTexture(manager.get("data/tankBlue.png", Texture.class));

        JoystickManager.get(0).setTexture(manager.get("data/joystick.png", Texture.class), manager.get("data/joystick.png", Texture.class));
        JoystickManager.get(1).setTexture(manager.get("data/joystick.png", Texture.class), manager.get("data/joystick.png", Texture.class));

        BTManager.clientButton.setTexture(manager.get("data/joystick.png", Texture.class));
        BTManager.serverButton.setTexture(manager.get("data/joystick.png", Texture.class));
        BTManager.syncButton.setTexture(manager.get("data/joystick.png", Texture.class));

        Level.tileTexture = manager.get("data/tile.png", Texture.class);
        Level.wallTexture = manager.get("data/wall.png", Texture.class);

        ExplosionManager.texture = manager.get("data/wall.png", Texture.class);

        Bullet.setTexture(manager.get("data/bullet.png", Texture.class));
    }

    public void update(float deltaTime) {
        GameMaster.update(deltaTime);
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
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.enableBlending();

        batch.begin();

        if (StateManager.getCurrentState() == StateManager.State.MULTIPLAYER) {
            GameMaster.draw(batch);
        }
        else if (StateManager.getCurrentState() == StateManager.State.MAINMENU) {
            glavni_meni.render(batch);
        }

        BTManager.draw(batch);

        batch.end();

        int calls = batch.totalRenderCalls;
    }
}
/*
684.6667 115.333336
115.33331 334.66666
 */
