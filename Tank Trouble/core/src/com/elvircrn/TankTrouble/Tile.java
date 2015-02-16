package com.elvircrn.TankTrouble;

/**
 * Created by elvircrn on 2/16/2015.
 */
public class Tile {
    public Bitmask walls;

    public Tile() { walls = new Bitmask(); }
    public Tile(int mask) { walls = new Bitmask(mask); }

    public boolean wallAt(int direction) {
        return walls.bit_at(direction);
    }
}
