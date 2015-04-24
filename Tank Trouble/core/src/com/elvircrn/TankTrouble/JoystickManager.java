package com.elvircrn.TankTrouble;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by elvircrn on 3/5/2015.
 */
public class JoystickManager {
    public static Joystick[] joysticks;

    public static void create() {
        joysticks = new Joystick[GameMaster.playerCount];
        for (int i = 0; i < GameMaster.playerCount; i++)
            joysticks[i] = new Joystick();
    }

    public static Joystick get(int index) {
        return joysticks[index];
    }

    public static void update(float deltaTime) {
        joysticks [0].update(deltaTime);
        joysticks [1].update(deltaTime);
    }

    public static void draw(SpriteBatch batch) {
        for (int i = 0; i < GameMaster.playerCount; i++)
            joysticks [i].draw(batch);
    }
}
