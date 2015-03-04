package com.elvircrn.TankTrouble;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by elvircrn on 2/17/2015.
 */
public class Wall {
    public int x, y, width, height, wallType;
    private Rectangle collisionRectangle;

    public Wall() { }
    public Wall(int x, int y, int width, int height, int wallType) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.wallType = wallType;
        this.collisionRectangle = new Rectangle(x, y, width, height);
    }

    public Rectangle getCollisionRectangle() { return collisionRectangle; }
}