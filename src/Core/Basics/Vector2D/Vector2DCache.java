package Core.Basics.Vector2D;

import Core.Boards.Board;

/**
 * TODO implement a cache for Vector2D!!!
 * Why caching?:
 * Vectors will be very commonly used... It is very possible
 * that a lot of operations or a lot of objects will use the same Vector,
 * so I wanna cached!!!
 * I will do Vector2D
 * immutable so any operation that returns (2,2) will be returning
 * the same object in memory. Now, knowing that, I can store in
 * Vector2D other useful information like the magnitude, and the
 * reference to other related vectors, like the perpendiculars, the inverse,
 * etc.
 */
public class Vector2DCache extends Board {


    public static void main(String[] args) {
        Vector2DCache cache = new Vector2DCache(10,20);
        System.out.println(cache.getAndCheck(9,-12));
    }

    Vector2D[][] vectors;
    private final int xb;
    private final int yb;

    public Vector2DCache(int w, int h) {
        super(w, h);
        vectors = new Vector2D[w * 2 - 1][h * 2 - 1];
        xb = w - 1;
        yb = h - 1;
        fillCache();
    }

    private void fillCache() {
        int vx, vy;
        for (int x = 0; x < vectors.length; x++) {
            vx = x > xb ? xb - x : x;
            for (int y = 0; y < vectors[x].length; y++) {
                vy = y > yb ? yb - y : y;
                vectors[x][y] = new Vector2D(vx, vy);
            }
        }
    }

    public Vector2D getPositive(int x, int y) {
        return vectors[x][y];
    }

    public Vector2D get(int x, int y) {
        return vectors[x < 0 ? xb - x : x][y < 0 ? yb - y : y];
    }

    public Vector2D getAndCheck(int x, int y){
        if (x > xb) {
            throw new IndexOutOfBoundsException("x = " + x + " is too big for a cache with w = " + w);
        } else if (x < -xb) {
            throw new IndexOutOfBoundsException("x = " + x + " is too small for a cache with w = " + w);
        } else if (y > yb) {
            throw new IndexOutOfBoundsException("y = " + y + " is too big for a cache with h = " + h);
        } else if (y < -yb) {
            throw new IndexOutOfBoundsException("y = " + y + " is too small for a cache with h = " + h);
        }
        return get(x,y);
    }

    private static int abs(int x) {
        return x < 0 ? -x : x;
    }
}
