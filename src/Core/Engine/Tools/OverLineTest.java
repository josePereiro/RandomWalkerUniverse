package Core.Engine.Tools;

import Core.Engine.Vector.Vector;
import processing.core.PApplet;

public class OverLineTest extends PApplet {

    public static void main(String... args) {
        //Write your own main class path
        PApplet.main("Core.Engine.Tools.OverLineTest");
    }

    @Override
    public void settings() {
        size(500, 500);
    }

    Vector v1 = new Vector(250, 200);
    Vector v2 = new Vector(250, 400);
    Vector toCheck;


    @Override
    public void setup() {
        noCursor();
        stroke(0);
    }

    @Override
    public void draw() {

        background(255);
        line(v1.x, v1.y, v2.x, v2.y);

        toCheck = new Vector(mouseX, mouseY);
        if (Tools.isOverLine(toCheck, v1, v2, 1.5F)) {
            noFill();
        } else {
            fill(100, 100);
        }

        ellipse(mouseX, mouseY, 10, 10);
        point(mouseX, mouseY);

    }


}