package com.elvircrn.TankTrouble;

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

public class TankTrouble extends ApplicationAdapter  {

    public static float PrefferedWidth = 800, PrefferedHeight;

    public static BitmapFont font16;

	static SpriteBatch batch;
    static AssetManager manager;

    public static OrthographicCamera cam;

    static boolean updateManager = false;
    public static FreeTypeFontGenerator generator;
    public static float w, h;

    static String fontChars = "abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ 1234567890.:,; ' (!?) +-*/=";

    public static FreeTypeFontGenerator.FreeTypeFontParameter fontParameters;

	@Override
	public void create () {
        //asset manager init OK
        fontParameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameters.color = Color.BLACK;
        generator = new FreeTypeFontGenerator(Gdx.files.internal("data/ProFontWindows.ttf"));
        font16 = generator.generateFont(fontParameters);
        batch = new SpriteBatch();
        manager = new AssetManager();

        //camera init OK
        w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        PrefferedHeight = (h / w) * PrefferedWidth;
        cam = new OrthographicCamera(PrefferedWidth, PrefferedHeight);
        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        cam.update();

        //game init
        GameMaster.createGame();

        //UI init
        PointsUI.init();
        FPSCounter.create();

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

        Level.Draw(batch);
        TankManager.draw(batch);
        JoystickManager.draw(batch);
        FPSCounter.draw(batch);
        BulletManager.draw(batch);

        batch.end();

        int calls = batch.totalRenderCalls;
    }
}
/*
684.6667 115.333336
115.33331 334.66666
 */
