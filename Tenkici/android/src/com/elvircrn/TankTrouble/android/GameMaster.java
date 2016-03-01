package com.elvircrn.TankTrouble.android;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by elvircrn on 3/5/2015.
 */
public class GameMaster {
    /*
    elements of a game:
        Level
        Joystick
        Tank
        Bullets

    initialize static values of:
        Level [x]
        Joystick [x]
        Tank [x]
        Bullets [x]

    instantiate game elements:
        Level [x]
        Joystick [x]
        Tank [ ]
        Bullets [ ]
     */

    public static int playerCount = 2;
    public static float globalRatio;

    public static void createGame() {
        //initialize all static variables
        com.elvircrn.TankTrouble.android.Level.create();
        com.elvircrn.TankTrouble.android.Analog.create();
        com.elvircrn.TankTrouble.android.Input.create();
        com.elvircrn.TankTrouble.android.JoystickManager.create();
        TankManager.create();
        com.elvircrn.TankTrouble.android.BulletManager.create(com.elvircrn.TankTrouble.android.Level.wallWidth / 2 + 1, com.elvircrn.TankTrouble.android.Level.wallWidth / 2 + 1);

        //create objects
        initJoysticks();
        initTanks();
    }

    private static void initTanks() {
        Vector2 tankOne = com.elvircrn.TankTrouble.android.Level.tileToScreen(0, 0);
        Vector2 tankTwo = com.elvircrn.TankTrouble.android.Level.tileToScreen(com.elvircrn.TankTrouble.android.Level.width - 1, com.elvircrn.TankTrouble.android.Level.height - 1);

        float tankDimens = (2.0f * com.elvircrn.TankTrouble.android.Level.getTileDimens()) / 5.0f;
/*
        tankOne.set(tankOne.x + Level.getTileDimens() / 2, tankOne.y + Level.getTileDimens() / 2);
        tankTwo.set(tankTwo.x + Level.getTileDimens() / 2, tankTwo.y + Level.getTileDimens() / 2);
*/
        TankManager.get(0).init(0, new Vector2(tankOne.x + com.elvircrn.TankTrouble.android.Level.getTileDimens() / 2, tankOne.y + com.elvircrn.TankTrouble.android.Level.getTileDimens() / 2), com.elvircrn.TankTrouble.android.JoystickManager.get(0).analog.getNorAngle(), tankDimens, tankDimens);
        TankManager.get(1).init(1, new Vector2(tankTwo.x + com.elvircrn.TankTrouble.android.Level.getTileDimens() / 2, tankTwo.y + com.elvircrn.TankTrouble.android.Level.getTileDimens() / 2), com.elvircrn.TankTrouble.android.JoystickManager.get(1).analog.getNorAngle(), tankDimens, tankDimens);
    }

    private static void initJoysticks() {
        com.elvircrn.TankTrouble.android.LevelManager.initLevel();

        com.elvircrn.TankTrouble.android.JoystickManager.get(0).button = new com.elvircrn.TankTrouble.android.Button(0,
                0.18f * Tenkici.PrefferedWidth,
                0.18f * Tenkici.PrefferedHeight,
                0.10f * Tenkici.PrefferedWidth,
                0.10f * Tenkici.PrefferedWidth);

        com.elvircrn.TankTrouble.android.JoystickManager.get(1).button = new com.elvircrn.TankTrouble.android.Button(1,
                0.82f * Tenkici.PrefferedWidth,
                0.82f * Tenkici.PrefferedHeight,
                0.10f * Tenkici.PrefferedWidth,
                0.10f * Tenkici.PrefferedWidth);

        com.elvircrn.TankTrouble.android.JoystickManager.get(0).analog = new com.elvircrn.TankTrouble.android.Analog(0.18f * Tenkici.PrefferedWidth,
                0.82f * Tenkici.PrefferedHeight,
                0.10f * Tenkici.PrefferedWidth,
                0.10f * Tenkici.PrefferedWidth,
                0.05f * Tenkici.PrefferedWidth);

        com.elvircrn.TankTrouble.android.JoystickManager.get(1).analog = new com.elvircrn.TankTrouble.android.Analog(0.82f * Tenkici.PrefferedWidth,
                0.18f * Tenkici.PrefferedHeight,
                0.10f * Tenkici.PrefferedWidth,
                0.10f * Tenkici.PrefferedWidth,
                0.05f * Tenkici.PrefferedWidth);

        com.elvircrn.TankTrouble.android.JoystickManager.get(0).setInputRegion(new Rectangle(0, 0, Tenkici.PrefferedWidth / 2, Tenkici.PrefferedHeight));
        com.elvircrn.TankTrouble.android.JoystickManager.get(1).setInputRegion(new Rectangle(Tenkici.PrefferedWidth / 2, 0, Tenkici.PrefferedWidth / 2, Tenkici.PrefferedHeight));
    }

    public static void update(float deltaTime) {
        com.elvircrn.TankTrouble.android.FPSCounter.update(deltaTime);
        com.elvircrn.TankTrouble.android.Input.update();
        com.elvircrn.TankTrouble.android.JoystickManager.update(deltaTime);
        TankManager.update(deltaTime);
        com.elvircrn.TankTrouble.android.BulletManager.update(deltaTime);

        tankBulletCollision();
    }

    public static void draw(SpriteBatch batch) {
        Level.Draw(batch);
        TankManager.draw(batch);
        com.elvircrn.TankTrouble.android.JoystickManager.draw(batch);
        com.elvircrn.TankTrouble.android.FPSCounter.draw(batch);
        BulletManager.draw(batch);
    }

    public static void tankBulletCollision() {
        for (int i = 0; i < com.elvircrn.TankTrouble.android.BulletManager.count(); i++) {
            for (int j = 0; j < playerCount; j++) {
                if (TankManager.get(j).getCollisionCircle().contains(com.elvircrn.TankTrouble.android.BulletManager.get(i).getCollisionCircle())) {
                    TankManager.get((j + 1) % 2).points++;
                    Gdx.app.log("points", "one: " + Integer.toString(TankManager.get(0).points) + " two: " + Integer.toString(TankManager.get(1).points));
                    initNewRound();
                }
            }
        }
    }

    public static void initNewRound() {
        com.elvircrn.TankTrouble.android.BulletManager.clearBullets();
        com.elvircrn.TankTrouble.android.LevelManager.initLevel();
        Vector2 tankOne = com.elvircrn.TankTrouble.android.Level.tileToScreen(0, 0);
        Vector2 tankTwo = com.elvircrn.TankTrouble.android.Level.tileToScreen(com.elvircrn.TankTrouble.android.Level.width - 1, com.elvircrn.TankTrouble.android.Level.height - 1);

        float tankDimens = (2.0f * com.elvircrn.TankTrouble.android.Level.getTileDimens()) / 5.0f;
        TankManager.get(0).spawnTo(tankOne.x + com.elvircrn.TankTrouble.android.Level.getTileDimens() / 2, tankOne.y + com.elvircrn.TankTrouble.android.Level.getTileDimens() / 2, com.elvircrn.TankTrouble.android.JoystickManager.get(0).analog.getNorAngle(), tankDimens, tankDimens);
        TankManager.get(1).spawnTo(tankTwo.x + com.elvircrn.TankTrouble.android.Level.getTileDimens() / 2, tankTwo.y + com.elvircrn.TankTrouble.android.Level.getTileDimens() / 2, com.elvircrn.TankTrouble.android.JoystickManager.get(1).analog.getNorAngle(), tankDimens, tankDimens);
    }
}
