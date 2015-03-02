package com.elvircrn.TankTrouble;

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

        for (int i = 0; i < MyGdxGame.tankOne.points; i++) {
            batch.draw(MyGdxGame.tankOne.texture, location.x, location.y, width, height);
            location.y += height + padding;
        }

        location.x = MyGdxGame.PrefferedWidth - (padding + width);
        location.y = MyGdxGame.PrefferedHeight - (padding + height);

        for (int i = 0; i < MyGdxGame.tankTwo.points; i++) {
            batch.draw(MyGdxGame.tankTwo.texture, location.x, location.y, width, height);
            location.y -= (height + padding);
        }
    }
}
