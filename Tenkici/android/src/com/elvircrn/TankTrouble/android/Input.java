package com.elvircrn.TankTrouble.android;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by elvircrn on 2/14/2015.
 */
public class Input {
    private static int touchCount;
    public static Vector2[] touchList;

    public static void create() {
        touchList = new Vector2[7];
        for (int i = 0; i < 7; i++)
            touchList[i] = new Vector2();
        touchCount = 0;
    }

    public static int count() {
        return touchCount;
    }

    public static void update() {
        touchCount = 0;

        for (int i = 0; i < 7; i++) {
            if (Gdx.input.isTouched(i)) {
                float locX = (float)Gdx.input.getX(i);
                float locY = (float)Gdx.input.getY(i);

                locY = Graphics.screenHeight - locY;

                locX = (locX / Graphics.screenWidth) * Graphics.prefferedWidth;
                locY = (locY / Graphics.screenHeight) * Graphics.prefferedHeight;

                touchList[touchCount].set((float) Math.floor((double) locX), (float) Math.floor((double) locY));
                touchCount++;
            }
        }
    }

    public static Vector2 get(int index) {
        return touchList[index];
    }
}
