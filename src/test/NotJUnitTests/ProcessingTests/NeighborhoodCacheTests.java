package test.NotJUnitTests.ProcessingTests;

import Core.World.*;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.*;

public class NeighborhoodCacheTests extends PApplet {

    World world;
    Vector2DCache vector2DCache;
    int offset = 50;

    //TODO fix this bug. Some parts of the world do not belong to a neigh...
    public static void main(String[] args) {
        PApplet.main("test.NotJUnitTests.ProcessingTests.NeighborhoodCacheTests");
    }

    @Override
    public void settings() {
        size(302, 602);
    }

    @Override
    public void setup() {
        World.WorldFactory factory =
                new World.WorldFactory(width - 2 * offset, height - 2 * offset);


        //TODO delete
//        World.WorldFactory factory = new World.WorldFactory(5,7);
//        factory.setNeighDRadius(2);
//        factory.setNeighOffset(0);

        world = factory.createNewWorld();
        vector2DCache = world.getVector2DCache();
        noCursor();
    }

    @Override
    public void draw() {

        background(255);

        //World
        fill(200);
        stroke(0);
        rect(offset, offset, world.width, world.height);

        //Neighborhoods
        Vector2D currentPoint = vector2DCache.get(mouseX - offset,
                mouseY - offset);

        Neighborhood[] neighborhoods = currentPoint.getNeighborhoods();
        Neighborhood neighborhood;
        Vector2D origin;
        if (neighborhoods != null) {
            for (int ni = neighborhoods.length - 1; ni >= 0; ni--) {

                fill(0, 30);
                if (ni == 0) {
                    stroke(Color.YELLOW.getRGB());
                } else {
                    noStroke();
                }
                //Neighborhood
                neighborhood = neighborhoods[ni];
                origin = neighborhood.getOrigin();
                rect(origin.x + offset, origin.y + offset,
                        neighborhoods[ni].getWidth(), neighborhoods[ni].getHeight());
                ellipse(neighborhoods[ni].getCenterX() + offset,
                        neighborhoods[ni].getCenterY() + offset,
                        5, 5);
            }
        }


        //mouse
        fill(255);
        ellipse(mouseX, mouseY, 2, 2);


        //Info
        fill(80, 155);
        if (neighborhoods == null) {
            text((mouseX - offset) +
                    " : " + (mouseY - offset) + " : " +
                    world.width + " : " + world.height, 20, 20);
        }else {
            text((mouseX - offset) +
                    " : " + (mouseY - offset) + " : " +
                    world.width + " : " + world.height + " : " +
                    neighborhoods.length, 20, 20);
        }

    }

    @Override
    public void mouseClicked() {
        World.WorldFactory factory =
                new World.WorldFactory(width - 2 * offset, height - 2 * offset);
        factory.setNeighDRadius((int) map(mouseX, 0, width, 10, 50));
        factory.setNeighOffset(factory.getNeighDRadius());
        world = factory.createNewWorld();
        vector2DCache = world.getVector2DCache();
    }

    private PVector neighborhoodMassCenter(Neighborhood[] neighborhoods) {
        int xSum = 0;
        int ySum = 0;
        int area;
        int totalArea = 0;
        for (Neighborhood neighborhood : neighborhoods) {
            area = neighborhood.getWidth() + neighborhood.getHeight();
            xSum += area * neighborhood.getCenterX();
            ySum += area * neighborhood.getCenterY();
            totalArea += area;
        }

        return new PVector(xSum / totalArea,
                ySum / totalArea);
    }


}
