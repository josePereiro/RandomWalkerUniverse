package test.NotJUnitTests.ProcessingTests;

import Core.Basics.Vector2D.Vector2D;
import Core.World;
import processing.core.PApplet;

import static Core.World.Statics.Caches.randomFloatCache;
import static Core.World.Statics.Caches.vector2Dcache;

public class RandomWalkerTests extends PApplet {

    public static void main(String[] args) {
        PApplet.main("test.NotJUnitTests.ProcessingTests.RandomWalkerTests");
    }


    @Override
    public void settings() {
        size(300, 600);
    }

    Vector2D walker;
    Vector2D tendency;

    @Override
    public void setup() {
        World.Statics.Caches.initCaches();
        walker = vector2Dcache.getPositive(width / 2, height / 2);
    }

    @Override
    public void draw() {
        tendency = vector2Dcache.getPositive(mouseX, mouseY);
        walker = randomFloatCache.getNextStep(walker,
                vector2Dcache.get(0, 0));
        background(255);
        ellipse(walker.x, walker.y, 5, 5);
    }
}
