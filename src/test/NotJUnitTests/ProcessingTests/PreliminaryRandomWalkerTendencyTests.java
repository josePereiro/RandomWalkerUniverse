package test.NotJUnitTests.ProcessingTests;

import Core.World.RandomNumbersCache;
import Core.World.SpacePoint;
import Core.World.SpacePointsCache;
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
    ArrayList<SpacePoint> walkers;
    World world;
    SpacePointsCache spacePointsCache;
    RandomNumbersCache randomNumbersCache;
    SpacePoint tendency;
    SpacePoint center;
    int rpf = 20;//900;//1300 //2400
    int itrs = 0;
    float lastFrameRate = 0;

    @Override
    public void setup() {
        World.WorldFactory factory = new World.WorldFactory(width, height);
        world = factory.createNewWorld();
        spacePointsCache = world.getSpacePointsCache();
        randomNumbersCache = world.getRandomNumbersCache();
        walkers = new ArrayList<>();
        SpacePoint walker;
        for (int i = 0; i < walkerCount; i++) {
            walker = spacePointsCache.get(width / 2, height / 2);
            walkers.add(walker);
        }
        tendency = spacePointsCache.get(0, 0);
        background(255);
        fill(0);
        frameRate(desiredFrameRate);
        center = spacePointsCache.get(width / 2, height / 2);
    }

    @Override
    public void draw() {

        background(255);

        tendency = spacePointsCache.distance(center.x, center.y, mouseX, mouseY);
        tendency = spacePointsCache.multiply(tendency, 2);

        if (frameRate > lastFrameRate) {
            rpf += (1 - lastFrameRate / frameRate) * 100;
        } else if (rpf > 1) {
            rpf -= (1 - frameRate / lastFrameRate) * 100;
        }
        lastFrameRate = frameRate;

        stroke(0);
        for (int r = 0; r < rpf; r++) {
            for (int i = 0; i < walkers.size(); i++) {
                SpacePoint walker = walkers.get(i);
                walker = randomNumbersCache.pickANeighbor(walker,
                        tendency);
                walkers.set(i, walker);
            }
            itrs++;
        }

        //Drawing
        for (SpacePoint walker : walkers) {
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
