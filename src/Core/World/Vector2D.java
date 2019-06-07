package Core.World;

public abstract class Vector2D {

    public final int x;
    public final int y;
    public final float mg;

    public Vector2D(int x, int y){
        this.x = x;
        this.y = y;
        mg = magnitude(0, 0, x, y);
    }

    private static float magnitude(int x1, int y1, int x2, int y2) {
        x1 -= x2;
        y1 -= y2;
        return (float) Math.sqrt(x1 * x1 + y1 * y1);
    }


}
