package test.NotJUnitTests.ProcessingTests;

import Core.World.*;
import processing.core.PApplet;

import java.util.ArrayList;

public class RandomWalkerTest_1 extends PApplet {

    public static void main(String[] args) {
        PApplet.main("test.NotJUnitTests.ProcessingTests.RandomWalkerTest_1");
    }

    @Override
    public void settings() {
        size(5, 300);
    }

    World world;
    ArrayList<RandomWalker> walkers;
    int walkerCount = 10000;
    int factor = 1;
    boolean sense = false;

    private class Walker extends RandomWalker{

        public Walker(Vector2D location) {
            super(location);
        }

        Vector2D tendency;
        Vector2D temp;
        RandomWalker neighbor;

        @Override
        public Vector2D onTendencyUpdate(WalkersBuffer neighbors, Vector2DCache cache) {
            int ncount = Math.min(neighbors.getCount(), 25);
            tendency = cache.get(0, 0);
            for (int ni = 0; ni < ncount; ni++) {
                neighbor = neighbors.get(ni);
                temp = cache.distanceVector2D(neighbor.getLocation(),
                        this.getLocation());
                temp = cache.substract(temp.getMaxCollinear(), temp).getBaseCollinear();
                tendency = cache.add(tendency, temp);
            }
            return cache.add(cache.multiply(tendency, factor), cache.get(0,100));
        }
    }

    @Override
    public void setup() {
        world = World.WorldFactory.createWorld(width, height);

        for (int i = 0; i < walkerCount; i++) {
            world.addWalker(new Walker(world.getVector2DCache().get(width/2,height/2)));
        }

//        for (int i = 0; i < walkerCount; i++) {
//            world.addWalker(new Walker(world.getVector2DCache().get(width/2 + 1,height/2)));
//        }

        walkers = world.getRandomWalkers();
        fill(0);
        stroke(0);
    }

    @Override
    public void draw() {

        //background
        background(255);

        //Drawing
        Vector2D location;
        for (RandomWalker walker : walkers) {
            location = walker.getLocation();
            point(location.x, location.y);
        }

        //Steping
        world.step();

    }

    @Override
    public void mouseClicked() {
        setup();
    }
}
