package test.NotJUnitTests.ProcessingTests;

import Core.World.Neighborhood;
import Core.World.NeighborhoodsCache;
import Core.World.Vector2D;
import Core.World.World;
import processing.core.PApplet;

import java.util.Random;

public class NeighborhoodTestField extends PApplet {

    NeighborhoodsCache board;
    World world;

    public static void main(String[] args) {
        PApplet.main("test.NotJUnitTests.ProcessingTests.NeighborhoodTestField");
    }

    @Override
    public void settings() {
        size(300, 600);
    }

    @Override
    public void setup() {
        world = new World(201, 401);
        board = world.getNeighborhoodsCache();
        frameRate(1);
    }

    @Override
    public void draw() {
        background(255);
        Random rand = new Random();
        Vector2D center;
        int r;
        int offset = 50;
        fill(200);
        stroke(0);
        rect(offset, offset, world.width, world.height);
        stroke(100, 155);
        noFill();
        Neighborhood[] neighborhoods = board.getNeighborhoods();
        Neighborhood neighborhood;
        for (int i = 0; i < neighborhoods.length; i++) {
            neighborhood = neighborhoods[i];
            center = neighborhood.getCenter();
            r = neighborhood.getR();
            rect(center.x - r + offset,
                    center.y - r + offset, r + r, r + r);
            ellipse(center.x + offset, center.y + offset, 5, 5);
        }


        neighborhood = neighborhoods[rand.nextInt(neighborhoods.length)];
        center = neighborhood.getCenter();
        r = neighborhood.getR();
        noStroke();
        fill(0, 155);
        rect(center.x - r + offset,
                center.y - r + offset, r + r, r + r);

        text(neighborhoods.length + " neighborhoods", 20, 20);

    }
}
