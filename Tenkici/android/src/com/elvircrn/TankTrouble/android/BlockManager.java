package com.elvircrn.TankTrouble.android;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by elvircrn on 4/24/2015.
 */
public class BlockManager {
    private static final float realWallWidth = 8.0f;
    public static float wallWidth = 8;
    public static int paddingX, paddingY;
    public static int mapWidth, mapHeight;
    public static int blockHeight, blockWidth;
    public static Block[][] blocks;

    public static void create(int width, int height) {
        blocks = new Block[width] [height];
        init(width, height);
    }

    public static void init(int width, int height) {
        wallWidth = Graphics.scaleWidth(realWallWidth);

        mapWidth  = width;
        mapHeight = height;

        float mapPixWidth  = width  * (blockWidth + wallWidth) + wallWidth;
        float mapPixHeight = height * (blockHeight + wallWidth) + wallWidth;

        paddingX = (int)((Graphics.prefferedWidth - mapPixWidth) / 2.0f);
        paddingY = (int)((Graphics.prefferedHeight - mapPixHeight) / 2.0f);
    }

    public static void draw(SpriteBatch batch) {

    }

}
