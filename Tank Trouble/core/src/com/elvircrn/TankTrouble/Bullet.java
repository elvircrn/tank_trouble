package com.elvircrn.TankTrouble;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Created by elvircrn on 2/14/2015.
 */
public class Bullet implements Poolable {
    //public static properties
    public static Texture texture;
    public static int width, height; //initialized from the manager
    public static float bulletDuration = 8.0f;
    public static float bulletSpeed = 150.0f;

    public int owner;

    //private static properties (e.g. physics helpers)
    private Vector2 approximation;

    //static methods
    public static void init(int bulletWidth, int bulletHeight) {
        width = bulletWidth;
        height = bulletHeight;
    }

    public static void setTexture(Texture texture) {
        Bullet.texture = texture;
    }

    //instance properties
    public Vector2 worldLocation, direction;
    public boolean alive;
    public float tick;
    private Circle collisionCircle;

    //constructors
    public Bullet() {
        worldLocation = new Vector2();
        direction = new Vector2();
        alive = false;
        collisionCircle = new Circle();
        tick = bulletDuration;
    }

    public Bullet(Vector2 worldLocation, Vector2 direction) {
        this.worldLocation = worldLocation;
        this.direction = direction;
        alive = true;
        tick = bulletDuration;
    }

    //instance methods
    public Circle getCollisionCircle() {
        collisionCircle.set(worldLocation.x, worldLocation.y, width / 2);
        return collisionCircle;
    }

    @Override
    public void reset() {
        alive = false;
    }

    public void init(float x, float y, float dirX, float dirY, int owner) {
        this.worldLocation.set(x, y);
        this.direction.set(dirX, dirY);
        this.alive = true;
        this.tick = bulletDuration;
        this.owner = owner;
    }

    public void update(float deltaTime) {
        tick -= deltaTime;

        if (tick < 0.0f) {
            alive = false;
            return;
        }

        int x, y;
        float scaledSpeed = deltaTime * bulletSpeed, keepX = worldLocation.x, keepY = worldLocation.y;
        boolean intersectX = false, intersectY = false;

        /** old collision*/
        for (Wall wall : Level.walls) {
            worldLocation.x += (direction.x * scaledSpeed);

            if (Intersector.overlaps(getCollisionCircle(), wall.getCollisionRectangle()))
                intersectX = true;

            worldLocation.x -= (direction.x * scaledSpeed);
            worldLocation.y += (direction.y * scaledSpeed);

            if (Intersector.overlaps(getCollisionCircle(), wall.getCollisionRectangle()))
                intersectY = true;

            worldLocation.y -= (direction.y * scaledSpeed);

            if (intersectX && intersectY)
                break;
        }

        if (intersectX)
            direction.x *= -1;
        if (intersectY)
            direction.y *= -1;

        worldLocation.add(direction.x * scaledSpeed, direction.y * scaledSpeed);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture, worldLocation.x - (width / 2), worldLocation.y - (height / 2), (float)width, (float)height);
    }
}





























