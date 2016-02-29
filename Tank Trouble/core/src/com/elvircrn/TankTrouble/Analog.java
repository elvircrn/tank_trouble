package com.elvircrn.TankTrouble;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by elvircrn on 3/4/2015.
 */
public class Analog {
    public static Vector2 calc;

    public Texture texture;
    public float stickWidth, stickHeight, largeWidth, largeHeight, moveRadius;
    public Vector2 analogLocation;
    public Vector2 center;

    protected Vector2 norDirection;
    protected Rectangle screenRectangle;
    protected float norAngle;
    protected boolean prevAnalogMoved, analogMoved;

    public static void create() {
        calc = new Vector2();
    }

    public Analog() {
        norDirection = new Vector2();
        analogLocation = new Vector2();
        screenRectangle = new Rectangle();
        center = new Vector2();
    }
    public Analog(float locX, float locY, float stickWidth, float stickHeight, float moveRadius) {
        this.norAngle = 0;
        this.prevAnalogMoved = false;
        this.analogMoved = false;
        this.stickWidth = stickWidth;
        this.stickHeight = stickHeight;
        this.moveRadius = moveRadius;
        this.largeWidth = 2 * stickWidth;
        this.largeHeight = 2 * stickHeight;

        this.center = new Vector2(locX, locY);
        this.analogLocation = new Vector2(locX, locY);
        this.norDirection = new Vector2();
        this.screenRectangle = new Rectangle();
    }

    public void init(float locX, float locY, float stickWidth, float stickHeight, float moveRadius) {
        norAngle = 0;
        prevAnalogMoved = false;
        analogMoved = false;
        this.stickWidth = stickWidth;
        this.stickHeight = stickHeight;
        this.moveRadius = moveRadius;
        largeWidth = 2 * stickWidth;
        largeHeight = 2 * stickHeight;
        center.set(locX, locY);
        analogLocation.set(locX, locY);
    }

    public Rectangle getScreenRectangle() {
        screenRectangle.set(analogLocation.x - stickWidth / 2, analogLocation.y - stickHeight / 2, stickWidth, stickHeight);
        return screenRectangle;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    //C + X = L   X = L - C
    public void displaceAnalog(Vector2 where) {
        calc.set(where.x - center.x, where.y - center.y);
        float distSquared = calc.x * calc.x + calc.y * calc.y;

        norAngle = calc.angle();

        //OK
        if (moveRadius * moveRadius > distSquared) {
            analogLocation.set(where.x, where.y);
        }
        else {
            calc.nor().scl(moveRadius);
            analogLocation.set(center.x + calc.x, center.y + calc.y);
        }

        norDirection.set(calc.nor().x, calc.nor().y);
    }

    public void reset() {
        norAngle = 0.0f;
        analogLocation.set(center.x, center.y);
    }

    public Vector2 getNorDirection() {
        return norDirection;
    }

    public float getNorAngle() {
        return norAngle;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, analogLocation.x - stickWidth / 2, analogLocation.y - stickHeight / 2, stickWidth, stickHeight);
        batch.draw(texture, center.x - largeWidth / 2, center.y - largeHeight / 2, largeWidth, largeHeight);
    }

    public boolean justMoved() {
        return !prevAnalogMoved && analogMoved;
    }

    public boolean moving() {
        return analogMoved;
    }
}
