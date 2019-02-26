package Core.Engine.Shape;

import Core.Engine.Exceptions.IllegalVertexesCountException;
import Core.Engine.Exceptions.IllegalVertexesPositionException;
import Core.Engine.Tools.Tools;
import Core.Engine.Vector.Vector;
import Core.Processing.ProcessingTools;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.Random;

public class PinnedShapeTest extends PApplet {

    public static void main(String... args) {
        //Write your own main class path
        PApplet.main("Core.Engine.Shape.PinnedShapeTest");
    }

    @Override
    public void settings() {
        size(500, 500);
    }


    @Override
    public void setup() {

        imageMode(CENTER);

    }

    PImage pImage;
    int millis = 0;

    @Override
    public void draw() {

        delay(500);
        background(255);
        createShapeAndPImage();

        if (pImage != null)
            image(pImage, width / 2, height / 2);


    }

    @Override
    public void mousePressed() {
        super.mousePressed();

        createShapeAndPImage();
    }

    private void createShapeAndPImage() {
        Random r = new Random();
        Vector[] vertexes = Tools.getClockDirectionVertexes(r.nextInt(100) + 100,
                r.nextInt(47) + 3);
        try {
            PinnedShape shape = PinnedShape.PolygonalShapeFactory.createShape(vertexes);
            pImage = ProcessingTools.createPImage(shape.getFullColorImage(), this);
            System.out.println(shape);
        } catch (IllegalVertexesCountException | IllegalVertexesPositionException e) {
            System.out.println(e.getMessage());
        }


    }

}