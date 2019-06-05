package Core.World;

public class Neighborhood {

    private final Vector2D origin;
    private final int r;

    Neighborhood(Vector2D origin, int width, int heigth) {
        this.origin = origin;
        this.r = width;
    }

    public Vector2D getOrigin() {
        return origin;
    }

    public int getR() {
        return r;
    }
}
