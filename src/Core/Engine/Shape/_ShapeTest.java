package Core.Engine.Shape;

import Core.Engine.Exceptions.IllegalVertexesCountException;
import Core.Engine.Exceptions.IllegalVertexesPositionException;
import Core.Engine.Tools.Tools;
import Core.Engine.Vector.Vector;
import Core.Processing.ProcessingTools;
import processing.core.PApplet;
import processing.event.MouseEvent;

import java.util.Random;

public class _ShapeTest extends PApplet {

    public static void main(String... args) {
        //Write your own main class path
        PApplet.main("Core.Engine.Shape._ShapeTest");
    }

    @Override
    public void settings() {
        size(500, 500);
    }

    Shape shape;
    float t = 0;
    int drawMode;

    //Modes
    private static final int ALL_POINTS = 48;
    private static final int PERIMETER = 56;
    private static final int INNER_POINTS = 747;
    private static final int NORMAL_POINTS = 852;
    private static final int VERTEXES = 658;

    @Override
    public void setup() {

        Random r = new Random();
        Vector[] vertexes = Tools.getClockDirectionVertexes(100, r.nextInt(20) + 4);

        try {
            shape = Shape.createPolygonalShape(vertexes);
        } catch (IllegalVertexesCountException | IllegalVertexesPositionException e) {
            e.printStackTrace();
            setup();
        }

        noCursor();
        fill(0);

        drawMode = ALL_POINTS;

    }

    @Override
    public void draw() {

        background(255);

        //Rotate
        rotateShape();
        if (drawMode == ALL_POINTS)
            ProcessingTools.drawShapePoints(mouseX, mouseY, shape, this);
        else if (drawMode == VERTEXES) {
            for (ShapePoint vertex : shape.getVertexes()) {
                ellipse(mouseX + vertex.x, mouseY + vertex.y, 1, 1);
            }
        } else if (drawMode == PERIMETER) {
            for (ShapePoint perimeterPoint : shape.getPerimeterPoints()) {
                point(mouseX + perimeterPoint.x, mouseY + perimeterPoint.y);
            }
        }

        drawText();
    }

    public void rotateShape() {

        float lastNoise = noise(t);
        t += 0.05F;
        float currentNoise = noise(t);
        if (lastNoise < currentNoise)
            shape.rotateClockWise();
        else shape.rotateCounterClockWise();

    }

    public void drawText() {
        String text = "Shape: " + shape.toString();
        text += "\n";
        text += "TotalPointsCount: " + shape.getAllPoints().length;
        text += "\n";
        text += "PerimeterPointsCount: " + shape.getPerimeterPoints().length;
        text += "\n";
        text += "InnerPointsCount: " + shape.getInnerPoints().length;
        text += "\n";
        text += "maxSpinRadius: " + shape.getMaxSpinRadius();

        text(text, 20, 20);
    }

    @Override
    public void mousePressed(MouseEvent event) {
        super.mousePressed(event);

        setup();

    }

    @Override
    public void keyPressed() {
        super.keyPressed();

        if (key == 'v') {
            drawMode = VERTEXES;
        } else if (key == 'p'){
            drawMode = PERIMETER;
        }
        else {
            drawMode = ALL_POINTS;
        }

    }
}