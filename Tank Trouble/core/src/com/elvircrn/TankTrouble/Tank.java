package com.elvircrn.TankTrouble;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by elvircrn on 2/14/2015.
 */
public class Tank extends GameObject {

    public int points = 0;

    public boolean dead;

    float Rotation = 0.0f;
    float Speed = 190.0f;

    public int width, height;

    public int index;

    private Circle collisionCircle;

    public Tank() { }
    public Tank(int width, int height) { this.width = width; this.height = height; }

    void init(int width, int height) {
        dead = false;
        this.width = width;
        this.height = height;
        collisionCircle = new Circle();
    }

    public void Shoot() {
        Gdx.app.log("tank shoot", "Shoot()");

        if (index == 1)
            BulletManager.addBullet(new Bullet(new Vector2(collisionCircle.x + MyGdxGame.joystickOne.lastDirection.x * (collisionCircle.radius + 2), collisionCircle.y + MyGdxGame.joystickOne.lastDirection.y * (collisionCircle.radius + 2)), MyGdxGame.joystickOne.GetNorDirection()));
        else
            BulletManager.addBullet(new Bullet(new Vector2(collisionCircle.x + MyGdxGame.joystickTwo.lastDirection.x * (collisionCircle.radius + 2), collisionCircle.y + MyGdxGame.joystickTwo.lastDirection.y * (collisionCircle.radius + 2)), MyGdxGame.joystickTwo.GetNorDirection()));
    }

    public void initLoc() {
        collisionCircle.x = WorldLocation.x + width / 2;
        collisionCircle.y = WorldLocation.y  + width / 2;
        collisionCircle.radius = ((float)Math.sqrt(width * width + height * height)) / 2;
    }

    public void Update(float deltaTime) {

        if (index == 1 && !MyGdxGame.joystickOne.analogMoved() ||
            index == 2 && !MyGdxGame.joystickTwo.analogMoved())
            return;

        float scaledSpeed = Speed * deltaTime;

        Vector2 direction;
        if (index == 1)
            direction = MyGdxGame.joystickOne.GetNorDirection();
        else
            direction = MyGdxGame.joystickTwo.GetNorDirection();

        collisionCircle.x = WorldLocation.x + (direction.x * scaledSpeed) + width / 2;
        collisionCircle.y = WorldLocation.y + (direction.y * scaledSpeed) + width / 2;
        collisionCircle.radius = (float)width / 2;

        for (Wall wall : Level.walls) {
            if (com.badlogic.gdx.math.Intersector.overlaps(collisionCircle, wall.getCollisionRectangle())) {
                return;
            }
        }

        WorldLocation.add(direction.x * scaledSpeed, direction.y * scaledSpeed);

        if (index == 1 && MyGdxGame.joystickOne.analogMoved())
            Rotation = MyGdxGame.joystickOne.GetNorDirection().angle();
        if (index == 2 && MyGdxGame.joystickTwo.analogMoved())
            Rotation = MyGdxGame.joystickTwo.GetNorDirection().angle();
    }

    public void Draw(Batch batch) {
        batch.draw(texture,
            WorldLocation.x,
            WorldLocation.y,
            width / 2,
            height / 2,
            width,
            height,
            1.0f,
            1.0f,
            Rotation,
            0,
            0,
            0,
            0,
            false,
            false);
    }

    public Circle getCollisionCircle() {
        return collisionCircle;
    }
}
