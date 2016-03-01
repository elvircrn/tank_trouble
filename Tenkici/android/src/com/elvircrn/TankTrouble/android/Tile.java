package com.elvircrn.TankTrouble.android;

/**
 * Created by elvircrn on 2/16/2015.
 */
public class Tile {
    public com.elvircrn.TankTrouble.android.Bitmask walls;

    public Tile() { walls = new com.elvircrn.TankTrouble.android.Bitmask(); }
    public Tile(int mask) { walls = new com.elvircrn.TankTrouble.android.Bitmask(mask); }

    public boolean wallAt(int direction) {
        return walls.bit_at(direction);
    }
}
