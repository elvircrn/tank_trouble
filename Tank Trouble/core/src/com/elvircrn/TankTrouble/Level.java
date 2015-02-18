package com.elvircrn.TankTrouble;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Created by elvircrn on 2/14/2015.
 */

public class Level {

    public static final int HORIZONTAL = 0, VERTICAL = 1;
    public static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;
    public static int wallWidth = 8;

    public static Texture tileTexture, wallTexture;

    public static Vector2 offset;

    public static int[] dirX = new int[]{ 0, 1, 0, -1 };
    public static int[] dirY = new int[]{ 1, 0, -1, 0 };

    public static ArrayList<Wall> walls;

    private static Boolean[][] visited;
    private static int[][] priority;

    public static int height, width;
    public static Tile[][] Tiles;

    public Level() { }

    public static void set_priorities(int width, int height) {
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                priority[i] [j] = (int)(Math.random() * 10000.0f);
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
        walls = new ArrayList<Wall>();
        width = widthParam;
        height = heightParam;

        Tiles = new Tile[width + 5][height + 5];

        visited = new Boolean[width + 5] [height + 5];
        priority = new int[width + 5] [height + 5];

        offset = new Vector2(MyGdxGame.PrefferedWidth / 2 - getMapPixelWidth() / 2, 0);

        for (int i = 0; i < width + 5; i++)
            for (int j = 0; j < height + 5; j++) {
                visited[i][j] = false;
                priority[i] [j] = 0;
                Tiles[i] [j] = new Tile(15);
            }

        set_priorities(width, height);

        PriorityQueue<edge> Q = new PriorityQueue<edge>();

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
        return (int)((MyGdxGame.PrefferedHeight - wallWidth) / height) - wallWidth;
    }

    public static int getMapPixelWidth() {
        return width * getTileDimens() + wallWidth * width;
    }

    public static int getMapPixelHeight() {
        return height * getTileDimens() + wallWidth * height;
    }

    public static void Draw(SpriteBatch batch) {
        /*for (int i = 0; i < width; i++) {
            for (int j = 0 ; j < height; j++) {
                int locX = (int)offset.x + i * getTileDimens() + wallWidth * i;
                int locY = (j * getTileDimens() + wallWidth * (j + 1));

                for (int k = 0; k < 4; k++)
                    if (Tiles[i] [j].wallAt(k))
                        drawWall(batch, locX, locY, k);

                //batch.draw(tileTexture, locX, locY, getTileDimens(), getTileDimens());
            }
        }*/

        for (Wall wall : walls) {
            batch.draw(wallTexture, wall.x, wall.y, wall.width, wall.height);
        }
    }

