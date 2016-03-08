package com.elvircrn.TankTrouble.android;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Vedo on 05.03.2016..
 */
public class ButtonManager {
    public static Texture[] textures;

    public static void load(int textureCount) {
        textures = new Texture[textureCount];
        textures [0] = Tenkici.manager.get  ("data/wall.png", Texture.class);
    }
}
