package com.elvircrn.TankTrouble;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Created by elvircrn on 2/14/2015.
 */

public class Level {

    public ArrayList<Rectangle> walls;

    public static Texture tileTexture, wallTexture;

    public static int[] dirX = new int[]{ 0, 1, 0, -1 };
    public static int[] dirY = new int[]{ 1, 0, -1, 0 };

    public class edge  implements Comparable<edge> {
        public int px, py, x, y, w;

        public edge() { }
        public edge(int x, int y, int w) { px = x; py = y; this.x = x; this.y = y; this.w = w; }
        public edge(int px, int py, int x, int y, int w) { this.px = px; this.py = py; this.x = x; this.y = y; this.w = w; }
        @Override
        public int compareTo(edge B) {
            if (this.w == B.w)
                return 0;
            else if (this.w < B.w)
                return -1;
            else
                return 1;
        }
    }

    private static Boolean[][] visited;
    private static int[][] priority;

    public int height, width;
    public Tile[][] Tiles;

    public Level() { }

    void set_priorities(int width, int height) {
        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                priority[i] [j] = (int)(Math.random() * 10000.0f);
    }

    void destroy_wall(int px, int py, int x, int y) {
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
        else {
            Gdx.app.log("log", "ebiga brate");
        }
    }

    void generateLevel(int width, int height) {
        walls = new ArrayList<Rectangle>();
        this.width = width;
        this.height = height;

        Tiles = new Tile[width + 5][height + 5];

        visited = new Boolean[width + 5] [height + 5];
        priority = new int[width + 5] [height + 5];

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

                Gdx.app.log("LOG", Integer.toString(help.px) + "   " + Integer.toString(help.py) + "    " + Integer.toString(help.x) + "    " + Integer.toString(help.y));

                for (int i = 0; i < 4; i++) {
                    int X = help.x + dirX [i];
                    int Y = help.y + dirY [i];

                    if (X < 0 || Y < 0 || X >= width || Y >= height || visited[X] [Y])
                        continue;

                    Q.add(new edge(help.x, help.y, X, Y, priority[X] [Y]));
                }
            }
        }


        int unvisited = 0;

        for (int i = 0; i < width; i++)
            for (int j = 0; j < height; j++)
                if (!visited [i] [j])
                    unvisited++;

        Gdx.app.log("unvisited: ", Integer.toString(unvisited));

        Debug("generated", this);
    }

    public static int wallWidth = 8;

    int getTileDimens() {
        return (int)(MyGdxGame.PrefferedHeight / height + wallWidth);
    }

    int getMapPixelWidth() {
        return getTileDimens() * width;
    }

    int getMapPixelHeight() {
        return getTileDimens() * height;
    }

    public void Draw(SpriteBatch batch) {
        Vector2 offset = new Vector2(MyGdxGame.PrefferedWidth / 2 - getMapPixelWidth() / 2, 0);

        for (int i = 0; i < width; i++) {
            for (int j = 0 ; j < height; j++) {
                int locX = (int)offset.x + i * getTileDimens() + wallWidth * i;
                int locY = /*(int)MyGdxGame.PrefferedHeight - */(j * getTileDimens() + wallWidth * j);

                for (int k = 0; k < 4; k++)
                    if (Tiles[i] [j].wallAt(k))
                        drawWall(batch, locX, locY, k);

                //batch.draw(tileTexture, locX, locY, getTileDimens(), getTileDimens());
            }
        }
    }

    public void drawWall(SpriteBatch batch, int x, int y, int direction) {

        if (direction == 0) {
            batch.draw(wallTexture, x - wallWidth, y + getTileDimens(), getTileDimens() + wallWidth, wallWidth);
            walls.add(new Rectangle(x - wallWidth, y + getTileDimens(), getTileDimens() + wallWidth, wallWidth));
        }
        else if (direction == 1) {
            batch.draw(wallTexture, x + getTileDimens(), y - wallWidth, wallWidth, getTileDimens() + wallWidth);
            walls.add(new Rectangle(x + getTileDimens(), y - wallWidth, wallWidth, getTileDimens() + wallWidth));
        }
        else if (direction == 2) {
            batch.draw(wallTexture, x - wallWidth, y - wallWidth, getTileDimens() + wallWidth, wallWidth);
            walls.add(new Rectangle(x - wallWidth, y - wallWidth, getTileDimens() + wallWidth, wallWidth));
        }
        else if (direction == 3) {
            batch.draw(wallTexture, x - wallWidth, y - wallWidth, wallWidth, getTileDimens() + wallWidth);
            walls.add(new Rectangle(x - wallWidth, y - wallWidth, wallWidth, getTileDimens() + wallWidth));
        }
    }

    public static void Debug(String logTag, Level l) {
        String debugString = "|\n\n\n";

        for (int i = l.height - 1; i > -1; i--) {
            for (int j = 0; j < l.width; j++) {
                debugString += (Integer.toString(l.Tiles[j] [i].walls.mask) + " ");
            }
            debugString += "\n";
        }

        Gdx.app.log(logTag, debugString);
    }
}
