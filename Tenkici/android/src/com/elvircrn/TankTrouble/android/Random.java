package com.elvircrn.TankTrouble.android;

/**
 * Created by elvircrn on 2/18/2015.
 */
public class Random {

    public static int getRand() {
        return (int)(Math.random() * 100000.0f);
    }

    public static int getRand(int maximum) {
        if (maximum == 0)
            return 0;
        else
            return getRand() % maximum;
    }

    public static int getRange(int minimum, int maximum) {
        return minimum + getRand(maximum - minimum);
    }
}
