package Core.World;

import Core.Tools.Tools;

import static Core.World.World.Statics.*;

/**
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

    /**
     * TODO
     * Implement a square cache...
     * Why?
     * If the cache only depends of the size of the world,
     * if the world width is 10 time smaller than the height,
     * that means the x axis will be 10 time smaller than the y.
     * This affects, for instance, the tendencies. The y tendency
     * parts will be 10 time more 'continuous' than the xs...
     */


    final Vector2D[][] cache;
    private final int xb;
    private final int yb;
    private final int width;
    private final int height;

    public Vector2DCache(int width, int height) {
        this.width = width;
        this.height = height;
        cache = new Vector2D[width * 2 - 1][height * 2 - 1];
        xb = width - 1;
        yb = height - 1;
        fillCache();
    }

    private void fillCache() {
        int vx, vy;
        Vector2D vector2D;

        Vector2DFactory factory = new Vector2DFactory();

        //Creating partials
        for (int x = 0; x < cache.length; x++) {
            vx = x > xb ? xb - x : x;
            for (int y = 0; y < cache[x].length; y++) {
                vy = y > yb ? yb - y : y;
                vector2D = factory.getPartialVector2D(vx, vy);
                cache[x][y] = vector2D;
            }
        }

        //Finishing
        for (int x = 0; x < cache.length; x++) {
            for (int y = 0; y < cache[x].length; y++) {
                vector2D = cache[x][y];
                factory.setFourNeighbors(vector2D);
                factory.setTendDistribution(vector2D);
                factory.setMaxCollinear(vector2D);
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
            throw new IndexOutOfBoundsException("x = " + x + " is too big for a cache with w = " + width);
        } else if (x < -xb) {
            throw new IndexOutOfBoundsException("x = " + x + " is too small for a cache with w = " + width);
        } else if (y > yb) {
            throw new IndexOutOfBoundsException("y = " + y + " is too big for a cache with h = " + height);
        } else if (y < -yb) {
            throw new IndexOutOfBoundsException("y = " + y + " is too small for a cache with h = " + height);
        }
        return get(x, y);
    }

    public Vector2D distance(Vector2D p1, Vector2D p2) {
        return get(p1.x - p2.x, p1.y - p2.y);
    }

    public Vector2D distance(int x1, int y1, int x2, int y2) {
        return get(x2 - x1, y2 - y1);
    }

    public Vector2D inverse(Vector2D v) {
        return get(0 - v.x, 0 - v.y);
    }

    public Vector2D getMaxCollinear(Vector2D v) {
        float rb = (float) yb / xb;
        if (v.x == 0) {
            return get(0, Tools.toSameSign(v.y, yb));
        } else {
            float rv = Math.abs(((float) v.y) / v.x);
            if (rv < rb) {
                return get(Tools.toSameSign(v.x, xb), Tools.toSameSign(v.y, Math.round(rv * xb)));
            } else {
                return get(Tools.toSameSign(v.x, Math.round(yb / rv)), Tools.toSameSign(v.y, yb));
            }
        }
    }

    public Vector2D multiply(Vector2D v, int i) {
        return get(v.x * i, v.y * i);
    }

    private class Vector2DFactory {

        /**
         * Returns an Vector2D in an incomplete state.
         *
         * @param x the x coordinate
         * @param y the y coordinate
         * @return
         */
        private Vector2D getPartialVector2D(int x, int y) {
            return new Vector2D(x, y);
        }

        private void setFourNeighbors(Vector2D vector2D) {
            if (vector2D.x < 0 || vector2D.y < 0)
                return;

            Vector2D[] neighbors = vector2D.fourNeighbors;
            assert neighbors != null;

            //UP
            if (vector2D.y == 0) {
                neighbors[UP_NEIGHBORHOOD_INDEX] = get(vector2D.x, vector2D.y);
            } else {
                neighbors[UP_NEIGHBORHOOD_INDEX] = get(vector2D.x, vector2D.y - 1);
            }

            //Down
            if (vector2D.y == yb) {
                neighbors[DOWN_NEIGHBORHOOD_INDEX] = get(vector2D.x, vector2D.y);
            } else {
                neighbors[DOWN_NEIGHBORHOOD_INDEX] = get(vector2D.x, vector2D.y + 1);
            }

            //Right
            if (vector2D.x == xb) {
                neighbors[RIGHT_NEIGHBORHOOD_INDEX] = get(vector2D.x, vector2D.y);
            } else {
                neighbors[RIGHT_NEIGHBORHOOD_INDEX] = get(vector2D.x + 1, vector2D.y);
            }

            //Left
            if (vector2D.x == 0) {
                neighbors[LEFT_NEIGHBORHOOD_INDEX] = get(vector2D.x, vector2D.y);
            } else {
                neighbors[LEFT_NEIGHBORHOOD_INDEX] = get(vector2D.x - 1, vector2D.y);
            }

        }

        private void setTendDistribution(Vector2D vector2D) {
            float axm = 4 * (width - 1);
            float aym = 4 * (height - 1);
            float[] tendDist = vector2D.tendDistribution;
            assert tendDist != null;
            tendDist[UP_NEIGHBORHOOD_INDEX] = 0.25F - vector2D.y / aym;
            tendDist[DOWN_NEIGHBORHOOD_INDEX] = tendDist[UP_NEIGHBORHOOD_INDEX] + 0.25F + vector2D.y / aym;
            tendDist[RIGHT_NEIGHBORHOOD_INDEX] = tendDist[DOWN_NEIGHBORHOOD_INDEX] + 0.25F + vector2D.x / axm;
        }

        private void setMaxCollinear(Vector2D vector2D) {
            vector2D.maxCollinear = getMaxCollinear(vector2D);
        }

    }

}
