package Core.World;

import Core.Tools.Tools;

import java.util.ArrayList;
import java.util.Random;

public class RandomNumbersCache {

    private float[] cache;
    private int index;
    private int lastIndex;
    private int restartBound;

    private Random rand = new Random();

    public RandomNumbersCache(int length) {
        cache = new float[length];
        index = 0;
        lastIndex = length - 1;
        restartBound = lastIndex / 4;
        randomize(new Random());
    }

    public float getNextFloat() {
        if (index == lastIndex) {
            index = rand.nextInt(restartBound);
        } else index++;
        return cache[index];
    }

    public int getNextInt(int bound) {
        return (int) getNextFloat() * bound;
    }

    public float getMean() {
        return Tools.CollectionsOps.mean(cache);
    }

    private void randomize(Random r) {
        for (int i = 0; i < cache.length; i++) {
            cache[i] = r.nextFloat();
        }
    }

    public Vector2D pickANeighbor(Vector2D position, Tendency tendency) {
        float rn = getNextFloat();
        float[] td = tendency.getTendencyDistribution();
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

    public int getIndex() {
        return index;
    }

    public <T> void unsort(ArrayList<T> list) {
        int ri;
        int bound = list.size();
        T temp;
        for (int i = 0; i < list.size(); i++) {
            ri = getNextInt(bound);
            temp = list.get(i);
            list.set(i, list.get(ri));
            list.set(ri, temp);
        }
    }

}
