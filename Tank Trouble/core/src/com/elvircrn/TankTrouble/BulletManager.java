package com.elvircrn.TankTrouble;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

/**
 * Created by elvircrn on 2/14/2015.
 */
public class BulletManager {

    public static ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    private static ArrayList<Integer> toRemove = new ArrayList<Integer>();


    public static void init() {
        bullets.clear();
        Bullet.initScale(LevelManager.scale);
    }

    public static void addBullet(Bullet bullet) {
        if (bullets.size() > 6)
            return;
        bullets.add(bullet);
    }

    public static void Update(float deltaTime) {
        toRemove.clear();

        for (int i = 0; i < bullets.size(); i++)
            if (bullets.get(i).Update(deltaTime) == false)
                toRemove.add(i);

        for (int i : toRemove)
            bullets.remove(i);
    }

    public static void Draw(SpriteBatch batch) {
        for (Bullet bullet : bullets) {
            Rectangle drawRectangle = bullet.getWorldRectangle();
            batch.draw(Bullet.texture, drawRectangle.x, drawRectangle.y, drawRectangle.width, drawRectangle.height);
        }
    }
}
