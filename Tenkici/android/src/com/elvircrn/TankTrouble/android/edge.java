package com.elvircrn.TankTrouble.android;

/**
 * Created by elvircrn on 2/17/2015.
*/
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