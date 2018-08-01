package Core.Engine;

import javax.xml.bind.annotation.XmlType;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class is the entire world of the RandomWalkersUniverse. In this universe
 * you only have particles that move randomly through it, the random walkers.
 * <p>
 * Random walkers characteristics:<br>
 * 1. They move randomly (it is obvious but is important to know!!!).<br>
 * 2. They can move in each direction with a different probability, this
 * way we can set a tendency in the direction of the walker.<br>
 * 3. Two randomWalkers will not collide.<br>
 * 4. A random walker will always collide with a barrier.<br>
 * 5. It is consider as the position of a walker the center
 * of the shape.<br>
 * <p>
 * Barriers characteristics<br>
 * 1. They are Static objects.<br>
 * 2. If a barrier is created and overlap an existing walker it will be repositioned
 * to the closer position outside the barrier.<br>
 * 3. Will collide with walkers. <br>
 * <p>
 * The RandomWalkersWorld characteristics:<br>
 * 1. In this world exist as objects only walkers and barriers.<br>
 * 2. It keep all the walkers into a array so, they will always be
 * calls by its index in this array. The same for the Barriers.<br>
 * 3. It set the probability of each walker to chose a direction,
 * by default this probability is equal for the walker position 4 neighbors
 * coordinates and the position itself. This mean that each direction has 1/5 chances to
 * be selected as the next step of the walker.<br>
 * 4. It always check for walkers collision with a barrier and has a feedback
 * methods to it.<br>
 * 5. It can be imagined as a grid with the origin the
 * upper left corner.<br>
 */
public class RandomWalkersWorld {

    public static void main(String... args) {

        PositionValueBoard board = new PositionValueBoard(100, 150);
        board.checkRectangularPerimeter(50, 75, 20, 25);

    }

    //Simulation
    private int step;

    //Walkers
    private final ArrayList<RandomWalker> walkers;
    private final ArrayList<Barrier> barrier;

    //PositionIndexBoard
    private final PositionValueBoard walkersIndexBoard;
    private final PositionValueBoard barriersIndexBoard;

    //tendencyBoard
    private final TendencyBoard tendencyBoard;

    private final int w, h;

    public RandomWalkersWorld(int w, int h) {

        //Size
        this.w = w;
        this.h = h;

        //Walkers
        walkers = new ArrayList<>();
        barrier = new ArrayList<>();

        //Boards
        walkersIndexBoard = new PositionValueBoard(w, h);
        barriersIndexBoard = new PositionValueBoard(w, h);
        tendencyBoard = new TendencyBoard(w, h);

        //Simulation
        step = Constants.DEFAULT_STEP;
    }

    /**
     * @param x
     * @param y
     * @param size
     * @return true if the operation was successful, false otherwise
     */
    public boolean addSquareWalker(int x, int y, int size) {

        //Checking boundaries
        if (x < size || y < size || x > w - size || y > h - size) return false;

        //Check barriers overlapping
        if (barriersIndexBoard.checkSquareArea(x, y, size)) return false;

        //Adding walker
        walkers.add(new RandomWalker(x, y, size, Constants.SQUARE_SHAPE));
        walkersIndexBoard.setValue(walkers.size() - 1, x, y);
        return true;
    }

    public Point getWalkerPosition(int walkerIndex) {
        return new Point(walkers.get(walkerIndex).getPositionX(), walkers.get(walkerIndex).getPositionY());
    }

    public void run() {

        //Moving walkers
        Point newPosition;
        RandomWalker walker;
        for (int i = 0; i < walkers.size(); i++) {

            //Walker
            walker = walkers.get(i);

            //New Position
            newPosition = tendencyBoard.tryDirection(walker.positionX, walker.positionY, step);

            //Checking New Position
            if (newPosition.x < walker.getSize() || newPosition.x >= w - walker.getSize() ||
                    newPosition.y < walker.getSize() && newPosition.y >= h - walker.getSize()) {
                continue;
            } else {
                walker.setPositionX(newPosition.x);
                walker.setPositionY(newPosition.y);
            }
        }
    }

    public void setTendency(int xPosition, int yPosition, int xTendency, int yTendency) {
        tendencyBoard.setTendency(xPosition, yPosition, xTendency, yTendency);
    }

