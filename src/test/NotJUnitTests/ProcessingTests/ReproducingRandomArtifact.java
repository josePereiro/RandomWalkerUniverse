package test.NotJUnitTests.ProcessingTests;

import processing.core.PApplet;

import java.util.ArrayList;
import java.util.Random;

public class ReproducingRandomArtifact extends PApplet {

    RandomCache cache;
    int wCount = 10000;
    ArrayList<Walker> walkers;

    public static void main(String[] args) {
        PApplet.main("test.NotJUnitTests.ProcessingTests.ReproducingRandomArtifact");
    }

    @Override
    public void settings() {
        size(300, 600);
    }

    @Override
    public void setup() {
        cache = new RandomCache(1000000);
        walkers = new ArrayList<>();
        for (int i = 0; i < wCount; i++) {
            walkers.add(new Walker(width / 2, height / 2));
        }
        fill(0);
    }

    @Override
    public void draw() {
        background(255);
        for (Walker walker : walkers) {
            walker.move(width - 1, height - 1, cache);
            walker.draw(this);
        }
        text("rIndex: " + cache.index + " : " + cache.l + " : " +
                (cache.index * 1.0) / cache.l, 5,15);
    }

    private static class RandomCache {

        float[] cache;
        private final int l;
        int index;
        int indexRestartValue = 0;
        int lastIndex;

        public RandomCache(int l) {
            cache = new float[l];
            this.l = l;
            randomize(new Random());
            index = indexRestartValue;
            lastIndex = l - 1;
        }

        void randomize(Random r) {
            for (int i = 0; i < cache.length; i++) {
                cache[i] = r.nextFloat();
            }
        }

        public float getNextFloat() {
            if (index == lastIndex) {
                index = indexRestartValue;
            } else index++;
            return cache[index];
        }

    }

    private static class Walker {

        int x, y;

        public Walker(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void move(int xb, int yb, RandomCache cache) {
            float rn = cache.getNextFloat();
            int nx, ny;
            if (rn <= 0.25) {           //Up
                nx = x;
                ny = y - 1;
            } else if (rn <= 0.5) {     //Down
                nx = x;
                ny = y + 1;
            } else if (rn <= 0.75) {    //Right
                nx = x + 1;
                ny = y;
            } else {                     //Left
                nx = x - 1;
                ny = y;
            }
            x = nx;
            y = ny;
        }

        public void draw(PApplet context) {
            context.point(x, y);
        }

    }
}
