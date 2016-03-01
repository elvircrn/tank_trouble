package com.elvircrn.ParticleEngine;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Created by elvircrn on 2/29/2016.
 */


public class Particle implements Poolable {
    //static properties
    public static Texture texture;

    //public properties
    public boolean exists;
    public Vector2 worldLocation;

    @Override
    public void reset() {
        exists = false;
    }


}
