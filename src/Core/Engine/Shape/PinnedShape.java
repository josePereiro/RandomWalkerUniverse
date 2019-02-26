package Core.Engine.Shape;

import Core.Engine.Exceptions.IllegalVertexesCountException;
import Core.Engine.Exceptions.IllegalVertexesPositionException;
import Core.Engine.Geometry.Geometry;
import Core.Engine.Iterations.Iterations;
import Core.Engine.Iterations.OnIterationActionHandler;
import Core.Engine.PositionValueBoard.LabeledPositionsBoard;
import Core.Engine.Tools.Tools;
import Core.Engine.Vector.Localizable;
import Core.Engine.Vector.Vector;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * This class handle a single shape with no transformation.
 */
public class PinnedShape {

    //Vertexes
    private ShapePoint[] vertexes;

    //Perimeter
    private ShapePoint[] perimeterPoints;
    private ShapePoint[] unsortedPerimeterPoints;

    //Area
    private ShapePoint[] innerPoints;
    private ShapePoint[] unsortedInnerAreaPoints;

    //All points
    private ShapePoint[] allPoints;

    //Center
    private ShapePoint center;

    //GeometryFields
    private int type;
    private int maxSpinRadius;
    private int mediumSpinRadius;
    private int w;
    private int h;

    //IndexBoard
    LabeledPositionsBoard indexBoard;
    int xTranslation;
    int yTranslation;

    public PinnedShape() {
    }

    public ShapePoint[] getPerimeterPoints() {
        return perimeterPoints;
    }

    public ShapePoint[] getInnerPoints() {
        return innerPoints;
    }

    public ShapePoint[] getVertexes() {
        return vertexes;
    }

    public ShapePoint getCenter() {
        return center;
    }

    public ShapePoint[] getAllPoints() {
        return allPoints;
    }

