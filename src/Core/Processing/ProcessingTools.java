package Core.Processing;

import Core.Engine.PositionValueBoard.LabeledPositionsBoard;
import Core.Engine.Shape.PinnedShape;
import Core.Engine.Shape.Shape;
import Core.Engine.Shape.ShapePoint;
import Core.Engine.Tools.Tools;
import Core.Engine.Vector.Vector;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PImage;

import java.awt.*;
import java.awt.image.BufferedImage;
public class ProcessingTools {


    public static PImage zoom(int x, int y, int w, int h, int zoom, PApplet context) {

        PImage area = context.get(x, y, w, h);
        area.resize(area.width * zoom, area.height * zoom);
        return area;
    }

    public static PImage createPImage(BufferedImage image, PApplet context) {
        PImage pImage = context.createImage(image.getWidth(), image.getHeight(), PConstants.RGB);
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                pImage.set(x, y, image.getRGB(x, y));
            }
        }
        return pImage;
    }

    /**
     * Draw a shape in a given position of the context,
     * it will return the layout configuration at his initial values.
     *
     * @param centerX
     * @param centerY
     * @param shape
     * @param context
     */
    public static void drawShapePoints(int centerX, int centerY, PinnedShape shape, PApplet context) {

        //initial layout
        int initialStrokeColor = context.g.strokeColor;
        float initialStrokeWeight = context.g.strokeWeight;

        //layout
        context.strokeWeight(1);

        //Points Colors
        int innerPointsColor = Color.BLUE.getRGB();
        int perimeterPointsColor = Color.BLACK.getRGB();
        int vertexesPointsColor = Color.YELLOW.getRGB();
        int normalPointsColor = Color.RED.getRGB();
        int centerPointColor = Color.BLACK.getRGB();

        //Perimeter Points
        context.stroke(perimeterPointsColor);
        for (ShapePoint perimeterPoint : shape.getPerimeterPoints()) {
            context.point(perimeterPoint.x + centerX, perimeterPoint.y + centerY);
        }

        //InnerPoints
        context.stroke(innerPointsColor);
        for (ShapePoint innerPoint : shape.getInnerPoints()) {
            context.point(innerPoint.x + centerX, innerPoint.y + centerY);
        }

        //Vertexes
        context.stroke(vertexesPointsColor);
        for (ShapePoint vertexPoint : shape.getVertexes()) {
            context.point(vertexPoint.x + centerX, vertexPoint.y + centerY);
        }

        //Center
        context.stroke(centerPointColor);
        context.point(centerX, centerY);

        //NormalPoints
        context.stroke(normalPointsColor);
        float normalVectorMagnitude = shape.getMaxSpinRadius() * 0.1F;
        Vector normal;
        for (ShapePoint perimeterPoint : shape.getPerimeterPoints()) {
            normal = perimeterPoint.normal.getCopy();
            Vector.setMagnitude(normalVectorMagnitude, normal);
            context.point(perimeterPoint.x + normal.x + centerX, perimeterPoint.y + normal.y + centerY);
        }

        //setting bac layout
        context.stroke(initialStrokeColor);
        context.strokeWeight(initialStrokeWeight);

    }

    public static void drawShapePoints(int centerX, int centerY, Shape shape, PApplet context) {

        //initial layout
        int initialStrokeColor = context.g.strokeColor;
        float initialStrokeWeight = context.g.strokeWeight;

        //layout
        context.strokeWeight(1);

        //Points Colors
        int innerPointsColor = Color.BLUE.getRGB();
        int perimeterPointsColor = Color.BLACK.getRGB();
        int vertexesPointsColor = Color.YELLOW.getRGB();
        int normalPointsColor = Color.RED.getRGB();
        int centerPointColor = Color.BLACK.getRGB();

        //Perimeter Points
        context.stroke(perimeterPointsColor);
        for (ShapePoint perimeterPoint : shape.getPerimeterPoints()) {
            context.point(perimeterPoint.x + centerX, perimeterPoint.y + centerY);
        }

        //InnerPoints
        context.stroke(innerPointsColor);
        for (ShapePoint innerPoint : shape.getInnerPoints()) {
            context.point(innerPoint.x + centerX, innerPoint.y + centerY);
        }

        //Vertexes
        context.stroke(vertexesPointsColor);
        for (ShapePoint vertexPoint : shape.getVertexes()) {
            context.point(vertexPoint.x + centerX, vertexPoint.y + centerY);
        }

        //Center
        context.stroke(centerPointColor);
        context.point(centerX, centerY);

        //NormalPoints
        context.stroke(normalPointsColor);
        float normalVectorMagnitude = shape.getMaxSpinRadius() * 0.1F;
        Vector normal;
        for (ShapePoint perimeterPoint : shape.getPerimeterPoints()) {
            normal = perimeterPoint.normal.getCopy();
            Vector.setMagnitude(normalVectorMagnitude, normal);
            context.point(perimeterPoint.x + normal.x + centerX, perimeterPoint.y + normal.y + centerY);
        }

        //setting bac layout
        context.stroke(initialStrokeColor);
        context.strokeWeight(initialStrokeWeight);

    }

    public static void drawShapeVertexes(int centerX, int centerY, Shape shape, PApplet context) {
        //initial layout
        int initialStrokeColor = context.g.strokeColor;
        float initialStrokeWeight = context.g.strokeWeight;

        //Vertexes
        context.stroke(Color.BLACK.getRGB());
        for (ShapePoint vertexPoint : shape.getVertexes()) {
            context.point(vertexPoint.x + centerX, vertexPoint.y + centerY);
        }

        //setting bac layout
        context.stroke(initialStrokeColor);
        context.strokeWeight(initialStrokeWeight);
    }

    public static PImage getBoardImage(LabeledPositionsBoard board, PApplet context) {


        PImage pImage = context.createImage(board.getW(), board.getH(), PConstants.RGB);

        for (int x = 0; x < board.getW(); x++) {
            for (int y = 0; y < board.getH(); y++) {
                if (board.isOccupied(x, y))
                    pImage.set(x, y, Color.BLACK.getRGB());
                else
                    pImage.set(x, y, Color.WHITE.getRGB());
            }
        }

        return pImage;

    }

    public static PImage getBoardImage(LabeledPositionsBoard board, int[] values, int[] colors, PApplet context) {


        PImage pImage = context.createImage(board.getW(), board.getH(), PConstants.RGB);

        for (int x = 0; x < board.getW(); x++) {
            for (int y = 0; y < board.getH(); y++) {
                if (board.isOccupied(x, y)) {
                    int index = Tools.indexOf(board.getLabelAt(x, y), values);
                    if (index != -1)
                        pImage.set(x, y, colors[index]);
                    else
                        pImage.set(x, y, Color.BLACK.getRGB());
                } else
                    pImage.set(x, y, Color.WHITE.getRGB());
            }
        }

        return pImage;

    }

}
