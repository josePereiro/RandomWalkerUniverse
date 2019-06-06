package Core.World;

import java.util.ArrayList;

public class NeighborhoodsCache {

    private final Vector2DCache vector2DCache;
    private final int wWidth;
    private final int wHeight;
    private final int dRadius;
    private Neighborhood[] neighborhoods;


    public NeighborhoodsCache(int wWidth, int wHeight, int dRadius, Vector2DCache vector2DCache) {
        this.wWidth = wWidth;
        this.wHeight = wHeight;
        this.dRadius = dRadius;
        this.vector2DCache = vector2DCache;
        createNeighborhoods();
    }

    private static Vector2D getOriginAndSize(int index, ArrayList<Integer> centers, int dimLength, int dRadius,
                                             Vector2DCache cache) {
        int origin, size;
        if (index == 0) {
            if (centers.size() > 1) {
                origin = centers.get(0) - dRadius;
                size = centers.get(2) - centers.get(1) + 1;
            } else {
                origin = 0;
                size = dimLength;
            }
        } else {
            origin = centers.get(index) - dRadius;
            if (origin < 0) {
                origin = 0;
                size = dRadius + centers.get(index) + 1;
            } else {
                size = 2 * dRadius + 1;
                if (centers.get(index) + dRadius >= dimLength) {
                    size = dimLength - origin;
                }
            }
        }

        return cache.getPositive(origin, size);
    }

    private void createNeighborhoods() {

        ArrayList<Integer> centerXs = getCentersProjectionCoors(wWidth, dRadius);
        ArrayList<Integer> centerYs = getCentersProjectionCoors(wHeight, dRadius);

        neighborhoods = new Neighborhood[centerXs.size() * centerYs.size()];
        Vector2D xOriSize, yOriSize;
        int ni = 0;
        for (int cx = 0; cx < centerXs.size(); cx++) {
            xOriSize = getOriginAndSize(cx, centerXs, wWidth, dRadius,
                    vector2DCache);
            for (int cy = 0; cy < centerYs.size(); cy++) {

                yOriSize = getOriginAndSize(cy, centerYs, wHeight, dRadius,
                        vector2DCache);
                neighborhoods[ni] = new Neighborhood(vector2DCache.getPositive(xOriSize.x, yOriSize.x),
                        xOriSize.y, yOriSize.y);
                ni++;
            }
        }
    }

    private static ArrayList<Integer> getCentersProjectionCoors(int dimLength, int dRadius) {
        int midIndex = (dimLength - 1) / 2;
        ArrayList<Integer> neighs = new ArrayList<>();
        int offset = dimLength % 2 == 0 ? 1 : 0;
        for (int dr = 0; dr < midIndex; dr += dRadius) {
            if (dr == 0) {
                neighs.add(midIndex);
            } else {
                neighs.add(midIndex - dr);
                neighs.add(midIndex + dr + offset);
            }
        }
        return neighs;
    }


    private void checkParameters(World world, int r) {
        if (r < 2) {
            throw new Exceptions.IllegalValueException("r must be bigger than 1");
        }
    }

    public Neighborhood[] getNeighborhoods() {
        return neighborhoods;
    }

    public static void main(String[] args) {
        int dimLength = 10;
        int dRadius = 3;
        Vector2DCache cache = new Vector2DCache(20, 20);
        ArrayList<Integer> centers = getCentersProjectionCoors(dimLength, dRadius);
        for (int i = 0; i < dimLength; i++) {
            if (centers.contains(i)) {
                System.out.println(i + "**");
            } else {
                System.out.println(i);
            }
        }
        System.out.println(centers);
        int index = 2;
        System.out.println("Cheking: " + index);
        System.out.println(getOriginAndSize(index, centers, dimLength, dRadius, cache));
    }
}
