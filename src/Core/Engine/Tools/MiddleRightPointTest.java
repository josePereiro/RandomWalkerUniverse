package Core.Engine.Tools;

import Core.Engine.Geometry.Geometry;
import Core.Engine.Vector.Vector;
import Core.Processing.ProcessingTools;
import processing.core.PApplet;
import processing.core.PImage;

import java.awt.*;

public class MiddleRightPointTest extends PApplet {

    public static void main(String... args) {
        //Write your own main class path
        PApplet.main("Core.Engine.Tools.MiddleRightPointTest");
    }

    @Override
    public void settings() {
        size(500, 500);
    }


    @Override
    public void setup() {
    }

    @Override
    public void mousePressed() {
        super.mousePressed();

        stroke(0);
        imageMode(CORNER);

    }

    @Override
    public void draw() {

        background(255);
        noCursor();
        Vector rightMiddlePoint = Geometry.getMiddleRightPoint(width / 2, height / 2, mouseX, mouseY);
        stroke(0);
        line(width / 2, height / 2, mouseX, mouseY);
        stroke(Color.BLUE.getRGB());
        point(rightMiddlePoint.x, rightMiddlePoint.y);

        //Zoom
        int z = 2;
        int areaRadius = 20;
        PImage zoom = ProcessingTools.zoom(rightMiddlePoint.x - areaRadius,
                rightMiddlePoint.y - areaRadius,
                areaRadius * 2,
                areaRadius * 2, z, this);
        image(zoom, rightMiddlePoint.x - areaRadius * 2, rightMiddlePoint.y - areaRadius * 2);

    }
}