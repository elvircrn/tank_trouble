package com.elvircrn.TankTrouble.android;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by elvircrn on 3/4/2015.
 */
public class Button {
    //static values
    public static Texture texture;

    //instance properties
    public Vector2 screenLocation; //set at center
    public float width, height;
    private Rectangle screenRectangle;
    public SimpleText text = null;

    public int index;

    //event properties
    public boolean prevButtonPressed, buttonPressed;

    //constructors
    public Button() {
        screenLocation = new Vector2();
        screenRectangle = new Rectangle();
    }

    public Button(int index, float locX, float locY, float width, float height) {
        screenRectangle = new Rectangle();
        this.index = index;
        screenLocation = new Vector2(locX, locY);
        this.width = width;
        this.height = height;
        buttonPressed = false;
        prevButtonPressed = false;
    }

    public Button(int index, float locX, float locY, float width, float height, SimpleText text) {
        screenRectangle = new Rectangle();
        this.index = index;
        screenLocation = new Vector2(locX, locY);
        this.width = width;
        this.height = height;
        buttonPressed = false;
        prevButtonPressed = false;
        this.text = text;
    }

    public Button(int index, float locX, float locY, float width, float height, String text) {
        screenRectangle = new Rectangle();
        this.index = index;
        screenLocation = new Vector2(locX, locY);
        this.width = width;
        this.height = height;
        buttonPressed = false;
        prevButtonPressed = false;
        this.text = new SimpleText (screenLocation, text);
    }


    //instance methods
    public void init(int index, float locX, float locY, float width, float height, SimpleText text) {
        this.text = text;
        this.index = index;
        screenLocation.set(locX, locY);
        this.width = width;
        this.height = height;
        buttonPressed = false;
        prevButtonPressed = false;
        text.set(text.getLocation(), text.getText());
    }

    public void init(int index, float locX, float locY, float width, float height) {
        this.index = index;
        screenLocation.set(locX, locY);
        this.width = width;
        this.height = height;
        buttonPressed = false;
        prevButtonPressed = false;
    }

    public void init(int index, float locX, float locY, float width, float height, Vector2 location, String text) {
        this.index = index;
        screenLocation.set(locX, locY);
        this.width = width;
        this.height = height;
        buttonPressed = false;
        prevButtonPressed = false;
        this.text.set(location, text);
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Rectangle getScreenRectangle() {
        screenRectangle.set(screenLocation.x - width / 2, screenLocation.y - height / 2, width, height);
        return screenRectangle;
    }

    public boolean update(Vector2 touchLocation) {
        prevButtonPressed = buttonPressed;
        buttonPressed = getScreenRectangle().contains(touchLocation);
        return buttonPressed;
    }

    public boolean update(Vector2[] locations) {
        prevButtonPressed = buttonPressed;
        buttonPressed = false;
        for (Vector2 loc : locations)
            buttonPressed |= getScreenRectangle().contains(loc);

        return buttonPressed;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, screenLocation.x - width / 2, screenLocation.y - height / 2, width, height);

        if (text != null)
            text.draw(batch);
    }

    public boolean fingerOnButton(int index) {
        Vector2 location = Input.get(index);
        return getScreenRectangle().contains(location);
    }

    //events
    public boolean justPressed() {
        if (!prevButtonPressed && buttonPressed)
            return true;
        else
            return false;
    }

    public boolean isDown() {
        return buttonPressed;
    }

    public boolean justRaised() {
        return !buttonPressed && prevButtonPressed;
    }
}
