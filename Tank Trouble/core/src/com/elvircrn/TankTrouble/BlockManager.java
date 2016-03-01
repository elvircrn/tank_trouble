package com.elvircrn.TankTrouble;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by elvircrn on 4/24/2015.
 */
public class BlockManager {
    public static int wallWidth = 8;
    public static int paddingX, paddingY;
    public static int mapWidth, mapHeight;
    public static int blockHeight, blockWidth;
    public static Block[][] blocks;

    public static void create(int width, int height) {
        blocks = new Block[width] [height];
        init(width, height);
    }

    public static void init(int width, int height) {
        mapWidth  = width;
        mapHeight = height;

        int mapPixWidth  = width  * (blockWidth + wallWidth) + wallWidth;
        int mapPixHeight = height * (blockHeight + wallWidth) + wallWidth;

        paddingX = ((int)TankTrouble.PrefferedWidth - mapPixWidth) / 2;
        paddingY = ((int)TankTrouble.PrefferedHeight - mapPixHeight) / 2;
    }

    public static void draw(SpriteBatch batch) {

    }

}