package com.elvircrn.TankTrouble;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by elvircrn on 2/14/2015.
 */
public class Tank extends GameObject {

    float Rotation = 0.0f;
    float Speed = 4.0f;

    public Tank() {

    }

    public void Shoot() {

    }

    public void Move() {

    }

    public void Update(float deltaTime) {
        Vector2 direction = MyGdxGame.joystick.GetNorDirection();
        Vector2 res = direction.scl(Speed);
        Circle collisionCircle = new Circle((float)WorldLocation.x + res.x + texture.getWidth() / 2, (float)WorldLocation.y + res.y + texture.getWidth() / 2, (float)texture.getWidth() / 2);

        Vector2 worldLocation = new Vector2((float)WorldLocation.x + texture.getWidth() / 2, (float)WorldLocation.y + texture.getWidth() / 2);

        for (Wall wall : Level.walls) {
            if (com.badlogic.gdx.math.Intersector.overlaps(collisionCircle, wall.getCollisionRectangle())) {
                return;
            }
        }

        WorldLocation.add(res);

        if (Input.TouchList.size() > 0)
            Rotation = MyGdxGame.joystick.GetNorDirection().angle();
    }

    public void Draw(Batch batch) {
        batch.draw(texture,
            WorldLocation.x,
            WorldLocation.y,
            texture.getWidth() / 2,
            texture.getHeight() / 2,
            texture.getWidth(),
            texture.getHeight(),
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
