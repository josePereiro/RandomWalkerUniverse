package Core.Engine.Iterations;

import Core.Engine.Tools.Tools;
import Core.Engine.Vector.Vector;
import processing.core.PApplet;

import java.util.Random;

public class _PolygonPerimeterIterationTest extends PApplet {

    public static void main(String... args) {
        //Write your own main class path
        PApplet.main("Core.Engine.Iterations._PolygonPerimeterIterationTest");
    }

    @Override
    public void settings() {
        size(500, 500);
    }


    @Override
    public void setup() {
        stroke(0);
        background(255);
        smooth();
    }

    @Override
    public void draw() {
    }

    @Override
    public void mousePressed() {
        super.mousePressed();

        background(255);
        stroke(0, 50);
        noFill();
        OnIterationActionHandler lineIterationActionHandler = new OnIterationActionHandler() {

            float radius = 10;

            @Override
            public void action(int x, int y) {
                ellipse(x, y, radius, radius);
                point(x, y);
                radius += 0.05;
            }
        };
        Random r = new Random();
        Vector[] vertexes = Tools.getClockDirectionVertexes(r.nextInt(140) + 100, r.nextInt(47) + 3);
        pushMatrix();
        translate(width / 2, height / 2);
        Iterations.iteratePolygonPerimeter(vertexes, lineIterationActionHandler);
        popMatrix();
    }
}