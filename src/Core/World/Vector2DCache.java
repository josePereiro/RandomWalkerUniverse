package Core.World;

import static Core.World.World.Statics.*;

/**
 * TODO implement a cache for Vector2DTest!!!
 * Why caching?:
 * Vectors will be very commonly used... It is very possible
 * that a lot of operations or a lot of objects will use the same Vector,
 * so I wanna cached!!!
 * I will do Vector2DTest
 * immutable so any operation that returns (2,2) will be returning
 * the same object in memory. Now, knowing that, I can store in
 * Vector2DTest other useful information like the magnitude, and the
 * reference to other related vectors, like the perpendiculars, the inverse,
 * etc.
 */
public class Vector2DCache {


    final Vector2D[][] cache;
    private final World world;
    private final int xb;
    private final int yb;

    public Vector2DCache(World world) {
        this.world = world;
        cache = new Vector2D[world.w * 2 - 1][world.h * 2 - 1];
        xb = world.w - 1;
        yb = world.h - 1;
        fillCache();
    }

    private void fillCache() {
        int vx, vy;
        Vector2D vector2D;

        //Creating partials
        for (int x = 0; x < cache.length; x++) {
            vx = x > xb ? xb - x : x;
            for (int y = 0; y < cache[x].length; y++) {
                vy = y > yb ? yb - y : y;
                vector2D = Vector2DFactory.getPartialVector2D(vx, vy);
                cache[x][y] = vector2D;
            }
        }

        //Finishing
        for (int x = 0; x < cache.length; x++) {
            for (int y = 0; y < cache[x].length; y++) {
                vector2D = cache[x][y];
                Vector2DFactory.setFourNeighbors(vector2D, this);
                Vector2DFactory.setTendDistribution(vector2D, world.w, world.h);
            }
        }
    }

    public Vector2D getPositive(int x, int y) {
        return cache[x][y];
    }

    public Vector2D get(int x, int y) {
        return cache[x < 0 ? xb - x : x][y < 0 ? yb - y : y];
    }

    public Vector2D getAndCheck(int x, int y) {
        if (x > xb) {
            throw new IndexOutOfBoundsException("x = " + x + " is too big for a cache with w = " + world.w);
        } else if (x < -xb) {
            throw new IndexOutOfBoundsException("x = " + x + " is too small for a cache with w = " + world.w);
        } else if (y > yb) {
            throw new IndexOutOfBoundsException("y = " + y + " is too big for a cache with h = " + world.h);
        } else if (y < -yb) {
            throw new IndexOutOfBoundsException("y = " + y + " is too small for a cache with h = " + world.h);
        }
        return get(x, y);
    }

    private static int abs(int x) {
        return x < 0 ? -x : x;
    }

    private static class Vector2DFactory {

        /**
         * Returns an Vector2D in an incomplete state.
         *
         * @param x the x coordinate
         * @param y the y coordinate
         * @return
         */
        private static Vector2D getPartialVector2D(int x, int y) {
            return new Vector2D(x, y);
        }

        private static void setFourNeighbors(Vector2D vector2D, Vector2DCache vector2Dcache) {
            if (vector2D.x < 0 || vector2D.y < 0)
                return;

            Vector2D[] neighbors = vector2D.fourNeighbors;
            assert neighbors != null;

            //UP
            if (vector2D.y == 0) {
                neighbors[UP_NEIGHBORHOOD_INDEX] = vector2Dcache.get(vector2D.x, vector2D.y);
            } else {
                neighbors[UP_NEIGHBORHOOD_INDEX] = vector2Dcache.get(vector2D.x, vector2D.y - 1);
            }

            //Down
            if (vector2D.y == vector2Dcache.yb) {
                neighbors[DOWN_NEIGHBORHOOD_INDEX] = vector2Dcache.get(vector2D.x, vector2D.y);
            } else {
                neighbors[DOWN_NEIGHBORHOOD_INDEX] = vector2Dcache.get(vector2D.x, vector2D.y + 1);
            }

            //Right
            if (vector2D.x == vector2Dcache.yb) {
                neighbors[RIGHT_NEIGHBORHOOD_INDEX] = vector2Dcache.get(vector2D.x, vector2D.y);
            } else {
                neighbors[RIGHT_NEIGHBORHOOD_INDEX] = vector2Dcache.get(vector2D.x + 1, vector2D.y);
            }

            //Left
            if (vector2D.x == 0) {
                neighbors[LEFT_NEIGHBORHOOD_INDEX] = vector2Dcache.get(vector2D.x, vector2D.y);
            } else {
                neighbors[LEFT_NEIGHBORHOOD_INDEX] = vector2Dcache.get(vector2D.x - 1, vector2D.y);
            }

        }

        private static void setTendDistribution(Vector2D vector2D, int worldW, int worldH) {
            float axm = 4 * (worldW - 1);
            float aym = 4 * (worldH - 1);
            float[] tendDist = vector2D.tendDistribution;
            assert tendDist != null;
            tendDist[UP_NEIGHBORHOOD_INDEX] = 0.25F - vector2D.y / aym;
            tendDist[DOWN_NEIGHBORHOOD_INDEX] = tendDist[UP_NEIGHBORHOOD_INDEX] + 0.25F + vector2D.y / aym;
            tendDist[RIGHT_NEIGHBORHOOD_INDEX] = tendDist[DOWN_NEIGHBORHOOD_INDEX] + 0.25F + vector2D.x / axm;
        }


    }

}
