package Core.Boards;

public class FloatBoard extends Board {

    private final float[][] floats;

    public FloatBoard(int w, int h) {
        super(w, h);
        floats = new float[w][h];
    }
}
