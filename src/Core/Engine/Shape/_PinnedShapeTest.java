package Core.Engine.Shape;

import Core.Engine.Exceptions.IllegalVertexesCountException;
import Core.Engine.Exceptions.IllegalVertexesPositionException;
import Core.Engine.Tools.Tools;
import Core.Engine.Vector.Vector;
import Core.Processing.ProcessingTools;
import processing.core.PApplet;

import java.util.Random;

public class _PinnedShapeTest extends PApplet {

    public static void main(String... args) {
        //Write your own main class path
        PApplet.main("Core.Engine.Shape._PinnedShapeTest");
    }

    @Override
    public void settings() {
        size(500, 500);
    }


    @Override
    public void setup() {

        imageMode(CENTER);
        noCursor();

        //Create first shape
        while (true) {
            setNewShape();
            if (shape != null)
                break;
        }
    }

    PinnedShape shape;

    @Override
    public void draw() {

        background(255);

        if (shape != null)
            ProcessingTools.drawShapePoints(mouseX, mouseY, shape, this);


    }

    @Override
    public void mousePressed() {
        super.mousePressed();

        setNewShape();
    }

    private void setNewShape() {
        Random r = new Random();
        Vector[] vertexes = Tools.getClockDirectionVertexes(r.nextInt(100) + 100,
                r.nextInt(47) + 3);
        try {
            shape = PinnedShape.PolygonalShapeFactory.createShape(vertexes);
            System.out.println(shape);
        } catch (IllegalVertexesCountException | IllegalVertexesPositionException e) {
            System.out.println(e.getMessage());
        }


    }

}