package com.elvircrn.TankTrouble.android;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class TextManager {
    public static ArrayList<SimpleText> text;

    public static void init() {
        text = new ArrayList<>();
    }

    public static void add(SimpleText text) {
        TextManager.text.add(text);
    }

    public static void draw(SpriteBatch batch) {
        for (SimpleText simpleText : text)
            simpleText.draw(batch);
    }
}
