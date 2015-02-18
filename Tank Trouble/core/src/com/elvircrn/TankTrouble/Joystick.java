package com.elvircrn.TankTrouble;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by elvircrn on 2/14/2015.
 */
public class Joystick {

    //analog
    public Vector2 Center, Location;
    public Texture texture;
    public float analogRadius;


    //fire button
    public Vector2 ButtonLocation;

    public float Width, Height;

    public float getMaxDist() {
        return Center.x / 2.0f;
    }

    public Joystick(Texture texture, Vector2 centerLocation, Vector2 ButtonLocation) {
        //Center = centerLocation;
        //Location = Center;

        Center = new Vector2();

        Center.x = MyGdxGame.w / 15.f + 30.0f;
        Center.y = Center.x;

        Width  = 64.0f;
        Height = 64.0f;

        Location = Center;

        analogRadius = 80.0f;

        this.texture = texture;
        this.ButtonLocation = new Vector2(MyGdxGame.w - Center.x, Center.y);
    }

    public void Draw(Batch batch) {
        float largeWidth = Width * 2, largeHeight = Height * 2;

        batch.draw(texture, Center.x - largeWidth / 2, Center.y - largeHeight / 2, largeWidth, largeHeight);
        batch.draw(texture, Location.x - Width / 2, Location.y - Height / 2, Width, Height);
        batch.draw(texture, MyGdxGame.PrefferedWidth - Center.x, Center.y, Width, Height);
    }

    public Rectangle GetButtonRectangle() {
        return new Rectangle(MyGdxGame.PrefferedWidth - Center.x, Center.y, Width, Height);
    }

    public Vector2 GetMoveVector2() {
        return Location.sub(Center).nor();
    }

    public void UpdateLocation(Vector2 touchLocation) {
        Vector2 tl = new Vector2(touchLocation.x, touchLocation.y);
        Vector2 d = tl.sub(Center);

        //Gdx.app.log("at center before()", "center.x: " + Float.toString(Center.x) + "  center.y: " + Float.toString(Center.y));

        float distance = Center.dst(tl);

        //Gdx.app.log("at centerbefore()", "center.x: " + Float.toString(Center.x) + "  center.y: " + Float.toString(Center.y));

        d.nor();
        Vector2 normalized = d;

        normalized = new Vector2(normalized.x * analogRadius, normalized.y * analogRadius);

        Vector2 centercopy = new Vector2(Center.x, Center.y);;

        centercopy.add(normalized);

        Location = centercopy;
    }

    public Vector2 GetNorDirection() {
        Vector2 a = new Vector2(Center.x, Center.y), b = new Vector2(Location.x, Location.y);
        return (b.sub(a)).nor();
    }

    public void Update(float deltaTime) {
        boolean found = false;
        for (int i = 0; i < Input.TouchList.size(); i++) {
            Vector2 touchPoint = Input.TouchList.get(i);
            if (GetButtonRectangle().contains(touchPoint)) {
                Level.generateLevel(5, 5);
            }
            else {
                found = true;
                UpdateLocation(touchPoint);
                break;
            }
        }

        if (found == false)
            Location = new Vector2(Center.x, Center.y);
    }
}
