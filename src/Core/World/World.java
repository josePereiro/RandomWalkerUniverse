package Core.World;

public class World {

    public int width;
    public int height;
    private Vector2DCache vector2DCache;
    private RandomNumbersCache randomNumbersCache;
    private NeighborhoodsCache neighborhoodsCache;

    private World(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Vector2DCache getVector2DCache() {
        return vector2DCache;
    }

    public RandomNumbersCache getRandomNumbersCache() {
        return randomNumbersCache;
    }

    public NeighborhoodsCache getNeighborhoodsCache() {
        return neighborhoodsCache;
    }

    static class Statics {

        //Statics
        public static final int UP_NEIGHBORHOOD_INDEX = 0;
        public static final int DOWN_NEIGHBORHOOD_INDEX = 1;
        public static final int RIGHT_NEIGHBORHOOD_INDEX = 2;
        public static final int LEFT_NEIGHBORHOOD_INDEX = 3;

        public static class Defaults {

            private static final int DEFAULT_RANDOM_CACHE_LENGTH = 10000000;
            private static final int DEFAULT_NEIGHBORHOOD_DRADIUS = 20;
            private static final int DEFAULT_NEIGHBORHOOD_OFFSET = 5;

        }

    }

    public static final class WorldFactory {

        //World Fields
        //Dimensions
        private final int wWidth;
        private final int wHeight;
        //Neighborhoods
        private int neighDRadius;
        private int neighOffset;
        //RandomNumbersCache
        private int randNumbersCacheLength;

        public WorldFactory(int wWidth, int wHeight) {
            this.wWidth = wWidth;
            this.wHeight = wHeight;
            setDefaults();
        }

        private void setDefaults() {
            //Neighborhoods
            neighDRadius = Statics.Defaults.DEFAULT_NEIGHBORHOOD_DRADIUS;
            neighOffset = Statics.Defaults.DEFAULT_NEIGHBORHOOD_OFFSET;

            //RandomNumbersCache
            randNumbersCacheLength = Statics.Defaults.DEFAULT_RANDOM_CACHE_LENGTH;
        }

        public World createNewWorld() {

            //Vector2DCache
            Vector2DCache vector2DCache = new Vector2DCache(wWidth, wHeight);

            //NeighborhoodCache
            NeighborhoodsCache neighborhoodsCache = new NeighborhoodsCache(wWidth,
                    wHeight, neighDRadius, neighOffset, vector2DCache);

            //RandomNu
            RandomNumbersCache randomNumbersCache = new RandomNumbersCache(randNumbersCacheLength);

            //newWorld
            World newWorld = new World(wWidth, wHeight);
            newWorld.vector2DCache = vector2DCache;
            newWorld.neighborhoodsCache = neighborhoodsCache;
            newWorld.randomNumbersCache = randomNumbersCache;

            return newWorld;
        }

        public int getNeighDRadius() {
            return neighDRadius;
        }

        public void setNeighDRadius(int neighDRadius) {
            this.neighDRadius = neighDRadius;
        }

        public int getRandNumbersCacheLength() {
            return randNumbersCacheLength;
        }

        public void setRandNumbersCacheLength(int randNumbersCacheLength) {
            this.randNumbersCacheLength = randNumbersCacheLength;
        }

        public void setNeighOffset(int neighOffset) {
            this.neighOffset = neighOffset;
        }

        public int getwWidth() {
            return wWidth;
        }

        public int getwHeight() {
            return wHeight;
        }

        public int getNeighOffset() {
            return neighOffset;
        }
    }

}
