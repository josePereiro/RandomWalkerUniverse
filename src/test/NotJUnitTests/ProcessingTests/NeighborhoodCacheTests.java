package test.NotJUnitTests.ProcessingTests;

import Core.World.*;
import processing.core.PApplet;
import processing.core.PVector;
import test.JUnit.Vector2D.SpacePointCacheTest;

import java.awt.*;

public class NeighborhoodCacheTests extends PApplet {

    World world;
    SpacePointsCache spacePointsCache;
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
        spacePointsCache = world.getSpacePointsCache();
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
        SpacePoint currentPoint = spacePointsCache.get(mouseX - offset,
                mouseY - offset);

        Neighborhood[] neighborhoods = currentPoint.getNeighborhoods();
        Neighborhood neighborhood;
        SpacePoint origin;
        if (neighborhoods != null) {
            for (int ni = neighborhoods.length - 1; ni >= 0; ni--) {

                fill(0, 155);
                //Neighborhood
                neighborhood = neighborhoods[ni];
                origin = neighborhood.getOrigin();
                rect(origin.x + offset, origin.y + offset,
                        neighborhoods[ni].getWidth(), neighborhoods[ni].getHeight());

                //Neighs Center
                if (ni == 0) {
                    fill(Color.YELLOW.getRGB());
                }
                ellipse(neighborhoods[ni].getCenterX() + offset,
                        neighborhoods[ni].getCenterY() + offset,
                        5, 5);
            }
        }


//        for (Neighborhood neighborhood : neighborhoods) {
//            origin = neighborhood.getOrigin();
//            if (neighborhood.isWithing(world.getSpacePointsCache().get(mouseX
//                    - offset, mouseY - offset))) {
//                fill(0, 155);
//            } else {
//                noFill();
//            }
//            noStroke();
//
//            //Neighborhood
//            rect(origin.x + offset, origin.y + offset,
//                    neighborhood.getWidth(), neighborhood.getHeight());
////            ellipse(origin.x + offset, origin.y + offset, 5, 5);
//
//            //Neighs Center
//            ellipse(neighborhood.getCenterX() + offset, neighborhood.getCenterY() + offset,
//                    5, 5);
//
//        }


        fill(255);
        //mouse
        ellipse(mouseX, mouseY, 5, 5);
        fill(0, 80);
        ellipse(mouseX, mouseY, 1, 1);


        //Info
        fill(80, 155);
        text((mouseX - offset) +
                " : " + (mouseY - offset) + " : " +
                world.width + " : " + world.height, 20, 20);

    }

    @Override
    public void mouseClicked() {
        World.WorldFactory factory =
                new World.WorldFactory(width - 2 * offset, height - 2 * offset);
        factory.setNeighDRadius((int) map(mouseX, 0, width, 10, 50));
        factory.setNeighOffset(0);
        world = factory.createNewWorld();
        spacePointsCache = world.getSpacePointsCache();
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
