package com.elvircrn.TankTrouble;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by elvircrn on 2/17/2015.
 */
public class Wall {
    public int x, y, width, height;
    public int wallType;

    public Wall() { }
    public Wall(int x, int y, int width, int height, int wallType) { this.x = x; this.y = y; this.width = width; this.height = height; this.wallType = wallType; }

    public Rectangle getCollisionRectangle() {
        return new Rectangle(x, y, width, height);
    }
}