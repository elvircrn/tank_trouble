package com.elvircrn.TankTrouble;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Joystick {
    public int index;

    public Analog analog;
    public Button button;

    public Rectangle inputRegion;

    public Joystick() { }
    public Joystick(Analog analog, Button button) {
        this.analog = analog;
        this.button = button;
    }

    public void setInputRegion(Rectangle inputRegion) {
        this.inputRegion = inputRegion;
    }

    public void draw(SpriteBatch batch) {
        analog.draw(batch);
        button.draw(batch);
    }

    public void update(float deltaTime) {
        analog.prevAnalogMoved = analog.analogMoved;
        button.prevButtonPressed = button.buttonPressed;
        boolean foundAnalog = false, foundButton = false;
        for (int i = 0; i < Input.count(); i++) {
            if (inputRegion.contains(Input.get(i))) {
                if (button.getScreenRectangle().contains(Input.get(i))) {
                    foundButton = true;
                }
                else {
                    foundAnalog = true;
                    analog.displaceAnalog(Input.get(i));
                }
            }
        }

        button.buttonPressed = foundButton;
        analog.analogMoved = foundAnalog;

        if (!foundAnalog)
            analog.reset();
    }

    public boolean justMoved() {
        return analog.justMoved();
    }

    public boolean moving() {
        return analog.moving();
    }

    public void setAnalogTexture(Texture texture) {
        analog.setTexture(texture);
    }

    public void setButtonTexture(Texture texture) {
        button.setTexture(texture);
    }

    public void setTexture(Texture analogTexture, Texture buttonTexture) {
        setAnalogTexture(analogTexture);
        setButtonTexture(buttonTexture);
    }
}

