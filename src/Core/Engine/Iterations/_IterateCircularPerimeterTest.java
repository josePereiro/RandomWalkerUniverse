package Core.Engine.Iterations;

import processing.core.PApplet;

public class _IterateCircularPerimeterTest extends PApplet {

    public static void main(String... args) {
        //Write your own main class path
        PApplet.main("Core.Engine.Iterations._IterateCircularPerimeterTest");
    }

    @Override
    public void settings() {
        size(500, 500);
    }

    OnIterationActionHandler onIterationActionHandler;

    @Override
    public void setup() {

        onIterationActionHandler = new OnIterationActionHandler() {
            @Override
            public void action(int x, int y) {
                point(x, y);
            }
        };

    }

    @Override
    public void draw() {

        background(255);
        stroke(0);
        Iterations.iterateCircularPerimeter(mouseX, mouseY, (mouseX / (mouseY + 1) * 10) + 10, onIterationActionHandler);
    }

}