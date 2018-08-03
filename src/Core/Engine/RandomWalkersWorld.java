package Core.Engine;

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

    }

    //Simulation
    private long counter;

    //Walkers
    private final ArrayList<RandomWalker> walkers;
    private final ArrayList<Barrier> barrier;

    //PositionIndexBoard
    private final PositionValueBoard walkersIndexBoard;

    //tendencyBoard
    private final TendencyBoard tendencyBoard;
    private final ArrayList<Tendency> localTendencies;

    //collisions Boards
    private final CollisionBoard collisionBoards;

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
        tendencyBoard = new TendencyBoard(w, h);

        //Tendencies
        localTendencies = new ArrayList<>();

        //Collision Boards
        collisionBoards = new CollisionBoard(w, h, barrier);
    }

    /**
     * @param x
     * @param y
     * @param size
     * @return true if the operation was successful, false otherwise.
     */
    public boolean addSquareWalker(int x, int y, int size) {

        RandomWalker newWalker = new RandomWalker(x, y, size, Constants.SQUARE_SHAPE);

        //Update collision board
        collisionBoards.attachThisWalker(newWalker);

        //Check collisions
        if (collisionBoards.checkCollision(x, y, newWalker)) return false;

        //Adding walker
        walkers.add(newWalker);
        walkersIndexBoard.setValue(walkers.size() - 1, x, y);
        return true;
    }

    public Vector getWalkerPosition(int walkerIndex) {
        return new Vector(walkers.get(walkerIndex).getPositionX(), walkers.get(walkerIndex).getPositionY());
    }

    public void run(int iterations) {

        do {
            run();
            iterations--;
        } while (iterations >= 0);

    }

    public void run() {

        //Moving walkers
        Vector newPosition;
        RandomWalker walker;
        for (int i = 0; i < walkers.size(); i++) {

            //Walker
            walker = walkers.get(i);

            //New Position
            newPosition = tendencyBoard.tryDirection(walker.positionX, walker.positionY);

            //Check collisions
            if (collisionBoards.checkCollision(newPosition.x, newPosition.y, walker)) {
                continue;
            } else {
                walker.setPositionX(newPosition.x);
                walker.setPositionY(newPosition.y);
            }
        }

        counter++;
    }

    public void setTendency(int xPosition, int yPosition, int xTendency, int yTendency) {
        tendencyBoard.setTendency(xPosition, yPosition, xTendency, yTendency);
    }

    public void setTendency(int xPosition, int yPosition, int xTendency, int yTendency, float factor) {
        tendencyBoard.setTendency(xPosition, yPosition, (int) (xTendency * factor), (int) (yTendency * factor));
    }

    public Vector getTendency(int xPosition, int yPosition) {
        return tendencyBoard.getTendency(xPosition, yPosition);
    }

    public void setGlobalTendency(int xGlobalTendency, int yGlobalTendency) {
        tendencyBoard.setGlobalTendency(xGlobalTendency, yGlobalTendency);
    }

    /**
     * It will set a tendency after translated. Translate means move the vector from the
     * upper left origin screen coordinate system to a standard Cartesian coordinate system with the center
     * in xPosition and yPosition. In this new system the vector will start at the origin, due to, it can be
     * represented as a single pair of coordinates and set as the Tendency of this point.
     * This method guaranty that the Tendency behave just as you set it in the original system. If it represent
     * a vector pointing to the North direction, it will point to the North direction in the new system and will
     * still have the same magnitude.
     *
     * @param xPosition the x position you want to set a Tendency, at the same time it is the
     *                  x coordinate of the first point of the virgin vector.
     * @param yPosition the y position you want to set a Tendency, at the same time it is the
     *                  y coordinate of the first point of the virgin vector.
     * @param xTendency The value of the x Tendency, at the same time it is the
     *                  x coordinate of the second point of the virgin vector.
     * @param yTendency The value of the y Tendency, at the same time it is the
     *                  y coordinate of the second point of the virgin vector.
     */
    public void translateAndSetTendency(int xPosition, int yPosition, int xTendency, int yTendency) {

        int xt = xTendency - xPosition;
        int yt = yTendency - yPosition;

        tendencyBoard.setTendency(xPosition, yPosition, xt, -yt);

    }

    /**
     * It will set a tendency after translated. Translate means move the vector from the
     * upper left origin screen coordinate system to a standard Cartesian coordinate system with the center
     * in xPosition and yPosition. In this new system the vector will start at the origin, due to, it can be
     * represented as a single pair of coordinates and set as the Tendency of this point.
     * This method guaranty that the Tendency behave just as you set it in the original system. If it represent
     * a vector pointing to the North direction, it will point to the North direction in the new system and will
     * still have the same magnitude.
     *
     * @param xPosition the x position you want to set a Tendency, at the same time it is the
     *                  x coordinate of the first point of the virgin vector.
     * @param yPosition the y position you want to set a Tendency, at the same time it is the
     *                  y coordinate of the first point of the virgin vector.
     * @param xTendency The value of the x Tendency, at the same time it is the
     *                  x coordinate of the second point of the virgin vector.
     * @param yTendency The value of the y Tendency, at the same time it is the
     *                  y coordinate of the second point of the virgin vector.
     */
    public void translateAndSetTendency(int xPosition, int yPosition, int xTendency, int yTendency, float factor) {

        int xt = xTendency - xPosition;
        int yt = yTendency - yPosition;

        tendencyBoard.setTendency(xPosition, yPosition, (int) (xt * factor), (int) (-yt * factor));

    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public long getCounter() {
        return counter;
    }

    //CLASSES ----------------------------------------------------------------------------------------------------------

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
        final int[][] board;
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
         * Return true if the Position contain the empty value
         *
         * @param x
         * @param y
         * @return true if the position is EMPTY
         */
        public boolean checkForEmptyPosition(int x, int y) {
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
                    if (!checkForEmptyPosition(d0, d1))
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
                            if (!checkForEmptyPosition(d0, d1))
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
            if (!checkForEmptyPosition(centerX - radius, centerY)) return false;
            if (!checkForEmptyPosition(centerX + radius, centerY)) return false;

            int y;
            for (int x = 1 - radius; x < radius; x++) {

                //Diameter
                if (!checkForEmptyPosition(centerX + x, centerY)) return false;


                y = (int) Math.round(Math.sqrt(radius * radius - x * x));

                for (int i = 0; i < y; i++) {

                    //Upper half
                    if (!checkForEmptyPosition(centerX - x, centerY - y + i)) return false;
                    //Downer half
                    if (!checkForEmptyPosition(centerX - x, centerY + y - i)) return false;
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

                    if (!checkForEmptyPosition(cornerX + x, cornerY + y)) return false;

                }
            }

            return true;
        }

        public boolean checkRectangularArea(int centerX, int centerY, int sizeX, int sizeY) {

            int lineV = sizeY * 2 + 1;
            int lineH = sizeX * 2 + 1;
            int cornerX = centerX - sizeX;
            int cornerY = centerY - sizeY;

            for (int x = 0; x < lineH; x++) {
                for (int y = 0; y < lineV; y++) {

                    if (!checkForEmptyPosition(cornerX + x, cornerY + y)) return false;

                }
            }

            return true;
        }

        public boolean checkCircularPerimeter(int centerX, int centerY, int radius) {
            //|x| = radius
            if (!checkForEmptyPosition(centerX - radius, centerY)) return false;
            if (!checkForEmptyPosition(centerX + radius, centerY)) return false;

            int y, lastY = 0, dy;
            for (int x = radius - 1; x >= 0; x--) {

                y = (int) Math.round(Math.sqrt(radius * radius - x * x));
                dy = Math.abs(lastY - y);

                if (!checkForEmptyPosition(centerX + x, centerY - y)) return false;
                if (!checkForEmptyPosition(centerX + x, centerY + y)) return false;
                if (!checkForEmptyPosition(centerX - x, centerY - y)) return false;
                if (!checkForEmptyPosition(centerX - x, centerY + y)) return false;

                for (int i = 1; i < dy; i++) {

                    if (!checkForEmptyPosition(centerX + x, centerY - y + i)) return false;
                    if (!checkForEmptyPosition(centerX + x, centerY + y - i)) return false;
                    if (!checkForEmptyPosition(centerX - x, centerY - y + i)) return false;
                    if (!checkForEmptyPosition(centerX - x, centerY + y - i)) return false;

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
            if (!checkForEmptyPosition(lm, um)) return false;
            if (!checkForEmptyPosition(rm, um)) return false;
            if (!checkForEmptyPosition(lm, dm)) return false;
            if (!checkForEmptyPosition(rm, dm)) return false;

            for (int i = 1; i < size * 2; i++) {

                if (!checkForEmptyPosition(lm + i, um)) return false;
                if (!checkForEmptyPosition(lm, um + i)) return false;
                if (!checkForEmptyPosition(rm, um + i)) return false;
                if (!checkForEmptyPosition(lm + i, dm)) return false;

            }

            return true;

        }

        public boolean checkRectangularPerimeter(int centerX, int centerY, int sizeX, int sizeY) {

            //Borders
            int ub = centerY - sizeY;
            int db = centerY + sizeY;
            int lb = centerX - sizeX;
            int rb = centerX + sizeX;


            //Upper line
            for (int x = 0; x < sizeX * 2 + 1; x++) {
                if (!checkForEmptyPosition(lb + x, ub)) return false;

            }

            //Sides lines
            for (int y = 1; y < sizeY * 2; y++) {
                if (!checkForEmptyPosition(lb, ub + y)) return false;
                if (!checkForEmptyPosition(rb, ub + y)) return false;

            }

            //Downer line
            for (int x = 0; x < sizeX * 2 + 1; x++) {
                if (!checkForEmptyPosition(lb + x, db)) return false;

            }

            return true;

        }

        public void setCircularArea(int centerX, int centerY, int radius, int value) {

            //|x| = radius
            setValue(value, centerX - radius, centerY);
            setValue(value, centerX + radius, centerY);

            int y;
            for (int x = 1 - radius; x < radius; x++) {

                //Diameter
                setValue(value, centerX + x, centerY);

                y = (int) Math.round(Math.sqrt(radius * radius - x * x));

                for (int i = 0; i < y; i++) {

                    //Upper half
                    setValue(value, centerX - x, centerY - y + i);
                    //Downer half
                    setValue(value, centerX - x, centerY + y - i);
                }

            }
        }

        public void setSquareArea(int centerX, int centerY, int size, int value) {

            int boxels = size * 2 + 1;
            int cornerX = centerX - size;
            int cornerY = centerY - size;

            for (int x = 0; x < boxels; x++) {
                for (int y = 0; y < boxels; y++) {

                    setValue(value, cornerX + x, cornerY + y);

                }
            }

        }

        public void setRectangularArea(int centerX, int centerY, int sizeX, int sizeY, int value) {

            int lineV = sizeY * 2 + 1;
            int lineH = sizeX * 2 + 1;
            int cornerX = centerX - sizeX;
            int cornerY = centerY - sizeY;

            for (int x = 0; x < lineH; x++) {
                for (int y = 0; y < lineV; y++) {

                    setValue(value, cornerX + x, cornerY + y);

                }
            }

        }

        public void setCircularPerimeter(int centerX, int centerY, int radius, int value) {
            //|x| = radius
            setValue(value, centerX - radius, centerY);
            setValue(value, centerX + radius, centerY);

            int y, lastY = 0, dy;
            for (int x = radius - 1; x >= 0; x--) {

                y = (int) Math.round(Math.sqrt(radius * radius - x * x));
                dy = Math.abs(lastY - y);

                setValue(value, centerX + x, centerY - y);
                setValue(value, centerX + x, centerY + y);
                setValue(value, centerX - x, centerY - y);
                setValue(value, centerX - x, centerY + y);

                for (int i = 1; i < dy; i++) {

                    setValue(value, centerX + x, centerY - y + i);
                    setValue(value, centerX + x, centerY + y - i);
                    setValue(value, centerX - x, centerY - y + i);
                    setValue(value, centerX - x, centerY + y - i);

                }
                lastY = y;

            }
        }

        public void setSquarePerimeter(int centerX, int centerY, int size, int value) {
            //Margins
            int lm = centerX - size;
            int rm = centerX + size;
            int um = centerY - size;
            int dm = centerY + size;

            //Corners
            setValue(value, lm, um);
            setValue(value, rm, um);
            setValue(value, lm, dm);
            setValue(value, rm, dm);

            for (int i = 1; i < size * 2; i++) {

                setValue(value, lm + i, um);
                setValue(value, lm, um + i);
                setValue(value, rm, um + i);
                setValue(value, lm + i, dm);

            }

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

        public boolean checkCircularAreaSafely(int centerX, int centerY, int radius) {

            //|x| = radius
            if (isWithinBoard(centerX - radius, centerY))
                if (!checkForEmptyPosition(centerX - radius, centerY)) return false;
            if (isWithinBoard(centerX + radius, centerY))
                if (!checkForEmptyPosition(centerX + radius, centerY)) return false;

            int y;
            for (int x = 1 - radius; x < radius; x++) {

                //Diameter
                if (isWithinBoard(centerX + x, centerY))
                    if (!checkForEmptyPosition(centerX + x, centerY)) return false;


                y = (int) Math.round(Math.sqrt(radius * radius - x * x));

                for (int i = 0; i < y; i++) {

                    //Upper half
                    if (isWithinBoard(centerX - x, centerY - y + i))
                        if (!checkForEmptyPosition(centerX - x, centerY - y + i)) return false;
                    //Downer half
                    if (isWithinBoard(centerX - x, centerY + y - i))
                        if (!checkForEmptyPosition(centerX - x, centerY + y - i)) return false;
                }

            }

            return true;

        }

        public boolean checkSquareAreaSafely(int centerX, int centerY, int size) {

            int boxels = size * 2 + 1;
            int cornerX = centerX - size;
            int cornerY = centerY - size;

            for (int x = 0; x < boxels; x++) {
                for (int y = 0; y < boxels; y++) {

                    if (isWithinBoard(x, y))
                        if (!checkForEmptyPosition(cornerX + x, cornerY + y)) return false;

                }
            }

            return true;
        }

        public boolean checkRectangularAreaSafely(int centerX, int centerY, int sizeX, int sizeY) {

            int lineV = sizeY * 2 + 1;
            int lineH = sizeX * 2 + 1;
            int cornerX = centerX - sizeX;
            int cornerY = centerY - sizeY;

            for (int x = 0; x < lineH; x++) {
                for (int y = 0; y < lineV; y++) {

                    if (isWithinBoard(x, y))
                        if (!checkForEmptyPosition(cornerX + x, cornerY + y)) return false;

                }
            }

            return true;
        }

        public boolean checkCircularPerimeterSafely(int centerX, int centerY, int radius) {

            //|x| = radius
            if (isWithinBoard(centerX - radius, centerY))
                if (!checkForEmptyPosition(centerX - radius, centerY)) return false;
            if (isWithinBoard(centerX + radius, centerY))
                if (!checkForEmptyPosition(centerX + radius, centerY)) return false;

            int y, lastY = 0, dy;
            for (int x = radius - 1; x >= 0; x--) {

                y = (int) Math.round(Math.sqrt(radius * radius - x * x));
                dy = Math.abs(lastY - y);
                if (isWithinBoard(centerX + x, centerY - y))
                    if (!checkForEmptyPosition(centerX + x, centerY - y)) return false;
                if (isWithinBoard(centerX + x, centerY + y))
                    if (!checkForEmptyPosition(centerX + x, centerY + y)) return false;
                if (isWithinBoard(centerX - x, centerY - y))
                    if (!checkForEmptyPosition(centerX - x, centerY - y)) return false;
                if (isWithinBoard(centerX - x, centerY + y))
                    if (!checkForEmptyPosition(centerX - x, centerY + y)) return false;

                for (int i = 1; i < dy; i++) {

                    if (isWithinBoard(centerX + x, centerY - y + i))
                        if (!checkForEmptyPosition(centerX + x, centerY - y + i)) return false;
                    if (isWithinBoard(centerX + x, centerY + y - i))
                        if (!checkForEmptyPosition(centerX + x, centerY + y - i)) return false;
                    if (isWithinBoard(centerX - x, centerY - y + i))
                        if (!checkForEmptyPosition(centerX - x, centerY - y + i)) return false;
                    if (isWithinBoard(centerX - x, centerY + y - i))
                        if (!checkForEmptyPosition(centerX - x, centerY + y - i)) return false;

                }
                lastY = y;

            }

            return true;
        }

        public boolean checkSquarePerimeterSafely(int centerX, int centerY, int size) {

            //Margins
            int lm = centerX - size;
            int rm = centerX + size;
            int um = centerY - size;
            int dm = centerY + size;

            //Corners
            if (isWithinBoard(lm, um))
                if (!checkForEmptyPosition(lm, um)) return false;
            if (isWithinBoard(rm, um))
                if (!checkForEmptyPosition(rm, um)) return false;
            if (isWithinBoard(lm, dm))
                if (!checkForEmptyPosition(lm, dm)) return false;
            if (isWithinBoard(rm, dm))
                if (!checkForEmptyPosition(rm, dm)) return false;

            for (int i = 1; i < size * 2; i++) {

                if (isWithinBoard(lm + i, um))
                    if (!checkForEmptyPosition(lm + i, um)) return false;
                if (isWithinBoard(lm, um + i))
                    if (!checkForEmptyPosition(lm, um + i)) return false;
                if (isWithinBoard(rm, um + i))
                    if (!checkForEmptyPosition(rm, um + i)) return false;
                if (isWithinBoard(lm + i, dm))
                    if (!checkForEmptyPosition(lm + i, dm)) return false;

            }

            return true;

        }

        public boolean checkRectangularPerimeterSafely(int centerX, int centerY, int sizeX, int sizeY) {

            //Borders
            int ub = centerY - sizeY;
            int db = centerY + sizeY;
            int lb = centerX - sizeX;
            int rb = centerX + sizeX;


            //Upper line
            for (int x = 0; x < sizeX * 2 + 1; x++) {
                if (isWithinBoard(lb + x, ub))
                    if (!checkForEmptyPosition(lb + x, ub)) return false;

            }

            //Sides lines
            for (int y = 1; y < sizeY * 2; y++) {
                if (isWithinBoard(lb, ub + y))
                    if (!checkForEmptyPosition(lb, ub + y)) return false;
                if (isWithinBoard(rb, ub + y))
                    if (!checkForEmptyPosition(rb, ub + y)) return false;

            }

            //Downer line
            for (int x = 0; x < sizeX * 2 + 1; x++) {
                if (isWithinBoard(lb + x, db))
                    if (!checkForEmptyPosition(lb + x, db)) return false;

            }

            return true;

        }

        public void setCircularAreaSafely(int centerX, int centerY, int radius, int value) {

            //|x| = radius
            setValueSafely(value, centerX - radius, centerY);
            setValueSafely(value, centerX + radius, centerY);

            int y;
            for (int x = 1 - radius; x < radius; x++) {

                //Diameter
                setValueSafely(value, centerX + x, centerY);

                y = (int) Math.round(Math.sqrt(radius * radius - x * x));

                for (int i = 0; i < y; i++) {

                    //Upper half
                    setValueSafely(value, centerX - x, centerY - y + i);
                    //Downer half
                    setValueSafely(value, centerX - x, centerY + y - i);
                }

            }
        }

        public void setSquareAreaSafely(int centerX, int centerY, int size, int value) {

            int boxels = size * 2 + 1;
            int cornerX = centerX - size;
            int cornerY = centerY - size;

            for (int x = 0; x < boxels; x++) {
                for (int y = 0; y < boxels; y++) {

                    setValueSafely(value, cornerX + x, cornerY + y);

                }
            }

        }

        public void setRectangularAreaSafely(int centerX, int centerY, int sizeX, int sizeY, int value) {

            int lineV = sizeY * 2 + 1;
            int lineH = sizeX * 2 + 1;
            int cornerX = centerX - sizeX;
            int cornerY = centerY - sizeY;

            for (int x = 0; x < lineH; x++) {
                for (int y = 0; y < lineV; y++) {

                    setValueSafely(value, cornerX + x, cornerY + y);

                }
            }

        }

        public void setCircularPerimeterSafely(int centerX, int centerY, int radius, int value) {
            //|x| = radius
            setValueSafely(value, centerX - radius, centerY);
            setValueSafely(value, centerX + radius, centerY);

            int y, lastY = 0, dy;
            for (int x = radius - 1; x >= 0; x--) {

                y = (int) Math.round(Math.sqrt(radius * radius - x * x));
                dy = Math.abs(lastY - y);

                setValueSafely(value, centerX + x, centerY - y);
                setValueSafely(value, centerX + x, centerY + y);
                setValueSafely(value, centerX - x, centerY - y);
                setValueSafely(value, centerX - x, centerY + y);

                for (int i = 1; i < dy; i++) {

                    setValueSafely(value, centerX + x, centerY - y + i);
                    setValueSafely(value, centerX + x, centerY + y - i);
                    setValueSafely(value, centerX - x, centerY - y + i);
                    setValueSafely(value, centerX - x, centerY + y - i);

                }
                lastY = y;

            }
        }

        public void setSquarePerimeterSafely(int centerX, int centerY, int size, int value) {
            //Margins
            int lm = centerX - size;
            int rm = centerX + size;
            int um = centerY - size;
            int dm = centerY + size;

            //Corners
            setValueSafely(value, lm, um);
            setValueSafely(value, rm, um);
            setValueSafely(value, lm, dm);
            setValueSafely(value, rm, dm);

            for (int i = 1; i < size * 2; i++) {

                setValueSafely(value, lm + i, um);
                setValueSafely(value, lm, um + i);
                setValueSafely(value, rm, um + i);
                setValueSafely(value, lm + i, dm);

            }

        }

        public void setRectangularPerimeterSafely(int centerX, int centerY, int sizeX, int sizeY, int value) {

            //Borders
            int ub = centerY - sizeY;
            int db = centerY + sizeY;
            int lb = centerX - sizeX;
            int rb = centerX + sizeX;


            //Upper line
            for (int x = 0; x < sizeX * 2 + 1; x++) {
                setValueSafely(value, lb + x, ub);

            }

            //Sides lines
            for (int y = 1; y < sizeY * 2; y++) {
                setValueSafely(value, lb, ub + y);
                setValueSafely(value, rb, ub + y);

            }

            //Downer line
            for (int x = 0; x < sizeX * 2 + 1; x++) {
                setValueSafely(value, lb + x, db);

            }
        }

        public ArrayList<RandomWalkersWorld.Vector> getCircularAreaPointsPointsSafely(int centerX, int centerY, int radius) {

            ArrayList<RandomWalkersWorld.Vector> points = new ArrayList<>();

            //|x| = radius
            if (isWithinBoard(centerX - radius, centerY))
                points.add(new RandomWalkersWorld.Vector(centerX - radius, centerY));
            if (isWithinBoard(centerX + radius, centerY))
                points.add(new RandomWalkersWorld.Vector(centerX + radius, centerY));

            int y;
            for (int x = 1 - radius; x < radius; x++) {

                //Diameter
                if (isWithinBoard(centerX + x, centerY))
                    points.add(new RandomWalkersWorld.Vector(centerX + x, centerY));


                y = (int) Math.round(Math.sqrt(radius * radius - x * x));

                for (int i = 0; i < y; i++) {

                    //Upper half
                    if (isWithinBoard(centerX - x, centerY - y + i))
                        points.add(new RandomWalkersWorld.Vector(centerX - x, centerY - y + i));
                    //Downer half
                    if (isWithinBoard(centerX - x, centerY + y - i))
                        points.add(new RandomWalkersWorld.Vector(centerX - x, centerY + y - i));
                }

            }

            return points;

        }

        public ArrayList<RandomWalkersWorld.Vector> getSquareAreaPointsSafely(int centerX, int centerY, int size) {

            ArrayList<RandomWalkersWorld.Vector> points = new ArrayList<>();

            int boxels = size * 2 + 1;
            int cornerX = centerX - size;
            int cornerY = centerY - size;

            for (int x = 0; x < boxels; x++) {
                for (int y = 0; y < boxels; y++) {

                    if (isWithinBoard(x, y))
                        points.add(new RandomWalkersWorld.Vector(cornerX + x, cornerY + y));

                }
            }

            return points;
        }

        public ArrayList<RandomWalkersWorld.Vector> getRectangularAreaPointsSafely(int centerX, int centerY, int sizeX, int sizeY) {

            ArrayList<RandomWalkersWorld.Vector> points = new ArrayList<>();

            int lineV = sizeY * 2 + 1;
            int lineH = sizeX * 2 + 1;
            int cornerX = centerX - sizeX;
            int cornerY = centerY - sizeY;

            for (int x = 0; x < lineH; x++) {
                for (int y = 0; y < lineV; y++) {

                    if (isWithinBoard(x, y))
                        points.add(new RandomWalkersWorld.Vector(cornerX + x, cornerY + y));

                }
            }

            return points;
        }

        public ArrayList<RandomWalkersWorld.Vector> getCircularPerimeterPointsSafely(int centerX, int centerY, int radius) {

            ArrayList<RandomWalkersWorld.Vector> points = new ArrayList<>();

            //|x| = radius
            if (isWithinBoard(centerX - radius, centerY))
                points.add(new RandomWalkersWorld.Vector(centerX - radius, centerY));
            if (isWithinBoard(centerX + radius, centerY))
                points.add(new RandomWalkersWorld.Vector(centerX + radius, centerY));

            int y, lastY = 0, dy;
            for (int x = radius - 1; x >= 0; x--) {

                y = (int) Math.round(Math.sqrt(radius * radius - x * x));
                dy = Math.abs(lastY - y);
                if (isWithinBoard(centerX + x, centerY - y))
                    points.add(new RandomWalkersWorld.Vector(centerX + x, centerY - y));
                if (isWithinBoard(centerX + x, centerY + y))
                    points.add(new RandomWalkersWorld.Vector(centerX + x, centerY + y));
                if (isWithinBoard(centerX - x, centerY - y))
                    points.add(new RandomWalkersWorld.Vector(centerX - x, centerY - y));
                if (isWithinBoard(centerX - x, centerY + y))
                    points.add(new RandomWalkersWorld.Vector(centerX - x, centerY + y));

                for (int i = 1; i < dy; i++) {

                    if (isWithinBoard(centerX + x, centerY - y + i))
                        points.add(new RandomWalkersWorld.Vector(centerX + x, centerY - y + i));
                    if (isWithinBoard(centerX + x, centerY + y - i))
                        points.add(new RandomWalkersWorld.Vector(centerX + x, centerY + y - i));
                    if (isWithinBoard(centerX - x, centerY - y + i))
                        points.add(new RandomWalkersWorld.Vector(centerX - x, centerY - y + i));
                    if (isWithinBoard(centerX - x, centerY + y - i))
                        points.add(new RandomWalkersWorld.Vector(centerX - x, centerY + y - i));

                }
                lastY = y;

            }

            return points;
        }

        public ArrayList<RandomWalkersWorld.Vector> getSquarePerimeterPointsSafely(int centerX, int centerY, int size) {

            ArrayList<RandomWalkersWorld.Vector> points = new ArrayList<>();

            //Margins
            int lm = centerX - size;
            int rm = centerX + size;
            int um = centerY - size;
            int dm = centerY + size;

            //Corners
            if (isWithinBoard(lm, um))
                points.add(new RandomWalkersWorld.Vector(lm, um));
            if (isWithinBoard(rm, um))
                points.add(new RandomWalkersWorld.Vector(rm, um));
            if (isWithinBoard(lm, dm))
                points.add(new RandomWalkersWorld.Vector(lm, dm));
            if (isWithinBoard(rm, dm))
                points.add(new RandomWalkersWorld.Vector(rm, dm));

            for (int i = 1; i < size * 2; i++) {

                if (isWithinBoard(lm + i, um))
                    points.add(new RandomWalkersWorld.Vector(lm + i, um));
                if (isWithinBoard(lm, um + i))
                    points.add(new RandomWalkersWorld.Vector(lm, um + i));
                if (isWithinBoard(rm, um + i))
                    points.add(new RandomWalkersWorld.Vector(rm, um + i));
                if (isWithinBoard(lm + i, dm))
                    points.add(new RandomWalkersWorld.Vector(lm + i, dm));

            }

            return points;

        }

        public ArrayList<RandomWalkersWorld.Vector> getRectangularPerimeterPointsSafely(int centerX, int centerY, int sizeX, int sizeY) {

            ArrayList<RandomWalkersWorld.Vector> points = new ArrayList<>();

            //Borders
            int ub = centerY - sizeY;
            int db = centerY + sizeY;
            int lb = centerX - sizeX;
            int rb = centerX + sizeX;


            //Upper line
            for (int x = 0; x < sizeX * 2 + 1; x++) {
                if (isWithinBoard(lb + x, ub))
                    points.add(new RandomWalkersWorld.Vector(lb + x, ub));

            }

            //Sides lines
            for (int y = 1; y < sizeY * 2; y++) {
                if (isWithinBoard(lb, ub + y))
                    points.add(new RandomWalkersWorld.Vector(lb, ub + y));
                if (isWithinBoard(rb, ub + y))
                    points.add(new RandomWalkersWorld.Vector(rb, ub + y));

            }

            //Downer line
            for (int x = 0; x < sizeX * 2 + 1; x++) {
                if (isWithinBoard(lb + x, db))
                    points.add(new RandomWalkersWorld.Vector(lb + x, db));

            }

            return points;

        }

    }

    public static class Barrier {


        private int positionX;
        private int positionY;
        private final int shape;
        private int sizeX;
        private int sizeY;

        /**
         * Dou to Barrier support rectangular shapes,  you can set more than a
         * size value. If the shape is SQUARE or CIRCULAR the first passed value
         * will be set as the size/radio of the barrier, the rest will be drop.
         * If the shape is RECTANGULAR the two first passed values will be set as
         * sizeX, sizeY respectively. If only one value is passed it will set as
         * boths.
         *
         * @param size
         */
        public Barrier(int positionX, int positionY, int shape, int... size) {

            this.positionX = positionX;
            this.positionY = positionY;
            this.shape = shape;

            if (shape == Constants.SQUARE_SHAPE || shape == Constants.CIRCULAR_SHAPE) {
                sizeX = size[0];
            } else if (shape == Constants.RECTANGULAR_SHAPE) {
                if (size.length > 1) {
                    sizeX = size[0];
                    sizeY = size[1];
                } else {
                    sizeX = size[0];
                    sizeY = size[0];
                }
            }
        }


        public int getSize() {
            return sizeX;
        }

        public int getSizeX() {
            return sizeX;
        }

        public int getSizeY() {
            if (shape == Constants.SQUARE_SHAPE || shape == Constants.CIRCULAR_SHAPE) {
                return sizeX;
            } else if (shape == Constants.RECTANGULAR_SHAPE) {
                return sizeY;
            } else {
                return sizeX;
            }
        }

        public int getPositionX() {
            return positionX;
        }

        public int getPositionY() {
            return positionY;
        }

        public int getShape() {
            return shape;
        }

        public void setPositionX(int positionX) {
            this.positionX = positionX;
        }

        public void setPositionY(int positionY) {
            this.positionY = positionY;
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
        private static final int DEFAULT_TOTAL_CHANCE = 4999;

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
         * @return
         */
        public Vector tryDirection(int xPosition, int yPosition) {

            int xTendency = xTendencyBoard.getValueAt(xPosition, yPosition);
            int yTendency = yTendencyBoard.getValueAt(xPosition, yPosition);

            if (xTendency == 0 && yTendency == 0) {
                return new Vector(xPosition + Constants._10n1_[r.nextInt(3)],
                        yPosition + Constants._10n1_[r.nextInt(3)]);
            }

            int n = Constants.DEFAULT_CHANCE;
            int e = Constants.DEFAULT_CHANCE;
            int s = Constants.DEFAULT_CHANCE;
            int w = Constants.DEFAULT_CHANCE;
            int total = Constants.DEFAULT_TOTAL_CHANCE;

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
                return new Vector(xPosition, yPosition - 1);
            } else if ((randomNumber -= e) < 0) {
                return new Vector(xPosition + 1, yPosition);
            } else if ((randomNumber -= s) < 0) {
                return new Vector(xPosition, yPosition + 1);
            } else if (randomNumber - w < 0) {
                return new Vector(xPosition - 1, yPosition);
            } else return new Vector(xPosition, yPosition);

        }

        public void setTendency(int xPosition, int yPosition, int xTendency, int yTendency) {
            xTendencyBoard.setValue(xTendency, xPosition, yPosition);
            yTendencyBoard.setValue(yTendency, xPosition, yPosition);
        }

        public void setGlobalTendency(int xGlobalTendency, int yGlobalTendency) {
            xTendencyBoard.fillBoard(xGlobalTendency);
            yTendencyBoard.fillBoard(yGlobalTendency);
        }

        public Vector getTendency(int xPosition, int yPosition) {
            return new Vector(xTendencyBoard.getValueAt(xPosition, yPosition), yTendencyBoard.getValueAt(xPosition, yPosition));
        }

    }

    public static class Vector {

        public final int x;
        public final int y;

        public Vector(int x, int y) {

            this.x = x;
            this.y = y;
        }


    }

    static class Tendency {

        public final int xPosition;
        public final int yPosition;
        public final int actionRatio;

        public Tendency(int xPosition, int yPosition, int actionRatio) {

            this.xPosition = xPosition;
            this.yPosition = yPosition;
            this.actionRatio = actionRatio;
        }
    }

    public static class AttractiveTendency extends Tendency {


        public AttractiveTendency(int xPosition, int yPosition, int actionRatio) {
            super(xPosition, yPosition, actionRatio);
        }
    }

    public static class CollisionBoard {

        private final int w;
        private final int h;
        private ArrayList<Barrier> barriers;

        //Statics
        private static final int BORDER = -2;

        private final ArrayList<PositionValueBoard> squareWalkersCollisionBoards;
        private final ArrayList<PositionValueBoard> circularWalkersCollisionBoards;
        private final ArrayList<Integer> squaredWalkerSizes;
        private final ArrayList<Integer> circularWalkerSizes;

        CollisionBoard(int w, int h, ArrayList<Barrier> barriers) {

            this.w = w;
            this.h = h;
            this.barriers = barriers;
            squaredWalkerSizes = new ArrayList<>();
            squareWalkersCollisionBoards = new ArrayList<>();
            circularWalkerSizes = new ArrayList<>();
            circularWalkersCollisionBoards = new ArrayList<>();
        }

        /**
         * Add a new walker to track, if a similar walker was added
         * before it will do nothing.
         *
         * @param walker
         */
        public void attachThisWalker(RandomWalker walker) {

            if (walker.getShape() == Constants.SQUARE_SHAPE) {
                if (squaredWalkerSizes.contains(walker.getSize()))
                    return;

                //New Size
                squaredWalkerSizes.add(walker.getSize());

                //new CollisionBoard
                addCollisionBoard(walker.getSize(), walker.getShape());

            } else if (walker.getShape() == Constants.CIRCULAR_SHAPE) {
                if (circularWalkerSizes.contains(walker.getSize()))
                    return;

                //New Size
                circularWalkerSizes.add(walker.getSize());
                addCollisionBoard(walker.getSize(), walker.getShape());
            }
        }

        private void addCollisionBoard(int targetSize, int targetShape) {

            //Capacity
            ensureBoardsArrayListCapacity(targetSize, targetShape);

            //Adding
            if (targetShape == Constants.SQUARE_SHAPE) {

                PositionValueBoard newBoard = new PositionValueBoard(w, h);
                updateCollisionBoard(targetSize, targetShape, newBoard);
                squareWalkersCollisionBoards.set(targetSize, newBoard);

            } else if (targetShape == Constants.CIRCULAR_SHAPE) {

                PositionValueBoard newBoard = new PositionValueBoard(w, h);
                updateCollisionBoard(targetSize, targetShape, newBoard);
                circularWalkersCollisionBoards.set(targetSize, newBoard);

            }

        }

        /**
         * Will update the permitted positions of a given board.
         *
         * @param targetSize
         * @param targetShape
         * @param board
         */
        private void updateCollisionBoard(int targetSize, int targetShape, PositionValueBoard board) {

            //Positioning barriers...
            getBarriersInPosition(board);

            //Extending restriction
            extendBarriersRestriction(targetSize, targetShape, board);

            //Border
            addBorderRestriction(targetSize, board);

        }

        private void getBarriersInPosition(PositionValueBoard board) {
            int index = 0;
            for (Barrier barrier : barriers) {
                if (barrier.getShape() == Constants.SQUARE_SHAPE) {
                    board.setSquareAreaSafely(barrier.getPositionX(), barrier.getPositionY(),
                            barrier.getSize(), index);
                } else if (barrier.getShape() == Constants.CIRCULAR_SHAPE) {
                    board.setCircularAreaSafely(barrier.getPositionX(), barrier.getPositionY(),
                            barrier.getSize(), index);
                } else if (barrier.getShape() == Constants.RECTANGULAR_SHAPE) {
                    board.setRectangularAreaSafely(barrier.getPositionX(), barrier.getPositionY(),
                            barrier.getSizeX(), barrier.sizeY, index);
                } else {
                    System.out.println("Error: Unsupported shape " + barrier.getShape());
                    return;
                }
                index++;
            }
        }

        private void extendBarriersRestriction(int targetSize, int targetShape, PositionValueBoard board) {
            int index = 0;
            for (Barrier barrier : barriers) {

                //Getting perimeter
                ArrayList<Vector> perimeter;
                if (barrier.getShape() == Constants.SQUARE_SHAPE) {
                    perimeter = board.getSquarePerimeterPointsSafely(barrier.getPositionX(), barrier.getPositionY(),
                            barrier.getSize());
                } else if (barrier.getShape() == Constants.CIRCULAR_SHAPE) {
                    perimeter = board.getCircularPerimeterPointsSafely(barrier.getPositionX(), barrier.getPositionY(),
                            barrier.getSize());
                } else if (barrier.getShape() == Constants.RECTANGULAR_SHAPE) {
                    perimeter = board.getRectangularPerimeterPointsSafely(barrier.getPositionX(), barrier.getPositionY(),
                            barrier.getSizeX(), barrier.getSizeY());
                } else {
                    System.out.println("Error: Unsupported shape " + barrier.getShape());
                    return;
                }
                index++;

                //updating points
                if (targetShape == Constants.SQUARE_SHAPE) {
                    for (Vector pp : perimeter) {
                        board.setSquarePerimeterSafely(pp.x, pp.y, targetSize, index);
                    }
                } else if (targetShape == Constants.CIRCULAR_SHAPE) {
                    for (Vector pp : perimeter) {
                        board.setCircularPerimeterSafely(pp.x, pp.y, targetSize, index);
                    }
                }

            }
        }

        private void addBorderRestriction(int targetSize, PositionValueBoard board) {

            //Upper line
            for (int y = 0; y < targetSize; y++) {
                for (int x = 0; x < w; x++) {
                    board.setValue(BORDER, x, y);
                }
            }

            //left Border lines
            int lastY = h - targetSize;
            for (int x = 0; x < targetSize; x++) {
                for (int y = targetSize; y < lastY; y++) {
                    board.setValue(BORDER, x, y);
                }
            }

            //right Border lines
            int lastX = w - targetSize;
            for (int x = lastX; x < w; x++) {
                for (int y = targetSize; y < lastY; y++) {
                    board.setValue(BORDER, x, y);
                }
            }

            //Upper line
            for (int y = lastY; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    board.setValue(BORDER, x, y);
                }
            }

        }

        private void ensureBoardsArrayListCapacity(int minCapacity, int shape) {
            if (shape == Constants.SQUARE_SHAPE)
                while (squareWalkersCollisionBoards.size() <= minCapacity) {
                    squareWalkersCollisionBoards.add(null);
                }
            else if (shape == Constants.CIRCULAR_SHAPE)
                while (circularWalkersCollisionBoards.size() <= minCapacity) {
                    circularWalkersCollisionBoards.add(null);
                }
        }

        /**
         * Return true is a walker will collide in a given position
         *
         * @return
         */
        public boolean checkCollision(int positionX, int positionY, RandomWalker walker) {

            if (walker.getShape() == Constants.SQUARE_SHAPE) {
                return squareWalkersCollisionBoards.get(walker.getSize()).
                        getValueAt(positionX, positionY) != PositionValueBoard.EMPTY;
            } else if (walker.getShape() == Constants.CIRCULAR_SHAPE) {
                return circularWalkersCollisionBoards.get(walker.getSize()).
                        getValueAt(positionX, positionY) != PositionValueBoard.EMPTY;
            } else {
                System.out.println("Error: Unsupported shape " + walker.getShape());
                return false;
            }
        }

    }

}