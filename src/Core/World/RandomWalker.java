package Core.World;

public class RandomWalker {

    private Vector2D location;
    private Vector2D tendency;

    //Extras
    int[] intExtras;
    float[] floatExtras;
    boolean[] booleanExtras;
    String[] stringExtras;

    public RandomWalker(Vector2D location) {
        this.location = location;
        onCreation();
    }

    public Vector2D onTendencyUpdate(WalkersBuffer neighbors, Vector2DCache cache) {
        return cache.get(0, 0);
    }

    public void onCreation() {
    }

    public final Vector2D getLocation() {
        return location;
    }

    public final Vector2D getTendency() {
        return tendency;
    }

    final void updateTendency(WalkersBuffer neighbors, Vector2DCache cache) {
        tendency = onTendencyUpdate(neighbors, cache);
    }

    final void setLocation(Vector2D location) {
        this.location = location;
    }

    Neighborhood getClosestNeighborhood() {
        return location.getClosestNeighborhood();
    }

    public final int[] getIntExtras() {
        return intExtras;
    }

    public final void setIntExtras(int[] intExtras) {
        this.intExtras = intExtras;
    }

    public final float[] getFloatExtras() {
        return floatExtras;
    }

    public final void setFloatExtras(float[] floatExtras) {
        this.floatExtras = floatExtras;
    }

    public final boolean[] getBooleanExtras() {
        return booleanExtras;
    }

    public final void setBooleanExtras(boolean[] booleanExtras) {
        this.booleanExtras = booleanExtras;
    }

    public final String[] getStringExtras() {
        return stringExtras;
    }

    public final void setStringExtras(String[] stringExtras) {
        this.stringExtras = stringExtras;
    }
}
