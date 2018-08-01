package Core.Proccessing;

import Core.Engine.RandomWalkersWorld;
import processing.core.PApplet;

import java.awt.*;

public class ProcessingTemplate extends PApplet {

    public static void main(String... args) {
        //Write your own main class path
        PApplet.main("Core.Proccessing.ProcessingTemplate");
    }

    @Override
    public void settings() {
        size(500, 500);
    }

    RandomWalkersWorld.Point[] positions;
    RandomWalkersWorld.TendencyBoard board;

    @Override
    public void setup() {

        board = new RandomWalkersWorld.TendencyBoard(width, height);
        positions = new RandomWalkersWorld.Point[5000];
        for (int i = 0; i < positions.length; i++)
            positions[i] = new RandomWalkersWorld.Point(width / 2, height / 2);

    }

    @Override
    public void draw() {

        background(255);
        fill(0);
        noStroke();
        for (int i = 0; i < positions.length; i++) {
            positions[i] = board.tryDirection(positions[i].getX(), positions[i].getY(), 1);
            ellipse(positions[i].getX(), positions[i].getY(), 5, 5);
        }
        stroke(0);
        fill(Color.BLUE.getRGB());
        ellipse(width / 2, height / 2, 10, 10);

    }
}