    public void setGlobalTendency(int xGlobalTendency, int yGlobalTendency) {
        tendencyBoard.setGlobalTendency(xGlobalTendency, yGlobalTendency);
    }

    /**
     * This class represent the RandomWalkers that will exist
     * in a medium.
     */
    public static class RandomWalker {

        private int positionX;
        private int positionY;
        private int size;
        private final int shape;

        public RandomWalker(int positionX, int positionY, int size, int shape) {

            this.positionX = positionX;
            this.positionY = positionY;
            this.size = size;
            this.shape = shape;
        }

        public int getPositionX() {
            return positionX;
        }

        public void setPositionX(int positionX) {
            this.positionX = positionX;
        }

        public int getPositionY() {
            return positionY;
        }

        public void setPositionY(int positionY) {
            this.positionY = positionY;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getShape() {
            return shape;
        }

    }

    /**
     * thsi class is used to relate a value with a position.
     */
    public static class PositionValueBoard {

        //Fields
        protected final int[][] board;
        private final int w;
        private final int h;
        private int emptyValue;

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
         * @param x
         * @param y
         * @return true if the position is EMPTY
         */
        public boolean isPositionEmpty(int x, int y) {
            return board[x][y] == emptyValue;
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

            //Deb
            System.out.println("PositionValueBoard getImage called!!!");

            BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            for (int d0 = 0; d0 < board.length; d0++) {
                for (int d1 = 0; d1 < board[0].length; d1++) {
                    if (!isPositionEmpty(d0, d1))
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
                            if (!isPositionEmpty(d0, d1))
                                image.setRGB(d0, d1, Color.BLACK.getRGB());
                            else
                                image.setRGB(d0, d1, Color.WHITE.getRGB());
                        }
                    }
                }
                return image;
            } else return getImage();
        }

        public int getEmptyValue() {
            return emptyValue;
        }

        public void setEmptyValue(int emptyValue) {
            this.emptyValue = emptyValue;
        }

        public boolean checkCircularArea(int centerX, int centerY, int radius) {

            //|x| = radius
            if (!isPositionEmpty(centerX - radius, centerY)) return false;
            if (!isPositionEmpty(centerX + radius, centerY)) return false;

//            //TODO Deb
//            board.setValue(5, centerX - radius, centerY);
//            board.setValue(5, centerX + radius, centerY);

            //|y| < radius
            int y;
            for (int x = 1 - radius; x < radius; x++) {

                //Diameter
                if (!isPositionEmpty(centerX + x, centerY)) return false;

//                //TODO Deb
//                //Diameter
//                board.setValue(5, centerX + x, centerY);

                y = (int) Math.round(Math.sqrt(radius * radius - x * x));

                for (int i = 0; i < y; i++) {

                    //Upper half
                    if (!isPositionEmpty(centerX - x, centerY - y + i)) return false;
                    //Downer half
                    if (!isPositionEmpty(centerX - x, centerY + y - i)) return false;
//                    //TODO Deb
//                    //Upper half
//                    board.setValue(5, centerX - x, centerY - y + i);
//                    //Downer half
//                    board.setValue(5, centerX - x, centerY + y - i);
                }

            }

            return true;

        }

        public boolean checkSquareArea(int centerX, int centerY, int size) {

            int boxels = size * 2 + 1;
            int cornerX = centerX - size;
            int cornerY = centerY - size;

            for (int x = 0; x < boxels; x++) {
                for (int y = 0; y < boxels; y++) {

                    if (!isPositionEmpty(cornerX + x, cornerY + y)) return false;

                    //TODO Deb
                    //board.setValue(5, cornerX + x, cornerY + y);
                }
            }

            return true;
        }

        public boolean checkRectangularArea(int centerX, int centerY, int sizeH, int sizeV) {

            int lineV = sizeV * 2 + 1;
            int lineH = sizeH * 2 + 1;
            int cornerX = centerX - sizeH;
            int cornerY = centerY - sizeV;

            for (int x = 0; x < lineH; x++) {
                for (int y = 0; y < lineV; y++) {

                    if (!isPositionEmpty(cornerX + x, cornerY + y)) return false;

                    //TODO Deb
                    //board.setValue(5, cornerX + x, cornerY + y);
                }
            }

            //TODO Deb
            //BufferedImage image = board.getImage();

            return true;
        }

