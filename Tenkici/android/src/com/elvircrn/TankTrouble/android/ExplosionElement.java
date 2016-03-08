package com.elvircrn.TankTrouble.android;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by elvircrn on 3/1/2016.
 */
public class ExplosionElement {
    Vector2 direction;
    float speed;

    public Vector2 worldLocation;
    protected float collisionRadius;
    public float width, height;
    public float rotation;
    public Rectangle collision;
    protected Vector2 moveDirection;
    protected Circle collisionCircle;


    public ExplosionElement() {

    }
    public ExplosionElement(float locX, float locY, float dirX, float dirY, float speed, int width, int height) {
        this.worldLocation = new Vector2(locX, locY);
        this.direction = new Vector2(dirX, dirY);
        this.speed = speed;
        this.width = width;
        this.height = height;
    }

    void init(float dirX, float dirY, float speed, int width, int height) {
        this.direction.set(dirX, dirY);
        this.speed = speed;
        this.width = width;
        this.height = height;
    }

    public void update(float deltaTime) {

    }

    public void draw(SpriteBatch batch) {
        batch.draw(ExplosionManager.texture,
                collisionCircle.x - width / 2,
                collisionCircle.y - width / 2,
                width / 2,
                width / 2,
                width,
                width,
                1.0f,
                1.0f,
                rotation,
                0,
                0,
                ExplosionManager.texture.getWidth(),
                ExplosionManager.texture.getHeight(),
                false,
                false);
    }
}
