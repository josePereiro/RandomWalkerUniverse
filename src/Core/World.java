package Core;

import Core.Basics.Collections.CircularIntSet;
import Core.Basics.Vector2D.Vector2DCache;
import Core.Boards.Board;

import java.util.Random;

public class World extends Board {


    public World(int w, int h) {
        super(w, h);
    }

    public static class Statics {

        public static class Defaults {

            private static final int DEFAULT_RANDOM_BUFFER_LENGTH = 1000000;
            private static final int DEFAULT_RANDOM_BUFFER_UPPER_BOUND = 1000;

        }

        public static class Caches {

            public static Vector2DCache vector2Dcache;
            public static CircularIntSet randomFloatCache;

            private static void initRandomCache(int bufferLength, int rBound) {

                Random r = new Random();
                int[] data = new int[bufferLength];
                for (int i = 0; i < data.length; i++) {
                    data[i] = r.nextInt(rBound);
                }

                randomFloatCache = new CircularIntSet(data);
            }

            public static void initCaches(int w, int h) {
                vector2Dcache = new Vector2DCache(w, h);
                initRandomCache(Defaults.DEFAULT_RANDOM_BUFFER_LENGTH,
                        Defaults.DEFAULT_RANDOM_BUFFER_UPPER_BOUND);
            }
        }


    }


}
