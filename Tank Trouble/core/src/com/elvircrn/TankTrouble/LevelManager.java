package com.elvircrn.TankTrouble;

/**
 * Created by elvircrn on 2/14/2015.
 */
public class LevelManager {

    public static float scale;

    public static void initLevel() {
        scale = TankTrouble.PrefferedWidth / TankTrouble.w;
        int levelWidth = Random.getRange(6, 8), levelHeight = Random.getRange(4, 6);

        int dim;

        if (levelWidth < levelHeight)
            dim = levelHeight;
        else
            dim = levelWidth;

        float d = dim;

        float scale = d / 9.0f;

        Level.generateLevel(levelWidth, levelHeight);
    }
}
