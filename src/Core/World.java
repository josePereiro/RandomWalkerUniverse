package Core;

import Core.Basics.Random.RandomNumbersCache;
import Core.Basics.Vector2D.Vector2DCache;
import Core.Boards.Board;

public class World extends Board {


    public World(int w, int h) {
        super(w, h);
    }

    public static class Statics {

        public static class Defaults {

            private static final int DEFAULT_RANDOM_CACHE_LENGTH = 1000000;
            private static final int DEFAULT_RANDOM_BUFFER_UPPER_BOUND = 1000;
            private static final int DEFAULT_WORLD_WIDTH = 100;
            private static final int DEFAULT_WORLD_HEIGHT = 150;

        }

        public static class Caches {

            public static Vector2DCache vector2Dcache;
            public static RandomNumbersCache randomFloatCache;

            public static void initCaches() {
                vector2Dcache = new Vector2DCache(worldW, worldH);
                randomFloatCache = new RandomNumbersCache(randomCacheLength);
            }
        }

        public static int worldW = Defaults.DEFAULT_WORLD_WIDTH;
        public static int worldH = Defaults.DEFAULT_WORLD_HEIGHT;
        public static int randomCacheLength = Defaults.DEFAULT_RANDOM_CACHE_LENGTH;

    }


}
