package Core.Basics.Walkers;

import Core.Basics.Vector2D.Vector2D;
import Core.World;

/**
 * The basis of any walker. It is a walker that occupy only
 * one pixel in the World.
 */
public class PointyWalker {

    private Vector2D position;

    /**
     * Create a Walker at the origin.
     */
    public PointyWalker() {
        setPosition(0, 0);
    }

    /**
     * Create a walker at the given position.
     *
     * @param x0 the starting x position
     * @param y0 the starting y position
     */
    public PointyWalker(int x0, int y0) {
        setPosition(x0, y0);
    }

    /**
     * Create a walker at the given position.
     *
     * @param p0 the starting position.
     */
    public PointyWalker(Vector2D p0) {
        setPosition(p0);
    }

    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }

    public void setPosition(int x0, int y0) {
        position = World.Statics.Caches.vector2Dcache.get(x0, y0);
    }
}
