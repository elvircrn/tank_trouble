package com.elvircrn.TankTrouble;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by elvircrn on 2/14/2015.
 */
public class LevelManager {

    public static float scale;

    public static void initGame() {

        scale = MyGdxGame.PrefferedWidth / MyGdxGame.w;
        int levelWidth = Random.getRange(6, 8), levelHeight = Random.getRange(4, 6);

        int dim;

        if (levelWidth < levelHeight)
            dim = levelHeight;
        else
            dim = levelWidth;

        float d = dim;

        float scale = d / 9.0f;

        /*MyGdxGame.tankOne.init((int)(scale * 50.0f),
                                     (int)(scale * 50.0f));
        MyGdxGame.tankTwo.init((int)(scale * 50.0f),
                                     (int)(scale * 50.0f));*/

        MyGdxGame.tankOne.index = 1;
        MyGdxGame.tankTwo.index = 2;

        Input.InitTouch();
        Input.InitJoystick();

        Level.generateLevel(levelWidth, levelHeight);

        MyGdxGame.tankOne.init(((Level.getTileDimens() * 2) / 7), ((Level.getTileDimens() * 2) / 7));
        MyGdxGame.tankTwo.init(((Level.getTileDimens() * 2) / 7), ((Level.getTileDimens() * 2) / 7));

        Vector2 oneSpawn = Level.tileToScreen(0, 0).add(new Vector2(Level.wallWidth, Level.wallWidth));
        Vector2 twoSpawn = Level.tileToScreen(Level.width - 1, Level.height - 1).add(new Vector2(Level.wallWidth, Level.wallWidth));

        /*
        MyGdxGame.tankOne.collisionCircle.x = oneSpawn.x;
        MyGdxGame.tankOne.collisionCircle.y = oneSpawn.y;
            */
        MyGdxGame.tankTwo.collisionCircle.x = twoSpawn.x;
        MyGdxGame.tankTwo.collisionCircle.y = twoSpawn.y;


        MyGdxGame.tankOne.collisionCircle.x = 0;
        MyGdxGame.tankOne.collisionCircle.y = 0;

        MyGdxGame.tankOne.initLoc();
        MyGdxGame.tankTwo.initLoc();

        BulletManager.init();

        Bullet.initScale(scale);
    }
}
