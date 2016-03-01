package com.elvircrn.TankTrouble.android;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by elvircrn on 3/2/2015.
 */
public class PointsUI {
    public static int width = 16;
    public static int height = 16;
    public static int padding = 5;

    private static Vector2 location;

    public static void init() {
        location = new Vector2();
    }

    public static void draw(SpriteBatch batch) {
        location = new Vector2(padding, padding);

        /*for (int i = 0; i < Tenkici.tankOne.points; i++) {
            batch.draw(Tenkici.tankOne.texture, location.x, location.y, width, height);
            location.y += height + padding;
        }

        location.x = Tenkici.PrefferedWidth - (padding + width);
        location.y = Tenkici.PrefferedHeight - (padding + height);

        for (int i = 0; i < Tenkici.tankTwo.points; i++) {
            batch.draw(Tenkici.tankTwo.texture, location.x, location.y, width, height);
            location.y -= (height + padding);
        }*/
    }
}
