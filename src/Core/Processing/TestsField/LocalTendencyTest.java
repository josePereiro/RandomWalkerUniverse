package Core.Processing.TestsField;

import Core.Engine.RandomWalkersWorld;
import Core.Engine.RandomWalkersWorld.*;
import processing.core.PApplet;
import processing.core.PImage;

import java.awt.*;
import java.awt.Point;

public class LocalTendencyTest extends PApplet {

    public static void main(String... args) {
        //Write your own main class path
        PApplet.main("Core.Processing.TestsField.LocalTendencyTest");
    }

    @Override
    public void settings() {
        size(500, 600);
        smooth();
    }


    //Fields
    private boolean drawTendency = false;
    RandomWalkersWorld world;
    Point localTendencyPosition;
    int walkerNumbers;
    int desireFrameRate = 10;
    PImage background;
    int stepsPerFrame;
    float factor;


    @Override
    public void setup() {

        walkerNumbers = 8000;
        world = new RandomWalkersWorld(width, height);
        for (int i = 0; i < walkerNumbers; i++) {
            world.addSquareWalker(width / 2, height / 2, 1);
        }
        background = null;
        localTendencyPosition = null;
        factor = 1;
        stepsPerFrame = 25;


    }

    @Override
    public void draw() {

        //Simulation
        updateStepsPerFrame();
        world.run(stepsPerFrame);

        //Draw Background
        if (drawTendency) {
            drawTendencies();
        } else {
            background(255);
        }

        //Draw Walkers
        fill(0);
        noStroke();
        Vector position;
        for (int i = 0; i < walkerNumbers; i++) {
            position = world.getWalkerPosition(i);
            ellipse(position.x, position.y, 3, 3);
        }

        //Mouse Handling
        if (mousePressed) {
            if (mouseButton == 37) {
                setTendencyLocation();
                setLocalTendency();
                background = null;
                drawTendencies();
            } else {
                quitLocalTendency();
            }
        }

        //Draw Tendency Location
        drawTendencyLocation();


        //Draw Text
        drawText();

    }

    private void drawText() {
        fill(255);
        text("Frame Rate: " + Math.round(frameRate) + "\n" + "IterationsPerFrame: " + stepsPerFrame, 19, 19);
        text("Frame Rate: " + Math.round(frameRate) + "\n" + "IterationsPerFrame: " + stepsPerFrame, 19, 22);
        text("Frame Rate: " + Math.round(frameRate) + "\n" + "IterationsPerFrame: " + stepsPerFrame, 22, 19);
        text("Frame Rate: " + Math.round(frameRate) + "\n" + "IterationsPerFrame: " + stepsPerFrame, 22, 22);
        fill(0);
        text("Frame Rate: " + Math.round(frameRate) + "\n" + "IterationsPerFrame: " + stepsPerFrame, 20, 20);
    }

    private void setLocalTendency() {

        if (localTendencyPosition == null) return;
        for (int x = 0; x < world.getW(); x++) {
            for (int y = 0; y < world.getH(); y++) {
                world.translateAndSetTendency(x, y,
                        localTendencyPosition.x, localTendencyPosition.y, factor);
            }
        }
        System.out.println("Local Tendency set at: " + localTendencyPosition.x + "," + localTendencyPosition.y + " Factor " + factor);
    }

    private void quitLocalTendency() {
        System.out.println("Local Tendency off, everybody free!!!");
        localTendencyPosition = null;
        background = null;
        world.setGlobalTendency(0, 0);
    }

    private void updateStepsPerFrame() {
        if (frameRate < desireFrameRate) {
            stepsPerFrame--;
        } else {
            stepsPerFrame++;
        }
    }

    private void drawTendencies() {

        if (localTendencyPosition == null) {
            background(255);
            return;
        }

        if (drawTendency) {
            if (background != null) {
                background(background);
                return;
            }
        } else return;

        int margin = 20;
        background(255);
        stroke(Color.GRAY.getRGB());
        fill(Color.GRAY.getRGB(), 150);
        RandomWalkersWorld.Vector tendency;
        int distanceX;
        int distanceY;
        for (int x = margin; x < world.getW(); x = x + margin) {
            for (int y = margin; y < world.getH(); y = y + margin) {
                tendency = world.getTendency(x, y);
                distanceX = Math.abs(x - localTendencyPosition.x);
                distanceY = Math.abs(y - localTendencyPosition.y);
                ellipse(x, y, 3, 3);
                line(x, y, x + constrain(map(tendency.x, -width, width, -margin, margin), -distanceX, distanceX),
                        y - constrain(map(tendency.y, -height, height, -margin, margin), -distanceY, distanceY));
            }
        }

        drawTendencyLocation();
        background = get();
    }

    private void drawTendencyLocation() {
        //Draw tendencyPoint
        if (localTendencyPosition != null) {
            stroke(0);
            fill(Color.BLUE.getRGB());
            ellipse(localTendencyPosition.x, localTendencyPosition.y, 8, 8);
        }

    }

    private void setTendencyLocation() {
        localTendencyPosition = new Point(mouseX, mouseY);
    }

    @Override
    public void keyPressed() {
        super.keyPressed();

        if (key == '+') {
            factor++;
            System.out.println("Tendency Factor increased: " + factor);
            setLocalTendency();
            background = null;
            drawTendencies();
        } else if (key == '-') {
            if (factor > 1) {
                factor--;
                System.out.println("Tendency Factor decreased: " + factor);
            } else factor = 1;
            setLocalTendency();
            background = null;
            drawTendencies();
        } else if (key == 'r') {
            setup();
            System.out.println("System rebooted: ");
        } else if (key == 't') {
            drawTendency = !drawTendency;
            drawTendencies();
        }
    }
}
