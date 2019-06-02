package Core.Basics.NeighborhoodsBoard;

import Core.Basics.Vector2D.Vector2D;
import Core.Boards.Board;

import java.util.ArrayList;

import static Core.World.Statics.Caches.vector2Dcache;

public class NeighborhoodsBoard extends Board {

    private final int r;
    private Neighborhood[] neighborhoods;


    public NeighborhoodsBoard(int w, int h, int r) {
        super(w, h);
        this.r = r;
        createNeighborhoods();
    }

    private void createNeighborhoods() {
        int wmid = (w - 1) / 2;
        int hmid = (h - 1) / 2;
        ArrayList<Neighborhood> neighborhoods = new ArrayList<>();
        for (int i = 0; i < wmid; i += r) {
            for (int j = 0; j < hmid; j += r) {
                if (i == 0) {
                    if (j == 0) {
                        neighborhoods.add(new Neighborhood(new Vector2D(wmid, hmid), r));
                    } else {
                        neighborhoods.add(new Neighborhood(new Vector2D(wmid, hmid - j), r));
                        neighborhoods.add(new Neighborhood(new Vector2D(wmid, hmid + j), r));
                    }
                } else {
                    if (j == 0) {
                        neighborhoods.add(new Neighborhood(new Vector2D(wmid - i, hmid), r));
                        neighborhoods.add(new Neighborhood(new Vector2D(wmid + i, hmid), r));
                    } else {
                        neighborhoods.add(new Neighborhood(new Vector2D(wmid - i, hmid + j), r));
                        neighborhoods.add(new Neighborhood(new Vector2D(wmid - i, hmid - j), r));
                        neighborhoods.add(new Neighborhood(new Vector2D(wmid + i, hmid + j), r));
                        neighborhoods.add(new Neighborhood(new Vector2D(wmid + i, hmid - j), r));
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
