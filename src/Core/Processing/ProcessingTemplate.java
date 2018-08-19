package Core.Processing;

import Core.Engine.RandomWalkersWorld;
import processing.core.PApplet;

import java.lang.reflect.Type;

public class ProcessingTemplate extends PApplet {

    public static void main(String... args) {
        //Write your own main class path
        PApplet.main("Core.Processing.ProcessingTemplate");
    }

    @Override
    public void settings() {
        size(500, 500);
    }

    @Override
    public void setup() {

        stroke(0);
    }

    @Override
    public void draw() {
        background(255);
        drawLine(new RandomWalkersWorld.Vector(mouseX, mouseY), new RandomWalkersWorld.Vector(width / 2, height / 2));

        Object o = method(2, 3);

    }

    private <T> T method(int bla, int blo) {
        return null;
    }

    public void drawLine(RandomWalkersWorld.Vector v1, RandomWalkersWorld.Vector v2) {

        //v = mv' + c
        float m;
        float c;

        if (Math.abs(v1.x - v2.x) > Math.abs(v1.y - v2.y)) {
            //thought Xs

            m = ((float) (v1.y - v2.y)) / (v1.x - v2.x);
            c = v1.y - m * v1.x;
            float y;


            if (v1.x < v2.x) {

                if (m == 0)
                    for (int x = v1.x; x < v2.x; x++) {
                        //Action
                        point(x, v1.y);
                    }
                else
                    for (int x = v1.x; x < v2.x; x++) {
                        y = m * x + c;
                        //Action
                        point(x, y);

                    }

            } else if (v1.x > v2.x) {

                if (m == 0)
                    for (int x = v2.x; x < v1.x; x++) {
                        //Action
                        point(x, v1.y);
                    }
                else
                    for (int x = v2.x; x < v1.x; x++) {
                        y = m * x + c;
                        //Action
                        point(x, y);
                    }

            }

        } else {

            //thought Ys
            m = ((float) (v1.x - v2.x)) / (v1.y - v2.y);
            c = v1.x - m * v1.y;
            float x;

            if (v1.y < v2.y) {

                if (m == 0)
                    for (int y = v1.y; y < v2.y; y++) {
                        //Action
                        point(v1.x, y);
                    }
                else
                    for (int y = v1.y; y < v2.y; y++) {
                        x = m * y + c;
                        //Action
                        point(x, y);

                    }

            } else if (v1.y > v2.y) {

                if (m == 0)
                    for (int y = v2.y; y < v1.y; y++) {
                        //Action
                        point(v1.x, y);
                    }
                else
                    for (int y = v2.y; y < v1.y; y++) {
                        x = m * y + c;
                        //Action
                        point(x, y);
                    }

            } else {
                //Action
                point(v1.x, v1.y);
            }

        }


    }
}