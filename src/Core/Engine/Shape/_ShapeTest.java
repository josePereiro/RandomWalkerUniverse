package Core.Engine.Shape;

import Core.Engine.Exceptions.IllegalVertexesCountException;
import Core.Engine.Exceptions.IllegalVertexesPositionException;
import Core.Engine.Tools.Tools;
import Core.Engine.Vector.Vector;
import Core.Processing.ProcessingTools;
import processing.core.PApplet;
import processing.core.PImage;
import processing.event.MouseEvent;

import java.util.Random;

public class ShapeTest extends PApplet {

    public static void main(String... args) {
        //Write your own main class path
        PApplet.main("Core.Engine.Shape.ShapeTest");
    }

    @Override
    public void settings() {
        size(500, 500);
    }

    Shape shape;
    PImage image;
    Random r = new Random();

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

        stroke(0, 150);
        imageMode(CENTER);
        noFill();

        image = ProcessingTools.createPImage(shape.getFullColorImage(), this);


    }

    @Override
    public void draw() {

        background(255);
        image(image, width / 2, height / 2);

        //Rotate
        if (r.nextFloat() > 0.9F)
            if (r.nextFloat() < 0.5F)
                shape.rotateCounterClockWise();
            else shape.rotateClockWise();
        image = ProcessingTools.createPImage(shape.getFullColorImage(), this);

        frameRate(map(mouseX, 0, width, 5, 60));

    }


    @Override
    public void mousePressed(MouseEvent event) {
        super.mousePressed(event);

        setup();

    }

    @Override
    public void keyPressed() {
        super.keyPressed();


    }
}