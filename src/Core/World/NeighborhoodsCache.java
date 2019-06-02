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
        int wMid = (world.w - 1) / 2;
        int hMid = (world.h - 1) / 2;
        ArrayList<Neighborhood> neighborhoods = new ArrayList<>();
//        Vector2DCache cache = world.
        for (int i = 0; i < wMid; i += r) {
            for (int j = 0; j < hMid; j += r) {
                if (i == 0) {
                    if (j == 0) {
                        neighborhoods.add(new Neighborhood(new Vector2D(wMid, hMid), r));
                    } else {
                        neighborhoods.add(new Neighborhood(new Vector2D(wMid, hMid - j), r));
                        neighborhoods.add(new Neighborhood(new Vector2D(wMid, hMid + j), r));
                    }
                } else {
                    if (j == 0) {
                        neighborhoods.add(new Neighborhood(new Vector2D(wMid - i, hMid), r));
                        neighborhoods.add(new Neighborhood(new Vector2D(wMid + i, hMid), r));
                    } else {
                        neighborhoods.add(new Neighborhood(new Vector2D(wMid - i, hMid + j), r));
                        neighborhoods.add(new Neighborhood(new Vector2D(wMid - i, hMid - j), r));
                        neighborhoods.add(new Neighborhood(new Vector2D(wMid + i, hMid + j), r));
                        neighborhoods.add(new Neighborhood(new Vector2D(wMid + i, hMid - j), r));
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
