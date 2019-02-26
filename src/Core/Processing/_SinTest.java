package Core.Processing;

import Core.Engine.Tools.Tools;
import Core.Engine.Vector.Vector;
import processing.core.PApplet;

public class _SinTest extends PApplet {

    public static void main(String... args) {
        //Write your own main class path
        PApplet.main("Core.Processing._SinTest");
    }

    @Override
    public void settings() {
        size(500, 500);
    }

    Vector[] points;
    double da = Math.PI / 100;

    @Override
    public void setup() {

        points = new Vector[]{
                //new Vector(100, 100),
                //new Vector(-100, 100),
                //new Vector(-100, -100),
                new Vector(100, -100)
        };

        stroke(0);
        //noFill();
    }

    @Override
    public void mousePressed() {
        super.mousePressed();

        da = -da;

    }

    @Override
    public void draw() {

        float PIx2 = PI * 2;
        float a = degrees(map(mouseX, 0, width, 0, PIx2));
        System.out.println("Angle: " + a + "  sin: " + sin(a));

    }
}