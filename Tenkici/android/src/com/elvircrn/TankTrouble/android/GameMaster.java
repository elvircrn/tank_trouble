package com.elvircrn.TankTrouble.android;

import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.elvircrn.TankTrouble.android.Blue.BTManager;

import java.io.IOException;

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

    public enum Mode { SERVER, CLIENT };

    private static Mode mode;

    public static int playerCount = 2;
    public static float globalRatio;

    public static Mode getMode() {
        return mode;
    }

    public static void setMode(Mode mode) {
        GameMaster.mode = mode;
    }

    public static void createGame() {
        //initialize all static variables
        Level.create();
        Analog.create();
        Input.create();
        JoystickManager.create();
        TankManager.create();
        BulletManager.create(Level.wallWidth / 2 + 1, Level.wallWidth / 2 + 1);

        //create objects
        initJoysticks();
        initTanks();

        BulletManager.makeSafe();
    }

    private static void initTanks() {
        Vector2 tankOne = Level.tileToScreen(0, 0);
        Vector2 tankTwo = Level.tileToScreen(Level.width - 1, Level.height - 1);

        float tankDimens = (2.0f * Level.getTileDimens()) / 5.0f;
/*
        tankOne.set(tankOne.x + Level.getTileDimens() / 2, tankOne.y + Level.getTileDimens() / 2);
        tankTwo.set(tankTwo.x + Level.getTileDimens() / 2, tankTwo.y + Level.getTileDimens() / 2);
*/
        TankManager.get(0).init(0, new Vector2(tankOne.x + Level.getTileDimens() / 2, tankOne.y + Level.getTileDimens() / 2), JoystickManager.get(0).analog.getNorAngle(), tankDimens, tankDimens);
        TankManager.get(1).init(1, new Vector2(tankTwo.x + Level.getTileDimens() / 2, tankTwo.y + Level.getTileDimens() / 2), JoystickManager.get(1).analog.getNorAngle(), tankDimens, tankDimens);
    }

    private static void initJoysticks() {
        LevelManager.initLevel();

        JoystickManager.get(0).button = new Button(0,
                0.18f * Graphics.prefferedWidth,
                0.18f * Graphics.prefferedHeight,
                0.10f * Graphics.prefferedWidth,
                0.10f * Graphics.prefferedWidth);

        JoystickManager.get(1).button = new Button(1,
                0.82f * Graphics.prefferedWidth,
                0.82f * Graphics.prefferedHeight,
                0.10f * Graphics.prefferedWidth,
                0.10f * Graphics.prefferedWidth);

        JoystickManager.get(0).analog = new Analog(0.18f * Graphics.prefferedWidth,
                0.82f * Graphics.prefferedHeight,
                0.10f * Graphics.prefferedWidth,
                0.10f * Graphics.prefferedWidth,
                0.05f * Graphics.prefferedWidth);

        JoystickManager.get(1).analog = new Analog(0.82f * Graphics.prefferedWidth,
                0.18f * Graphics.prefferedHeight,
                0.10f * Graphics.prefferedWidth,
                0.10f * Graphics.prefferedWidth,
                0.05f * Graphics.prefferedWidth);

        BTManager.serverButton = new Button(101,
                                            Graphics.prefferedWidth / 2 - 10,
                                            Graphics.prefferedHeight / 2 - 0.20f * Graphics.prefferedWidth,
                                            0.10f * Graphics.prefferedWidth,
                                            0.10f * Graphics.prefferedWidth,
                                            "server");

        BTManager.clientButton = new Button(102,
                Graphics.prefferedWidth / 2 - 10,
                Graphics.prefferedHeight / 2 + 0.20f * Graphics.prefferedWidth,
                0.10f * Graphics.prefferedWidth,
                0.10f * Graphics.prefferedWidth,
                "client");

        BTManager.syncButton = new Button(103,
                Graphics.prefferedWidth / 2,
                Graphics.prefferedHeight / 2,
                0.10f * Graphics.prefferedWidth,
                0.10f * Graphics.prefferedWidth,
                "sync");

        JoystickManager.get(0).setInputRegion(new Rectangle(0, 0, Graphics.prefferedWidth / 2, Graphics.prefferedHeight));
        JoystickManager.get(1).setInputRegion(new Rectangle(Graphics.prefferedWidth / 2, 0, Graphics.prefferedWidth / 2, Graphics.prefferedHeight));
    }

    public static void update(float deltaTime) {
        FPSCounter.update(deltaTime);
        Input.update();
        JoystickManager.update(deltaTime);
        TankManager.update(deltaTime);
        BulletManager.update(deltaTime);
        BTManager.update();
        tankBulletCollision();
        updateBluetooth(deltaTime);
    }

    public static void updateBluetooth(float deltaTime) {
        if (BTManager.serverButton.justPressed()) {
            AndroidLauncher.tenkici.myGameCallback.onStartActivityServer();
        }

        if (BTManager.clientButton.justPressed()) {
            AndroidLauncher.tenkici.myGameCallback.onStartActivityClient();
        }

        if (BTManager.syncButton.justPressed()) {
            if (mode == Mode.CLIENT) {
                while (true) {
                    String request = String.valueOf(CodeManager.RequestNewGame);
                    try {
                        BTManager.sendData(request.getBytes());
                        break;
                    } catch (IOException e) {
                        Log.d("SYNC PRESSED", e.getMessage());
                        continue;
                    }
                }
            }
        }
    }

    public static void draw(SpriteBatch batch) {
        Level.Draw(batch);
        TankManager.draw(batch);
        JoystickManager.draw(batch);
        FPSCounter.draw(batch);
        BulletManager.draw(batch);
    }

    public static void tankBulletCollision() {
        if (!BulletManager.isLethal())
            return;

        for (int i = 0; i < BulletManager.count(); i++) {
            for (int j = 0; j < playerCount; j++) {
                if (TankManager.get(j).getCollisionCircle().contains(BulletManager.get(i).getCollisionCircle())) {
                    Gdx.app.log("points", "one: " + Integer.toString(TankManager.get(0).points) + " two: " + Integer.toString(TankManager.get(1).points));
                    onTankHit(j);
                }
            }
        }
    }

    public static void onTankHit(int index) {
        //ovdje moze ici kod za eksplozije

        //povecaj poene
        TankManager.get((index + 1) % 2).points++;

        //pocni novu rundu
        initNewRound();
    }

    public static void initNewRound() {
        BulletManager.clearBullets();
        LevelManager.initLevel();
        Vector2 tankOne = Level.tileToScreen(0, 0);
        Vector2 tankTwo = Level.tileToScreen(Level.width - 1, Level.height - 1);

        float tankDimens = (2.0f * Level.getTileDimens()) / 5.0f;
        TankManager.get(0).spawnTo(tankOne.x + Level.getTileDimens() / 2, tankOne.y + Level.getTileDimens() / 2, JoystickManager.get(0).analog.getNorAngle(), tankDimens, tankDimens);
        TankManager.get(1).spawnTo(tankTwo.x + Level.getTileDimens() / 2, tankTwo.y + Level.getTileDimens() / 2, JoystickManager.get(1).analog.getNorAngle(), tankDimens, tankDimens);
    }

    public static void initNewRound(int seed) {
        BulletManager.clearBullets();
        RandomWrapper.init(seed);
        LevelManager.initLevel();
        Vector2 tankOne = Level.tileToScreen(0, 0);
        Vector2 tankTwo = Level.tileToScreen(Level.width - 1, Level.height - 1);

        float tankDimens = (2.0f * Level.getTileDimens()) / 5.0f;
        TankManager.get(0).spawnTo(tankOne.x + Level.getTileDimens() / 2, tankOne.y + Level.getTileDimens() / 2, JoystickManager.get(0).analog.getNorAngle(), tankDimens, tankDimens);
        TankManager.get(1).spawnTo(tankTwo.x + Level.getTileDimens() / 2, tankTwo.y + Level.getTileDimens() / 2, JoystickManager.get(1).analog.getNorAngle(), tankDimens, tankDimens);
    }
}
