package Core;

import Core.Basics.Collections.CircularIntSet;
import Core.Basics.Vector2D.Vector2D;
import Core.Basics.Vector2D.Vector2DCache;
import Core.Boards.Board;

import java.util.Random;

public class World extends Board {

    //Statics
    public static Vector2DCache vector2Dcache;
    public static CircularIntSet randomBuffer;
    private static final int DEFAULT_RANDOM_BUFFER_LENGTH = 1000000;
    private static final int DEFAULT_RANDOM_BUFFER_UPPER_BOUND = 1000;


    public World(int w, int h) {
        super(w, h);
        vector2Dcache = new Vector2DCache(w, h);
        initRandomBuffer(DEFAULT_RANDOM_BUFFER_LENGTH, DEFAULT_RANDOM_BUFFER_UPPER_BOUND);
    }

    private static void initRandomBuffer(int bufferLength, int rBound) {

        Random r = new Random();
        int[] data = new int[bufferLength];
        for (int i = 0; i < data.length; i++) {
            data[i] = r.nextInt(rBound);
        }

        randomBuffer = new CircularIntSet(data);
    }


}
