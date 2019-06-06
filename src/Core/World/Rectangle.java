package Core.World;

public abstract class Rectangle {

    private final Vector2D origin;
    private final int width;
    private final int height;

    public Rectangle(Vector2D origin, int width, int height) {
        this.origin = origin;
        this.width = width;
        this.height = height;
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
        return width / 2 + origin.x;
    }

    public int getCenterY() {
        return height / 2 + origin.y;
    }

    public boolean isWithing(Vector2D vector2D) {
        return vector2D.x >= origin.x && vector2D.x < origin.x + width &&
                vector2D.y >= origin.y && vector2D.y < origin.y + height;
    }
}
