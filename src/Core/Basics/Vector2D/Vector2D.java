package Core.Basics.Vector2D;

import Core.World;

import static Core.World.Statics.Caches.*;
import static Core.World.Statics.worldH;
import static Core.World.Statics.worldW;

public final class Vector2D {


    public final int x;
    public final int y;
    public final float mg;
    /**
     * An array used to transform this vector representation
     * of a tendency to a nextStep.
     * Indexes are arrange as follow:<p>
     * 0. Chance to go Up<p>
     * 1. Chance to go Down<p>
     * 2. Chance to go Right<p>
     * As the total chances sum 1.0,
     * the expected probability of choosing Left
     * will be 1 - Up - Down - Right.
     */
    public final float[] tendDistribution;
    public final Vector2D[] fourNeighbors;

    //Statics
    public static final int UP_INDEX = 0;
    public static final int DOWN_INDEX = 1;
    public static final int RIGHT_INDEX = 2;
    public static final int LEFT_INDEX = 3;

    public Vector2D(int x, int y) {
        this.x = x;
        this.y = y;
        mg = magnitude(0, 0, x, y);
        tendDistribution = getTendDistribution(x, y,
                4 * (worldW - 1), 4 * (worldH - 1));
        fourNeighbors = getFourNeighbors(x, y, worldW, worldH);
    }

    private Vector2D[] getFourNeighbors(int x, int y, int worldW, int worldH) {
        if (x < 0 || y < 0)
            return null;

        Vector2D[] neighbors = new Vector2D[4];

        //UP
        if (y == 0) {
            neighbors[UP_INDEX] = vector2Dcache.get(x, y);
        } else {
            neighbors[UP_INDEX] = vector2Dcache.get(x, y - 1);
        }

        //Down
        if (y == worldH - 1) {
            neighbors[DOWN_INDEX] = vector2Dcache.get(x, y);
        } else {
            neighbors[DOWN_INDEX] = vector2Dcache.get(x, y + 1);
        }

        //Right
        if (x == worldW - 1) {
            neighbors[RIGHT_INDEX] = vector2Dcache.get(x, y);
        } else {
            neighbors[RIGHT_INDEX] = vector2Dcache.get(x + 1, y);
        }

        //Left
        if (x == 0) {
            neighbors[LEFT_INDEX] = vector2Dcache.get(x, y);
        } else {
            neighbors[LEFT_INDEX] = vector2Dcache.get(x - 1, y);
        }

        return neighbors;

    }

    private float[] getTendDistribution(int x, int y, float axm, float aym) {
        float[] tendDist = new float[3];
        tendDist[UP_INDEX] = 0.25F - y / aym;
        tendDist[DOWN_INDEX] = tendDist[UP_INDEX] + 0.25F + y / aym;
        tendDist[RIGHT_INDEX] = tendDist[DOWN_INDEX] + 0.25F + x / axm;
        return tendDist;
    }

    public static float magnitude(int x1, int y1, int x2, int y2) {
        x1 -= x2;
        y1 -= y2;
        return (float) Math.sqrt(x1 * x1 + y1 * y1);
    }

    /**
     * Return the clock-wise Normal {@code Vector2D} of a given vector.
     * If the given vector is in the {@link Vector2DCache} of
     * {@link World.Statics.Caches}, it is ensure that the returned
     * {@code Vector2D}
     * too.
     *
     * @param vector the given vector
     * @return the clock-wise Normal vector
     * @see Vector2DCache
     * @see World.Statics.Caches
     */
    public static Vector2D cwNormal(Vector2D vector) {
        return vector2Dcache.get(vector.x, -vector.y);
    }

    /**
     * Return a {@link Vector2D} result of the addition of the given vectors.
     * As any other operation with {@link Vector2D} it is restricted to the
     * size of the {@link Vector2DCache} of {@link World.Statics.Caches}, so, if
     * the sum overload it it will throw an Exception.
     *
     * @param v1 one vector to add
     * @param v2 the other vector to add.
     * @return tre result vector
     */
    public static Vector2D add(Vector2D v1, Vector2D v2) {
        return vector2Dcache.getAndCheck(v1.x + v2.x, v1.y + v2.y);
    }


    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " (x=" + x + ",y=" + y + ")";
    }
}
