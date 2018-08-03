package Core.Processing.TestsField;

import Core.Engine.RandomWalkersWorld;
import processing.core.PApplet;

import java.awt.*;

public class GlobalTendencyTest extends PApplet {

    public static void main(String... args) {
        //Write your own main class path
        PApplet.main("Core.Processing.TestsField.GlobalTendencyTest");
    }

    @Override
    public void settings() {
        size(500, 600);
    }

    //Fields
    RandomWalkersWorld world;
    Point globalTendencyPosition;
    int walkerNumbers;

    @Override
    public void setup() {

        walkerNumbers = 8000;
        world = new RandomWalkersWorld(width, height);
        for (int i = 0; i < walkerNumbers; i++) {
            world.addSquareWalker(width / 2, height / 2, 5);
        }

    }

    @Override
    public void draw() {

        //Simulation
        world.run(50);

        background(255);
        RandomWalkersWorld.Vector position;
        noStroke();
        fill(0);
        text(frameRate, 20, 20);
        for (int i = 0; i < walkerNumbers; i++) {
            position = world.getWalkerPosition(i);
            ellipse(position.x, position.y, 5, 5);
        }

        stroke(0);
        fill(Color.YELLOW.getRGB());
        ellipse(width / 2, height / 2, 8, 8);
        if (globalTendencyPosition != null) {
            fill(Color.BLUE.getRGB());
            ellipse(globalTendencyPosition.x, globalTendencyPosition.y, 8, 8);
            line(width/2, height/2, globalTendencyPosition.x, globalTendencyPosition.y);
        }


    }

    @Override
    public void mousePressed() {
        super.mousePressed();

        int xT = (int) map(mouseX, 0, width, -5000, 5000);
        int yT = (int) map(mouseY, 0, height, 5000, -5000);
        globalTendencyPosition = new Point(mouseX, mouseY);
        world.setGlobalTendency(xT, yT);
        System.out.println("Global tendency set: " + xT + " , " + yT);

    }

    @Override
    public void keyPressed() {
        super.keyPressed();

        setup();
    }
}
