package com.elvircrn.TankTrouble;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by elvircrn on 2/14/2015.
 */
public class LevelManager {

    public static void initGame() {

        int levelWidth = Random.getRange(5, 8), levelHeight = Random.getRange(5, 8);

        int dim;

        if (levelWidth < levelHeight)
            dim = levelHeight;
        else
            dim = levelWidth;

        float d = dim;

        float scale = d / 9.0f;

        MyGdxGame.tankOne.init((int)(scale * 32.0f),
                                     (int)(scale * 32.0f));
        MyGdxGame.tankTwo.init((int)(scale * 32.0f),
                                     (int)(scale * 32.0f));

        MyGdxGame.tankOne.index = 1;
        MyGdxGame.tankTwo.index = 2;

        Level.generateLevel(levelWidth, levelHeight);

        MyGdxGame.tankOne.WorldLocation = Level.tileToScreen(0, 0).add(new Vector2(Level.wallWidth, Level.wallWidth));
        MyGdxGame.tankTwo.WorldLocation = Level.tileToScreen(Level.width - 1, Level.height - 1).add(new Vector2(Level.wallWidth, Level.wallWidth));
    }

}
