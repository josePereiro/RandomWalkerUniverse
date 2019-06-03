package test.NotJUnitTests.ProcessingTests;

import Core.World.Vector2D;
import Core.World.Vector2DCache;
import Core.World.World;
import processing.core.PApplet;

public class MaxCollinearTest extends PApplet {

    public static void main(String[] args) {
        PApplet.main("test.NotJUnitTests.ProcessingTests.MaxCollinearTest");
    }

    @Override
    public void settings() {
        size(600, 600);
    }

    World world;
    Vector2DCache cache;

    @Override
    public void setup() {
        world = new World(width, height);
        cache = world.getVector2DCache();

        fill(0);
    }

    @Override
    public void draw() {
        Vector2D small = cache.get(mouseX - width / 2, mouseY - height / 2);
        Vector2D maxCollinear = small.getMaxCollinear();

        background(255);
        //Drawing
        ellipse(width / 2, height / 2, 2, 2);
        line(width / 2, height / 2,
                maxCollinear.x + width / 2, maxCollinear.y + height / 2);

    }
}
