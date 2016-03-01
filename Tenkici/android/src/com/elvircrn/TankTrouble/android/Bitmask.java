package com.elvircrn.TankTrouble.android;

/**
 * Created by elvircrn on 2/16/2015.
 */
public class Bitmask {
    public int mask;

    public Bitmask() {
        mask = 0;
    }

    public Bitmask(int mask) {
        this.mask = mask;
    }

    void turn_on(int index) {
        if ((mask & (1<<index)) == 0)
            mask += (1<<index);
    }

    void turn_off(int index) {
        if ((mask & (1<<index)) > 0)
            mask -= (1<<index);
    }

    boolean bit_at(int index) {
        if ((mask & (1<<index)) == 0)
            return false;
        else
            return true;
    }
}
