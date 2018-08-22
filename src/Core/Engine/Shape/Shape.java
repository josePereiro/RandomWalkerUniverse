package Core.Engine.Shape;

import Core.Engine.Vector.Vector;

import java.lang.management.MemoryUsage;
import java.util.ArrayList;

/**
 * This class define a shape. It it used to handler the building of the shape into a PositionValueBoard
 * or any up-left origin coordinate system. The shape class will calculate
 * all the points of the shape and all the changes require to do for any transformation fallowing the principles of
 * a discrete translation and a discrete rotation. A shape have not a position in the coordinate system, it contain the
 * positions of any point relative to the center position of the shape.
 * All the constructors of this class are private, to instantiate it use the static constructor methods or the builder.
 */
public class Shape {

    public static void main(String... args) {



    }

    //Shape Fields
    int type;
    int spaceResolution;
    float rotationResolution;


    private Shape() {

    }

    private static class Types {

        private static final int CIRCLE = 561;
        private static final int ELLIPSE = 231;
        private static final int SQUARE = 167;
        private static final int RECTANGLE = 449;
        private static final int UNDEFINED_POLYGON = 295;

    }

    /**
     * This class represent a vector with an undefined
     * position. You know, it has getMagnitude and direction but
     * it needs a reference to be placed in a particular position
     * in a board.
     */
    private static class RelativeVector {

        /**
         * the value to be translated
         */
        int tx;
        int ty;

        public RelativeVector(int tx, int ty) {
            this.tx = tx;
            this.ty = ty;
        }

        public Vector getVector(int referenceX, int referenceY) {
            return new Vector(referenceX + tx, referenceY + ty);
        }

        public Vector getVector(Vector reference) {
            return new Vector(reference.x + tx, reference.y + ty);
        }

    }

}
