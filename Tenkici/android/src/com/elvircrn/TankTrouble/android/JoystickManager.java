package com.elvircrn.TankTrouble.android;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class JoystickManager {
    public static com.elvircrn.TankTrouble.android.Joystick[] joysticks;

    public static void create() {
        joysticks = new com.elvircrn.TankTrouble.android.Joystick[com.elvircrn.TankTrouble.android.GameMaster.playerCount];
        for (int i = 0; i < com.elvircrn.TankTrouble.android.GameMaster.playerCount; i++)
            joysticks [i] = new com.elvircrn.TankTrouble.android.Joystick();
    }

    public static com.elvircrn.TankTrouble.android.Joystick get(int index) {
        return joysticks[index];
    }

    public static void update(float deltaTime) {
        for (int i = 0; i < com.elvircrn.TankTrouble.android.GameMaster.playerCount; i++) {
            joysticks[i].update(deltaTime);
        }
    }

    public static void draw(SpriteBatch batch) {
        for (int i = 0; i < com.elvircrn.TankTrouble.android.GameMaster.playerCount; i++)
            joysticks [i].draw(batch);
    }
}
