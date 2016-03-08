package com.elvircrn.TankTrouble.android;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class SimpleText {
    private Vector2 location;
    private String text;

    public SimpleText() { }
    public SimpleText(Vector2 location, String text) {
        this.location = location;
        this.text = text;
    }

    public void setLocation(Vector2 location) {
        this.location = location;
    }

    public Vector2 getLocation() {
        return location;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public void set(Vector2 location, String text) {
        setLocation(location);
        setText(text);
    }

    public void draw(SpriteBatch batch) {
        Tenkici.font16.draw(batch,
                            text,
                            location.x,
                            location.y);
    }
}
