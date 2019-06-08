package Core.World;

public class Neighborhood {

    private final Vector2D origin;
    private final Vector2D center;
    private final int width;
    private final int height;
    WalkersBuffer neighbors;

    Neighborhood(Vector2D origin, Vector2D center, int width, int height, int bufferCapacity) {
        this.origin = origin;
        this.center = center;
        this.width = width;
        this.height = height;
        neighbors = new WalkersBuffer(bufferCapacity);
    }

    public WalkersBuffer getNeighbors() {
        return neighbors;
    }

    public Vector2D getOrigin() {
        return origin;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getCenterX() {
        return center.x;
    }

    public int getCenterY() {
        return center.y;
    }

    public Vector2D getCenter() {
        return center;
    }

    public int getArea() {
        return width * height;
    }

    public boolean isWithing(Vector2D vector2D) {
        return vector2D.x >= origin.x && vector2D.x < origin.x + width &&
                vector2D.y >= origin.y && vector2D.y < origin.y + height;
    }
}