    private static void generateWalls() {
        //Vertical
        int counter = 1;
        Vector2 beginning = new Vector2(), ending = new Vector2();
        for (int i = 0; i < width; i++) {
            beginning.x = -1;
            counter = 1;
            for (int j = 0; j < height; j++) {
                if (Tiles[i] [j].wallAt(LEFT)) {
                    if (beginning.x != -1) {
                        ending = tileToScreen(i, j).add(0.0f, getTileDimens() + wallWidth);
                        counter++;
                    }
                    else {
                        beginning = tileToScreen(i, j).add(-wallWidth, -wallWidth);
                        ending = tileToScreen(i, j).add(0, getTileDimens() + wallWidth);
                        counter = 1;
                    }
                }
                else {
                    if (beginning.x != -1) {
                        walls.add(new Wall((int)beginning.x, (int)beginning.y, wallWidth, (int)ending.y - (int)beginning.y, VERTICAL));
                        beginning.x = -1;
                    }
                }
            }
            if (beginning.x != -1) {
                walls.add(new Wall((int)beginning.x, (int)beginning.y, wallWidth, (int)ending.y - (int)beginning.y, VERTICAL));
            }
        }


        //Horizontal
        for (int i = 0; i < height; i++) {
            beginning.x = -1;
            counter = 1;
            for (int j = 0; j < width; j++) {
                if (Tiles[j] [i].wallAt(DOWN)) {
                    if (beginning.x != -1) {
                        ending = tileToScreen(j, i).add(getTileDimens() + wallWidth, -wallWidth);
                        counter++;
                    }
                    else {
                        beginning = tileToScreen(j, i).add(-wallWidth, -wallWidth);
                        ending = tileToScreen(j, i).add(getTileDimens() + wallWidth, 0);
                        counter = 1;
                    }
                }
                else {
                    if (beginning.x != -1) {
                        walls.add(new Wall((int)beginning.x, (int)beginning.y, (int)ending.x - (int)beginning.x, wallWidth, VERTICAL));
                        beginning.x = -1;
                    }
                }
            }
            if (beginning.x != -1) {
                walls.add(new Wall((int)beginning.x, (int)beginning.y, (int)wallWidth, (int)ending.y - (int)beginning.y, VERTICAL));
            }
        }

        Gdx.app.log("top wall: ", "offset.x: " + Integer.toString((int)offset.x) +
                                  "MyGdxGame.PrefferedHeight: " + Integer.toString((int)MyGdxGame.PrefferedHeight) +
                                  "getMapPixelWidth(): " + Integer.toString(getMapPixelWidth()));

        walls.add(new Wall((int)offset.x, (int)MyGdxGame.PrefferedHeight - wallWidth, getMapPixelWidth(), wallWidth, HORIZONTAL));
        walls.add(new Wall((int)offset.x + getMapPixelWidth() - wallWidth, 0, wallWidth, getMapPixelHeight(), VERTICAL));
        walls.add(new Wall((int)offset.x, 0, getMapPixelWidth(), wallWidth, HORIZONTAL));

        Gdx.app.log("getTileDimens(): ", Integer.toString(getTileDimens()));

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Gdx.app.log("|||", "(" + Integer.toString(i) + ", " + Integer.toString(j) + "): " + tileToScreen(i, j).toString());
            }
        }


        for (Wall wall : walls) {
            //Gdx.app.log("RECTANGLE: ", "x: " + Integer.toString((int)wall.getCollisionRectangle().x) + " y: " + Integer.toString((int)wall.getCollisionRectangle().y) + " width: " +
            //                           Integer.toString((int)wall.getCollisionRectangle().getWidth()) + " height: "  + Integer.toString((int)wall.getCollisionRectangle().getHeight()));
        }
    }

    public static void drawWall(SpriteBatch batch, int x, int y, int direction) {

        if (direction == 0) {
            batch.draw(wallTexture, x - wallWidth, y + getTileDimens(), getTileDimens() + wallWidth, wallWidth);
            //walls.add(new Rectangle(x - wallWidth, y + getTileDimens(), getTileDimens() + wallWidth, wallWidth));
        }
        else if (direction == 1) {
            batch.draw(wallTexture, x + getTileDimens(), y - wallWidth, wallWidth, getTileDimens() + wallWidth);
            //walls.add(new Rectangle(x + getTileDimens(), y - wallWidth, wallWidth, getTileDimens() + wallWidth));
        }
        else if (direction == 2) {
            batch.draw(wallTexture, x - wallWidth, y - wallWidth, getTileDimens() + wallWidth, wallWidth);
            //walls.add(new Rectangle(x - wallWidth, y - wallWidth, getTileDimens() + wallWidth, wallWidth));
        }
        else if (direction == 3) {
            batch.draw(wallTexture, x - wallWidth, y - wallWidth, wallWidth, getTileDimens() + wallWidth);
            //walls.add(new Rectangle(x - wallWidth, y - wallWidth, wallWidth, getTileDimens() + wallWidth));
        }
    }

    public static void Debug(String logTag) {
        String debugString = "|\n\n\n";

        for (int i = height - 1; i > -1; i--) {
            for (int j = 0; j < width; j++) {
                debugString += (Integer.toString(Tiles[j] [i].walls.mask) + " ");
            }
            debugString += "\n";
        }

        Gdx.app.log(logTag, debugString);
    }
}
