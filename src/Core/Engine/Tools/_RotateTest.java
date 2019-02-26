package Core.Engine.Tools;

import Core.Engine.Vector.Vector;
import processing.core.PApplet;
import processing.event.MouseEvent;

public class _RotateTest extends PApplet {

    public static void main(String... args) {
        //Write your own main class path
        PApplet.main("Core.Engine.Tools._RotateTest");
    }

    @Override
    public void settings() {
        size(500, 500);
    }

    Vector[] points;
    float da = PI / 500;

    @Override
    public void setup() {

        points = new Vector[]{
                new Vector(100, 100),
                new Vector(-100, 100),
                new Vector(100, -100),
                new Vector(-100, -100)
        };

        stroke(0, 150);
        noFill();
    }

    @Override
    public void draw() {

        //Draw
        //background(255);
        pushMatrix();
        translate(width / 2, height / 2);
        ellipse(0, 0, 02, 2);
        for (int i = 0; i < 5; i++)
            for (Vector point : points) {
                Tools.rotate(point, da);
                line(0, 0, point.x, point.y);
                ellipse(point.x, point.y, 10, 10);
                point(point.x, point.y);
            }
        popMatrix();




    }


    @Override
    public void mousePressed(MouseEvent event) {
        super.mousePressed(event);

        background(255);
        da = PI / map(mouseX, 0, width, 0, 1000) * da / Math.abs(da);
        da = -da;
    }

}