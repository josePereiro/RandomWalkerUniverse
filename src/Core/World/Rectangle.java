package Core.World;

public abstract class Rectangle {

    private final SpacePoint origin;
    private final int width;
    private final int height;

    Rectangle(SpacePoint origin, int width, int height) {
        this.origin = origin;
        this.width = width;
        this.height = height;
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
        return width / 2 + origin.x;
    }

    public int getCenterY() {
        return height / 2 + origin.y;
    }

    public boolean isWithing(SpacePoint spacePoint) {
        return spacePoint.x >= origin.x && spacePoint.x < origin.x + width &&
                spacePoint.y >= origin.y && spacePoint.y < origin.y + height;
    }
}
