package test.NotJUnitTests.ProcessingTests;

import Core.World.Neighborhood;
import Core.World.NeighborhoodsCache;
import Core.World.Vector2D;
import Core.World.World;
import processing.core.PApplet;

import java.util.Random;

public class NeighborhoodCacheTests extends PApplet {

    NeighborhoodsCache board;
    World world;
    int offset = 10;

    public static void main(String[] args) {
        PApplet.main("test.NotJUnitTests.ProcessingTests.NeighborhoodCacheTests");
    }

    @Override
    public void settings() {
        size(300, 600);
    }

    @Override
    public void setup() {
        World.WorldFactory factory =
                new World.WorldFactory(width - 2 * offset, height - 2 * offset);
        world = factory.createNewWorld();
    }

    @Override
    public void draw() {

        board = world.getNeighborhoodsCache();
        background(255);
        Random rand = new Random();
        Vector2D origin;
        fill(200);
        stroke(0);
        rect(offset, offset, world.width, world.height);
        Neighborhood[] neighborhoods = board.getNeighborhoods();
        for (Neighborhood neighborhood : neighborhoods) {
            origin = neighborhood.getOrigin();
            if (neighborhood.isWithing(world.getVector2DCache().get(mouseX
                    - offset, mouseY - offset))) {
                noStroke();
                fill(0, 155);
            } else {
                noFill();
                stroke(100, 155);
            }
            rect(origin.x + offset, origin.y + offset,
                    neighborhood.getWidth(), neighborhood.getHeight());
            ellipse(origin.x + offset, origin.y + offset, 5, 5);

        }

        text(neighborhoods.length + " neighborhoods", 20, 20);

    }

    @Override
    public void mouseClicked() {
        World.WorldFactory factory =
                new World.WorldFactory(width - 2 * offset, height - 2 * offset);
        factory.setNeighDRadius((int) map(mouseX, 0, width, 10, 50));
        factory.setNeighOffset(factory.getNeighDRadius() / 3);
        world = factory.createNewWorld();
    }
}
