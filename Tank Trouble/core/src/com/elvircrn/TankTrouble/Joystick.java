package com.elvircrn.TankTrouble;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by elvircrn on 2/14/2015.
 */
public class Joystick {

    public int index;

    public static Texture joyTexture;

    //analog
    public Vector2 joyCenter, joyLocation;
    public Texture texture;
    public float analogRadius;
    private boolean analogMoved = false;
    public float joyWidth, joyHeight;

    //fire button
    public Vector2 ButtonLocation;
    public Vector2 lastDirection;
    private boolean buttonPressed = false;
    private boolean prevButtonPressed = false;
    public Rectangle ButtonRectangle;

    public Joystick(int index) {
        this.index = index;

        texture = joyTexture;

        joyWidth  = 80.0f;
        joyHeight = 80.0f;

        analogRadius = 80.0f;

        joyCenter = new Vector2(MyGdxGame.w / 15.0f + 30.0f, MyGdxGame.w / 15.0f + 30.0f);

        if (index == 2)
            joyCenter = new Vector2(MyGdxGame.PrefferedWidth - joyCenter.x, MyGdxGame.PrefferedHeight - joyCenter.y);

        this.ButtonLocation = new Vector2(joyCenter.x, MyGdxGame.PrefferedHeight - joyCenter.y);
        joyLocation = joyCenter;
        ButtonRectangle = new Rectangle(ButtonLocation.x - joyWidth / 2, ButtonLocation.y - joyHeight / 2, joyWidth, joyHeight);
        lastDirection = new Vector2(0, 1);
    }

    public void Draw(Batch batch) {
        float largeWidth = joyWidth * 2, largeHeight = joyHeight * 2;

        batch.draw(joyTexture, joyCenter.x - largeWidth / 2, joyCenter.y - largeHeight / 2, joyWidth * 2, joyHeight * 2);
        batch.draw(joyTexture, joyLocation.x - joyWidth / 2, joyLocation.y - joyHeight / 2, joyWidth, joyHeight);

        batch.draw(joyTexture, ButtonLocation.x - joyWidth / 2, ButtonLocation.y - joyWidth / 2, joyWidth, joyHeight);
    }

    public float getMaxDist() {
        return joyCenter.x / 2.0f;
    }

    public void UpdateLocation(Vector2 touchLocation) {
        float distanceSquared = (joyCenter.x - touchLocation.x) * (joyCenter.x - touchLocation.x) + (joyCenter.y - touchLocation.y) * (joyCenter.y - touchLocation.y);

        if (distanceSquared < (analogRadius * analogRadius) + 0.1f)
            joyLocation = touchLocation;
        else {
            Vector2 jc = new Vector2(joyCenter.x, joyCenter.y);
            joyLocation = jc.add((new Vector2(touchLocation.x - joyCenter.x, touchLocation.y - joyCenter.y).nor()).scl(analogRadius));
        }
    }



    public Vector2 GetNorDirection() {
        if (Math.abs(joyCenter.x - joyLocation.x) > 0.1f || Math.abs(joyCenter.y - joyLocation.y) > 0.1f) {
            lastDirection.x = (-joyCenter.x + joyLocation.x);
            lastDirection.y = (-joyCenter.y + joyLocation.y);
        }

        lastDirection.nor();

        return lastDirection;
    }

    public void Update(float deltaTime) {
        prevButtonPressed = buttonPressed;
        analogMoved = false;
        buttonPressed = false;

        Vector2 touchPoint;

        for (int i = 0; i < Input.TouchList.size(); i++) {
            touchPoint = Input.TouchList.get(i);

            if (index == 2 && touchPoint.x < (MyGdxGame.PrefferedWidth / 2) ||
                index == 1 && (MyGdxGame.PrefferedWidth / 2) < touchPoint.x)
                continue;

            if (ButtonRectangle.contains(touchPoint)) {
                buttonPressed = true;
                continue;
            }

            if (!analogMoved) {
                UpdateLocation(touchPoint);
                analogMoved = true;
            }
        }

        if (!analogMoved) {
            joyLocation.x = joyCenter.x;
            joyLocation.y = joyCenter.y;
        }
    }

    public boolean buttonPressed() {
        return buttonPressed;
    }

    public boolean prevButtonPressed() {
        return prevButtonPressed;
    }

    public boolean analogMoved() { return analogMoved; }

    public boolean justPressed() { return !prevButtonPressed && buttonPressed; }
}


//10001011010000
/*
10001011010000
100010110100
1000101101
10001011
100010
1000
10
 */