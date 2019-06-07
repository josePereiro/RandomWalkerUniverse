package Core.World;


import Core.Tools.Tools;

public final class SpacePoint implements Tendency {

    private static final int TENDENCY_DISTRIBUTION_LENGTH = 3;

    public final int x;
    public final int y;
    public final float mg;
    SpacePoint maxCollinear;

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

    //Space Info
    final SpacePoint[] fourNeighbors;
    Neighborhood[] neighborhoods;
    private int neighborhoodsCount;

    SpacePoint(int x, int y) {
        this.x = x;
        this.y = y;
        mg = distance(0, 0, x, y);

        tendDistribution = new float[TENDENCY_DISTRIBUTION_LENGTH];
        if (x < 0 || y < 0)
            fourNeighbors = null;
        else
            fourNeighbors = new SpacePoint[4];
        neighborhoodsCount = 0;
    }

    public SpacePoint getMaxCollinear() {
        return maxCollinear;
    }

    private static float distance(int x1, int y1, int x2, int y2) {
        x1 -= x2;
        y1 -= y2;
        return (float) Math.sqrt(x1 * x1 + y1 * y1);
    }

    void addNeighborhood(Neighborhood newNeighborhood) {
        Neighborhood firstNeighborhood;
        SpacePoint centerFirst, centerNew;
        float distToFirst, distToNew;
        if (neighborhoodsCount < neighborhoods.length) {
            if (neighborhoodsCount == 0) {
                addNeighborhoodAsFirst(newNeighborhood);
            } else {
                firstNeighborhood = neighborhoods[0];
                centerFirst = firstNeighborhood.getCenter();
                distToFirst = distance(centerFirst.x, centerFirst.y, x, y);
                centerNew = newNeighborhood.getCenter();
                distToNew = distance(centerNew.x, centerNew.y, x, y);
                if (distToFirst == distToNew) {
                    if (firstNeighborhood.getArea() < newNeighborhood.getArea()) {
                        addNeighborhoodAsFirst(newNeighborhood);
                    } else {
                        addNeighborhoodAtEnd(newNeighborhood);
                    }
                } else if (distToFirst > distToNew) {
                    addNeighborhoodAsFirst(newNeighborhood);
                } else {
                    addNeighborhoodAtEnd(newNeighborhood);
                }
            }
        }
    }

    private void addNeighborhoodAsFirst(Neighborhood neighborhood) {
        if (neighborhoodsCount > 0) {
            Tools.CollectionsOps.moveItemsForward(0, neighborhoods);
        }
        neighborhoods[0] = neighborhood;
        neighborhoodsCount++;
    }

    private void addNeighborhoodAtEnd(Neighborhood neighborhood) {
        neighborhoods[neighborhoodsCount] = neighborhood;
        neighborhoodsCount++;
    }

    public Neighborhood[] getNeighborhoods() {
        return neighborhoods;
    }

    public int getNeighborhoodsCount() {
        return neighborhoodsCount;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " (x=" + x + ",y=" + y + ")";
    }

    @Override
    public float[] getTendencyDistribution() {
        return tendDistribution;
    }
}
