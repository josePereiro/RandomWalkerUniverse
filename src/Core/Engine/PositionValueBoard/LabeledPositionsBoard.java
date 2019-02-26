package Core.Engine.PositionValueBoard;

import Core.Engine.Iterations.Iterations;
import Core.Engine.Iterations.OnIterationActionHandler;
import Core.Engine.Vector.Vector;

import java.util.ArrayList;

/**
 * This general class is used to relate an int label with a position.
 */
public class LabeledPositionsBoard {

    //Fields
    final int[][] board;
    private final int w;
    private final int h;
    private int emptyLabel;

    //IterationHandlers
    private OnIterationActionHandler<Vector[], Integer, Void> setterIterationHandler =
            new OnIterationActionHandler<Vector[], Integer, Void>() {
                @Override
                public void action(int x, int y) {
                    LabeledPositionsBoard.this.setLabel(getExtraTwo(), x, y);
                }

            };

    private OnIterationActionHandler<Boolean, Void, Void> checkerIterationHandler =
            new OnIterationActionHandler<Boolean, Void, Void>() {
                @Override
                public void action(int x, int y) {
                    if (LabeledPositionsBoard.this.isOccupied(x, y)) {
                        setExtraOne(true);
                        break_ = true;
                    }
                }
            };

    //CONSTANTS
    /**
     * The label that is consider as null or empty, it is important
     * to avoid the uses of this label with others proposes.
     */
    public static final int EMPTY = Integer.MIN_VALUE;

    /**
     * @param w the width of the board
     * @param h the height of the board
     */
    public LabeledPositionsBoard(int w, int h) {
        this.w = w;
        this.h = h;
        board = new int[w][h];
        emptyLabel = EMPTY;
        fillBoard(emptyLabel);
    }

    public LabeledPositionsBoard(int w, int h, int emptyLabel) {
        this.w = w;
        this.h = h;
        board = new int[w][h];
        this.emptyLabel = emptyLabel;
        fillBoard(emptyLabel);
    }

    /**
     * Fill all the board with a given label
     *
     * @param label
     */
    public void fillBoard(int label) {
        for (int d0 = 0; d0 < board.length; d0++) {
            for (int d1 = 0; d1 < board[0].length; d1++) {
                board[d0][d1] = label;
            }
        }
    }

    /**
     * Get the label stored in a given position.
     *
     * @param x xPosition
     * @param y yPosition
     * @return
     */
    public int getLabelAt(int x, int y) {
        return board[x][y];
    }

    public int getLabelAt(Vector v) {
        return board[v.x][v.y];
    }

    /**
     * Set the label of a given position in the Board checking that this is
     * within it.
     *
     * @param label
     * @param x
     * @param y
     */
    public void setLabelSafely(int label, int x, int y) {
        if (isWithinBoard(x, y))
            board[x][y] = label;
    }

    /**
     * Set the label of a given position in the Board
     *
     * @param label
     * @param x
     * @param y
     */
    public void setLabel(int label, int x, int y) {
        board[x][y] = label;
    }

    public void setLabel(int label, Vector v) {
        board[v.x][v.y] = label;
    }

    public void setLabel(int label, Vector... vs) {
        for (Vector v : vs) {
            board[v.x][v.y] = label;
        }
    }

    public void setLabel(int label, ArrayList<Vector> vs) {
        for (Vector v : vs) {
            board[v.x][v.y] = label;
        }
    }

    /**
     * Return true if the Position have a label different than Empty
     *
     * @param x
     * @param y
     * @return true if the position is EMPTY
     */
    public boolean isOccupied(int x, int y) {
        return board[x][y] != emptyLabel;
    }

