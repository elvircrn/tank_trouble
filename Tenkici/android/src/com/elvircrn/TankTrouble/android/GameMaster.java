package com.elvircrn.TankTrouble.android;

import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.elvircrn.TankTrouble.android.Blue.BTManager;

import java.io.IOException;

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

    public enum Mode { LOCAL, SERVER, CLIENT }

    private static Mode mode = Mode.LOCAL;

    public static int playerCount = 2;
    public static float globalRatio;

    public static Mode getMode() {
        return mode;
    }

    public static void setMode(Mode mode) {
        GameMaster.mode = mode;
    }

    public static Sound explode_effect;
    public static boolean play_explode=true;

    public static float points_red;
    public static float points_blue;

    public static int max_points=5;
    public static boolean blue_winner=false;

    public static int hp=1;

    public static boolean vibrate=false;

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

        BulletManager.makeLethal();
        explode_effect = Gdx.audio.newSound(Gdx.files.internal("SoundEffects/TankExplode.mp3"));
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
                0.11f * Graphics.prefferedWidth,
                0.18f * Graphics.prefferedHeight,
                0.10f * Graphics.prefferedWidth,
                0.10f * Graphics.prefferedWidth);

        JoystickManager.get(1).button = new Button(1,
                0.89f * Graphics.prefferedWidth,
                0.82f * Graphics.prefferedHeight,
                0.10f * Graphics.prefferedWidth,
                0.10f * Graphics.prefferedWidth);

        JoystickManager.get(0).analog = new Analog(0.11f * Graphics.prefferedWidth,
                0.82f * Graphics.prefferedHeight,
                0.10f * Graphics.prefferedWidth,
                0.10f * Graphics.prefferedWidth,
                0.05f * Graphics.prefferedWidth);

        JoystickManager.get(1).analog = new Analog(0.89f * Graphics.prefferedWidth,
                0.18f * Graphics.prefferedHeight,
                0.10f * Graphics.prefferedWidth,
                0.10f * Graphics.prefferedWidth,
                0.05f * Graphics.prefferedWidth);

        BTManager.serverButton = new Button(101,
                Graphics.prefferedWidth / 2 - 10,
                Graphics.prefferedHeight / 2 - 0.20f * Graphics.prefferedWidth,
                0.10f * Graphics.prefferedWidth,
                0.10f * Graphics.prefferedWidth * Graphics.heightRatio(),
                "server");

        BTManager.clientButton = new Button(102,
                Graphics.prefferedWidth / 2 - 10,
                Graphics.prefferedHeight / 2 + 0.20f * Graphics.prefferedWidth,
                0.10f * Graphics.prefferedWidth,
                0.10f * Graphics.prefferedWidth * Graphics.heightRatio(),
                "client");

        BTManager.syncButton = new Button(103,
                Graphics.prefferedWidth / 2,
                Graphics.prefferedHeight / 2,
                0.10f * Graphics.prefferedWidth,
                0.10f * Graphics.prefferedWidth * Graphics.heightRatio(),
                "sync");

        JoystickManager.get(0).setInputRegion(new Rectangle(0, 0, Graphics.prefferedWidth / 2, Graphics.prefferedHeight));
        JoystickManager.get(1).setInputRegion(new Rectangle(Graphics.prefferedWidth / 2, 0, Graphics.prefferedWidth / 2, Graphics.prefferedHeight));
    }

    public static void update(float deltaTime) {
        FPSCounter.update(deltaTime);
        JoystickManager.update(deltaTime);
        TankManager.update(deltaTime);
        BulletManager.update(deltaTime);
        BTManager.update();
        tankBulletCollision();
        updateBluetooth(deltaTime);


        if(mode == Mode.LOCAL && (GameMaster.points_red == GameMaster.max_points || GameMaster.points_blue == GameMaster.max_points)) {
            StateManager.changeState(StateManager.State.VICTORY_SCREEN);
            if(GameMaster.points_blue == GameMaster.max_points) {TankManager.get(1).points = 0; TankManager.get(0).points=0;}
            if(GameMaster.points_red == GameMaster.max_points) {TankManager.get(0).points = 0; TankManager.get(1).points=0;}
        }

    }

    public static void requestNewRound() {
        if (mode == Mode.CLIENT) {
            try {
                ByteArrayList message = new ByteArrayList(3);
                BTManager.shorts [0] = (short)(Graphics.prefferedHeight * 10.0f);

                Serializer.serializeMessage(message,
                        CodeManager.RequestNewGame,
                        (short)1,
                        BTManager.shorts);

                BTManager.sendData(message.getContents());

                Log.d("SYNC PRESSED", "OK");
            }
            catch (IOException e) {
                Log.d("SYNC PRESSED FAIL", e.getMessage());
            }
        }
        else {
            Log.d("FUCK", "WHAT THE FUCK");
        }
    }

    public static synchronized void updateBluetooth(float deltaTime) {
        BTManager.messageBuffer.clear();

        if (mode == Mode.CLIENT) {
            ClientManager.writeTankLocation(BTManager.messageBuffer);
            try {
                BTManager.sendData(BTManager.messageBuffer.getContents());
            }
            catch (IOException e) {
                Log.d("CLIENT", "failed to send tank data");
            }
        }

        if (mode == Mode.SERVER) {
            ServerManager.writeTankLocation(BTManager.messageBuffer);
            try {
                BTManager.sendData(BTManager.messageBuffer.getContents());
            }
            catch (IOException e) {
                Log.d("SERVER", "failed to send tank data");
            }

            //TODO: CHECK
            BTManager.messageBuffer.clear();
            ServerManager.writeBullets(BTManager.messageBuffer);

            try {
                BTManager.sendData(BTManager.messageBuffer.getContents());
            }
            catch (IOException e) {
                Log.d("SERVER", "failed to send bullet data");
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

    public static synchronized void tankBulletCollision() {
        /*
        if (!BulletManager.isLethal())
            return;
*/
        for (Bullet bullet : BulletManager.bullets) {
            for (int j = 0; j < playerCount; j++) {
                if (TankManager.get(j).getCollisionCircle().contains(bullet.getCollisionCircle())) {
                    Gdx.app.log("points", "one: " + Integer.toString(TankManager.get(0).points) + " two: " + Integer.toString(TankManager.get(1).points));
                    onTankHit(j);
                }
            }
        }
    }

    public static void onTankHit(int index) {
        if (mode == Mode.LOCAL) {
            float xPos = TankManager.get(index).worldLocation.x;
            float yPos = TankManager.get(index).worldLocation.y;

            if (index == 0) {
                Tenkici.explosion_effect_red.setPosition(xPos, yPos);
                Tenkici.explosion_effect_red.start();
                points_blue = TankManager.get(1).points++;
            } else if (index == 1) {
                Tenkici.explosion_effect_blue.setPosition(xPos, yPos);
                Tenkici.explosion_effect_blue.start();
                points_red = TankManager.get(0).points++;
            }

            TankManager.get(index).drawable = false;

            if (play_explode) {
                explode_effect.play(Options.SOUND_VOLUME);
            }
            if(vibrate == true){
                Gdx.input.vibrate(300);
            }
        }

        if (mode != Mode.LOCAL)
            requestNewRound();
        else
            initNewRound();
    }

    public static void initNewRound() {
        if (mode == Mode.LOCAL) {
            if(points_blue==max_points-1){
                blue_winner = true;
            }
            else if(points_red==max_points-1){
                blue_winner = false;
            }
            else {

                com.elvircrn.TankTrouble.android.BulletManager.clearBullets();
                com.elvircrn.TankTrouble.android.LevelManager.initLevel();
                Vector2 tankOne = com.elvircrn.TankTrouble.android.Level.tileToScreen(0, 0);
                Vector2 tankTwo = com.elvircrn.TankTrouble.android.Level.tileToScreen(com.elvircrn.TankTrouble.android.Level.width - 1, com.elvircrn.TankTrouble.android.Level.height - 1);

                float tankDimens = (2.0f * com.elvircrn.TankTrouble.android.Level.getTileDimens()) / 5.0f;
                TankManager.get(0).spawnTo(tankOne.x + com.elvircrn.TankTrouble.android.Level.getTileDimens() / 2, tankOne.y + com.elvircrn.TankTrouble.android.Level.getTileDimens() / 2, Tank.defaultRotation, tankDimens, tankDimens);
                TankManager.get(1).spawnTo(tankTwo.x + com.elvircrn.TankTrouble.android.Level.getTileDimens() / 2, tankTwo.y + com.elvircrn.TankTrouble.android.Level.getTileDimens() / 2, Tank.defaultRotation, tankDimens, tankDimens);
            }
        }
        else {
            BulletManager.clearBullets();
            LevelManager.initLevel();
            Vector2 tankOne = Level.tileToScreen(0, 0);
            Vector2 tankTwo = Level.tileToScreen(Level.width - 1, Level.height - 1);

            float tankDimens = (2.0f * Level.getTileDimens()) / 5.0f;
            TankManager.get(0).spawnTo(tankOne.x + Level.getTileDimens() / 2, tankOne.y + Level.getTileDimens() / 2, JoystickManager.get(0).analog.getNorAngle(), tankDimens, tankDimens);
            TankManager.get(1).spawnTo(tankTwo.x + Level.getTileDimens() / 2, tankTwo.y + Level.getTileDimens() / 2, JoystickManager.get(1).analog.getNorAngle(), tankDimens, tankDimens);

        }
    }

    public static void initNewRound(short seed) {
        BulletManager.clearBullets();

        LevelManager.initLevel(seed);
        Vector2 tankOne = Level.tileToScreen(0, 0);
        Vector2 tankTwo = Level.tileToScreen(Level.width - 1, Level.height - 1);

        float tankDimens = (2.0f * Level.getTileDimens()) / 5.0f;
        TankManager.get(0).spawnTo(tankOne.x + Level.getTileDimens() / 2, tankOne.y + Level.getTileDimens() / 2, JoystickManager.get(0).analog.getNorAngle(), tankDimens, tankDimens);
        TankManager.get(1).spawnTo(tankTwo.x + Level.getTileDimens() / 2, tankTwo.y + Level.getTileDimens() / 2, JoystickManager.get(1).analog.getNorAngle(), tankDimens, tankDimens);
    }
}
