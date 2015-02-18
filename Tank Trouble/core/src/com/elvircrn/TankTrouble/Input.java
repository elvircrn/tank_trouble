package com.elvircrn.TankTrouble;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created by elvircrn on 2/14/2015.
 */
public class Input {

    public static ArrayList<Vector2> TouchList;

    public static void Init() {
        TouchList = new ArrayList<Vector2>();
    }

    public static void Update() {
        TouchList.clear();

        for (int i = 0; i < 5; i++) {
            if (Gdx.input.isTouched(i)) {
                float locX = (float)Gdx.input.getX(i);
                float locY = (float)Gdx.input.getY(i);

                locY = MyGdxGame.h - locY;

                locX = (locX / MyGdxGame.w) * MyGdxGame.PrefferedWidth;
                locY = (locY / MyGdxGame.h) * MyGdxGame.PrefferedHeight;
                TouchList.add(new Vector2((float)Math.floor ((double)locX), (float)Math.floor((double)locY)));
            }
        }
    }

    public static void Debug(String tag) {
        for (int i = 0; i < TouchList.size(); i++) {
            Vector2 current_element = TouchList.get(i);
            Gdx.app.log(tag, Float.toString(current_element.x) + "  " + Float.toString(current_element.y));
        }
    }
}
