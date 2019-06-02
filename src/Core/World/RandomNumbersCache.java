package Core.World;

import Core.Basics.Tools.Tools;

import java.util.Random;

public class RandomNumbersCache {

    private float[] cache;
    private int index;
    private int lastIndex;

    private Random rand = new Random();

    public RandomNumbersCache(int length) {
        cache = new float[length];
        index = 0;
        lastIndex = length - 1;
        randomize();
    }

    public float getNextFloat() {
        if (index == lastIndex) {
            index = rand.nextInt(lastIndex);
        } else index++;
        return cache[index];
    }

    public int getNextInt(int bound) {
        return (int) getNextFloat() * bound;
    }

    public float getMean() {
        return Tools.CollectionsOps.mean(cache);
    }

    private void randomize() {
        Random r = new Random();
        for (int i = 0; i < cache.length; i++) {
            cache[i] = r.nextFloat();
        }
    }

    public Vector2D getNextStep(Vector2D position, Vector2D tendency) {

        float rn = getNextFloat();
        float[] td = tendency.tendDistribution;
        if (rn <= td[World.Statics.UP_NEIGHBORHOOD_INDEX]) {
            return position.fourNeighbors[World.Statics.UP_NEIGHBORHOOD_INDEX];
        } else if (rn <= td[World.Statics.DOWN_NEIGHBORHOOD_INDEX]) {
            return position.fourNeighbors[World.Statics.DOWN_NEIGHBORHOOD_INDEX];
        } else if (rn <= td[World.Statics.RIGHT_NEIGHBORHOOD_INDEX]) {
            return position.fourNeighbors[World.Statics.RIGHT_NEIGHBORHOOD_INDEX];
        } else {
            return position.fourNeighbors[World.Statics.LEFT_NEIGHBORHOOD_INDEX];
        }
    }
}