        public boolean checkCircularPerimeter(int centerX, int centerY, int radius) {
            //|x| = radius
            if (!isPositionEmpty(centerX - radius, centerY)) return false;
            if (!isPositionEmpty(centerX + radius, centerY)) return false;

            //TODO Deb
//            board.setValue(5, centerX - radius, centerY);
//            board.setValue(5, centerX + radius, centerY);

            //|x| < radius
            int y, lastY = 0, dy;
            for (int x = radius - 1; x >= 0; x--) {

                y = (int) Math.round(Math.sqrt(radius * radius - x * x));
                dy = Math.abs(lastY - y);

                if (!isPositionEmpty(centerX + x, centerY - y)) return false;
                if (!isPositionEmpty(centerX + x, centerY + y)) return false;
                if (!isPositionEmpty(centerX - x, centerY - y)) return false;
                if (!isPositionEmpty(centerX - x, centerY + y)) return false;

                //TODO Deb
//                board.setValue(5, centerX + x, centerY - y);
//                board.setValue(5, centerX + x, centerY + y);
//                board.setValue(5, centerX - x, centerY - y);
//                board.setValue(5, centerX - x, centerY + y);

                for (int i = 1; i < dy; i++) {

                    if (!isPositionEmpty(centerX + x, centerY - y + i)) return false;
                    if (!isPositionEmpty(centerX + x, centerY + y - i)) return false;
                    if (!isPositionEmpty(centerX - x, centerY - y + i)) return false;
                    if (!isPositionEmpty(centerX - x, centerY + y - i)) return false;

                    //TODO Deb
//                    board.setValue(5, centerX + x, centerY - y + i);
//                    board.setValue(5, centerX + x, centerY + y - i);
//                    board.setValue(5, centerX - x, centerY - y + i);
//                    board.setValue(5, centerX - x, centerY + y - i);

                }
                lastY = y;

            }

            return true;
        }

        public boolean checkSquarePerimeter(int centerX, int centerY, int size) {

            //Margins
            int lm = centerX - size;
            int rm = centerX + size;
            int um = centerY - size;
            int dm = centerY + size;

            //Corners
            if (!isPositionEmpty(lm, um)) return false;
            if (!isPositionEmpty(rm, um)) return false;
            if (!isPositionEmpty(lm, dm)) return false;
            if (!isPositionEmpty(rm, dm)) return false;

            //TODO Deb
            //board.setValue(5, lm, um);
            //board.setValue(5, rm, um);
            //board.setValue(5, lm, dm);
            //board.setValue(5, rm, dm);

            //Filling center
            for (int i = 1; i < size * 2; i++) {

                if (!isPositionEmpty(lm + i, um)) return false;
                if (!isPositionEmpty(lm, um + i)) return false;
                if (!isPositionEmpty(rm, um + i)) return false;
                if (!isPositionEmpty(lm + i, dm)) return false;

                //TODO Deb
                //board.setValue(5, lm + i, um);
                //board.setValue(5, lm, um + i);
                //board.setValue(5, rm, um + i);
                //board.setValue(5, lm + i, dm);

            }

            return true;

        }

