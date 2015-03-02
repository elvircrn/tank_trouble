package com.elvircrn.TankTrouble;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by elvircrn on 2/14/2015.
 */
public class Bullet {

    public static Texture texture;

    public short Source;

    public static final float bulletExpiration = 8.0f;
    public static final float defaultMeasure = 8.0f;

    public static int bulletWidth;

    public float expiration;

    public Vector2 worldLocation;
    public Vector2 direction;

    public static float bulletSpeed = 250.0f;
    public Vector2 ForceAccum, Direction;

    public int notHit;

    public Bullet() { }
    public Bullet(Vector2 location, Vector2 direction) {
        worldLocation = new Vector2 (location.x, location.y);
        this.direction = new Vector2 (direction.x, direction.y);
        expiration = bulletExpiration;
        notHit = 0;
    }

    public Rectangle getWorldRectangle() {
        return new Rectangle(worldLocation.x - bulletWidth / 2, worldLocation.y - bulletWidth / 2, bulletWidth, bulletWidth);
    }

    public static void initScale(float scale) {
        bulletWidth = (int)(scale * defaultMeasure);

        if (bulletWidth % 2 == 1)
            bulletWidth++;
    }

    public  boolean Update(float deltaTime) {

        expiration -= deltaTime;

        if (expiration < 0.0f)
            return false;

        float scaledSpeed = bulletSpeed * deltaTime;

        //VERTICAL

        worldLocation.add(direction.x * scaledSpeed, 0);

        int newDirection = getNewDirection();

        if (newDirection != -1) {
            worldLocation.sub(direction.x * scaledSpeed, 0);
            direction.x *= -1;
            worldLocation.add(direction.x * scaledSpeed, direction.y * scaledSpeed);
            return true;
        }

        //HORZIONTAL

        worldLocation.sub(direction.x, 0);
        worldLocation.add(0, direction.y * scaledSpeed);

        newDirection = getNewDirection();

        if (newDirection != -1) {
            worldLocation.sub(0, direction.y * scaledSpeed);
            direction.y *= -1;
            worldLocation.add(direction.x * scaledSpeed, direction.y * scaledSpeed);
        }

        Circle oneCollision = MyGdxGame.tankOne.getCollisionCircle();
        Circle twoCollision = MyGdxGame.tankTwo.getCollisionCircle();

        if (Intersector.overlaps(oneCollision, getWorldRectangle())) {
            MyGdxGame.tankOne.dead = true;
            MyGdxGame.tankTwo.points++;
        }

        if (Intersector.overlaps(twoCollision, getWorldRectangle())) {
            MyGdxGame.tankTwo.dead = true;
            MyGdxGame.tankOne.points++;
        }

        return true;
    }

    protected int getNewDirection() {
        Rectangle bulletRectangle = getWorldRectangle();

        for (int i = 0; i < Level.walls.size(); i++) {
            if (Intersector.overlaps(Level.walls.get(i).getCollisionRectangle(), bulletRectangle)) {
                return Level.walls.get(i).wallType;
            }
        }

        return -1;
    }
}





























