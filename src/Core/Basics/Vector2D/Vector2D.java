package Core.Basics.Vector2D;

import Core.World;

public final class Vector2D {

    public static void main(String[] args) {
        Vector2D v1 = new Vector2D(5, 5);
        System.out.println(v1);
    }


    public final int x;
    public final int y;
    public final float mg;
    public final Vector2D cwNormal;
    public final Vector2D acwNormal;
    public final Vector2D inverse;

    public Vector2D(int x, int y) {
        this.x = x;
        this.y = y;
        mg = magnitude(0, 0, x, y);
        cwNormal = this;
        acwNormal = this;
        inverse = null;
    }

    public static float magnitude(int x1, int y1, int x2, int y2) {
        x1 -= x2;
        y1 -= y2;
        return (float) Math.sqrt(x1 * x1 + y1 * y1);
    }

    public static Vector2D cwNormal(Vector2D vector) {
        return World.vector2Dcache.get(vector.x, -vector.y);
    }


    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " (x=" + x + ",y=" + y + ")";
    }
}
