package Core.World;

public abstract class Walker {

    private Vector2D location;
    private Vector2D tendency;

    //Extras
    int[] intExtras;
    float[] floatExtras;
    boolean[] booleanExtras;
    String[] stringExtras;

    public Walker(Vector2D location) {
        this.location = location;
    }

    public Vector2D getLocation() {
        return location;
    }

    public Vector2D getTendency() {
        return tendency;
    }

    public abstract Vector2D computeTendency(WalkersBuffer neighbors, Vector2DCache cache);

    void updateTendency(WalkersBuffer neighbors, Vector2DCache cache) {
        tendency = computeTendency(neighbors, cache);
    }

    void setLocation(Vector2D location) {
        this.location = location;
    }

    Neighborhood getClosestNeighborhood() {
        return location.getClosestNeighborhood();
    }

    public int[] getIntExtras() {
        return intExtras;
    }

    public void setIntExtras(int[] intExtras) {
        this.intExtras = intExtras;
    }

    public float[] getFloatExtras() {
        return floatExtras;
    }

    public void setFloatExtras(float[] floatExtras) {
        this.floatExtras = floatExtras;
    }

    public boolean[] getBooleanExtras() {
        return booleanExtras;
    }

    public void setBooleanExtras(boolean[] booleanExtras) {
        this.booleanExtras = booleanExtras;
    }

    public String[] getStringExtras() {
        return stringExtras;
    }

    public void setStringExtras(String[] stringExtras) {
        this.stringExtras = stringExtras;
    }
}
