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

    public static float fontRed_locX, fontRed_locY;
    public static float fontBlue_locX, fontBlue_locY;

    public static void create() {
        location = new Vector2(0, Graphics.prefferedHeight);
        frameCount = 0;
        timeLeft = 1.0f;

        fontRed_locX = Graphics.prefferedWidth/2-75;
        fontRed_locY = Graphics.prefferedHeight/2;

        fontBlue_locX = Graphics.prefferedWidth/2+75;
        fontBlue_locY = Graphics.prefferedHeight/2;

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
        Tenkici.font_red.draw(batch, Integer.toString(TankManager.get(0).points), fontRed_locX, fontRed_locY);
        Tenkici.font_blue.draw(batch, Integer.toString(TankManager.get(1).points), fontBlue_locX, fontBlue_locY);

    }
}
