package com.elvircrn.TankTrouble;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by elvircrn on 2/14/2015.
 */
public class Tank extends GameObject {

    float Rotation = 0.0f;
    float Speed = 2.0f;

    public int width, height;

    public int index;

    public Tank() { }
    public Tank(int width, int height) { this.width = width; this.height = height; }

    void init(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void Shoot() {

    }

    public void Update(float deltaTime) {
        Vector2 direction;
        if (index == 1)
            direction = MyGdxGame.joystickOne.GetNorDirection();
        else
            direction = MyGdxGame.joystickTwo.GetNorDirection();

        Vector2 res = direction.scl(Speed);
        Circle collisionCircle = new Circle((float)WorldLocation.x + res.x + width / 2, (float)WorldLocation.y + res.y + width / 2, (float)width / 2);

        Vector2 worldLocation = new Vector2((float)WorldLocation.x + width / 2, (float)WorldLocation.y + width / 2);

        for (Wall wall : Level.walls) {
            if (com.badlogic.gdx.math.Intersector.overlaps(collisionCircle, wall.getCollisionRectangle())) {
                return;
            }
        }

        WorldLocation.add(res);

        if (Input.TouchList.size() > 0 && index == 1)
            Rotation = MyGdxGame.joystickOne.GetNorDirection().angle();
        if (Input.TouchList.size() > 0 && index == 2)
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

}
