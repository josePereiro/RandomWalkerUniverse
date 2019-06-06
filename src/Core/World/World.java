package Core.World;

public class World {

    private final Vector2DCache vector2DCache;
    private final RandomNumbersCache randomNumbersCache;
    private NeighborhoodsCache neighborhoodsCache;
    public final int width;
    public final int height;
    private int neighborsRadius;

    public World(int width, int height) {
        this.width = width;
        this.height = height;
        vector2DCache = new Vector2DCache(width,height);
        randomNumbersCache = new RandomNumbersCache(Statics.Defaults.DEFAULT_RANDOM_CACHE_LENGTH);
        neighborsRadius = Math.max(width, height) / 9;
        setNeighborhoodsCache();
    }

    //TODO delete
    private void setNeighborhoodsCache() {
        neighborhoodsCache = new NeighborhoodsCache(this);
    }

    public int getNeighborsRadius() {
        return neighborsRadius;
    }


    //TODO delete
    public void setNeighborsRadius(int neighborsRadius) {
        this.neighborsRadius = neighborsRadius;
        setNeighborhoodsCache();
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

    public static class Statics {

        //Statics
        public static final int UP_NEIGHBORHOOD_INDEX = 0;
        public static final int DOWN_NEIGHBORHOOD_INDEX = 1;
        public static final int RIGHT_NEIGHBORHOOD_INDEX = 2;
        public static final int LEFT_NEIGHBORHOOD_INDEX = 3;

        public static class Defaults {

            private static final int DEFAULT_RANDOM_CACHE_LENGTH = 10000000;

        }

    }

}
