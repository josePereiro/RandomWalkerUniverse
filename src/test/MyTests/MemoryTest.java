package test.MyTests;

import Core.Basics.Vector2D.Vector2DCache;
import Core.RandomWalkers.RandomWalker;

import java.util.Random;

public class MemoryTest {

    private static final long MEGABYTE = 1024L * 1024L;

    private static double bytesToMegabytes(long bytes) {
        return (double) bytes / MEGABYTE;
    }

    public static void main(String[] args) {

        //Put your code here
//        Vector2DCache cache = new Vector2DCache(1200, 1200);
        double[] randoms = new double[1000000];
        Random r = new Random();
        for (int i = 0; i < randoms.length; i++) {
            randoms[i] = r.nextInt(10000);
        }

        // Get the Java runtime
        Runtime runtime = Runtime.getRuntime();
        // Run the garbage collector
        runtime.gc();
        // Calculate the used memory
        long memory = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Used memory " + memory + " bytes");
        System.out.println("Used memory " + bytesToMegabytes(memory) + " megabytes");
    }


}
