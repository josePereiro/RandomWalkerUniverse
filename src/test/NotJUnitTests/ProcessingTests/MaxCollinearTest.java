package test.NotJUnitTests.ProcessingTests;

import Core.World.SpacePoint;
import Core.World.SpacePointsCache;
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
    SpacePointsCache cache;

    @Override
    public void setup() {
        World.WorldFactory factory = new World.WorldFactory(width, height);
        world = factory.createNewWorld();
        cache = world.getSpacePointsCache();

        fill(0);
    }

    @Override
    public void draw() {
        SpacePoint small = cache.get(mouseX - width / 2, mouseY - height / 2);
        SpacePoint maxCollinear = small.getMaxCollinear();

        background(255);
        //Drawing
        ellipse(width / 2, height / 2, 2, 2);
        line(width / 2, height / 2,
                maxCollinear.x + width / 2, maxCollinear.y + height / 2);

    }
}
