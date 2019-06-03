package Core.Basics.Walkers;

import Core.World.Vector2D;
import Core.World.World;

/**
 * The basis of any walker. It is a walker that occupy only
 * one pixel in the World.
 */
public class PointyWalker {

    private Vector2D position;

    /**
     * Create a walker at the given position.
     *
     * @param p0 the starting position.
     */
    public PointyWalker(Vector2D p0) {
    }

    public Vector2D getPosition() {
        return position;
    }

}
