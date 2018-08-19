package Core.Processing.Simulations;

import Core.Engine.RandomWalkersWorld;
import processing.core.PApplet;

import java.awt.*;

public class Diffusion extends PApplet {

    public static void main(String... args) {
        //Write your own main class path
        PApplet.main("Core.Processing.Simulations.Diffusion");
    }

    //Fields
    RandomWalkersWorld world;
    int marginForGraph = 100;
    int globalMargin = 10;
    int desireParticleCount = 8000;
    int walkerRadius = 1;
    int targetFrameRate = 15;
    int particlesToDraw = (int) (desireParticleCount * 0.1);
    int maxXValue, maxYValue;
    boolean drawInfo;
    RandomWalkersWorld.Vector startPos;

    @Override
    public void settings() {
        size(500, 500);
    }

    @Override
    public void setup() {

        //World
        try {
            world = RandomWalkersWorld.createWorld(width - marginForGraph - globalMargin * 2, height - marginForGraph - globalMargin * 2);
        } catch (RandomWalkersWorld.WorldAlreadyCreatedException e) {
            e.printStackTrace();
        }
        world.setEnablePerPositionWalkerCount(true);

        initializing();
    }

    public void initializing() {

        //Adding Walkers
        if (startPos == null)
            startPos = new RandomWalkersWorld.Vector(world.getWorldCenterX(), world.getWorldCenterY());
        for (int i = 0; i < desireParticleCount; i++) {
            world.addCircularWalker(startPos.x, startPos.y, walkerRadius);

        }

        maxYValue = 0;
        maxXValue = 0;

        world.run();
    }

    @Override
    public void draw() {

        //GUI
        background(240F);
        drawBoard();
        drawParticles();
        drawText();
        drawXGraph();
        drawYGraph();
        drawAxis();


    }

    private void drawAxis() {

        int startX = world.getW() + globalMargin * 2;
        int startY = world.getH() + globalMargin * 2;
        int textHSize = 20;
        int arrowD = 5;

        fill(100F, 200);
        text("# de \n partículas", startX, startY);

        //Xs V arrow
        stroke(100F, 200);
        int arrowH = marginForGraph - globalMargin * 4;
        int arrowW = arrowH - globalMargin;
        Point p0 = new Point(startX + globalMargin, startY + textHSize);
        Point p1_0 = new Point(p0.x - arrowD, p0.y + globalMargin);
        Point p1_1 = new Point(p0.x + arrowD, p1_0.y);
        Point p2 = new Point(p0.x, p0.y + arrowH);
        line(p0.x, p0.y, p1_0.x, p1_0.y);
        line(p0.x, p0.y, p1_1.x, p1_1.y);
        line(p0.x, p1_0.y, p2.x, p2.y);


        //Xs H arrow
        p0.x = p2.x + arrowW;
        p0.y = p2.y;
        p1_0.x = p0.x - globalMargin;
        p1_0.y = p0.y + arrowD;
        p1_1.x = p1_0.x;
        p1_1.y = p0.y - arrowD;
        line(p0.x, p0.y, p1_0.x, p1_0.y);
        line(p0.x, p0.y, p1_1.x, p1_1.y);
        line(p1_0.x, p2.y, p2.x, p2.y);


        //Ys V arrow
        p2.x = width - globalMargin * 2;
        p2.y = startY - 5;
        p0.x = p2.x;
        p0.y = p2.y + arrowH;
        p1_0.x = p0.x - arrowD;
        p1_0.y = p0.y - globalMargin;
        p1_1.x = p0.x + arrowD;
        p1_1.y = p1_0.y;
        line(p0.x, p0.y, p1_0.x, p1_0.y);
        line(p0.x, p0.y, p1_1.x, p1_1.y);
        line(p0.x, p1_0.y, p2.x, p2.y);


        //Ys H arrow
        p0.x = p2.x - arrowW;
        p0.y = p2.y;
        p1_0.x = p0.x + globalMargin;
        p1_0.y = p0.y + arrowD;
        p1_1.x = p1_0.x;
        p1_1.y = p0.y - arrowD;
        line(p0.x, p0.y, p1_0.x, p1_0.y);
        line(p0.x, p0.y, p1_1.x, p1_1.y);
        line(p1_0.x, p2.y, p2.x, p2.y);

    }

