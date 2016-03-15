package com.elvircrn.TankTrouble.android;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

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
        if (GameMaster.getMode() == GameMaster.Mode.CLIENT) {
            for (int i = 0; i < GameMaster.playerCount; i++)
                tanks[i].update(deltaTime);
        }
        else if (GameMaster.getMode() == GameMaster.Mode.SERVER) {
            tanks[CodeManager.ServerTankIndex].update(deltaTime);
        }
        else {
            for (int i = 0; i < GameMaster.playerCount; i++)
                tanks[i].update(deltaTime);
        }
    }
}
