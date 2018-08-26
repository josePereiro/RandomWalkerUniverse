package Core.Engine.Shape;

import Core.Engine.Exceptions.IllegalVertexesCountException;
import Core.Engine.Exceptions.IllegalVertexesPositionException;
import Core.Engine.Tools.Tools;
import Core.Engine.Vector.Vector;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * This class define a shape. It it used to handler the building of the shape into a LabeledPositionsBoard
 * or any up-left origin coordinate system. The shape class will calculate
 * all the points of the shape and all the changes require to do for any transformation fallowing the principles of
 * a discrete translation and a discrete rotation. A shape have not a position in the coordinate system, it contain the
 * positions of any point relative to the center position of the shape.
 * All the constructors of this class are private, to instantiate it use the static constructor methods or the builder.
 */
public class Shape {

    //Shape Fields
    private final int rotationsCount;
    private int currentRotation;

    //Pinned Shapes
    private final PinnedShape[] rotatedShapes;


    private Shape() {
        rotationsCount = Constants.DEFAULT_ROTATIONS_COUNT;
        rotatedShapes = new PinnedShape[rotationsCount];
        currentRotation = 0;
    }

    private Shape(int rotationsCount) {
        this.rotationsCount = rotationsCount;
        rotatedShapes = new PinnedShape[rotationsCount];
        currentRotation = 0;
    }

    public void rotate() {
        if (currentRotation < rotationsCount - 1)
            currentRotation++;
        else currentRotation = 0;
    }

    public BufferedImage getFullColorImage() {
        return rotatedShapes[currentRotation].getFullColorImage();
    }

    public BufferedImage getPerimeterImage() {
        return rotatedShapes[currentRotation].getPerimeterImage();
    }

    public static class Constants {

        public static final int CIRCLE = 561;
        public static final int TRIANGLE = 786;
        public static final int ELLIPSE = 231;
        public static final int SQUARE = 167;
        public static final int RECTANGLE = 449;
        public static final int POLYGON = 295;

        private static final int DEFAULT_ROTATIONS_COUNT = 30;

        public static final int MIN_LEGAL_VERTEXES = 3;
        public static final int MAX_LEGAL_VERTEXES = 100;

    }

    public static Shape createPolygonalShape(Vector... vertexes)
            throws IllegalVertexesCountException, IllegalVertexesPositionException {
        return PolygonalShapeFactory.createPolygonalShape(vertexes);
    }

    private static class PolygonalShapeFactory {

        public static Shape createPolygonalShape(Vector... vertexes) throws IllegalVertexesCountException, IllegalVertexesPositionException {

            Shape shape = new Shape();

            //PinnedShapes
            float angle;
            float da = Tools.PIx2 / shape.rotationsCount;
            Vector[] vertexesCopy;
            ArrayList<Vector> tempVertexes;
            for (int r = 0; r < shape.rotationsCount; r++) {
                angle = da * r;

                //Rotate Vertexes
                tempVertexes = new ArrayList<>();
                for (int v = 0; v < vertexes.length; v++) {
                    Vector vertex = vertexes[v];
                    tempVertexes.add(vertex.getCopy());
                    Tools.rotate(tempVertexes.get(v), angle);
                }
                Tools.deleteDuplicates(tempVertexes);
                vertexesCopy = tempVertexes.toArray(new Vector[]{});

                //PinnedShape
                shape.rotatedShapes[r] = PinnedShape.PolygonalShapeFactory.createShape(vertexesCopy);

            }

            return shape;
        }

    }

}