    private void drawBoard() {
        fill(250F);
        stroke(0, 200);
        int offset = walkerRadius * 2;
        rect(globalMargin - offset, globalMargin - offset, world.getW() + offset, world.getH() + offset);

    }

    private void drawXGraph() {

        int[] particlesCount = new int[width];

        maxXValue = 0;


        int count;
        for (int x = 0; x < world.getW(); x++) {
            count = 0;
            for (int y = 0; y < world.getH(); y++) {
                count += world.getWalkersCount(x, y);
            }
            if (maxXValue < count) {
                maxXValue = count;
            }
            particlesCount[x] = count;
        }

        int endY = height - globalMargin - 10;
        int graphYSize = abs(marginForGraph - globalMargin * 3);

        fill(Color.BLACK.getRGB(), 200);
        float xToDraw, yToDraw;
        for (int x = 0; x < world.getW(); x++) {

            if (particlesCount[x] == 0)
                continue;

            xToDraw = x + globalMargin;
            yToDraw = endY - map(particlesCount[x], 0, maxXValue, 0, graphYSize);

            //points
            noStroke();
            ellipse(xToDraw, yToDraw, 2, 2);
            //value lines
            stroke(map(particlesCount[x], 0, maxXValue, 0, 255), 200);
            line(xToDraw, yToDraw, xToDraw, endY);
        }


    }

    private void drawYGraph() {

        int[] particlesCount = new int[height];

        maxYValue = 0;

        int count;
        for (int y = 0; y < world.getH(); y++) {
            count = 0;
            for (int x = 0; x < world.getW(); x++) {
                count += world.getWalkersCount(x, y);
            }
            if (maxYValue < count) {
                maxYValue = count;
            }
            particlesCount[y] = count;
        }

        int endX = width - globalMargin - 10;
        int graphXSize = abs(marginForGraph - globalMargin * 3);

        fill(Color.BLACK.getRGB(), 200);
        float xToDraw, yToDraw;
        for (int y = 0; y < world.getW(); y++) {

            if (particlesCount[y] == 0)
                continue;

            yToDraw = y + globalMargin;
            xToDraw = endX - map(particlesCount[y], 0, maxYValue, 0, graphXSize);

            //points
            noStroke();
            ellipse(xToDraw, yToDraw, 2, 2);
            //value lines
            stroke(map(particlesCount[y], 0, maxXValue, 0, 255), 200);
            line(xToDraw, yToDraw, endX, yToDraw);
        }

    }

    private void drawParticles() {

        noStroke();
        fill(0, 200);
        int diameter = walkerRadius * 2 + 1;
        optimizeParticlesToDraw();
        for (int p = 0; p < particlesToDraw; p++) {
            ellipse(globalMargin + world.getWalkerPositionX(p), globalMargin + world.getWalkerPositionY(p), diameter, diameter);
        }

    }

    private void drawText() {

        if (!drawInfo) return;

        fill(255);
        String text = "WalkerCount: " + world.getWalkersCount() +
                "\nWalkerDrown: : " + particlesToDraw +
                "\nFrame Rate: " + Math.round(frameRate) +
                "\nSimulationSteps: " + Math.round(world.getStepsCounter());

        int x = 18;
        int y = 25;

        text(text, x - 1, y - 1);
        text(text, x - 1, y + 1);
        text(text, x + 1, y - 1);
        text(text, x + 1, y + 1);
        fill(0);
        text(text, x, y);
    }

    private void optimizeParticlesToDraw() {

        if (targetFrameRate < (int) frameRate) {
            particlesToDraw += 10;
        } else {
            particlesToDraw -= 10;
        }

        if (particlesToDraw > desireParticleCount) {
            particlesToDraw = desireParticleCount;
        }

    }

    @Override
    public void mousePressed() {
        super.mousePressed();

        int translatedMouseX = mouseX - globalMargin;
        int translatedMouseY = mouseY - globalMargin;
        if (!world.isOutOfTheWorld(translatedMouseX, translatedMouseY)) {
            startPos = new RandomWalkersWorld.Vector(translatedMouseX, translatedMouseY);
            if (world.addCircularWalker(startPos.x, startPos.y, walkerRadius)) {
                world.clearWorld();
                initializing();
                System.gc();
            }
        }
    }

    @Override
    public void keyPressed() {
        super.keyPressed();

        if (key == 'i')
            drawInfo = !drawInfo;
        else if (key == 's')
            world.stopRunning();
        else if (key == 'p')
            world.run();


    }
}
