package com.elvircrn.TankTrouble.android;

import com.badlogic.gdx.Gdx;

public class Graphics {
    public static float screenWidth, screenHeight;
    public static float prefferedWidth, prefferedHeight;

    private static float hRatio;

    public static void initValues() {
        Graphics.prefferedWidth = 1024.0f;
        Graphics.screenWidth = Gdx.graphics.getWidth();
        Graphics.screenHeight = Gdx.graphics.getHeight();
        Graphics.prefferedHeight = 600.0f;
        //Graphics.prefferedHeight = (Graphics.screenHeight / Graphics.screenWidth) * Graphics.prefferedWidth;
        hRatio = prefferedWidth / prefferedHeight;
    }

    public static float scaleWidth(float dim) {
        return (prefferedWidth / screenWidth) * dim;
    }

    public static float scaleHeight(float dim) {
        return (prefferedHeight / screenHeight) * dim;
    }

    public static float heightRatio() {
        return 1.0f;
    }
}
