package com.elvircrn.TankTrouble.android;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by elvircrn on 3/4/2015.
 */
public class TankManager {
    public static com.elvircrn.TankTrouble.android.Tank[] tanks;

    private TankManager() { }

    public static void create() {
        tanks = new com.elvircrn.TankTrouble.android.Tank[com.elvircrn.TankTrouble.android.GameMaster.playerCount];
        for (int i = 0; i < com.elvircrn.TankTrouble.android.GameMaster.playerCount; i++)
            tanks[i] = new com.elvircrn.TankTrouble.android.Tank();
    }

    public static com.elvircrn.TankTrouble.android.Tank get(int index) {
        return tanks[index];
    }

    public static void init() {

    }

    public static void draw(SpriteBatch batch) {
        for (int i = 0; i < com.elvircrn.TankTrouble.android.GameMaster.playerCount; i++)
            tanks[i].draw(batch);
    }

    public static void update(float deltaTime) {
        //for (int i = 0; i < 1; i++)
        for (int i = 0; i < com.elvircrn.TankTrouble.android.GameMaster.playerCount; i++)
            tanks[i].update(deltaTime);
    }
}
