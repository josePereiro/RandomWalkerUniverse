package test.NotJUnitTests.ProcessingTests;

import Core.World.RandomNumbersCache;
import Core.World.Vector2D;
import Core.World.Vector2DCache;
import Core.World.World;
import processing.core.PApplet;

import java.util.ArrayList;

public class PreliminaryRandomWalkerTendencyTests extends PApplet {

    public static void main(String[] args) {
        PApplet.main("test.NotJUnitTests." +
                "ProcessingTests.PreliminaryRandomWalkerTendencyTests");
    }


    @Override
    public void settings() {
        size(301, 601);
    }

    int desiredFrameRate = 60;
    int walkerCount = 10000;
    ArrayList<Vector2D> walkers;
    World world;
    Vector2DCache vector2DCache;
    RandomNumbersCache randomNumbersCache;
    Vector2D tendency;
    Vector2D center;
    int rpf = 20;//900;//1300 //2400
    int itrs = 0;
    float lastFrameRate = 0;

    @Override
    public void setup() {
        World.WorldFactory factory = new World.WorldFactory(width, height);
        world = factory.createNewWorld();
        vector2DCache = world.getVector2DCache();
        randomNumbersCache = world.getRandomNumbersCache();
        walkers = new ArrayList<>();
        Vector2D walker;
        for (int i = 0; i < walkerCount; i++) {
            walker = vector2DCache.get(width / 2, height / 2);
            walkers.add(walker);
        }
        tendency = vector2DCache.get(0, 0);
        background(255);
        fill(0);
        frameRate(desiredFrameRate);
        center = vector2DCache.get(width / 2, height / 2);
    }

    @Override
    public void draw() {

        background(255);

        tendency = vector2DCache.distance(center.x, center.y, mouseX, mouseY);
        tendency = vector2DCache.multiply(tendency, 2);

        if (frameRate > lastFrameRate) {
            rpf += (1 - lastFrameRate / frameRate) * 100;
        } else if (rpf > 1) {
            rpf -= (1 - frameRate / lastFrameRate) * 100;
        }
        lastFrameRate = frameRate;

        stroke(0);
        for (int r = 0; r < rpf; r++) {
            for (int i = 0; i < walkers.size(); i++) {
                Vector2D walker = walkers.get(i);
                walker = randomNumbersCache.pickANeighbor(walker,
                        tendency);
                walkers.set(i, walker);
            }
            itrs++;
        }

        //Drawing
        for (Vector2D walker : walkers) {
            point(walker.x, walker.y);
        }
        //Tendency
        ellipse(center.x, center.y, 2, 2);
        line(center.x, center.y, mouseX, mouseY);
        rect(mouseX, mouseY, 2, 2);

        noStroke();
        fill(255);
        rect(0, 0, width, 20);
        fill(0);
        text(itrs + " : " + tendency.x + " : " + tendency.y + " : " + (int) frameRate +
                " : " + (int) ((float) tendency.x / (world.width - 1) * 100) +
                " : " + (int) ((float) tendency.y / (world.height - 1) * 100), 10, 15);
    }
}
