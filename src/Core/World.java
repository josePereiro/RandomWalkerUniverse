package Core;

import Core.Basics.Vector2D.Vector2D;
import Core.Basics.Vector2D.Vector2DCache;
import Core.Boards.Board;

public class World extends Board {

    public static Vector2DCache vector2Dcache = null;

    public World(int w, int h) {
        super(w, h);
        vector2Dcache = new Vector2DCache(w, h);
    }


}
