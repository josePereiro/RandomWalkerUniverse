package Core.World;

import Core.Tools.Tools;

import static Core.World.World.Statics.*;

/**
 * Why caching?:
 * Vectors will be very commonly used... It is very possible
 * that a lot of operations or a lot of objects will use the same Vector,
 * so I wanna cached!!!
 * I will do SpacePointTest
 * immutable so any operation that returns (2,2) will be returning
 * the same object in memory. Now, knowing that, I can store in
 * SpacePointTest other useful information like the magnitude, and the
 * reference to other related vectors, like the perpendiculars, the inverse,
 * etc.
 */
public class SpacePointsCache {

    private final SpacePoint[][] cache;
    private final int size;
    private final int lastIndex;
    private Vector2DFactory factory;

    SpacePointsCache(int wWidth, int wHeight) {
        size = Math.max(wWidth, wHeight);
        cache = new SpacePoint[2 * size - 1][2 * size - 1];
        lastIndex = size - 1;
        fillCache(wWidth, wHeight);
    }

    private void fillCache(int wWidth, int wHeight) {
        int vx, vy;
        SpacePoint spacePoint;

        factory = new Vector2DFactory();

        //Creating partials
        for (int x = 0; x < cache.length; x++) {
            vx = x > lastIndex ? lastIndex - x : x;
            for (int y = 0; y < cache[x].length; y++) {
                vy = y > lastIndex ? lastIndex - y : y;
                spacePoint = factory.getPartialVector2D(vx, vy);
                cache[x][y] = spacePoint;
            }
        }

        //Finishing
        for (int x = 0; x < cache.length; x++) {
            for (int y = 0; y < cache[x].length; y++) {
                spacePoint = cache[x][y];
                factory.setFourNeighbors(spacePoint, wWidth - 1,
                        wHeight - 1);
                factory.setTendDistribution(spacePoint);
                factory.setMaxCollinear(spacePoint);
            }
        }
    }

    public SpacePoint getPositive(int x, int y) {
        return cache[x][y];
    }

    public SpacePoint get(int x, int y) {
        return cache[x < 0 ? lastIndex - x : x][y < 0 ? lastIndex - y : y];
    }

    public SpacePoint getAndCheck(int x, int y) {
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

    public SpacePoint distance(SpacePoint p1, SpacePoint p2) {
        return get(p1.x - p2.x, p1.y - p2.y);
    }

    public SpacePoint distance(int x1, int y1, int x2, int y2) {
        return get(x2 - x1, y2 - y1);
    }

    public SpacePoint inverse(SpacePoint v) {
        return get(0 - v.x, 0 - v.y);
    }

    private SpacePoint getMaxCollinear(SpacePoint v) {
        if (v.x == 0) {
            return get(0, Tools.toSameSign(v.y, lastIndex));
        } else {
            float rv = Math.abs(((float) v.y) / v.x);
            if (rv < 1F) {
                return get(Tools.toSameSign(v.x, lastIndex),
                        Tools.toSameSign(v.y, Math.round(rv * lastIndex)));
            } else {
                return get(Tools.toSameSign(v.x, Math.round(lastIndex / rv)),
                        Tools.toSameSign(v.y, lastIndex));
            }
        }
    }

    public SpacePoint multiply(SpacePoint v, int i) {
        return get(v.x * i, v.y * i);
    }

    private class Vector2DFactory {

        /**
         * Returns an SpacePoint in an incomplete state.
         *
         * @param x the x coordinate
         * @param y the y coordinate
         * @return
         */
        private SpacePoint getPartialVector2D(int x, int y) {
            return new SpacePoint(x, y);
        }

        private void setFourNeighbors(SpacePoint spacePoint, int lastX, int lastY) {
            if (spacePoint.x < 0 || spacePoint.y < 0)
                return;

            SpacePoint[] neighbors = spacePoint.fourNeighbors;
            assert neighbors != null;

            //UP
            if (spacePoint.y == 0) {
                neighbors[UP_NEIGHBORHOOD_INDEX] = get(spacePoint.x, spacePoint.y);
            } else {
                neighbors[UP_NEIGHBORHOOD_INDEX] = get(spacePoint.x, spacePoint.y - 1);
            }

            //Down
            if (spacePoint.y == lastY) {
                neighbors[DOWN_NEIGHBORHOOD_INDEX] = get(spacePoint.x, spacePoint.y);
            } else {
                neighbors[DOWN_NEIGHBORHOOD_INDEX] = get(spacePoint.x, spacePoint.y + 1);
            }

            //Right
            if (spacePoint.x == lastX) {
                neighbors[RIGHT_NEIGHBORHOOD_INDEX] = get(spacePoint.x, spacePoint.y);
            } else {
                neighbors[RIGHT_NEIGHBORHOOD_INDEX] = get(spacePoint.x + 1, spacePoint.y);
            }

            //Left
            if (spacePoint.x == 0) {
                neighbors[LEFT_NEIGHBORHOOD_INDEX] = get(spacePoint.x, spacePoint.y);
            } else {
                neighbors[LEFT_NEIGHBORHOOD_INDEX] = get(spacePoint.x - 1, spacePoint.y);
            }

        }

        private void setTendDistribution(SpacePoint spacePoint) {
            float max = 4 * (size - 1);
            float[] tendDist = spacePoint.tendDistribution;
            assert tendDist != null;
            tendDist[UP_NEIGHBORHOOD_INDEX] = 0.25F - spacePoint.y / max;
            tendDist[DOWN_NEIGHBORHOOD_INDEX] = tendDist[UP_NEIGHBORHOOD_INDEX] + 0.25F + spacePoint.y / max;
            tendDist[RIGHT_NEIGHBORHOOD_INDEX] = tendDist[DOWN_NEIGHBORHOOD_INDEX] + 0.25F + spacePoint.x / max;
        }

        private void setMaxCollinear(SpacePoint spacePoint) {
            spacePoint.maxCollinear = getMaxCollinear(spacePoint);
        }

    }

}
