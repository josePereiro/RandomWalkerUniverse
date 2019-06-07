package Core.World;

public class Neighborhood {

    private final SpacePoint origin;
    private final SpacePoint center;
    private final int width;
    private final int height;
    WalkersBuffer neighbors;

    Neighborhood(SpacePoint origin, SpacePoint center, int width, int height, int bufferCapacity) {
        this.origin = origin;
        this.center = center;
        this.width = width;
        this.height = height;
        neighbors = new WalkersBuffer(bufferCapacity);
    }

    public WalkersBuffer getNeighbors() {
        return neighbors;
    }

    public SpacePoint getOrigin() {
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

    public SpacePoint getCenter() {
        return center;
    }

    public int getArea() {
        return width * height;
    }

    public boolean isWithing(SpacePoint spacePoint) {
        return spacePoint.x >= origin.x && spacePoint.x < origin.x + width &&
                spacePoint.y >= origin.y && spacePoint.y < origin.y + height;
    }
}
