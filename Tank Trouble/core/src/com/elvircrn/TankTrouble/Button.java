package com.elvircrn.TankTrouble;

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

    public int index;

    //event properties
    public boolean prevButtonPressed, buttonPressed;

    //constructors
    public Button() { screenLocation = new Vector2(); screenRectangle = new Rectangle(); }
    public Button(int index, Vector2 worldLocation, float width, float height) {
        this();
        init(index, worldLocation.x, worldLocation.y, width, height);
    }

    //instance methods
    public void init(int index, float locX, float locY, float width, float height) {
        this.index = index;
        screenLocation.set(locX, locY);
        this.width = width;
        this.height = height;
        buttonPressed = false;
        prevButtonPressed = false;
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

    public void draw(SpriteBatch batch) {
        batch.draw(texture, screenLocation.x - width / 2, screenLocation.y - height / 2, width, height);
    }

    public boolean fingerOnButton(int index) {
        Vector2 location = Input.get(index);
        return getScreenRectangle().contains(location);
    }

    //events
    public boolean justPressed() {
        if (prevButtonPressed == false && buttonPressed == true)
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
