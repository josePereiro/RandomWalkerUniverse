package Core.World;


public final class SpacePoint extends Vector2D {

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
    final float[] tendDistribution;
    final SpacePoint[] fourNeighbors;
    SpacePoint maxCollinear;
    Neighborhood[] neighborhoods;

    SpacePoint(int x, int y) {
        super(x, y);
        tendDistribution = new float[3];
        if (x < 0 || y < 0)
            fourNeighbors = null;
        else
            fourNeighbors = new SpacePoint[4];

    }

    public SpacePoint getMaxCollinear() {
        return maxCollinear;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " (x=" + x + ",y=" + y + ")";
    }


}
