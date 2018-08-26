package Core.Processing;

import Core.Engine.Exceptions.IllegalVertexesCountException;
import Core.Engine.Exceptions.IllegalVertexesPositionException;
import Core.Engine.Shape.Shape;
import Core.Engine.Tools.Tools;
import Core.Engine.Vector.Vector;
import processing.core.PApplet;
import processing.core.PImage;
import processing.event.MouseEvent;

public class ProcessingTemplate extends PApplet {

    public static void main(String... args) {
        //Write your own main class path
        PApplet.main("Core.Processing.ProcessingTemplate");
    }

    @Override
    public void settings() {
        size(500, 500);
    }

    Shape shape;
    PImage image;

    @Override
    public void setup() {

        Vector[] vertexes = Tools.getClockDirectionVertexes(50, 10);

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
        shape.rotate();
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