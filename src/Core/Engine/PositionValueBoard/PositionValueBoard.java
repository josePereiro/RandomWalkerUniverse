package Core.Engine.PositionValueBoard;

import Core.Engine.Maths.Tools;
import Core.Engine.OnIterationActionHandler.Iterations;
import Core.Engine.OnIterationActionHandler.OnIterationActionHandler;
import Core.Engine.World.RandomWalkersWorld;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * This general class is used to relate a value with a position.
 */
public class PositionValueBoard {

    public static void main(String... args) {

    }

    //Fields
    final int[][] board;
    private final int w;
    private final int h;
    private int emptyValue;

    //IterationHandlers
    private OnIterationActionHandler<Integer, Void, Void> setterIterationHandler =
            new OnIterationActionHandler<Integer, Void, Void>() {
                @Override
                public void action(int x, int y) {
                    PositionValueBoard.this.setValue(getExtraOne(), x, y);
                }

            };

    private OnIterationActionHandler<Boolean, Void, Void> checkerIterationHandler =
            new OnIterationActionHandler<Boolean, Void, Void>() {
                @Override
                public void action(int x, int y) {
                    if (PositionValueBoard.this.isOccupied(x, y)) {
                        setExtraOne(true);
                        iterate = false;
                    }
                }
            };

    //CONSTANTS
    /**
     * The value that is consider as null or empty, it is important
     * to avoid the uses of this value with others proposes.
     */
    public static final int EMPTY = Integer.MIN_VALUE;

    /**
     * @param w the width of the board
     * @param h the height of the board
     */
    public PositionValueBoard(int w, int h) {
        this.w = w;
        this.h = h;
        board = new int[w][h];
        emptyValue = EMPTY;
        fillBoard(emptyValue);
    }

    /**
     * Fill all the board with a given value
     *
     * @param value
     */
    public void fillBoard(int value) {
        for (int d0 = 0; d0 < board.length; d0++) {
            for (int d1 = 0; d1 < board[0].length; d1++) {
                board[d0][d1] = value;
            }
        }
    }

    /**
     * Get the value stored in a given position.
     *
     * @param x xPosition
     * @param y yPosition
     * @return
     */
    public int getValueAt(int x, int y) {
        return board[x][y];
    }

    /**
     * Set the value of a given position in the Board checking that this is
     * within it.
     *
     * @param value
     * @param x
     * @param y
     */
    public void setValueSafely(int value, int x, int y) {
        if (isWithinBoard(x, y))
            board[x][y] = value;
    }

    /**
     * Set the value of a given position in the Board
     *
     * @param value
     * @param x
     * @param y
     */
    public void setValue(int value, int x, int y) {
        board[x][y] = value;
    }

