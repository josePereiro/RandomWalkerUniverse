package Core.Boards;

/**
 * A DistanceBoard returns the magnitude vector between any point within it.
 * This implementation compute the vector the first time that is required and cache it
 * for future uses.
 */
public class DistanceBoard extends Board{

    public DistanceBoard(int w, int h) {
        super(w, h);
    }
}
