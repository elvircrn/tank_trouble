package com.elvircrn.TankTrouble;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by elvircrn on 2/14/2015.
 */
public class Tank {
    //public static values
    public static float tankSpeed = 100.0f;
    public Texture texture;

    //private static values
    public Rectangle collision;

    //static properties
    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    //instance values
    public int index;
    public int points;

    //collision
    public Vector2 worldLocation;
    public float collisionRadius;
    public float width, height;
    public float rotation;

    protected Vector2 moveDirection;
    protected Circle collisionCircle;

    public Tank() {
        collision = new Rectangle();
        moveDirection = new Vector2();
        collisionCircle = new Circle();
        worldLocation = new Vector2();
    }

    public void init(int index, Vector2 startLocation, float startRotation, float tankWidth, float tankHeight) {
        this.index = index;
        points = 0;
        collisionCircle = new Circle();
        worldLocation = new Vector2(startLocation.x, startLocation.y);
        rotation = startRotation;
        width = tankWidth;
        height = tankHeight;
        initCollisionCircle();
        moveDirection.set(0, 1);
    }

    private void initCollisionCircle() {
        collisionRadius = (float)Math.sqrt((double)(width * width + height * height)) / 2.0f;
        collisionCircle.set(worldLocation.x, worldLocation.y, collisionRadius);
    }

    public Circle getCollisionCircle() {
        collisionCircle.set(worldLocation.x, worldLocation.y, collisionRadius);
        return collisionCircle;
    }

    public void shoot() {
        float r = getCollisionCircle().radius + 5;
        BulletManager.addBullet(worldLocation.x + moveDirection.x * r, worldLocation.y + moveDirection.y * r, moveDirection.x, moveDirection.y);
    }

    protected void moveTo(float deltaTime) {
        float scaledSpeed = tankSpeed * deltaTime;
        boolean intersectX = false, intersectY = false;

        for (Wall wall : Level.walls) {
            worldLocation.x += (moveDirection.x * scaledSpeed);

            if (Intersector.overlaps(getCollisionCircle(), wall.getCollisionRectangle()))
                intersectX = true;

            worldLocation.x -= (moveDirection.x * scaledSpeed);
            worldLocation.y += (moveDirection.y * scaledSpeed);

            if (Intersector.overlaps(getCollisionCircle(), wall.getCollisionRectangle()))
                intersectY = true;

            worldLocation.y -= (moveDirection.y * scaledSpeed);

            if (intersectX && intersectY)
                break;
        }

        if (!intersectX)
            worldLocation.x += moveDirection.x * scaledSpeed;
        if (!intersectY)
            worldLocation.y += moveDirection.y * scaledSpeed;
    }

    public void update(float deltaTime) {
        if (JoystickManager.get(index).moving()) {
            Gdx.app.log("analog", "moving " + Integer.toString(index));
            rotation = JoystickManager.get(index).analog.getNorAngle();
            moveDirection = new Vector2(JoystickManager.get(index).analog.getNorDirection());
            moveTo(deltaTime);
        }
        if (JoystickManager.get(index).button.justPressed())
            shoot();
    }

    public void draw(SpriteBatch batch) {
        batch.draw(texture,
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
                   texture.getWidth(),
                   texture.getHeight(),
                   false,
                   false);
    }

    public void debug() {
        int approxX = Level.approxX(worldLocation.x);
        int approxY = Level.approxY(worldLocation.y);
    }
}
