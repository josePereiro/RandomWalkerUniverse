package Core.Engine.Iterations;

import processing.core.PApplet;

public class IterateLineTest extends PApplet {

    public static void main(String... args) {
        //Write your own main class path
        PApplet.main("Core.Engine.Iterations.IterateLineTest");
    }

    @Override
    public void settings() {
        size(500, 500);
    }


    @Override
    public void setup() {
        noCursor();
        stroke(0);
        smooth();
    }

    @Override
    public void draw() {

        background(255);
        stroke(0, 50);
        strokeWeight(2);
        noFill();
        OnIterationActionHandler lineIterationActionHandler = new OnIterationActionHandler() {

            int radius = 10;

            @Override
            public void action(int x, int y) {
                ellipse(x, y, radius, radius);
                point(x, y);
                radius++;
            }
        };
        Iterations.iterateLine(width / 2, height / 2, mouseX, mouseY, lineIterationActionHandler);


    }


}