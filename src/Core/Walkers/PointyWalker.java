package Core.Walkers;

import Core.World.SpacePoint;

/**
 * The basis of any walker. It is a walker that occupy only
 * one pixel in the World.
 */
public class PointyWalker implements Walker {

    private SpacePoint location;

    /**
     * Create a walker at the given location.
     *
     * @param location the starting location.
     */
    public PointyWalker(SpacePoint location) {
        this.location = location;
    }

    @Override
    public SpacePoint getLocation() {
        return location;
    }
}
