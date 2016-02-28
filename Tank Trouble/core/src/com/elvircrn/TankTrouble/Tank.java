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

    public Tank() { collision = new Rectangle(); moveDirection = new Vector2(); collisionCircle = new Circle(); worldLocation = new Vector2(); }
    public Tank(int index, Vector2 startLocation, float startRotation, float tankWidth, float tankHeight) {
        this();
        init(index, startLocation, startRotation, tankWidth, tankHeight);
    }

    public void init(int index, Vector2 startLocation, float startRotation, float tankWidth, float tankHeight) {
        this.index = index;
        points = 0;
        collisionCircle = new Circle();
        worldLocation = new Vector2();
        worldLocation = startLocation;
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

    public void update(float deltaTime) {
        if (JoystickManager.get(index).moving()) {
            rotation = JoystickManager.get(index).analog.getNorAngle();

            float scaledSpeed = tankSpeed * deltaTime;

            moveDirection.set(JoystickManager.get(index).analog.getNorDirection());

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


            /** new collision*/
            /*int approxX = Level.approxX(worldLocation.x);
            int approxY = Level.approxY(worldLocation.y);

            for (int i = 0; i < 4; i++) {
                Level.getWallRectangle(collision, approxX, approxY, i);

                worldLocation.x += (moveDirection.x * scaledSpeed);

                if (Intersector.overlaps(getCollisionCircle(), collision))
                    intersectX = true;

                worldLocation.x -= (moveDirection.x * scaledSpeed);
                worldLocation.y += (moveDirection.y * scaledSpeed);

                if (Intersector.overlaps(getCollisionCircle(), collision))
                    intersectY = true;

                worldLocation.y -= (moveDirection.y * scaledSpeed);

                if (intersectX && intersectY)
                    break;
            }

            if (!intersectX)
                worldLocation.x += moveDirection.x * scaledSpeed;
            if (!intersectY)
                worldLocation.y += moveDirection.y * scaledSpeed;*/
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

        Gdx.app.log("approximated location: ", Integer.toString(approxX) + " " + Integer.toString(approxY));
    }
}