    /**
     * Return true if the Position have a value different than Empty
     *
     * @param x
     * @param y
     * @return true if the position is EMPTY
     */
    public boolean isOccupied(int x, int y) {
        return board[x][y] != emptyValue;
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

    /**
     * It will return a image representation of the data stored
     * in the board, white pixels represent EMPTY values and black
     * pixels any other value.
     * This method has a debug propose and should not be used
     * as a graphic tool.
     */
    public BufferedImage getImage() {

        //TODO Deb
        if (RandomWalkersWorld.enablePrompt) System.out.println("PositionValueBoard getImage called!!!");

        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        for (int d0 = 0; d0 < board.length; d0++) {
            for (int d1 = 0; d1 < board[0].length; d1++) {
                if (isOccupied(d0, d1))
                    image.setRGB(d0, d1, Color.BLACK.getRGB());
                else
                    image.setRGB(d0, d1, Color.WHITE.getRGB());
            }
        }
        return image;
    }

    /**
     * It will return a image representation of the data stored
     * in the board, if the params are null or a value wasn't specified,
     * white pixels will represent EMPTY values and black pixels any other value.
     * Otherwise the given values will have the give colors respectively.
     * This method has a debug propose and should not be used
     * as a graphic tool.
     *
     * @param values
     * @param colors
     * @return
     */
    public BufferedImage getImage(int[] values, int[] colors) {

        if (values != null && colors != null) {
            BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            int value;
            for (int d0 = 0; d0 < board.length; d0++) {
                for (int d1 = 0; d1 < board[0].length; d1++) {
                    int i = 0;
                    value = board[d0][d1];
                    while (i < values.length) {
                        if (value == values[i]) {
                            image.setRGB(d0, d1, colors[i]);
                            break;
                        }
                        i++;
                    }
                    if (i == values.length) {
                        if (isOccupied(d0, d1))
                            image.setRGB(d0, d1, Color.BLACK.getRGB());
                        else
                            image.setRGB(d0, d1, Color.WHITE.getRGB());
                    }
                }
            }
            return image;
        } else return getImage();
    }

    /**
     * Returns the current EMPTY representation of this Board.
     *
     * @return
     */
    public int getEmptyValue() {
        return emptyValue;
    }

    /**
     * Set the value that wil be consider as EMPTY in this Board.
     *
     * @param emptyValue
     */
    public void setEmptyValue(int emptyValue) {
        this.emptyValue = emptyValue;
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
        checkerIterationHandler.iterate = true;
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
        checkerIterationHandler.iterate = true;
        checkerIterationHandler.setExtraOne(false);
        Iterations.iterateSquareArea(centerX, centerY, size, checkerIterationHandler);
        return checkerIterationHandler.getExtraOne();
    }

    public boolean checkRectangularArea(int centerX, int centerY, int sizeX, int sizeY) {

        checkerIterationHandler.iterate = true;
        checkerIterationHandler.setExtraOne(false);
        Iterations.iterateRectangularArea(centerX, centerY, sizeX, sizeY, checkerIterationHandler);
        return checkerIterationHandler.getExtraOne();
    }

    public boolean checkCircularPerimeter(int centerX, int centerY, int radius) {
        checkerIterationHandler.iterate = true;
        checkerIterationHandler.setExtraOne(false);
        Iterations.iterateCircularPerimeter(centerX, centerY, radius, checkerIterationHandler);
        return checkerIterationHandler.getExtraOne();
    }

    public boolean checkSquarePerimeter(int centerX, int centerY, int size) {
        checkerIterationHandler.iterate = true;
        checkerIterationHandler.setExtraOne(false);
        Iterations.iterateSquarePerimeter(centerX, centerY, size, checkerIterationHandler);
        return checkerIterationHandler.getExtraOne();
    }

    public boolean checkRectangularPerimeter(int centerX, int centerY, int sizeX, int sizeY) {
        checkerIterationHandler.iterate = true;
        checkerIterationHandler.setExtraOne(false);
        Iterations.iterateRectangularPerimeter(centerX, centerY, sizeX, sizeY, checkerIterationHandler);
        return checkerIterationHandler.getExtraOne();
    }

    //endregion Iterative Check Methods ............................................................................

    //region Iterative Set Methods _________________________________________________________________________________

    public void setCircularArea(int centerX, int centerY, int radius, int value) {
        setterIterationHandler.setExtraOne(value);
        Iterations.iterateCircularArea(centerX, centerY, radius, setterIterationHandler);
    }

    public void setSquareArea(int centerX, int centerY, int size, int value) {
        setterIterationHandler.setExtraOne(value);
        Iterations.iterateSquareArea(centerX, centerY, size, setterIterationHandler);
    }

    public void setRectangularArea(int centerX, int centerY, int sizeX, int sizeY, int value) {
        setterIterationHandler.setExtraOne(value);
        Iterations.iterateRectangularArea(centerX, centerY, sizeX, sizeY, setterIterationHandler);
    }

    public void setCircularPerimeter(int centerX, int centerY, int radius, int value) {
        setterIterationHandler.setExtraOne(value);
        Iterations.iterateCircularPerimeter(centerX, centerY, radius, setterIterationHandler);
    }

    public void setSquarePerimeter(int centerX, int centerY, int size, int value) {
        setterIterationHandler.setExtraOne(value);
        Iterations.iterateSquarePerimeter(centerX, centerY, size, setterIterationHandler);
    }

    public void setRectangularPerimeter(int centerX, int centerY, int sizeX, int sizeY, int value) {

        //Borders
        int ub = centerY - sizeY;
        int db = centerY + sizeY;
        int lb = centerX - sizeX;
        int rb = centerX + sizeX;


        //Upper line
        for (int x = 0; x < sizeX * 2 + 1; x++) {
            setValue(value, lb + x, ub);

        }

        //Sides lines
        for (int y = 1; y < sizeY * 2; y++) {
            setValue(value, lb, ub + y);
            setValue(value, rb, ub + y);

        }

        //Downer line
        for (int x = 0; x < sizeX * 2 + 1; x++) {
            setValue(value, lb + x, db);

        }
    }

    //endregion Iterative Set Methods ..............................................................................

}
