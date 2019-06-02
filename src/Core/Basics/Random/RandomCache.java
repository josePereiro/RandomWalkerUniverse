package Core.Basics.Random;

import Core.Basics.Tools.Tools;

import java.util.Random;

public class RandomCache {

    private float[] rdata;
    private int index;
    private int lastIndex;

    public RandomCache(int length) {
        rdata = new float[length];
        index = 0;
        lastIndex = length - 1;
        randomnize();
    }

    public float getNextFloat() {
        if (index == lastIndex) {
            index = 0;
        } else index++;
        return rdata[index];
    }

    public int getNextInt(int bound) {
        return (int) getNextFloat() * bound;
    }

    public float getMean() {
        return Tools.CollectionsOps.mean(rdata);
    }

    public void randomnize() {
        Random r = new Random();
        for (int i = 0; i < rdata.length; i++) {
            rdata[i] = r.nextFloat();
        }
    }
}
