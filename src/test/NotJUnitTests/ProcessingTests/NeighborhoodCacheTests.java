package test.NotJUnitTests.ProcessingTests;

import Core.World.Neighborhood;
import Core.World.NeighborhoodsCache;
import Core.World.SpacePoint;
import Core.World.World;
import processing.core.PApplet;
import processing.core.PVector;

public class NeighborhoodCacheTests extends PApplet {

    NeighborhoodsCache board;
    World world;
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
        world = factory.createNewWorld();
        noCursor();
    }

    @Override
    public void draw() {

        board = world.getNeighborhoodsCache();
        background(255);
        SpacePoint origin;
        fill(200);
        stroke(0);
        rect(offset, offset, world.width, world.height);
        Neighborhood[] neighborhoods = board.getNeighborhoods();
        for (Neighborhood neighborhood : neighborhoods) {
            origin = neighborhood.getOrigin();
            if (neighborhood.isWithing(world.getSpacePointsCache().get(mouseX
                    - offset, mouseY - offset))) {
                fill(0, 155);
            } else {
                noFill();
            }
            noStroke();

            //Neighborhood
            rect(origin.x + offset, origin.y + offset,
                    neighborhood.getWidth(), neighborhood.getHeight());
//            ellipse(origin.x + offset, origin.y + offset, 5, 5);

            //Neighs Center
            ellipse(neighborhood.getCenterX() + offset, neighborhood.getCenterY() + offset,
                    5, 5);

        }



        //Mass center
        PVector massCenter = neighborhoodMassCenter(neighborhoods);
        stroke(0);
        fill(0);
        ellipse(massCenter.x + offset, massCenter.y + offset, 2, 2);

        fill(255);
        //mouse
        ellipse(mouseX, mouseY, 5, 5);
        fill(0, 80);
        ellipse(mouseX, mouseY, 1, 1);


        //Info
        fill(80, 155);
        text(neighborhoods.length + " : " + (mouseX - offset) +
                " : " + (mouseY - offset) + " : " +
                world.width + " : " + world.height, 20, 20);

    }

    @Override
    public void mouseClicked() {
        World.WorldFactory factory =
                new World.WorldFactory(width - 2 * offset, height - 2 * offset);
        factory.setNeighDRadius((int) map(mouseX, 0, width, 10, 50));
        factory.setNeighOffset(factory.getNeighDRadius() / 3);
        world = factory.createNewWorld();
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
