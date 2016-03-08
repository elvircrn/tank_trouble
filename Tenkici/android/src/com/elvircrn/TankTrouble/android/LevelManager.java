package com.elvircrn.TankTrouble.android;

/**
 * Created by elvircrn on 2/14/2015.
 */
public class LevelManager {
    public static float scale;

    public static void initLevel() {
        scale = Graphics.prefferedWidth / Graphics.screenWidth;
        //int levelWidth = Random.getRange(10, 10), levelHeight = Random.getRange(10, 10);
        int levelWidth = Random.getRange(6, 8), levelHeight = Random.getRange(4, 6);
        /*
        int dim;
        if (levelWidth < levelHeight)
            dim = levelHeight;
        else
            dim = levelWidth;

        float scale = (float)dim / 9.0f;
        */
        Level.generateLevel(levelWidth, levelHeight);
    }
}
