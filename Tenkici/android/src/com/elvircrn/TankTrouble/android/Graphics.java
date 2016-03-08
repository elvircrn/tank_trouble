package com.elvircrn.TankTrouble.android;

import com.badlogic.gdx.Gdx;

/**
 * Created by elvircrn on 3/1/2016.
 */
public class Graphics {
    public static float screenWidth, screenHeight;
    public static float prefferedWidth, prefferedHeight;

    public static void initValues() {
        Graphics.prefferedWidth = 800.0f;
        Graphics.screenWidth = Gdx.graphics.getWidth();
        Graphics.screenHeight = Gdx.graphics.getHeight();
        Graphics.prefferedHeight = (Graphics.screenHeight / Graphics.screenWidth) * Graphics.prefferedWidth;
    }

    public static float scaleWidth(float dim) {
        return (prefferedWidth / screenWidth) * dim;
    }

    public static float scaleHeight(float dim) {
        return (prefferedHeight / screenHeight) * dim;
    }
}
