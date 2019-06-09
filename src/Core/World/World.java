package Core.World;

import java.util.ArrayList;

public class World {

    public int width;
    public int height;
    private Vector2DCache vector2DCache;
    private RandomNumbersCache randomNumbersCache;
    private NeighborhoodsCache neighborhoodsCache;
    private ArrayList<RandomWalker> randomWalkers;

    private World(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void step() {

        //Unsorting
        randomNumbersCache.unsort(randomWalkers);

        //Cleaning Neighbors
        neighborhoodsCache.clearNeighborhoods();

        //updating Neighborhoods
        for (RandomWalker randomWalker : randomWalkers) {
            neighborhoodsCache.locateWalker(randomWalker);
        }

        //Updating Tendencies
        for (RandomWalker randomWalker : randomWalkers) {
            randomWalker.updateTendency(randomWalker.getClosestNeighborhood().neighbors, vector2DCache);
        }

        //Moving
        for (RandomWalker randomWalker : randomWalkers) {
            randomWalker.setLocation(randomNumbersCache.pickANeighbor(randomWalker.getLocation(),
                    randomWalker.getTendency()));
        }

    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ArrayList<RandomWalker> getRandomWalkers() {
        return randomWalkers;
    }

    public void addWalker(RandomWalker randomWalker) {
        randomWalkers.add(randomWalker);
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
            private static final int DEFAULT_NEIGHBORHOOD_DRADIUS = 5;
            private static final int DEFAULT_NEIGHBORHOOD_OFFSET = 15;
            private static final int DEFAULT_NEIGHBORHOOD_BUFFER_CAPACITY = 50;
            private static final int DEFAULT_INITIAL_WALKERS_CAPACITY = 5000;

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
        private int neighBufferCapacity;
        //RandomNumbersCache
        private int randNumbersCacheLength;

        public static World createWorld(int wWidth, int wHeight) {
            return new World.WorldFactory(wWidth, wHeight).createNewWorld();
        }

        public WorldFactory(int wWidth, int wHeight) {
            this.wWidth = wWidth;
            this.wHeight = wHeight;
            setDefaults();
        }

        private void setDefaults() {
            //Neighborhoods
            neighDRadius = Statics.Defaults.DEFAULT_NEIGHBORHOOD_DRADIUS;
            neighOffset = Statics.Defaults.DEFAULT_NEIGHBORHOOD_OFFSET;
            neighBufferCapacity = Statics.Defaults.DEFAULT_NEIGHBORHOOD_BUFFER_CAPACITY;

            //RandomNumbersCache
            randNumbersCacheLength = Statics.Defaults.DEFAULT_RANDOM_CACHE_LENGTH;
        }

        public World createNewWorld() {

            //Vector2DCache
            Vector2DCache vector2DCache = new Vector2DCache(wWidth, wHeight);

            //NeighborhoodCache
            NeighborhoodsCache neighborhoodsCache = new NeighborhoodsCache(wWidth,
                    wHeight, neighDRadius, neighOffset, neighBufferCapacity, vector2DCache);

            //RandomNu
            RandomNumbersCache randomNumbersCache = new RandomNumbersCache(randNumbersCacheLength);

            //newWorld
            World newWorld = new World(wWidth, wHeight);
            newWorld.vector2DCache = vector2DCache;
            newWorld.neighborhoodsCache = neighborhoodsCache;
            newWorld.randomNumbersCache = randomNumbersCache;
            newWorld.randomWalkers = new ArrayList<>(Statics.Defaults.DEFAULT_INITIAL_WALKERS_CAPACITY);


            return newWorld;
        }

        public int getNeighDRadius() {
            return neighDRadius;
        }

        public void setNeighDRadius(int neighDRadius) {

            this.neighDRadius = neighDRadius;
        }

        public void setNeighOffset(int neighOffset) {
            this.neighOffset = neighOffset;
        }

        public int getNeighOffset() {
            return neighOffset;
        }

        public int getNeighBufferCapacity() {
            return neighBufferCapacity;
        }

        public void setNeighBufferCapacity(int neighBufferCapacity) {
            this.neighBufferCapacity = neighBufferCapacity;
        }

        public int getRandNumbersCacheLength() {
            return randNumbersCacheLength;
        }

        public void setRandNumbersCacheLength(int randNumbersCacheLength) {
            this.randNumbersCacheLength = randNumbersCacheLength;
        }

        public int getwWidth() {
            return wWidth;
        }

        public int getwHeight() {
            return wHeight;
        }
    }

}
