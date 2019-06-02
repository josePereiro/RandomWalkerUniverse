package Core.Basics.Random;

import Core.Basics.Tools.Tools;
import Core.Basics.Vector2D.Vector2D;

import java.util.Random;

public class RandomNumbersCache {

    private float[] cache;
    private int index;
    private int lastIndex;

    public RandomNumbersCache(int length) {
        cache = new float[length];
        index = 0;
        lastIndex = length - 1;
        randomize();
    }

    public float getNextFloat() {
        if (index == lastIndex) {
            index = 0;
        } else index++;
        return cache[index];
    }

    public int getNextInt(int bound) {
        return (int) getNextFloat() * bound;
    }

    public float getMean() {
        return Tools.CollectionsOps.mean(cache);
    }

    public void randomize() {
        Random r = new Random();
        for (int i = 0; i < cache.length; i++) {
            cache[i] = r.nextFloat();
        }
    }

    public Vector2D getNextStep(Vector2D position, Vector2D tendency) {

        float rn = getNextFloat();
        float[] td = tendency.tendDistribution;
        if (rn <= td[Vector2D.UP_INDEX]) {
            return position.fourNeighbors[Vector2D.UP_INDEX];
        } else if (rn <= td[Vector2D.DOWN_INDEX]) {
            return position.fourNeighbors[Vector2D.DOWN_INDEX];
        } else if (rn <= td[Vector2D.RIGHT_INDEX]) {
            return position.fourNeighbors[Vector2D.RIGHT_INDEX];
        } else {
            return position.fourNeighbors[Vector2D.LEFT_INDEX];
        }
    }
}
