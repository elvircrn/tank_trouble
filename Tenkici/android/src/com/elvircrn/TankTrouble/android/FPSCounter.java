package com.elvircrn.TankTrouble.android;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class FPSCounter {
    public static int     frameDisplay;
    public static float   timeLeft;
    public static Vector2 location;

    public static String extraMessage = "";

    private static int frameCount;

    public static void create() {
        location = new Vector2(0, Graphics.prefferedHeight);
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
        //Tenkici.font16.draw(batch, extraMessage, location.x, location.y);
        /*String text = Integer.toString(frameDisplay) + " " + extraMessage + "\n"
                + "RED: " + Float.toString(TankManager.tanks [0].worldLocation.x) + " "
                + Float.toString(TankManager.tanks [0].worldLocation.y) + "   BLUE: " +
                Float.toString(TankManager.tanks [1].worldLocation.x) + " "
                + Float.toString(TankManager.tanks [1].worldLocation.y);
        Tenkici.font16.draw(batch, text, location.x, location.y);*/
    }
}


