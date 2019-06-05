package Core.World;

import java.util.ArrayList;

public class NeighborhoodsCache {

    private final World world;
    private final Vector2DCache vector2DCache;
    private final int dRadius;
    private Neighborhood[] neighborhoods;


    public NeighborhoodsCache(World world, int dRadius) {
        this.dRadius = dRadius;
        checkParameters(world, dRadius);
        this.world = world;
        vector2DCache = world.getVector2DCache();
        createNeighborhoods();
    }

    private void createNeighborhoods() {


    }

    private Vector2D[] getDimProjections(int dimLength, int dRadius) {

        //Setting up
        ArrayList<Vector2D> projections = new ArrayList<>();
        int midIndex = (dimLength - 1) / 2;

        if (dimLength % 2 == 0) {
            return null;
        } else {
            //First
            int center = midIndex - dRadius;
            if (center < 0) {
                projections.add(vector2DCache.get(0, dimLength));
            } else {
                projections.add(vector2DCache.get(center - dRadius, 2 * dRadius + 1));
            }

            for (int dr = dRadius; dr < dimLength; dr += dRadius) {
                center = midIndex - dr;
                if (center < 0) {
                    projections.add(vector2DCache.get(0, dimLength));
                }

            }

        }

        return projections.toArray(new Vector2D[projections.size()]);
    }


    private void checkParameters(World world, int r) {
        if (r < 2) {
            throw new Exceptions.IllegalValueException("r must be bigger than 1");
        }
    }

    public Neighborhood[] getNeighborhoods() {
        return neighborhoods;
    }
}
