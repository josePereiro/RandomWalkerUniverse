package test.NotJUnitTests.ProcessingTests;

import Core.World.RandomNumbersCache;
import Core.World.SpacePoint;
import Core.World.SpacePointsCache;
import Core.World.World;
import processing.core.PApplet;

import java.util.ArrayList;

public class PreliminaryRandomWalkerTests extends PApplet {

    public static void main(String[] args) {
        PApplet.main("test.NotJUnitTests." +
                "ProcessingTests.PreliminaryRandomWalkerTests");
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
    int rpf = 1000;//1300 //2400
    int itrs = 0;
    float lastFrameRate = 0;

    @Override
    public void setup() {
        World.WorldFactory factory = new World.WorldFactory(width,height);
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
    }

    @Override
    public void draw() {

        background(255);

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

        for (SpacePoint walker : walkers) {
            point(walker.x, walker.y);
        }

        noStroke();
        fill(255);
        rect(0, 0, width, 20);
        fill(0);
        text(itrs + " : " + rpf + " : " + frameRate +
                " : " + desiredFrameRate + " : " + randomNumbersCache.getIndex(), 10, 15);
    }
}
