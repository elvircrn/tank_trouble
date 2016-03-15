package com.elvircrn.TankTrouble.android;

import java.util.Random;

public class RandomWrapper {
    public static Random generator = null;

    public static void init() {
        if (generator == null)
            generator = new Random();
    }

    public static void init(short seed) {
        if (generator == null)
            init();
        generator.setSeed(seed);
    }

    public static int getRand() {
        return generator.nextInt();
    }

    public static int getRand(int maximum) {
        return generator.nextInt(maximum);
    }

    public static int getRand(int minimum, int maximum) {
        return minimum + getRand(maximum - minimum + 1);
    }
}
