package Core.Engine.Vector;

import processing.core.PApplet;

import java.awt.*;

public class NormalTest extends PApplet {

    public static void main(String... args) {
        //Write your own main class path
        PApplet.main("Core.Engine.Vector.NormalTest");
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

        background(0, 0);
        Vector vector = new Vector((int) map(mouseX, 0, width, -width / 2, width / 2),
                (int) map(mouseY, 0, height, -height / 2, height / 2));
        Vector normal = Vector.getNormalVector(vector,100F);

        pushMatrix();
        translate(width / 2, height / 2);
        fill(Color.RED.getRGB());
        ellipse(vector.x, vector.y, 10, 10);
        fill(Color.BLUE.getRGB());
        ellipse(normal.x, normal.y, 10, 10);
        stroke(Color.RED.getRGB());
        line(0, 0, vector.x, vector.y);
        stroke(Color.BLUE.getRGB());
        line(0, 0, normal.x, normal.y);
        noStroke();
        fill(Color.WHITE.getRGB());
        ellipse(0, 0, 5, 5);
        popMatrix();

        String text = vector.toString();
        text += "\n";
        text += normal.toString() + " (Normal)";

        text(text, 10, 20);

    }


}