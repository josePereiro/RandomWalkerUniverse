package Core.World;

import java.util.ArrayList;

public class NeighborhoodsCache {

    private Neighborhood[] neighborhoods;

    NeighborhoodsCache(int wWidth, int wHeight, int dRadius, int offset, int bufferCapacity,
                       SpacePointsCache spacePointsCache) {
        createNeighborhoods(wWidth, wHeight, dRadius, offset, bufferCapacity,
                spacePointsCache);
    }

    private static int[] getOriginAndSize(int index, ArrayList<Integer> centers, int dimLength,
                                          int dRadius, int offset) {
        int origin, size;
        if (index == 0) {
            if (centers.size() > 1) {
                origin = centers.get(0) - dRadius + offset;
                size = centers.get(2) - centers.get(1) + 1 - 2 * offset;
            } else {
                origin = 0;
                size = dimLength;
            }
        } else {
            origin = centers.get(index) - dRadius;
            if (origin <= 0) {
                origin = 0;
                size = dRadius + centers.get(index) + 1 - offset;
            } else {
                origin += offset;
                size = 2 * dRadius + 1 - 2 * offset;
                if (centers.get(index) + dRadius >= dimLength - 1) {
                    size = dimLength - origin;
                }
            }
        }

        return new int[]{origin, size};
    }

    private void createNeighborhoods(int wWidth, int wHeight, int dRadius, int offset, int bufferCapacity,
                                     SpacePointsCache spacePointsCache) {

        ArrayList<Integer> centerXs = getCentersProjectionCoors(wWidth, dRadius);
        ArrayList<Integer> centerYs = getCentersProjectionCoors(wHeight, dRadius);

        neighborhoods = new Neighborhood[centerXs.size() * centerYs.size()];
        int[] xOriSize, yOriSize;
        int ni = 0;
        for (int cx = 0; cx < centerXs.size(); cx++) {
            xOriSize = getOriginAndSize(cx, centerXs, wWidth, dRadius, offset);
            for (int cy = 0; cy < centerYs.size(); cy++) {

                yOriSize = getOriginAndSize(cy, centerYs, wHeight, dRadius, offset);
                neighborhoods[ni] = new Neighborhood(spacePointsCache.getPositive(xOriSize[0], yOriSize[0]),
                        xOriSize[1], yOriSize[1], bufferCapacity);
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

    public Neighborhood[] getNeighborhoods() {
        return neighborhoods;
    }
}