    public int getMaxSpinRadius() {
        return maxSpinRadius;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public static int getHashCode(Localizable[] vertexes) {
        StringBuilder st = new StringBuilder();

        for (Localizable vertex : vertexes) {
            st.append(vertex.getX());
            st.append(",");
            st.append(vertex.getY());
        }

        return st.toString().hashCode();

    }

    public static int getHashCode(PinnedShape shape) {

        if (shape == null) return 0;

        if (shape.type == Shape.Types.POLYGON || shape.type == Shape.Types.SQUARE ||
                shape.type == Shape.Types.RECTANGLE) {

            return getHashCode(shape.vertexes);

        } else if (shape.type == Shape.Types.ELLIPSE) {
            //TODO implement
            return 0;
        } else if (shape.type == Shape.Types.CIRCLE) {
            //TODO implement
            return 0;
        } else {
            return 0;
        }
    }

    /**
     * This class handler the creation of a single polygonal PinnedShape.
     */
    public abstract static class PolygonalShapeFactory {

        public static PinnedShape createShape(Vector... vertexes) throws IllegalVertexesCountException, IllegalVertexesPositionException {

            checkVertexesCount(vertexes);
            checkVertexesPositions(vertexes);
            PinnedShape shape = new PinnedShape();
            setType(shape, vertexes);
            setSpinRadius(shape, vertexes);
            setWidthAndHeight(shape, vertexes);
            setPerimeterPointsAndVertexes(shape, vertexes);
            setInnerPointsAndTheCenterPoint(shape, vertexes);
            setAllPoints(shape);

            return shape;
        }

        private static void checkVertexesCount(Vector[] vertexes) throws IllegalVertexesCountException {
            if (vertexes.length < Shape.Constants.MIN_LEGAL_VERTEXES)
                throw new IllegalVertexesCountException("Not enough vertexes to createShape a shape. VertexesCount: " +
                        vertexes.length);
            if (vertexes.length > Shape.Constants.MAX_LEGAL_VERTEXES) {
                throw new IllegalVertexesCountException("Too many vertexes. " + Shape.Constants.MAX_LEGAL_VERTEXES +
                        " is the legal max, performance reasons!!! VertexesCount: " +
                        vertexes.length);
            }
        }

        private static void checkVertexesPositions(Vector[] vertexes) throws IllegalVertexesPositionException {
            for (int cv = 0; cv < vertexes.length; cv++) {
                for (int v = cv + 1; v + 1 < vertexes.length; v++) {
                    if (vertexes[cv].equals(vertexes[v]))
                        throw new IllegalVertexesPositionException("Two vertexes can't have the same position!!!, VertexPosition " +
                                vertexes[v]);
                }
            }
        }

        private static void setInnerPointsAndTheCenterPoint(PinnedShape shape, Vector[] vertexes) throws IllegalVertexesPositionException {

            //region HighResolutionBoard...
            int xFactor, yFactor, minFactor = 2;
            if (shape.getW() < shape.getH()) {
                yFactor = minFactor;
                xFactor = (int) (minFactor * ((float) shape.getH() / shape.getW()));
            } else {
                xFactor = minFactor;
                yFactor = (int) (minFactor * ((float) shape.getW() / shape.getH()));
            }
            LabeledPositionsBoard board =
                    new LabeledPositionsBoard(shape.getW() * xFactor + 1,
                            shape.getH() * yFactor + 1);

            //Copy vertexes
            Vector[] vertexesCopy = new Vector[vertexes.length];
            for (int v = 0; v < vertexesCopy.length; v++) {
                vertexesCopy[v] = vertexes[v].getCopy();
            }

            //Translating vertexes
            int xTranslation = Integer.MAX_VALUE;
            int yTranslation = Integer.MAX_VALUE;
            for (Vector vector : vertexesCopy) {
                if (vector.x < xTranslation)
                    xTranslation = vector.x;
                if (vector.y < yTranslation)
                    yTranslation = vector.y;
            }
            xTranslation = Math.abs(xTranslation);
            yTranslation = Math.abs(yTranslation);
            for (Vector vertex : vertexesCopy) {
                vertex.x *= xFactor;
                vertex.y *= yFactor;
                vertex.x += xTranslation * xFactor;
                vertex.y += yTranslation * yFactor;
            }

            //Setting perimeter into board
            int perimeterPointLabel = ShapePoint.PERIMETER_POINT_LABEL;
            board.setPolygonalPerimeter(vertexesCopy, perimeterPointLabel);

            //Get InnerArea
            int innerAreaLabel = ShapePoint.INNER_POINT_LABEL;
            Vector startPoint;
            for (int v = 0; v + 1 < vertexesCopy.length; v++) {
                startPoint = Tools.getMiddleRightPoint(vertexesCopy[v].x, vertexesCopy[v].y,
                        vertexesCopy[v + 1].x, vertexesCopy[v + 1].y);
                Tools.fillContinueArea(board, board.getEmptyLabel(),
                        innerAreaLabel, startPoint, null);
            }
            startPoint = Tools.getMiddleRightPoint(vertexesCopy[vertexesCopy.length - 1].x,
                    vertexesCopy[vertexesCopy.length - 1].y,
                    vertexesCopy[0].x, vertexesCopy[0].y);
            Tools.fillContinueArea(board, board.getEmptyLabel(),
                    innerAreaLabel, startPoint, null);

            //endregion HighResolutionBoard

            //region NormalResolutionBoard...
            board = Tools.getLowerResolutionBoard(board,
                    xFactor, yFactor);

            //Vertexes back to normal resolution
            for (int v = 0; v < vertexesCopy.length; v++) {
                vertexesCopy[v] = vertexes[v].getCopy();
                vertexesCopy[v].x += xTranslation;
                vertexesCopy[v].y += yTranslation;
            }
            board.setPolygonalPerimeter(vertexesCopy, perimeterPointLabel);

            //InnerPoints and Center
            Vector center = Tools.getCenter(vertexesCopy);
            ArrayList<ShapePoint> innerPoints = new ArrayList<>();
            ShapePoint innerPoint;
            int tx, ty;
            for (int x = 0; x < board.getW(); x++) {
                for (int y = 0; y < board.getH(); y++) {
                    //Inner Area Points
                    if (board.getLabelAt(x, y) == innerAreaLabel) {

                        //checking innerPoints
                        if (x == 0 || y == 0 || x == board.getW() - 1 || y == board.getH() - 1)
                            throw new IllegalVertexesPositionException("Shape building fails, check the vertexes." +
                                    " You must set them in clockwise!!!");

                        //Translate back
                        tx = x - xTranslation;
                        ty = y - yTranslation;

                        //InnerPoint
                        innerPoint = new ShapePoint(tx, ty);
                        innerPoint.type = ShapePoint.INNER_POINT_LABEL;
                        innerPoint.closerPerimeterPoint = getCloserPerimeterPoint(innerPoint.x, innerPoint.y, shape);
                        innerPoint.distanceToCloserPerimeterPoint =
                                Vector.getDistance(innerPoint.x, innerPoint.y,
                                        innerPoint.closerPerimeterPoint.x, innerPoint.closerPerimeterPoint.y);
                        innerPoint.distanceToCenter = Vector.getMagnitude(tx, ty);
                        innerPoint.normal = innerPoint.closerPerimeterPoint.normal;

                        //Checking Center
                        if (center.x == x && center.y == y)
                            shape.center = innerPoint;

                        //Adding to list
                        innerPoints.add(innerPoint);
                    }
                    //Checking Center Point
                    else if (center.x == x && center.y == y) {
                        //If it is a Perimeter Point
                        if (board.getLabelAt(x, y) == perimeterPointLabel) {
                            for (ShapePoint perimeterPoint : shape.perimeterPoints) {
                                if (perimeterPoint.x == center.x && perimeterPoint.y == center.y) {
                                    shape.center = perimeterPoint;
                                }
                            }
                        }
                        //If it is an outerPoint
                        else {

                            //Translate back
                            tx = x - xTranslation;
                            ty = y - yTranslation;

                            //Center Point
                            ShapePoint centerPoint = new ShapePoint(tx, ty);
                            centerPoint.type = ShapePoint.OUTER_POINT_LABEL;
                            centerPoint.closerPerimeterPoint = getCloserPerimeterPoint(centerPoint.x, centerPoint.y, shape);
                            centerPoint.distanceToCloserPerimeterPoint =
                                    Vector.getDistance(centerPoint.x, centerPoint.y,
                                            centerPoint.closerPerimeterPoint.x, centerPoint.closerPerimeterPoint.y);
                            centerPoint.distanceToCenter = Vector.getMagnitude(tx, ty);
                            centerPoint.normal = centerPoint.closerPerimeterPoint.normal;
                        }
                    }
                }
            }

            //checking innerPoint
            if (innerPoints.size() == 0)
                throw new IllegalVertexesPositionException("Shape building fails, check the vertexes." +
                        " You must set them in clockwise!!!");

            //Setting innerPoints
            shape.innerPoints = innerPoints.toArray(new ShapePoint[]{});

            //unSorting innerPoints
            Tools.sortRandomly(innerPoints);

            shape.unsortedInnerAreaPoints = innerPoints.toArray(new ShapePoint[]{});

            //endregion NormalResolutionBoard

        }

        private static void setAllPoints(PinnedShape shape) {

            shape.allPoints = new ShapePoint[shape.perimeterPoints.length +
                    shape.innerPoints.length];

            int index = 0;
            for (ShapePoint perimeterPoint : shape.perimeterPoints) {
                shape.allPoints[index] = perimeterPoint;
                index++;
            }
            for (ShapePoint innerAreaPoint : shape.innerPoints) {
                shape.allPoints[index] = innerAreaPoint;
                index++;
            }


        }

        private static ShapePoint getCloserPerimeterPoint(int innerPointX, int innerPointY, PinnedShape shape) {

            float dist;
            float lowerDist = Float.POSITIVE_INFINITY;
            ShapePoint closerPerimeterPoint = shape.perimeterPoints[0];
            for (int pp = 0; pp < shape.perimeterPoints.length; pp++) {
                dist = Vector.getDistance(shape.perimeterPoints[pp].x, shape.perimeterPoints[pp].x,
                        innerPointX, innerPointY);
                if (lowerDist > dist) {
                    lowerDist = dist;
                    closerPerimeterPoint = shape.perimeterPoints[pp];
                }
            }
            return closerPerimeterPoint;
        }

        private static void setType(PinnedShape shape, Vector[] vertexes) {

            if (vertexes.length == 3) {
                shape.type = Shape.Types.TRIANGLE;
            } else if (vertexes.length == 4) {
                float dist1 = Vector.getDistance(vertexes[0], vertexes[2]);
                float dist2 = Vector.getDistance(vertexes[1], vertexes[3]);
                if (dist1 == dist2) {
                    dist1 = Vector.getDistance(vertexes[0], vertexes[1]);
                    dist2 = Vector.getDistance(vertexes[1], vertexes[2]);
                    if (dist1 == dist2)
                        shape.type = Shape.Types.SQUARE;
                    else shape.type = Shape.Types.RECTANGLE;
                } else
                    shape.type = Shape.Types.POLYGON;
            } else {
                shape.type = Shape.Types.POLYGON;
            }

        }

        private static void setWidthAndHeight(PinnedShape shape, Vector[] vertexes) {

            int w = 0;
            int h = 0;
            int cx, cy, dx, dy;
            for (int cv = 0; cv < vertexes.length; cv++) {
                cx = vertexes[cv].x;
                cy = vertexes[cv].y;
                for (int v = cv + 1; v < vertexes.length; v++) {
                    dx = Math.abs(cx - vertexes[v].x);
                    dy = Math.abs(cy - vertexes[v].y);
                    if (w < dx) {
                        w = dx;
                    }
                    if (h < dy) {
                        h = dy;
                    }
                }
            }
            shape.w = w;
            shape.h = h;
        }

        private static void setSpinRadius(PinnedShape shape, Vector[] vertexes) {

            int maxX = 0, maxY = 0, sumX = 0, sumY = 0;
            int absX, absY;
            for (Vector vertex : vertexes) {
                absX = Math.abs(vertex.x);
                absY = Math.abs(vertex.y);

                sumX += absX;
                sumY += absY;


                if (absX > maxX)
                    maxX = absX;
                if (absY > maxY)
                    maxY = absY;
            }

            //maxRadius
            float maxRadius = Vector.getMagnitude(maxX, maxY);
            if (maxRadius - (int) maxRadius == 0.0f) {
                shape.maxSpinRadius = (int) maxRadius;
            } else {
                shape.maxSpinRadius = (int) maxRadius + 1;
            }

            //aveRadius
            shape.mediumSpinRadius =
                    Math.round(Vector.getMagnitude(sumX / vertexes.length, sumY / vertexes.length));

        }

        private static void setPerimeterPointsAndVertexes(PinnedShape shape, Vector[] vertexes) {

            //Temp list
            final ArrayList<ShapePoint> tempPerimeterPoints = new ArrayList<>();

            //vertexes
            shape.vertexes = new ShapePoint[vertexes.length];

            //Iteration Handler
            OnIterationActionHandler<Vector[], Void, Void> setPerimeterShapePointsActionHandler =
                    new OnIterationActionHandler<Vector[], Void, Void>() {

                        Vector currentNormal;
                        int vertexesCount = 0;

                        @Override
                        public void action(int x, int y) {

                            //Vertexes
                            Vector v1 = getExtraOne()[0];
                            Vector v2 = getExtraOne()[1];

                            //PerimeterPoint
                            ShapePoint perimeterPoint = new ShapePoint(x, y);
                            perimeterPoint.type = ShapePoint.PERIMETER_POINT_LABEL;
                            perimeterPoint.closerPerimeterPoint = null;
                            perimeterPoint.distanceToCloserPerimeterPoint = 0;
                            perimeterPoint.distanceToCenter = Vector.getMagnitude(x, y);

                            //Normal
                            Vector normal = Vector.getNormalVector(new Vector(v2.x - v1.x, v2.y - v1.y),
                                    ShapePoint.DEFAULT_NORMAL_VECTOR_MAGNITUDE);
                            if (normal != currentNormal)
                                currentNormal = normal;
                            perimeterPoint.normal = currentNormal;
                            tempPerimeterPoints.add(perimeterPoint);

                            //Set Vertex
                            if (x == v1.x && y == v1.y) {
                                shape.vertexes[vertexesCount] = perimeterPoint;
                                vertexesCount++;
                            }
                        }
                    };

            //Copy vertexes
            Vector[] vertexesCopy = new Vector[vertexes.length];
            for (int v = 0; v < vertexes.length; v++) {
                vertexesCopy[v] = vertexes[v].getCopy();
            }

            //Iterate Perimeter
            Iterations.iteratePolygonPerimeter(vertexesCopy, setPerimeterShapePointsActionHandler);

            //Setting perimeterPoints
            shape.perimeterPoints = tempPerimeterPoints.toArray(new ShapePoint[]{});

            //UnSorting Points
            Tools.sortRandomly(tempPerimeterPoints);

            //Setting unsortedPerimeterPoints
            shape.unsortedPerimeterPoints = tempPerimeterPoints.toArray(new ShapePoint[]{});

        }

    }

}
