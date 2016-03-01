package com.elvircrn.TankTrouble;

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
    //static properties
    public static float tankSpeed = 100.0f;
    public static int defaultMaxAmmo = 3;
    public static float defaultReloadTime = 2.0f;

    //Textures
    public Texture texture;

    //Fun tank stuff
    public int index;
    public int points;

    //Ammo
    public int currentAmmo;
    public int maxAmmo;
    public float reloadTime;
    public float reloadTick;

    //Physical properties
    public Vector2 worldLocation;
    protected float collisionRadius;
    public float width, height;
    public float rotation;
    public Rectangle collision;
    protected Vector2 moveDirection;
    protected Circle collisionCircle;

    //Boring flags
    public boolean drawable;

    //Constructors
    public Tank() {
        collision = new Rectangle();
        moveDirection = new Vector2();
        collisionCircle = new Circle();
        worldLocation = new Vector2();
    }

    public Tank(int index, Vector2 startLocation, float startRotation, float tankWidth, float tankHeight) {
        this();
        init(index, startLocation, startRotation, tankWidth, tankHeight);
    }

    public void init(int index, Vector2 startLocation, float startRotation, float tankWidth, float tankHeight) {
        this.index = index;
        this.worldLocation.set(startLocation.x, startLocation.y);
        this.rotation = startRotation;
        this.width = tankWidth;
        this.height = tankHeight;
        this.initCollisionCircle();
        this.moveDirection.set(0, 1);

        this.reloadTime = Tank.defaultReloadTime;
        this.currentAmmo = Tank.defaultMaxAmmo;
        this.reloadTick = Tank.defaultReloadTime;
        this.maxAmmo = Tank.defaultMaxAmmo;

        this.drawable = true;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void spawnTo(float startLocationX, float startLocationY, float startRotation, float tankWidth, float tankHeight) {
        this.worldLocation.set(startLocationX, startLocationY);
        this.rotation = startRotation;
        this.width = tankWidth;
        this.height = tankHeight;
        this.initCollisionCircle();
        this.moveDirection.set(0, 1);

        this.reloadTime = Tank.defaultReloadTime;
        this.currentAmmo = Tank.defaultMaxAmmo;
        this.reloadTick = Tank.defaultReloadTime;
        this.maxAmmo = Tank.defaultMaxAmmo;

        this.drawable = true;
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
        if (this.currentAmmo > 0) {
            float r = getCollisionCircle().radius + 5;
            BulletManager.addBullet(worldLocation.x + moveDirection.x * r, worldLocation.y + moveDirection.y * r, moveDirection.x, moveDirection.y, index);
            this.currentAmmo--;
        }
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
        if (currentAmmo == 0) {
            reloadTick -= deltaTime;
        }

        if (currentAmmo == 0 && reloadTick < 0) {
            reloadTick = reloadTime;
            currentAmmo = maxAmmo;
        }

        if (JoystickManager.get(index).moving()) {
            rotation = JoystickManager.get(index).analog.getNorAngle();
            moveDirection = new Vector2(JoystickManager.get(index).analog.getNorDirection());
            moveTo(deltaTime);
        }

        if (JoystickManager.get(index).button.justPressed()) {
            shoot();
        }
    }

    public void draw(SpriteBatch batch) {
        if (!drawable)
            return;
        
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
}
