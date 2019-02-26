package Core.Engine.Shape;

import Core.Engine.Vector.Localizable;
import Core.Engine.Vector.Vector;

/**
 * Will hold all the information related with a given point of a PinnedShape.
 */
public class ShapePoint implements Localizable{

    //Constants
    public static final int PERIMETER_POINT_LABEL = 730;
    public static final int INNER_POINT_LABEL = 29;
    public static final float DEFAULT_NORMAL_VECTOR_MAGNITUDE = 10;
    public static final int OUTER_POINT_LABEL = 869;

    //Fields
    int type;
    public final int x;
    public final int y;

    //ExtraData
    public ShapePoint closerPerimeterPoint;
    public float distanceToCloserPerimeterPoint;
    public float distanceToCenter;
    public Vector normal;

    public ShapePoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector toVector() {
        return new Vector(x, y);
    }

    public boolean equalsPos(ShapePoint toCompare) {
        return toCompare.x == x && toCompare.y == y;
    }

    public int getType() {
        return type;
    }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass() == this.getClass() && ((ShapePoint) obj).x == x && ((ShapePoint) obj).y == y;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }
}