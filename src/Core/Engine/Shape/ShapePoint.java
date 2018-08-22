package Core.Engine.Shape;

import Core.Engine.Vector.Vector;

/**
 * Will hold all the information related with a given point of the shape.
 * Contain the type and all the {@link Shape.RelativeVector} describing the
 * position of this point in any transformation.
 */
public class ShapePoint {

    //Constants
    private static final int PERIMETER_POINT = 730;
    private static final int INNER_POINT = 29;
    private static final int VERTEX_POINT = 874;
    private static final int CENTER_POINT = 458;
    public static final float DEFAULT_NORMAL_VECTOR_MAGNITUDE = 10;

    //Fields
    int type;
    int rx;
    int ry;

    //ExtraData
    public ShapePoint closerPerimeterPoint;
    public float distanceToCloserPerimeterPoint;
    public float distanceToCenter;
    public Vector closerPerimeterPointNormal;

    public ShapePoint(int rx, int ry) {
        this.rx = rx;
        this.ry = ry;
    }


}