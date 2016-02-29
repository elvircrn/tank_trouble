package com.elvircrn.TankTrouble;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by elvircrn on 3/4/2015.
 */
public class TankManager {
    public static Tank[] tanks;

    private TankManager() { }

    public static void create() {
        tanks = new Tank[GameMaster.playerCount];
        for (int i = 0; i < GameMaster.playerCount; i++)
            tanks[i] = new Tank();
    }

    public static Tank get(int index) {
        return tanks[index];
    }

    public static void init() {

    }

    public static void draw(SpriteBatch batch) {
        for (int i = 0; i < GameMaster.playerCount; i++)
            tanks[i].draw(batch);
    }

    public static void update(float deltaTime) {
        //for (int i = 0; i < 1; i++)
        for (int i = 0; i < GameMaster.playerCount; i++)
            tanks[i].update(deltaTime);
    }
}
