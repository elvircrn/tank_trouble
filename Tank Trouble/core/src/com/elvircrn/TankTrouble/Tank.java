package com.elvircrn.TankTrouble;

import com.badlogic.gdx.graphics.g2d.Batch;
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
        WorldLocation.add(new Vector2(direction.x * Speed, direction.y * Speed));
    }

    public void Draw(Batch batch) {
        batch.draw(texture, WorldLocation.x, WorldLocation.y);
    }

}
