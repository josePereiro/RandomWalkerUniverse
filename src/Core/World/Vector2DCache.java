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

    private final Vector2D[][] cache;
    private final int size;
    private final int lastIndex;
    private Vector2DFactory factory;

    Vector2DCache(int wWidth, int wHeight) {
        size = Math.max(wWidth, wHeight);
        cache = new Vector2D[2 * size - 1][2 * size - 1];
        lastIndex = size - 1;
        fillCache(wWidth, wHeight);
    }

    private void fillCache(int wWidth, int wHeight) {
        int vx, vy;
        Vector2D vector2D;

        factory = new Vector2DFactory();

        //Creating partials
        for (int x = 0; x < cache.length; x++) {
            vx = x > lastIndex ? lastIndex - x : x;
            for (int y = 0; y < cache[x].length; y++) {
                vy = y > lastIndex ? lastIndex - y : y;
                vector2D = factory.getPartialVector2D(vx, vy);
                cache[x][y] = vector2D;
            }
        }

        //Finishing
        for (int x = 0; x < cache.length; x++) {
            for (int y = 0; y < cache[x].length; y++) {
                vector2D = cache[x][y];
                factory.setFourNeighbors(vector2D, wWidth - 1,
                        wHeight - 1);
                factory.setTendDistribution(vector2D);
                factory.setCollinear(vector2D);
            }
        }
    }

    public Vector2D getPositive(int x, int y) {
        return cache[x][y];
    }

    public Vector2D get(int x, int y) {
        return cache[x < 0 ? lastIndex - x : x][y < 0 ? lastIndex - y : y];
    }

    public Vector2D getAndCheck(int x, int y) {
        if (x > lastIndex) {
            throw new IndexOutOfBoundsException("x = " + x + " is too big for a cache with size = " + size);
        } else if (x < -lastIndex) {
            throw new IndexOutOfBoundsException("x = " + x + " is too small for a cache with size = " + size);
        } else if (y > lastIndex) {
            throw new IndexOutOfBoundsException("y = " + y + " is too big for a cache with size = " + size);
        } else if (y < -lastIndex) {
            throw new IndexOutOfBoundsException("y = " + y + " is too small for a cache with size = " + size);
        }
        return get(x, y);
    }

    public Vector2D distanceVector2D(Vector2D p1, Vector2D p2) {
        return get(p1.x - p2.x, p1.y - p2.y);
    }

    public float distance(Vector2D p1, Vector2D p2) {
        return get(p1.x - p2.x, p1.y - p2.y).mg;
    }

    public Vector2D distanceVector2D(int x1, int y1, int x2, int y2) {
        return get(x2 - x1, y2 - y1);
    }

    public Vector2D inverse(Vector2D v) {
        return get(0 - v.x, 0 - v.y);
    }

    public Vector2D getCollinear(Vector2D v, int b) {
        if (v.x == 0) {
            return get(0, Tools.toSameSign(v.y, b));
        } else {
            float rv = Math.abs(((float) v.y) / v.x);
            if (rv < 1F) {
                return get(Tools.toSameSign(v.x, b),
                        Tools.toSameSign(v.y, Math.round(rv * b)));
            } else {
                return get(Tools.toSameSign(v.x, Math.round(b / rv)),
                        Tools.toSameSign(v.y, b));
            }
        }
    }

    public Vector2D multiply(Vector2D v, int i) {
        return get(v.x * i, v.y * i);
    }

    public Vector2D divide(Vector2D v, int i) {
        return get(v.x / i, v.y / i);
    }

    public Vector2D add(Vector2D v1, Vector2D v2) {
        return get(v1.x + v2.x, v1.y + v2.y);
    }

    public Vector2D addIfPossible(Vector2D v1, Vector2D v2) {
        int x = v1.x + v2.x;
        int y = v1.y + v2.y;
        if (Math.abs(x) <= lastIndex && Math.abs(y) <= lastIndex) {
            return get(x, y);
        }
        return v1;
    }

    public Vector2D substract(Vector2D v1, Vector2D v2) {
        return get(v1.x - v2.x, v1.y - v2.y);
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

        private void setFourNeighbors(Vector2D vector2D, int lastX, int lastY) {
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
            if (vector2D.y == lastY) {
                neighbors[DOWN_NEIGHBORHOOD_INDEX] = get(vector2D.x, vector2D.y);
            } else {
                neighbors[DOWN_NEIGHBORHOOD_INDEX] = get(vector2D.x, vector2D.y + 1);
            }

            //Right
            if (vector2D.x == lastX) {
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
            float max = 4 * (size - 1);
            float[] tendDist = vector2D.tendDistribution;
            assert tendDist != null;
            tendDist[UP_NEIGHBORHOOD_INDEX] = 0.25F - vector2D.y / max;
            tendDist[DOWN_NEIGHBORHOOD_INDEX] = tendDist[UP_NEIGHBORHOOD_INDEX] + 0.25F + vector2D.y / max;
            tendDist[RIGHT_NEIGHBORHOOD_INDEX] = tendDist[DOWN_NEIGHBORHOOD_INDEX] + 0.25F + vector2D.x / max;
        }

        private void setCollinear(Vector2D vector2D) {
            vector2D.maxCollinear = getCollinear(vector2D, lastIndex);
            vector2D.baseCollinear = getCollinear(vector2D, Vector2D.DEFAULT_BASE_BOUND);
        }

    }

}
