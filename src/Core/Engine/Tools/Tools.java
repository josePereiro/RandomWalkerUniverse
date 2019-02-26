package Core.Engine.Tools;

import Core.Engine.Iterations.Iterations;
import Core.Engine.Iterations.OnIterationActionHandler;
import Core.Engine.PositionValueBoard.LabeledPositionsBoard;
import Core.Engine.Vector.Vector;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Tools {

    private final static Random r = new Random();
    public static final float PI = (float) Math.PI;
    public static final float PIx2 = 2 * PI;

    /**
     * @param d
     * @return
     */
    public static int roundedSqrt(float d) {
        return (int) Math.round(Math.sqrt(d));
    }

    public static int roundedSqrt(int i) {
        return (int) Math.round(Math.sqrt(i));
    }

    public static <T> void sortRandomly(ArrayList<T> arrayList) {
        T o;
        int randomObjectIndex;
        int randomNumberLimit = arrayList.size();
        for (int i = 0; i < arrayList.size(); i++) {
            randomObjectIndex = r.nextInt(randomNumberLimit);
            randomNumberLimit--;
            o = arrayList.get(randomObjectIndex);
            arrayList.remove(randomObjectIndex);
            arrayList.add(o);
        }
    }

    public static <T> boolean deleteDuplicates(ArrayList<T> arrayList) {
        T o;
        boolean duplicates = false;
        for (int cp = 0; cp < arrayList.size(); cp++) {
            o = arrayList.get(cp);
            for (int p = cp + 1; p < arrayList.size(); p++) {
                if (o.equals(arrayList.get(p))) {
                    arrayList.remove(p);
                    p--;
                    duplicates = true;
                }
            }
        }
        return duplicates;
    }

    public static Vector[] getPolygonalPerimeter(Vector[] vertexes) {

        final ArrayList<Vector> tempPerimeter = new ArrayList<>();

        OnIterationActionHandler<Vector, Void, Void> setPerimeterShapePointsActionHandler =
                new OnIterationActionHandler<Vector, Void, Void>() {
                    @Override
                    public void action(int x, int y) {
                        tempPerimeter.add(new Vector(x, y));
                    }
                };

        //First segments
        Vector v1, v2;
        for (int v = 0; v + 1 < vertexes.length; v++) {
            v1 = vertexes[v];
            v2 = vertexes[v + 1];
            Iterations.iterateLine(v1.x, v1.y, v2.x, v2.y, setPerimeterShapePointsActionHandler);
        }

        //Last segment
        v1 = vertexes[vertexes.length - 1];
        v2 = vertexes[0];
        Iterations.iterateLine(v1.x, v1.y, v2.x, v2.y, setPerimeterShapePointsActionHandler);

        //delete duplicates
        Tools.deleteDuplicates(tempPerimeter);

        return tempPerimeter.toArray(new Vector[]{});
    }

    public static int getMaxSpinRadius(Vector[] points) {
        int maxX = 0, maxY = 0;
        int absX, absY;
        for (Vector point : points) {
            absX = Math.abs(point.x);
            absY = Math.abs(point.y);
            if (absX > maxX)
                maxX = absX;
            if (absY > maxY)
                maxY = absY;
        }

        float mag = Vector.getMagnitude(maxX, maxY);
        if (mag - (int) mag == 0.0f) {
            return (int) mag;
        } else {
            return (int) mag + 1;
        }
    }

    public static void translate(ArrayList<Vector> vectors, int xTranslation, int yTranslation) {
        for (Vector vector : vectors) {
            vector.x += xTranslation;
            vector.y += yTranslation;
        }
    }

    /**
     * Return a new board with a higher resolution. All the new coordinates will be filled
     * with the emptyValue of the lowResolutionBoard. The action of this methods do
     * not lose information, a next call of getLowerResolutionBoard with the same factor
     * will return a Board equivalent to the lowResolutionBoard used in this method.
     *
     * @param lowResolutionBoard the lowResolutionBoard
     * @param xFactor            the times the resolution will be increased
     * @param yFactor            the times the resolution will be increased
     * @return the highResolutionBoard...
     */
    public static LabeledPositionsBoard getHigherResolutionBoard(LabeledPositionsBoard lowResolutionBoard,
                                                                 int xFactor, int yFactor) {
        LabeledPositionsBoard highResolutionBoard =
                new LabeledPositionsBoard(lowResolutionBoard.getW() * xFactor,
                        lowResolutionBoard.getH() * yFactor, lowResolutionBoard.getEmptyLabel());

        for (int x = 0; x < lowResolutionBoard.getW(); x++) {
            for (int y = 0; y < lowResolutionBoard.getH(); y++) {
                highResolutionBoard.setLabel(lowResolutionBoard.getLabelAt(x, y), x * xFactor, y * yFactor);
            }
        }

        return highResolutionBoard;
    }

    /**
     * Returns a new Board with a lower resolution. It is not recomended to use this method with
     * boards with more than one value, beside the emptyValue, do to the lose of information. This method
     * will provoke a lose of information compare with the information of the highResolutionBoard.
     *
     * @param highResolutionBoard
     * @param xFactor
     * @param yFactor
     * @return
     */
    public static LabeledPositionsBoard getLowerResolutionBoard(LabeledPositionsBoard highResolutionBoard,
                                                                int xFactor, int yFactor) {
        LabeledPositionsBoard lowResolutionBoard =
                new LabeledPositionsBoard(highResolutionBoard.getW() / xFactor + 1,
                        highResolutionBoard.getH() / yFactor + 1, highResolutionBoard.getEmptyLabel());

        for (int x = 0; x < lowResolutionBoard.getW(); x++) {
            for (int y = 0; y < lowResolutionBoard.getH(); y++) {

                lowResolutionBoard.setLabel(highResolutionBoard.getLabelAt(x * xFactor, y * yFactor), x, y);
            }
        }
        return lowResolutionBoard;
    }

    public static Vector[] getInnerArea(Vector[] perimeter) {

        Vector[] translatedPerimeter = new Vector[perimeter.length];
        int boardSize = getMaxSpinRadius(perimeter) * 2 + 1;
        int translation = boardSize / 2;
        for (int p = 0; p < perimeter.length; p++) {
            translatedPerimeter[p] = new Vector(perimeter[p].x + translation,
                    perimeter[p].y + translation);
        }

        LabeledPositionsBoard helperBoard = new LabeledPositionsBoard(boardSize, boardSize);
        //Set Perimeter
        int perimeterPointLabel = 1;
        for (Vector translatedPerimeterPoint : translatedPerimeter) {
            helperBoard.setLabel(perimeterPointLabel, translatedPerimeterPoint.x, translatedPerimeterPoint.y);
        }

        return null;
    }

    public static Vector[] getClockDirectionVertexes(int maxRadius, int pointsCount) {

        double[] angles = new double[pointsCount];
        double PIx2 = Math.PI * 2;
        for (int p = 0; p < pointsCount; p++) {
            angles[p] = r.nextDouble() * PIx2;
        }
        Arrays.sort(angles);

        ArrayList<Vector> vertexes = new ArrayList<>();
        int x, y, mag;
        for (int a = angles.length - 1; a >= 0; a--) {
            mag = r.nextInt(maxRadius - 1) + 1;
            x = Math.round((float) Math.cos(angles[a]) * mag);
            y = Math.round((float) Math.sin(angles[a]) * mag);
            vertexes.add(new Vector(x, y));
        }

        return vertexes.toArray(new Vector[]{});
    }

    /**
     * Will return the height of the triangle relative to the segment v1 -> v2.
     *
     * @param v1
     * @param v2
     * @param v3
     * @return
     */
    public static float getTriangleHeight(Vector v1, Vector v2, Vector v3) {

        float a = Vector.getMagnitude(v1.x - v2.x, v1.y - v2.y);
        float b = Vector.getMagnitude(v2.x - v3.x, v2.y - v3.y);
        float c = Vector.getMagnitude(v3.x - v1.x, v3.y - v1.y);

        float t = (float) Math.sqrt((a + b - c) * (a - b + c) * (-a + b + c) * (a + b + c)) / 2;

        return t / a;
    }

    /**
     * @param toCheck
     * @param linePoint1
     * @param linePoint2
     * @param precision
     * @return
     */
    public static boolean isOverLine(Vector toCheck, Vector linePoint1, Vector linePoint2, float precision) {
        if (linePoint1.x != linePoint2.x) {
            float m = ((float) (linePoint1.y - linePoint2.y)) / (linePoint1.x - linePoint2.x);
            float c = linePoint1.y - m * linePoint1.x;

            int calculatedY = Math.round(m * toCheck.x + c);
            int calculatedX = Math.round((toCheck.y - c) / m);
            if (calculatedY != toCheck.y && calculatedX != toCheck.x) {
                float distance = getTriangleHeight(new Vector(toCheck.x, calculatedY),
                        new Vector(calculatedX, toCheck.y),
                        toCheck);
                return distance < precision;
            } else return true;
        } else {
            return Math.abs(toCheck.x - linePoint1.x) < precision;
        }
    }

    /**
     * get the center of a given group of points!!!
     *
     * @param coordinates the points
     * @return the center, just like the center of mass if all the points have the same mass
     */
    public static Vector getCenter(Vector[] coordinates) {
        int xCount = 0, yCount = 0;
        for (Vector coordinate : coordinates) {
            xCount += coordinate.x;
            yCount += coordinate.y;
        }
        return new Vector(Math.round(xCount / coordinates.length), Math.round(yCount / coordinates.length));
    }

    public static void fillContinueArea(LabeledPositionsBoard board, int targetLabel, int newLabel, Vector startPoint, ArrayList<Vector> areaPoints) {

        //Check areaPoints
        if (areaPoints == null) {
            areaPoints = new ArrayList<>();
        }

        //Check StartPoint
        if (!board.isWithinBoard(startPoint)) return;
        if (board.getLabelAt(startPoint) != targetLabel) return;
        if (board.getLabelAt(startPoint) == newLabel) return;

        //Adding startPoint
        areaPoints.add(startPoint);

        Vector currentPoint, neighbor;
        for (int p = 0; p < areaPoints.size(); p++) {

            //CurrentPoint
            currentPoint = areaPoints.get(p);

            //Neighbors
            neighbor = new Vector(currentPoint.x - 1, currentPoint.y);
            if (neighbor.x >= 0 && board.getLabelAt(neighbor) == targetLabel) {
                board.setLabel(newLabel, neighbor);
                areaPoints.add(neighbor);
            }

            neighbor = new Vector(currentPoint.x + 1, currentPoint.y);
            if (neighbor.x < board.getW() && board.getLabelAt(neighbor) == targetLabel) {
                board.setLabel(newLabel, neighbor);
                areaPoints.add(neighbor);
            }

            neighbor = new Vector(currentPoint.x, currentPoint.y - 1);
            if (neighbor.y >= 0 && board.getLabelAt(neighbor) == targetLabel) {
                board.setLabel(newLabel, neighbor);
                areaPoints.add(neighbor);
            }

            neighbor = new Vector(currentPoint.x, currentPoint.y + 1);
            if (neighbor.y < board.getH() && board.getLabelAt(neighbor) == targetLabel) {
                board.setLabel(newLabel, neighbor);
                areaPoints.add(neighbor);
            }
        }

    }

    /**
     * Get the positive side angle of the vector and the x axis.
     *
     * @param vector
     * @return
     */
    public static float getAngle(Vector vector) {

        float angle = (float) Math.atan(vector.y / (float) vector.x);
        float PI = (float) Math.PI;

        if (vector.x == 0 && vector.y > 0) {
            return PI / 2;
        }

        //I
        else if ((vector.x > 0 && vector.y >= 0)) {
            return angle;
        }
        //II
        else if (vector.x <= 0 && vector.y > 0) {
            return PI + angle;
        }
        //III
        else if (vector.x < 0) {
            return PI + angle;
        }
        //IV
        else {
            return 2 * PI + angle;
        }

    }

    /**
     * Rotate a given vector in a give angle.
     *
     * @param vector the vector
     * @param angle  the angle in radiants
     */
    public static void rotate(Vector vector, float angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        int x = (int) Math.round(vector.x * cos - vector.y * sin);
        vector.y = (int) Math.round(vector.x * sin + vector.y * cos);
        vector.x = x;
    }

    public static void rotate(Vector vector, float angle, float targetMagnitude) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        int x = (int) Math.round(vector.x * cos - vector.y * sin);
        vector.y = (int) Math.round(vector.x * sin + vector.y * cos);
        vector.x = x;
        Vector.setMagnitude(targetMagnitude, vector);
    }

    public static Vector getSegmentMiddlePoint(int x1, int y1, int x2, int y2) {
        return new Vector(Math.round((x1 + x2) / 2), Math.round((y1 + y2) / 2));
    }

    public static Vector getMiddleRightPoint(int x1, int y1, int x2, int y2) {
        Vector middle = getSegmentMiddlePoint(x1, y1, x2, y2);

        if (Math.abs(x1 - x2) > Math.abs(y1 - y2)) {
            //move in Y
            if (x1 < x2) {
                middle.y--;
            } else {
                middle.y++;
            }
        } else {
            //move in X
            if (y1 < y2) {
                middle.x++;
            } else {
                middle.x--;
            }
        }

        return middle;
    }

    /**
     * Returns the index of the given element on the array, -1 otherwise.
     *
     * @param array
     * @param element
     * @return
     */
    public static int indexOf(int element, int[] array) {

        for (int i = 0; i < array.length; i++) {
            if (array[i] == element)
                return i;
        }

        return -1;
    }
}