package com.elvircrn.TankTrouble.android;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by elvircrn on 3/4/2015.
 */
public class FPSCounter {

    public static float       timeLeft;
    public static Vector2     location;
    public static int     frameDisplay;

    private static int frameCount;

    public static void create() {
        location = new Vector2(0, Tenkici.PrefferedHeight);
        frameCount = 0;
        timeLeft = 1.0f;
    }

    public static void init() {

    }

    public static void update(float deltaTime) {
        timeLeft -= deltaTime;
        if (timeLeft < 0.0f) {
            frameDisplay = frameCount;
            frameCount = 0;
            timeLeft = 1.0f;
        }
        frameCount++;
    }

    public static void draw(SpriteBatch batch) {
        Tenkici.font16.draw(batch, Integer.toString(frameDisplay), location.x, location.y);
    }
}
