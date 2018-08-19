package Core.Engine;

import processing.core.PApplet;
import processing.core.PImage;

import java.awt.*;
import java.util.Random;

public class SettingPerimetersTest extends PApplet {

    public static void main(String... args) {
        //Write your own main class path
        PApplet.main("Core.Engine.SettingPerimetersTest");
    }

    @Override
    public void settings() {
        size(500, 500);
    }


    RandomWalkersWorld.PositionValueBoard board;

    @Override
    public void setup() {

        board = new RandomWalkersWorld.PositionValueBoard(width, height);
        noFill();
        noSmooth();
        stroke(0,150);
        strokeWeight(1F);
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
        ellipse(x, y, 20, 20);
    }


    @Override
    public void mousePressed() {
        super.mousePressed();

        int x = mouseX;
        if (x < 10) x = 10;
        if (x > width - 11) x = width - 11;
        int y = mouseY;
        if (y < 10) y = 10;
        if (y > height - 11) y = height - 11;
        board.setCircularPerimeter(x, y, 10, 5);
    }
}