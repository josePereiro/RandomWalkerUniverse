package Core.World;


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
    final float[] tendDistribution;
    final Vector2D[] fourNeighbors;
    Vector2D maxCollinear;
//    final Neighborhood[] neighborhoods;

    Vector2D(int x, int y) {
        this.x = x;
        this.y = y;
        mg = magnitude(0, 0, x, y);
        tendDistribution = new float[3];
        if (x < 0 || y < 0)
            fourNeighbors = null;
        else
            fourNeighbors = new Vector2D[4];

    }

    private static float magnitude(int x1, int y1, int x2, int y2) {
        x1 -= x2;
        y1 -= y2;
        return (float) Math.sqrt(x1 * x1 + y1 * y1);
    }

    public Vector2D getMaxCollinear() {
        return maxCollinear;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " (x=" + x + ",y=" + y + ")";
    }


}