        public boolean checkRectangularPerimeter(int centerX, int centerY, int sizeH, int sizeV) {

            //Borders
            int ub = centerY - sizeV;
            int db = centerY + sizeV;
            int lb = centerX - sizeH;
            int rb = centerX + sizeH;


            //Upper line
            for (int x = 0; x < sizeH * 2 + 1; x++) {
                if (!isPositionEmpty(lb + x, ub)) return false;

                //TODO Deb
                //board.setValue(5, lb + x, ub);
            }

            //Sides lines
            for (int y = 1; y < sizeV * 2; y++) {
                if (!isPositionEmpty(lb, ub + y)) return false;
                if (!isPositionEmpty(rb, ub + y)) return false;

                //TODO Deb
                //board.setValue(5, lb, ub + y);
                //board.setValue(5, rb, ub + y);
            }

            //Downer line
            for (int x = 0; x < sizeH * 2 + 1; x++) {
                if (!isPositionEmpty(lb + x, db)) return false;

                //TODO Deb
                //board.setValue(5, lb + x, db);
            }

            //TODO Deb
            //BufferedImage image = board.getImage();

            return true;

        }

    }

    public static class Barrier {

        public Barrier() {

        }

    }

    public static class Constants {

        //Simulation
        private static final int DEFAULT_STEP = 1;

        //Shapes
        public static final int CIRCULAR_SHAPE = 186;
        public static final int SQUARE_SHAPE = 468;
        public static final int RECTANGULAR_SHAPE = 399;

        //Directions
        public static final int DIRECTIONS_COUNT = 9;
        public static final int N = 0;
        public static final int NW = 1;
        public static final int W = 2;
        public static final int SW = 3;
        public static final int S = 4;
        public static final int SE = 5;
        public static final int E = 6;
        public static final int NE = 7;
        public static final int None = 8;

        //Values
        public static final float PI = 3.14F;

        //Probabilities
        private static final int[] _10n1_ = new int[]{-1, 0, 1};
        private static final int DEFAULT_CHANCE = 1000;
        private static final int DEFAULT_TOTAL_CHANCE = 8999;

    }

    public static class TendencyBoard {

        private final int w;
        private final int h;

        private final PositionValueBoard xTendencyBoard;
        private final PositionValueBoard yTendencyBoard;

        private final static Random r = new Random();

        public TendencyBoard(int w, int h) {
            this.w = w;
            this.h = h;

            xTendencyBoard = new PositionValueBoard(w, h);
            xTendencyBoard.fillBoard(0);
            yTendencyBoard = new PositionValueBoard(w, h);
            yTendencyBoard.fillBoard(0);
        }

        /**
         * Return a random direction depending of the value stored in the given position
         * of the probability board. This value can be used to move randomly the
         * walkers.
         *
         * @param xPosition
         * @param yPosition
         * @param step
         * @return
         */
        public Point tryDirection(int xPosition, int yPosition, int step) {

            int xTendency = xTendencyBoard.getValueAt(xPosition, yPosition);
            int yTendency = yTendencyBoard.getValueAt(xPosition, yPosition);

            if (xPosition == 0 && yPosition == 0) {
                return new Point(xPosition + Constants._10n1_[r.nextInt(3)],
                        yPosition + Constants._10n1_[r.nextInt(3)]);
            }

            int n = Constants.DEFAULT_CHANCE;
            int e = Constants.DEFAULT_CHANCE;
            int s = Constants.DEFAULT_CHANCE;
            int w = Constants.DEFAULT_CHANCE;
            int total = 4999;

            //Y
            if (yTendency > 0) {
                n += yTendency;
                total += yTendency;
            } else {
                s -= yTendency;
                total -= yTendency;
            }

            //X
            if (xTendency > 0) {
                e += xTendency;
                total += xTendency;
            } else {
                w -= xTendency;
                total -= xTendency;
            }

            int randomNumber = r.nextInt(total);
            if ((randomNumber -= n) < 0) {
                return new Point(xPosition, yPosition - step);
            } else if ((randomNumber -= e) < 0) {
                return new Point(xPosition + step, yPosition);
            } else if ((randomNumber -= s) < 0) {
                return new Point(xPosition, yPosition + step);
            } else if ((randomNumber -= w) < 0) {
                return new Point(xPosition - step, yPosition);
            } else return new Point(xPosition, yPosition);

        }

        public void setTendency(int xPosition, int yPosition, int xTendency, int yTendency) {
            xTendencyBoard.setValue(xTendency, xPosition, yPosition);
            yTendencyBoard.setValue(yTendency, xPosition, yPosition);
        }

        public void setGlobalTendency(int xGlobalTendency, int yGlobalTendency) {
            xTendencyBoard.fillBoard(xGlobalTendency);
            yTendencyBoard.fillBoard(yGlobalTendency);
        }
    }

    public static class Point {

        public final int x;
        public final int y;

        public Point(int x, int y) {

            this.x = x;
            this.y = y;
        }

    }


}