    public boolean isOccupied(Vector v) {
        return board[v.x][v.y] != emptyLabel;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    /**
     * @param x
     * @param y
     * @return tru if the given coordinate fit in this Board
     */
    public boolean isWithinBoard(int x, int y) {
        return x >= 0 && x < w && y >= 0 && y < h;
    }

    public boolean isWithinBoard(Vector v) {
        return v.x >= 0 && v.x < w && v.y >= 0 && v.y < h;
    }

    /**
     * Returns the current EMPTY representation of this Board.
     *
     * @return
     */
    public int getEmptyLabel() {
        return emptyLabel;
    }

    /**
     * Set the label that wil be consider as EMPTY in this Board.
     *
     * @param emptyValue
     */
    public void setEmptyLabel(int emptyValue) {
        this.emptyLabel = emptyValue;
    }

    public void reset() {
        fillBoard(emptyLabel);
    }

    public Vector getCenter() {
        return new Vector(w / 2, h / 2);
    }

    //region Iterative Check Methods _______________________________________________________________________________

    /**
     * Return True
     *
     * @param centerX The center x of the Area
     * @param centerY The center y of the Area
     * @param radius  The radius of the Area. It is important to know that
     *                the diameter of the area will be radius * 2 + 1.It is because
     *                the world can't support pair diameters particles...
     * @return The result
     */
    public boolean checkCircularArea(int centerX, int centerY, int radius) {
        checkerIterationHandler.break_ = false;
        checkerIterationHandler.setExtraOne(false);
        Iterations.iterateCircularArea(centerX, centerY, radius, checkerIterationHandler);
        return checkerIterationHandler.getExtraOne();
    }

    /**
     * @param centerX The center x of the Area
     * @param centerY The center y of the Area
     * @param size    The size of the Area. This size refers to the distans since the
     *                center of the Area and the borders. It is important to know that
     *                the total size of the area will be size * 2 + 1. it is because
     *                the world can't support pair total size particles...
     * @return The result
     */
    public boolean checkSquareArea(int centerX, int centerY, int size) {
        checkerIterationHandler.break_ = false;
        checkerIterationHandler.setExtraOne(false);
        Iterations.iterateSquareArea(centerX, centerY, size, checkerIterationHandler);
        return checkerIterationHandler.getExtraOne();
    }

    public boolean checkRectangularArea(int centerX, int centerY, int sizeX, int sizeY) {

        checkerIterationHandler.break_ = false;
        checkerIterationHandler.setExtraOne(false);
        Iterations.iterateRectangularArea(centerX, centerY, sizeX, sizeY, checkerIterationHandler);
        return checkerIterationHandler.getExtraOne();
    }

    public boolean checkCircularPerimeter(int centerX, int centerY, int radius) {
        checkerIterationHandler.break_ = false;
        checkerIterationHandler.setExtraOne(false);
        Iterations.iterateCircularPerimeter(centerX, centerY, radius, checkerIterationHandler);
        return checkerIterationHandler.getExtraOne();
    }

    public boolean checkSquarePerimeter(int centerX, int centerY, int size) {
        checkerIterationHandler.break_ = false;
        checkerIterationHandler.setExtraOne(false);
        Iterations.iterateSquarePerimeter(centerX, centerY, size, checkerIterationHandler);
        return checkerIterationHandler.getExtraOne();
    }

    public boolean checkRectangularPerimeter(int centerX, int centerY, int sizeX, int sizeY) {
        checkerIterationHandler.break_ = false;
        checkerIterationHandler.setExtraOne(false);
        Iterations.iterateRectangularPerimeter(centerX, centerY, sizeX, sizeY, checkerIterationHandler);
        return checkerIterationHandler.getExtraOne();
    }

    //endregion Iterative Check Methods ............................................................................

    //region Iterative Set Methods _________________________________________________________________________________

    public void setCircularArea(int centerX, int centerY, int radius, int label) {
        setterIterationHandler.setExtraTwo(label);
        Iterations.iterateCircularArea(centerX, centerY, radius, setterIterationHandler);
    }

    public void setSquareArea(int centerX, int centerY, int size, int label) {
        setterIterationHandler.setExtraTwo(label);
        Iterations.iterateSquareArea(centerX, centerY, size, setterIterationHandler);
    }

    public void setRectangularArea(int centerX, int centerY, int sizeX, int sizeY, int label) {
        setterIterationHandler.setExtraTwo(label);
        Iterations.iterateRectangularArea(centerX, centerY, sizeX, sizeY, setterIterationHandler);
    }

    public void setCircularPerimeter(int centerX, int centerY, int radius, int label) {
        setterIterationHandler.setExtraTwo(label);
        Iterations.iterateCircularPerimeter(centerX, centerY, radius, setterIterationHandler);
    }

    public void setSquarePerimeter(int centerX, int centerY, int size, int label) {
        setterIterationHandler.setExtraTwo(label);
        Iterations.iterateSquarePerimeter(centerX, centerY, size, setterIterationHandler);
    }

    public void setRectangularPerimeter(int centerX, int centerY, int sizeX, int sizeY, int label) {
        setterIterationHandler.setExtraTwo(label);
        Iterations.iterateRectangularArea(centerX, centerY, sizeX, sizeY, setterIterationHandler);
    }

    public void setPolygonalPerimeter(Vector[] vertexes, int label) {
        setterIterationHandler.setExtraTwo(label);
        Iterations.iteratePolygonPerimeter(vertexes, setterIterationHandler);
    }

    //endregion Iterative Set Methods ..............................................................................

}
