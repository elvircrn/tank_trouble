package com.elvircrn.TankTrouble.android;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class JoystickManager {
    public static Joystick[] joysticks;

    public static void create() {
        joysticks = new Joystick[GameMaster.playerCount];
        for (int i = 0; i < GameMaster.playerCount; i++)
            joysticks [i] = new Joystick();
    }

    public static Joystick get(int index) {
        return joysticks[index];
    }

    public static void update(float deltaTime) {
        if (GameMaster.getMode() == GameMaster.Mode.CLIENT) {
            joysticks [1].update(deltaTime);
        }
        else if (GameMaster.getMode() == GameMaster.Mode.SERVER) {
            joysticks [0].update(deltaTime);
        }
        else {
            for (int i = 0; i < GameMaster.playerCount; i++) {
                joysticks[i].update(deltaTime);
            }
        }
    }

    public static void draw(SpriteBatch batch) {
        if (GameMaster.getMode() == GameMaster.Mode.CLIENT) {
            joysticks [1].draw(batch);
        }
        else if (GameMaster.getMode() == GameMaster.Mode.SERVER) {
            joysticks [0].draw(batch);
        }
        else {
            for (int i = 0; i < GameMaster.playerCount; i++) {
                joysticks[i].draw(batch);
            }
        }
    }
}
