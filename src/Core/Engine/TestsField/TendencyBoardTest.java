package Core.Engine.TestsField;

import Core.Engine.RandomWalkersWorld;
import processing.core.PApplet;

import java.awt.*;

public class TendencyBoardTest extends PApplet {

    public static void main(String... args) {
        //Write your own main class path
        PApplet.main("Core.Engine.TestsField.TendencyBoardTest");
    }

    @Override
    public void settings() {
        size(500, 500);
    }

    //Fields



    @Override
    public void setup() {

        board = new RandomWalkersWorld.TendencyBoard(width, height);
        positions = new RandomWalkersWorld.Point[5000];
        for (int i = 0; i < positions.length; i++)
            positions[i] = new RandomWalkersWorld.Point(width / 2, height / 2);
        globalTendencyPosition = null;
    }

    @Override
    public void draw() {


    }

    @Override
    public void mousePressed() {
        super.mousePressed();

        int xT = (int) map(mouseX, 0, width, -5000, 5000);
        int yT = (int) map(mouseY, 0, width, 5000, -5000);
        globalTendencyPosition = new RandomWalkersWorld.Point(mouseX, mouseY);
        board.setGlobalTendency(xT, yT);
        System.out.println("Global tendency set: " + xT + " , " + yT);

    }

    @Override
    public void keyPressed() {
        super.keyPressed();

        setup();
    }
}
