package Core.Engine;

import Core.Engine.RandomWalkersWorld;
import processing.core.PApplet;

public class GlobalTendencyTest extends PApplet {

    public static void main(String... args) {
        //Write your own main class path
        PApplet.main("Core.Engine.GlobalTendencyTest");
    }

    @Override
    public void settings() {
        size(500, 500);
    }

    RandomWalkersWorld world;

    //Barriers
    int bs = 20;
    int bc = 20;

    //Walkers
    int ws = 1;
    int wc = 8000;

    @Override
    public void setup() {

        try {
            world = RandomWalkersWorld.createWorld(width, height);
        } catch (RandomWalkersWorld.WorldAlreadyCreatedException e) {
            e.printStackTrace();
        }

        //Barriers
        for (int b = 0; b < 1; b++) {
            world.addSquareBarrier(width / 2, height / 2, bs);
        }

        //Walkers
        for (int w = 0; w < wc; w++) {
            world.addCircularWalker(width / 4, height / 4, ws);
        }

        world.run();

    }

    @Override
    public void draw() {

        background(255);
        drawWalkers();

    }

    private void drawWalkers() {

        noStroke();
        fill(0);
        RandomWalkersWorld.Vector pos;
        int ws = this.ws * 2;
        for (int w = 0; w < world.getWalkersCount(); w++) {
            pos = world.getWalkerPosition(w);
            ellipse(pos.x, pos.y, ws, ws);
        }

    }

    @Override
    public void mousePressed() {
        super.mousePressed();

        world.setGlobalTendency((mouseX - width / 2) * 10, (height / 2 - mouseY) * 10);
    }


}