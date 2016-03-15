package com.elvircrn.TankTrouble.android;

public class LevelManager {
    public static float scale;

    public static void initLevel() {
        Level.setCurrentSeed((short)(System.currentTimeMillis() / 1000));
        scale = Graphics.prefferedWidth / Graphics.screenWidth;
        int levelWidth = RandomWrapper.getRand(6, 8), levelHeight = RandomWrapper.getRand(4, 6);
        Level.generateLevel(levelWidth, levelHeight);
    }

    public static void initLevel(short seed) {
        RandomWrapper.init(seed);
        scale = Graphics.prefferedWidth / Graphics.screenWidth;
        int levelWidth = RandomWrapper.getRand(6, 8), levelHeight = RandomWrapper.getRand(4, 6);
        Level.generateLevel(levelWidth, levelHeight);
    }
}
