package Core.World;

public class Neighborhood {

    private final Vector2D center;
    private final int r;

    public Neighborhood(Vector2D center, int r) {

        this.center = center;
        this.r = r;
    }

    public Vector2D getCenter() {
        return center;
    }

    public int getR() {
        return r;
    }
}
