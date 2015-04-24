package com.elvircrn.TankTrouble;

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
        Level.create();
        Analog.create();
        Input.create();
        JoystickManager.create();
        TankManager.create();
        BulletManager.create(Level.wallWidth / 2 + 1, Level.wallWidth / 2 + 1);

        //create objects
        initJoysticks();
        initTanks();
    }

    private static void initTanks() {
        Vector2 tankOne = Level.tileToScreen(0, 0);
        Vector2 tankTwo = Level.tileToScreen(Level.width - 1, Level.height - 1);

        float tankDimens = (2.0f * Level.getTileDimens()) / 5.0f;

        tankOne.set(tankOne.x + Level.getTileDimens() / 2, tankOne.y + Level.getTileDimens() / 2);
        tankTwo.set(tankTwo.x + Level.getTileDimens() / 2, tankTwo.y + Level.getTileDimens() / 2);

        TankManager.get(0).init(0, tankOne, JoystickManager.get(0).analog.getNorAngle(), tankDimens, tankDimens);
        TankManager.get(1).init(1, tankTwo, JoystickManager.get(1).analog.getNorAngle(), tankDimens, tankDimens);
    }

    private static void initJoysticks() {
        LevelManager.initLevel();
        JoystickManager.get(0).button.init(0,
                0.18f * TankTrouble.PrefferedWidth,
                0.18f * TankTrouble.PrefferedHeight,
                0.10f * TankTrouble.PrefferedWidth,
                0.10f * TankTrouble.PrefferedWidth);
        JoystickManager.get(1).button.init(1,
                0.82f * TankTrouble.PrefferedWidth,
                0.82f * TankTrouble.PrefferedHeight,
                0.10f * TankTrouble.PrefferedWidth,
                0.10f * TankTrouble.PrefferedWidth);

        JoystickManager.get(0).analog.init(0.18f * TankTrouble.PrefferedWidth, 0.82f * TankTrouble.PrefferedHeight, 0.10f * TankTrouble.PrefferedWidth, 0.10f * TankTrouble.PrefferedWidth, 0.05f * TankTrouble.PrefferedWidth);
        JoystickManager.get(1).analog.init(0.82f * TankTrouble.PrefferedWidth, 0.18f * TankTrouble.PrefferedHeight, 0.10f * TankTrouble.PrefferedWidth, 0.10f * TankTrouble.PrefferedWidth, 0.05f * TankTrouble.PrefferedWidth);

        JoystickManager.get(0).setInputRegion(new Rectangle(0, 0, TankTrouble.PrefferedWidth / 2, TankTrouble.PrefferedHeight));
        JoystickManager.get(1).setInputRegion(new Rectangle(TankTrouble.PrefferedWidth / 2, 0, TankTrouble.PrefferedWidth / 2, TankTrouble.PrefferedHeight));
    }
}
