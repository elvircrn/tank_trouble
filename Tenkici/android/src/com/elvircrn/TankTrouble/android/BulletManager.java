package com.elvircrn.TankTrouble.android;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;

public class BulletManager {
    public static final Array<Bullet> bullets = new Array<>();
    public static final Pool<Bullet> bulletPool = new Pool<Bullet>() {
        @Override
        protected Bullet newObject() {
            return new Bullet();
        }
    };

    protected static boolean lethal;

    public static void create(int width, int height) {
        Bullet.width = width;
        Bullet.height = height;
    }

    public static void init() {

    }

    public static boolean isLethal() {
        return lethal;
    }

    public static void makeLethal() {
        lethal = true;
    }

    public static void makeSafe() {
        lethal = false;
    }


    public static void addBullet(float x, float y) {
        Bullet bullet = bulletPool.obtain();
        bullet.init(x, y);
        bullets.add(bullet);
    }

    public static void addBullet(float x, float y, float dirX, float dirY, int owner) {
        Bullet bullet = bulletPool.obtain();
        bullet.init(x, y, dirX, dirY, owner);
        bullets.add(bullet);
    }

    public static void update(float deltaTime) {
        if (GameMaster.getMode() == GameMaster.Mode.SERVER ||
                GameMaster.getMode() == GameMaster.Mode.LOCAL) {
            Bullet bullet;
            int n = bullets.size;
            for (int i = n - 1; i > -1; i--) {
                bullet = bullets.get(i);
                bullet.update(deltaTime);
                if (!bullet.alive) {
                    bullets.removeIndex(i);
                    bulletPool.free(bullet);
                }
            }
        }
    }

    public static Bullet get(int index) {
        return bullets.get(index);
    }

    public static int count() {
        return bullets.size;
    }

    public static void clearBullets() {
        for (int i = 0; i < bullets.size; i++)
            bullets.get(i).alive = false;
    }

    public static void draw(SpriteBatch batch) {
        for (Bullet bullet : bullets) {
            bullet.draw(batch);
        }
    }
}
