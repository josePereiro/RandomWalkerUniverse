package Core.Engine;

import processing.core.PApplet;
import processing.core.PImage;

import java.awt.*;
import java.util.Random;

public class CheckingAreaTest extends PApplet {

    public static void main(String... args) {
        //Write your own main class path
        PApplet.main("Core.Engine.CheckingAreaTest");
    }

    @Override
    public void settings() {
        size(500, 500);
    }


    RandomWalkersWorld.PositionValueBoard board;

    @Override
    public void setup() {

        board = new RandomWalkersWorld.PositionValueBoard(width, height);

        Random r = new Random();
        for (int i = 0; i < 100; i++) {
            board.setValue(5, r.nextInt(width), r.nextInt(height));
        }

        noCursor();
    }

    @Override
    public void draw() {
        background(255);
        background(new PImage(board.getImage()));

        int x = mouseX;
        if (x < 10) x = 10;
        if (x > width - 11) x = width - 11;
        int y = mouseY;
        if (y < 10) y = 10;
        if (y > height - 11) y = height - 11;
        if (board.checkCircularArea(x, y, 10) ||
                board.checkCircularArea(x,y,9)) {
            noStroke();
            fill(Color.RED.getRGB(),80);
            ellipse(x, y, 10 * 2, 10 * 2);
        } else {
            stroke(0);
            noFill();
            ellipse(x, y, 10 * 2, 10 * 2);
        }

    }

}