package Core.Engine.Tools;

import Core.Engine.Vector.Vector;
import processing.core.PApplet;

public class GetAngleTest extends PApplet {

    public static void main(String... args) {
        //Write your own main class path
        PApplet.main("Core.Engine.Tools.GetAngleTest");
    }

    @Override
    public void settings() {
        size(500, 500);
    }

    @Override
    public void setup() {

        noCursor();

    }

    @Override
    public void draw() {

        background(255);
        noFill();
        pushMatrix();
        translate(width / 2, height / 2);

        //XAxis
        ellipse(0, 0, 5, 5);
        ellipse(100, 0, 5, 5);
        line(-100, 0, 100, 0);

        //YAxis
        ellipse(0, 0, 5, 5);
        ellipse(0, 100, 5, 5);
        line(0, -100, 0, 100);

        //Vector
        Vector point = new Vector(mouseX - width / 2, mouseY - height / 2);
        ellipse(point.x, point.y, 10, 10);
        line(0, 0, point.x, point.y);

        popMatrix();


        //Info
        fill(0);
        String text = point.toString();
        text += "\n";
        text += "Angle: " + degrees(Tools.getAngle(point)) + " (" + Tools.getAngle(point) + ")";

        text(text, 20, 20);

    }


}