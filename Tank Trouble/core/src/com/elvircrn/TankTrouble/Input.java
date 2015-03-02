package com.elvircrn.TankTrouble;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created by elvircrn on 2/14/2015.
 */
public class Input {

    public static ArrayList<Vector2> TouchList;

    public static void InitTouch() {
        TouchList = new ArrayList<Vector2>();
    }

    public static void InitJoystick() {
        MyGdxGame.joystickOne = new Joystick(1);
        MyGdxGame.joystickTwo = new Joystick(2);
    }

    public static void Update() {
        TouchList.clear();

        for (int i = 0; i < 7; i++) {
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

    public static void Debug() {
        if (MyGdxGame.joystickOne.justPressed())
            Gdx.app.log("joystickOne:", "just pressed");
        else if (MyGdxGame.joystickTwo.justPressed())
            Gdx.app.log("joystickTwo:", "just pressed");
    }
}
