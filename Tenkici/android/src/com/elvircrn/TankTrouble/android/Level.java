package com.elvircrn.TankTrouble.android;

import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.SnapshotArray;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Level {

    public static final int HORIZONTAL = 0, VERTICAL = 1;
    public static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;
    public static int wallWidth = 8;

    private static short currentSeed;

    private static Rectangle ret;

    public static Texture tileTexture, wallTexture;

    public static Vector2 offset;

    private static int[] dirX = new int[]{ 0, 1, 0, -1, 1, 1, -1, -1 };
    private static int[] dirY = new int[]{ 1, 0, -1, 0, 1, -1, 1, -1 };

    public static volatile ArrayList<Wall> walls;

    private static Boolean[][] visited;
    private static int[][] priority;

    public static int height, width;
    public static Tile[][] Tiles;

    public static SnapshotArray snap;

    private Level() { }

    public static void create() {
        priority  = new int[10] [10];
        Tiles     = new Tile[10][10];
        visited   = new Boolean[10] [10];
        walls     = new ArrayList<>();
        ret       = new Rectangle();
        snap      = new SnapshotArray();
    }

    public static void set_priorities(int width, int height) {
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                priority[i] [j] = RandomWrapper.getRand();
    }

    public static short getCurrentSeed() {
        return currentSeed;
    }

    public static void setCurrentSeed(short seed) {
        currentSeed = seed;
        RandomWrapper.init(seed);
    }
    public static void destroy_wall(int px, int py, int x, int y) {
        //right
        if (px < x) {
            Tiles[px] [py].walls.turn_off(1);
            Tiles[x] [y].walls.turn_off(3);
        }
        //left
        else if (x < px) {
            Tiles[px] [py].walls.turn_off(3);
            Tiles[x] [y].walls.turn_off(1);
        }
        //up
        else if (py < y) {
            Tiles[px] [py].walls.turn_off(0);
            Tiles[x] [y].walls.turn_off(2);
        }
        //down
        else if (y < py) {
            Tiles[px] [py].walls.turn_off(2);
            Tiles[x] [y].walls.turn_off(0);
        }
    }

    public static Vector2 tileToScreen(int i, int j) {
        int locX = (int)offset.x + i * getTileDimens() + wallWidth * i;
        int locY = (j * getTileDimens() + wallWidth * (j + 1));

        return new Vector2((float)locX, (float)locY);
    }

    public static void generateLevel(int widthParam, int heightParam) {
        walls.clear();
        width = widthParam;
        height = heightParam;

        offset = new Vector2(Graphics.prefferedWidth / 2 - getMapPixelWidth() / 2, 0);

        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++) {
                visited[i][j] = false;
                priority[i] [j] = 0;
                Tiles[i] [j] = new Tile(15);
            }

        set_priorities(width, height);

        PriorityQueue<edge> Q = new PriorityQueue<>();

        Q.add(new edge(0, 0, priority[0][0]));

        while (!Q.isEmpty()) {
            edge help = Q.poll();

            if (!visited[help.x] [help.y]) {
                visited [help.x] [help.y] = true;

                destroy_wall(help.px, help.py, help.x, help.y);

                for (int i = 0; i < 4; i++) {
                    int X = help.x + dirX [i];
                    int Y = help.y + dirY [i];

                    if (X < 0 || Y < 0 || X >= width || Y >= height || visited[X] [Y])
                        continue;

                    Q.add(new edge(help.x, help.y, X, Y, priority[X] [Y]));
                }
            }
        }

        generateWalls();

        Gdx.app.log("number of walls: ", Integer.toString(walls.size()));

        Debug("generated");
    }


    public static int getTileDimens() {
        return (int)((Graphics.prefferedHeight - wallWidth) / height) - wallWidth;
    }

    public static int getMapPixelWidth() {
        return width * getTileDimens() + wallWidth * width;
    }

    public static int getMapPixelHeight() {
        return height * getTileDimens() + wallWidth * height;
    }

    public static Vector2 pixelToTile(int x, int y) {
        int X = x - (int)offset.x;

        return new Vector2(X / width, y / height);
    }

    public static Vector2 pixelToTile(Vector2 loc) {
        return pixelToTile((int)loc.x, (int)loc.y);
    }

    public static synchronized void Draw(SpriteBatch batch) {
        for (Wall wall : walls) {
            batch.draw(wallTexture, wall.x, wall.y, wall.width, wall.height);
        }
    }

    private static synchronized void generateWalls() {
        //Vertical
        ArrayList<Wall> newWalls = new ArrayList<>();
        Vector2 beginning = new Vector2(), ending = new Vector2();
        for (int i = 0; i < width; i++) {
            beginning.x = -1;
            for (int j = 0; j < height; j++) {
                if (Tiles[i] [j].wallAt(LEFT)) {
                    if (beginning.x != -1) {
                        ending = tileToScreen(i, j).add(0.0f, getTileDimens() + wallWidth);
                    }
                    else {
                        beginning = tileToScreen(i, j).add(-wallWidth, -wallWidth);
                        ending = tileToScreen(i, j).add(0, getTileDimens() + wallWidth);
                    }
                }
                else {
                    if (beginning.x != -1) {
                        newWalls.add(new Wall((int)beginning.x, (int)beginning.y, wallWidth, (int)ending.y - (int)beginning.y, VERTICAL));
                        beginning.x = -1;
                    }
                }
            }
            if (beginning.x != -1) {
                newWalls.add(new Wall((int)beginning.x, (int)beginning.y, wallWidth, (int)ending.y - (int)beginning.y, VERTICAL));
            }
        }


        //Horizontal
        for (int i = 0; i < height; i++) {
            beginning.x = -1;
            for (int j = 0; j < width; j++) {
                if (Tiles[j] [i].wallAt(DOWN)) {
                    if (beginning.x != -1) {
                        ending = tileToScreen(j, i).add(getTileDimens() + wallWidth, -wallWidth);
                    }
                    else {
                        beginning = tileToScreen(j, i).add(-wallWidth, -wallWidth);
                        ending = tileToScreen(j, i).add(getTileDimens() + wallWidth, 0);
                    }
                }
                else {
                    if (beginning.x != -1) {
                        newWalls.add(new Wall((int)beginning.x, (int)beginning.y, (int)ending.x - (int)beginning.x, wallWidth, VERTICAL));
                        beginning.x = -1;
                    }
                }
            }
            if (beginning.x != -1) {
                newWalls.add(new Wall((int)beginning.x, (int)beginning.y, wallWidth, (int)ending.y - (int)beginning.y, VERTICAL));
            }
        }

        Gdx.app.log("top wall: ", "offset.x: " + Integer.toString((int)offset.x) +
                                  "MyGdxGame.PrefferedHeight: " + Integer.toString((int) Graphics.prefferedHeight) +
                                  "getMapPixelWidth(): " + Integer.toString(getMapPixelWidth()));

        newWalls.add(new Wall((int)offset.x - wallWidth, (int) Graphics.prefferedHeight - wallWidth, getMapPixelWidth() + wallWidth, 2 * wallWidth, HORIZONTAL));
        newWalls.add(new Wall((int)offset.x + getMapPixelWidth() - wallWidth, 0, wallWidth, getMapPixelHeight(), VERTICAL));
        newWalls.add(new Wall((int)offset.x, 0, getMapPixelWidth(), wallWidth, HORIZONTAL));

        for (Wall wall : newWalls)
            if (wall.width > 2 * wallWidth || wall.height > 2 * wallWidth)
                walls.add(wall);

        Debug("ses");
        DebugWalls();
    }

    public static void getWallRectangle(Rectangle rec, int x, int y, int direction) {
        x *= (getTileDimens() + wallWidth);
        y *= (getTileDimens() + wallWidth);

        if (direction == 0) {
            ret.set(x - wallWidth, y + getTileDimens(), getTileDimens() + wallWidth, wallWidth);
        }
        else if (direction == 1) {
            ret.set(x + getTileDimens(), y - wallWidth, wallWidth, getTileDimens() + wallWidth);
        }
        else if (direction == 2) {
            ret.set(x - wallWidth, y - wallWidth, getTileDimens() + wallWidth, wallWidth);
        }
        else {
            ret.set(x - wallWidth, y - wallWidth, wallWidth, getTileDimens() + wallWidth);
        }
    }

    public static void drawWall(SpriteBatch batch, int x, int y, int direction) {
        if (direction == 0) {
            batch.draw(wallTexture, x - wallWidth, y + getTileDimens(), getTileDimens() + wallWidth, wallWidth);
        }
        else if (direction == 1) {
            batch.draw(wallTexture, x + getTileDimens(), y - wallWidth, wallWidth, getTileDimens() + wallWidth);
        }
        else if (direction == 2) {
            batch.draw(wallTexture, x - wallWidth, y - wallWidth, getTileDimens() + wallWidth, wallWidth);
        }
        else if (direction == 3) {
            batch.draw(wallTexture, x - wallWidth, y - wallWidth, wallWidth, getTileDimens() + wallWidth);
        }
    }

    public static int approxX(float x) {
        return (int)((x - offset.x) / (getTileDimens() + wallWidth));
    }

    public static int approxY(float y) {
        return (int)((y - offset.y) / (getTileDimens() + wallWidth));
    }

    public static void DebugWalls() {
        Log.d("wallwidth: ", Integer.toString(wallWidth));
        for (Wall wall : walls) {
            Log.d("WALL: ", Integer.toString(wall.x) + " " + Integer.toString(wall.y) + " " + Integer.toString(wall.width) + " " + Integer.toString(wall.height));
        }
    }

    public static void Debug(String logTag) {
        Log.d("map pixel width: ", Integer.toString(getMapPixelWidth()));
        Log.d("map pixel height: ", Integer.toString(getMapPixelHeight()));

        Log.d("tile dimens: ", Integer.toString(getTileDimens()));

        Log.d("offset: ", Float.toString(offset.x) + " " + Float.toString(offset.y));
    }
}
