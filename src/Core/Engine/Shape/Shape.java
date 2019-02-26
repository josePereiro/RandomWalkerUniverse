package Core.Engine.Shape;

import Core.Engine.Exceptions.IllegalVertexesCountException;
import Core.Engine.Exceptions.IllegalVertexesPositionException;
import Core.Engine.Tools.Tools;
import Core.Engine.Vector.Vector;

import java.util.ArrayList;

/**
 * This class define a shape. The shape class will calculate
 * all the points of the shape and all the changes require to do for any transformation fallowing the principles of
 * a discrete translation and a discrete rotation. A shape have not a position in the coordinate system, it contain the
 * positions of any point relative to the center position of the shape.
 * All the constructors of this class are private, to instantiate it use the static constructor methods.
 */
public class Shape {

    //Shape Fields
    private final int rotationsCount;
    private int currentRotation;

    //Pinned Shapes
    private final PinnedShape[] rotatedShapes;

    //Handler
    private static ShapesHandler handler = new ShapesHandler();

    /**
     * @param rotationsCount the number of rotations that the shape will have.
     *                       So the minangle rotation will be 2 * PI / rotationCount.
     */
    private Shape(int rotationsCount) {
        this.rotationsCount = rotationsCount;
        rotatedShapes = new PinnedShape[rotationsCount];
    }

    /**
     * Rotate the shape in clockwise.
     */
    public void rotateClockWise() {

        if (currentRotation < rotationsCount - 1) {
            currentRotation++;
        } else {
            currentRotation = 0;
        }
    }

    /**
     * Rotate the shape in counter clockwise.
     */
    public void rotateCounterClockWise() {
        if (currentRotation > 0) {
            currentRotation--;
        } else {
            currentRotation = rotationsCount - 1;
        }
    }

    public int getRotationsCount() {
        return rotationsCount;
    }

    public ShapePoint[] getPerimeterPoints() {
        return rotatedShapes[currentRotation].getPerimeterPoints();
    }

    public ShapePoint[] getInnerPoints() {
        return rotatedShapes[currentRotation].getInnerPoints();
    }

    public ShapePoint[] getVertexes() {
        return rotatedShapes[currentRotation].getVertexes();
    }

    public ShapePoint getCenter() {
        return rotatedShapes[currentRotation].getCenter();
    }

    public ShapePoint[] getAllPoints() {
        return rotatedShapes[currentRotation].getAllPoints();
    }

    public int getMaxSpinRadius() {
        return rotatedShapes[currentRotation].getMaxSpinRadius();
    }

    public static class Types {

        public static final int CIRCLE = 561;
        public static final int TRIANGLE = 786;
        public static final int ELLIPSE = 231;
        public static final int SQUARE = 167;
        public static final int RECTANGLE = 449;
        public static final int POLYGON = 295;

    }

    public static class Constants {


        private static final int DEFAULT_ROTATIONS_COUNT = 30;

        public static final int MIN_LEGAL_VERTEXES = 3;
        public static final int MAX_LEGAL_VERTEXES = 100;

    }

    public static Shape createPolygonalShape(Vector... vertexes)
            throws IllegalVertexesCountException, IllegalVertexesPositionException {
        return createPolygonalShape(Constants.DEFAULT_ROTATIONS_COUNT, vertexes);
    }

    public static Shape createPolygonalShape(int rotationsCount, Vector... vertexes)
            throws IllegalVertexesCountException, IllegalVertexesPositionException {

        if (rotationsCount <= 1)
            rotationsCount = 1;

        Shape shape = new Shape(rotationsCount);

        //Vertexes to center
        translateVertexesToCenter(vertexes);

        //PinnedShapes
        float angle;
        float da = Tools.PIx2 / shape.rotationsCount;
        Vector[] vertexesCopy;
        ArrayList<Vector> tempVertexesCopy;
        for (int r = 0; r < shape.rotationsCount; r++) {
            angle = da * r;

            //Rotate Vertexes
            tempVertexesCopy = new ArrayList<>();
            for (int v = 0; v < vertexes.length; v++) {
                Vector vertex = vertexes[v];
                tempVertexesCopy.add(vertex.getCopy());
                Tools.rotate(tempVertexesCopy.get(v), angle, vertex.getMagnitude());
            }
            Tools.deleteDuplicates(tempVertexesCopy);
            vertexesCopy = tempVertexesCopy.toArray(new Vector[]{});

            //Handler
            PinnedShape pinnedShape = handler.resolve(vertexesCopy);
            if (pinnedShape == null) {
                pinnedShape = PinnedShape.PolygonalShapeFactory.createShape(vertexesCopy);
                handler.addShape(pinnedShape);
            }
            shape.rotatedShapes[r] = pinnedShape;

        }

        return shape;
    }

    private static void translateVertexesToCenter(Vector[] vertexes) {
        Vector center = getCenter(vertexes);
        for (int v = 0; v < vertexes.length; v++) {
            vertexes[v].sub(center);
        }
    }

    private static Vector getCenter(Vector[] vertexes) {

        int xSum = 0;
        int ySum = 0;
        for (int v = 0; v < vertexes.length; v++) {
            xSum += vertexes[v].x;
            ySum += vertexes[v].y;
        }

        return new Vector(Math.round(xSum / vertexes.length), Math.round(ySum / vertexes.length));
    }

}
