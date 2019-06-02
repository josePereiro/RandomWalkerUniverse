package Core.Basics.NeighborhoodsBoard;

import Core.Basics.Vector2D.Vector2D;

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
