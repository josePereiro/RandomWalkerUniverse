package Core.Engine.Tools;

import Core.Engine.Vector.Vector;
import processing.core.PApplet;
import processing.event.MouseEvent;

public class _RotateAndEnsureMagnitudeTest extends PApplet {

    public static void main(String... args) {
        //Write your own main class path
        PApplet.main("Core.Engine.Tools._RotateAndEnsureMagnitudeTest");
    }

    @Override
    public void settings() {
        size(500, 500);
    }

    Vector[] points;
    float da = PI / 500;
    float[] targetMagnitudes;

    @Override
    public void setup() {

        points = new Vector[]{
                new Vector(100, 100),
                new Vector(-100, 100),
                new Vector(100, -100),
                new Vector(-100, -100)
        };

        targetMagnitudes = new float[]{
                points[0].getMagnitude(),
                points[1].getMagnitude(),
                points[2].getMagnitude(),
                points[3].getMagnitude()
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
            for (int p = 0; p < points.length; p++) {
                Vector point = points[p];
                Tools.rotate(point, da, targetMagnitudes[p]);
                line(0, 0, point.x, point.y);
                ellipse(point.x, point.y, 10, 10);
                point(point.x, point.y);
                println(point.getMagnitude());
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