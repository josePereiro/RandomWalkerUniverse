package Core.World;

import java.util.ArrayList;

public class NeighborhoodsCache {

    private final World world;
    private final int r;
    private Neighborhood[] neighborhoods;


    public NeighborhoodsCache(World world, int r) {
        this.world = world;
        this.r = r;
        createNeighborhoods();
    }

    private void createNeighborhoods() {
        int wMid = (world.width - 1) / 2;
        int hMid = (world.height - 1) / 2;
        ArrayList<Neighborhood> neighborhoods = new ArrayList<>();
        Vector2DCache cache = world.getVector2DCache();
        for (int i = 0; i < wMid; i += r) {
            for (int j = 0; j < hMid; j += r) {
                if (i == 0) {
                    if (j == 0) {
                        neighborhoods.add(new Neighborhood(cache.getPositive(wMid, hMid), r));
                    } else {
                        neighborhoods.add(new Neighborhood(cache.getPositive(wMid, hMid - j), r));
                        neighborhoods.add(new Neighborhood(cache.getPositive(wMid, hMid + j), r));
                    }
                } else {
                    if (j == 0) {
                        neighborhoods.add(new Neighborhood(cache.getPositive(wMid - i, hMid), r));
                        neighborhoods.add(new Neighborhood(cache.getPositive(wMid + i, hMid), r));
                    } else {
                        neighborhoods.add(new Neighborhood(cache.getPositive(wMid - i, hMid + j), r));
                        neighborhoods.add(new Neighborhood(cache.getPositive(wMid - i, hMid - j), r));
                        neighborhoods.add(new Neighborhood(cache.getPositive(wMid + i, hMid + j), r));
                        neighborhoods.add(new Neighborhood(cache.getPositive(wMid + i, hMid - j), r));
                    }
                }
            }
        }
        this.neighborhoods = neighborhoods.toArray(new Neighborhood[neighborhoods.size()]);
    }

    public Neighborhood[] getNeighborhoods() {
        return neighborhoods;
    }
}
