package com.elvircrn.TankTrouble.android;

import java.util.Random;

public class RandomWrapper {
    public static Random generator;

    public static void init() {
        generator = new Random();
    }

    public static void init(long seed) {
        generator.setSeed(seed);
    }

    public static int getRand() {
        return generator.nextInt() * 100000;
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
