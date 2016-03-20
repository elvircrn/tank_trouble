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

    public boolean menuButton;

    //instance properties
    public Vector2 screenLocation; //set at center
    public float width, height;
    private Rectangle screenRectangle;
    public SimpleText text = null;
    public int textureIndex;
    public int index;

    //event properties
    public boolean prevButtonPressed, buttonPressed;

    //constructors
    public Button() {
        menuButton = false;
        textureIndex = -1;
        screenLocation = new Vector2();
        screenRectangle = new Rectangle();
    }

    public Button(int index, float locX, float locY, float width, float height) {
        menuButton = false;
        textureIndex = -1;
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

    public Button(int index, boolean isMenu, float locX, float locY, float width, float height, int textureIndex) {
        this.menuButton = isMenu;
        this.textureIndex = textureIndex;
        screenRectangle = new Rectangle();
        this.index = index;
        screenLocation = new Vector2(locX, locY);
        this.width = width;
        this.height = height;
        buttonPressed = false;
        prevButtonPressed = false;
    }

    //instance methods
    public void init(int index, float locX, float locY, float width, float height) {
        this.index = index;
        screenLocation.set(locX, locY);
        this.width = width;
        this.height = height;
        buttonPressed = false;
        prevButtonPressed = false;
        text.set(text.getLocation(), text.getText());
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
        Button.texture = texture;
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
        for (int i = 0; i < Input.count(); i++)
            buttonPressed |= getScreenRectangle().contains(Input.get(i));

        return buttonPressed;
    }


    public void draw(SpriteBatch batch) {
        if (textureIndex == -1)
            batch.draw(texture, screenLocation.x - width / 2, screenLocation.y - height / 2, width, height);
        else
            batch.draw(ButtonManager.textures[textureIndex], screenLocation.x - width / 2, screenLocation.y - height / 2, width, height);
    }

    public boolean fingerOnButton(int index) {
        Vector2 location = com.elvircrn.TankTrouble.android.Input.get(index);
        return getScreenRectangle().contains(location);
    }

    //events
    public boolean justPressed() {
        if (!menuButton) {
            if (!prevButtonPressed && buttonPressed)
                return true;
            else
                return false;
        }
        else {
            if (!Input.isPrevPressed() && buttonPressed)
                return true;
            else
                return false;
        }
    }

    public boolean isDown() {
        return buttonPressed;
    }

    public boolean justRaised() {
        return !buttonPressed && prevButtonPressed;
    }
}
