package Core.Basics.Vector2D;

import Core.World;

import java.awt.*;

import static Core.World.Statics.Caches.*;

public final class Vector2D {

    public static void main(String[] args) {
        Vector2D v1 = new Vector2D(5, 5);
        System.out.println(v1);
    }


    public final int x;
    public final int y;
    public final float mg;

    public Vector2D(int x, int y) {
        this.x = x;
        this.y = y;
        mg = magnitude(0, 0, x, y);
    }

    public static float magnitude(int x1, int y1, int x2, int y2) {
        x1 -= x2;
        y1 -= y2;
        return (float) Math.sqrt(x1 * x1 + y1 * y1);
    }

    /**
     * Return the clock-wise Normal {@code Vector2D} of a given vector.
     * If the given vector is in the {@link Vector2DCache} of
     * {@link World.Statics.Caches}, it is ensure that the returned
     * {@code Vector2D}
     * too.
     * @param vector the given vector
     * @return the clock-wise Normal vector
     * @see Vector2DCache
     * @see World.Statics.Caches
     */
    public static Vector2D cwNormal(Vector2D vector) {
        return vector2Dcache.get(vector.x, -vector.y);
    }

    /**
     * Return a {@link Vector2D}
     * @param v1
     * @param v2
     * @return
     */
    public static Vector2D add(Vector2D v1, Vector2D v2) {
        return vector2Dcache.getAndCheck(v1.x + v2.x, v1.y + v2.y);
    }


    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " (x=" + x + ",y=" + y + ")";
    }
}
