package Core.Engine.World;

import Core.Engine.Iterations.Iterations;
import Core.Engine.Iterations.OnIterationActionHandler;
import Core.Engine.PositionValueBoard.LabeledPositionsBoard;
import Core.Engine.RandomWalker.RandomWalker;
import Core.Engine.Vector.Vector;

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
 * 4. A random walker will always collide with a barrier and the borders of the world.<br>
 * 5. It is consider as the position of a walker the center of the shape.<br>
 * <p>
 * Barriers characteristics<br>
 * 1. They are Static objects.<br>
 * 2. Will collide with walkers. <br>
 * 3. They need to be created before any walker. <br>
 * <p>
 * The RandomWalkersWorld characteristics:<br>
 * 1. In this world exist as objects only walkers and barriers.<br>
 * 2. It keep all the walkers into a array so, they will always be
 * calls by its index in this array. The same for the Barriers.<br>
 * 3. It set the tendency of each walker to chose a direction,
 * by default this probability is equal for the walker position 4 neighbors
 * coordinates and the position itself. This mean that each direction has 1/5 chances to
 * be selected as the next step of the walker.<br>
 * 4. It always check for walkers collision with a barrier and has a feedback
 * methods to it.<br>
 * 5. It can be imagined as a grid with the origin the
 * upper left corner.<br>
 */
public class RandomWalkersWorld {

//    public static void main(String... args) {
//
//
//    }
//
//    //Walkers
//    private ArrayList<RandomWalker> walkers;
//    private ArrayList<Barrier> barrier;
//
//    //TendencyBoard
//    private TendencyBoard globalTendencyBoard;
//
//    //cache
//    private Cache cache;
//
//    //collisions Boards
//    /**
//     * This board handle the collision that will
//     * never change during the simulation
//     */
//    private CollisionBoard staticCollisionsBoard;
//
//    //walkersCountBoard
//    private LabeledPositionsBoard walkersCountBoard;
//
//    //World
//    private int w, h;
//
//    //Run
//    private long stepsCounter;
//    private int lastRunSteps;
//    private int stepsIncrement;
//    static RunThreadRunnable runnable;
//    static Thread runThread;
//
//    //Override control
//    private boolean enableAfterStep;
//    private boolean enableBeforeStep;
//    private boolean enableOnCollisionCallBack;
//    private boolean enablePerPositionWalkerCount;
//
//    //World
//    private static RandomWalkersWorld world;
//
//    //Constructor
//    private RandomWalkersWorld(int w, int h) {
//        setUpPreferences();
//        setUpData(w, h);
//    }
//
//    public static RandomWalkersWorld createWorld(int w, int h) throws WorldAlreadyCreatedException {
//
//        if (world != null)
//            throw new WorldAlreadyCreatedException();
//
//        world = new RandomWalkersWorld(w, h);
//        return world;
//    }
//
//    //Deb
//    public static boolean enablePrompt;
//
//    //region WORLD METHODS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//
//    /**
//     * A nuclear bomb will explode and a empty new world will remain. All the preference will
//     * be intact, only data will be erased...
//     */
//    public final void clearWorld() {
//
//        world.stopRunning();
//        setUpData(w, h);
//    }
//
//    /**
//     * It steps the world a number of times each time this method is call.
//     * This number will change depending of the relation between targetFrameRate and
//     * currentFrameRate. The method try to keep the closer possible
//     * the currentFrameRate to the targetFrameRate. This method do not
//     * run in a different thread.
//     * It is useful to increase the number of steps per minute of the world
//     * without affecting the frameRate of the external environment where
//     * it is being used.
//     *
//     * @param targetFrameRate  the desire frameRate of the environment (usually a graphic
//     *                         environment) where you are running this world.
//     * @param currentFrameRate the current frameRate of the environment (usually a graphic
//     *                         environment) where you are running this world.
//     */
//    public final void run(int targetFrameRate, int currentFrameRate) {
//
//        if (targetFrameRate > currentFrameRate) {
//            if (lastRunSteps > 0)
//                lastRunSteps -= stepsIncrement;
//        } else {
//            lastRunSteps += stepsIncrement;
//        }
//
//        int counter = lastRunSteps;
//        do {
//            step();
//            counter--;
//        } while (counter >= 0);
//
//    }
//
//    public final void run(long millis) {
//
//        long startMillis = System.currentTimeMillis();
//        lastRunSteps = 0;
//        do {
//            step();
//            lastRunSteps++;
//        } while (System.currentTimeMillis() - startMillis < millis);
//
//    }
//
//    public final void run(int steps) {
//
//        lastRunSteps = steps;
//
//        do {
//            step();
//            steps--;
//        } while (steps >= 0);
//
//    }
//
//    public final void run() {
//        if (runnable == null) {
//            runnable = new RunThreadRunnable(this);
//        } else {
//            runnable.terminate();
//            runnable = new RunThreadRunnable(this);
//        }
//
//        if (runThread == null) {
//            runThread = new Thread(runnable);
//        } else {
//            runThread = null;
//            runThread = new Thread(runnable);
//        }
//        runnable.running = true;
//        runThread.start();
//    }
//
//    public final void stopRunning() {
//        if (runnable != null) {
//            runnable.terminate();
//            runnable = null;
//        }
//
//        if (runThread != null) {
//            runThread = null;
//        }
//    }
//
//    public void afterStep() {
//        enableAfterStep = false;
//    }
//
//    public void beforeStep() {
//        enableBeforeStep = false;
//    }
//
//    public int getWalkersCount(int positionX, int positionY) {
//        return walkersCountBoard.getLabelAt(positionX, positionY);
//    }
//
//    /**
//     *
//     */
//    public final void step() {
//
//        if (enableBeforeStep)
//            beforeStep();
//
//        //Iterating all walkers
//        Vector newPosition;
//        RandomWalker walker;
//        for (int walkerIndex = 0; walkerIndex < walkers.size(); walkerIndex++) {
//
//            //Walker
//            walker = walkers.get(walkerIndex);
//
//            //New Position
//            newPosition = globalTendencyBoard.tryDirection(walker.positionX, walker.positionY);
//
//            //Checking collisions
//            if (staticCollisionsBoard.checkForCollision(newPosition.x, newPosition.y, walker)) {
//                if (enableOnCollisionCallBack)
//                    onCollisionOccurred(staticCollisionsBoard.lastCheckedCollisionValue, walkerIndex);
//                continue;
//            }
//
//            //delete old position
//            if (enablePerPositionWalkerCount) {
//                int oldValue = walkersCountBoard.getLabelAt(walker.positionX, walker.positionY);
//                if (oldValue != 0)
//                    walkersCountBoard.setLabel(walkersCountBoard.getLabelAt(walker.positionX, walker.positionY) - 1,
//                            walker.positionX, walker.positionY);
//            }
//
//            //Moving Walker
//            walker.setPositionX(newPosition.x);
//            walker.setPositionY(newPosition.y);
//
//            //count new position
//            if (enablePerPositionWalkerCount) {
//                walkersCountBoard.setLabel(walkersCountBoard.getLabelAt(walker.positionX, walker.positionY) + 1,
//                        walker.positionX, walker.positionY);
//            }
//
//
//        }
//
//        if (enableAfterStep)
//            afterStep();
//
//        stepsCounter++;
//    }
//
//    public final boolean isOutOfTheWorld(int x, int y) {
//        return x < 0 || x >= w || y < 0 || y >= h;
//    }
//
//    //endregion PUBLIC METHODS -----------------------------------------------------------------------------------------
//
//    //region BARRIER METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//
//    public final boolean addCircularBarrier(int x, int y, int radius) {
//
//        if (walkers.size() > 0) {
//            if (enablePrompt) System.out.println("Error: Barriers must be added before any walker");
//            return false;
//        }
//
//        Barrier newBarrier = new Barrier(x, y, Constants.CIRCULAR_SHAPE, radius);
//        barrier.add(newBarrier);
//        return true;
//    }
//
//    public final boolean addSquareBarrier(int x, int y, int radius) {
//
//        if (walkers.size() > 0) {
//            if (enablePrompt) System.out.println("Error: Barriers must be added before any walker");
//            return false;
//        }
//
//        Barrier newBarrier = new Barrier(x, y, Constants.SQUARE_SHAPE, radius);
//        barrier.add(newBarrier);
//        return true;
//    }
//
//    public final boolean addRectangularBarrier(int x, int y, int sizeX, int sizeY) {
//
//        if (walkers.size() > 0) {
//            if (enablePrompt) System.out.println("Error: Barriers must be added before any walker");
//            return false;
//        }
//
//        Barrier newBarrier = new Barrier(x, y, Constants.RECTANGULAR_SHAPE, sizeX, sizeY);
//        barrier.add(newBarrier);
//        return true;
//    }
//
//    /**
//     * Override this method to handle feedback collisions. No super call is required.
//     *
//     * @param barrierIndex The barrier that collide, if you got a negative
//     *                     value (BORDER VALUE), so you the walker hit the border
//     *                     not a barrier.
//     * @param walkerIndex  the walker that collide.
//     */
//    public void onCollisionOccurred(int barrierIndex, int walkerIndex) {
//    }
//
//    //endregion BARRIER METHODS ----------------------------------------------------------------------------------------
//
//    //region WALKERS METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//
//    /**
//     * Add a walker to the world, if the process was successful and the
//     * walker was actually added it returns true, other wise returns false
//     * and this means the walker wasn't added :(
//     *
//     * @param x    the x position of the new walker
//     * @param y    the y position of the new walker
//     * @param size the size of the walker
//     * @return true if the operation was successful, false otherwise.
//     */
//    public final boolean addSquareWalker(int x, int y, int size) {
//
//        if (checkForErrorsAddingWalker(x, y, size)) return false;
//
//        RandomWalker newWalker = new RandomWalker(x, y, size, Constants.SQUARE_SHAPE);
//
//        //Update collision board
//        staticCollisionsBoard.attachWalker(newWalker);
//
//        //Check collisions
//        if (staticCollisionsBoard.checkForCollision(x, y, newWalker)) {
//            if (enablePrompt) System.out.println("Error: The walker was overlapping something.");
//            return false;
//        }
//
//        //Adding walker
//        walkers.add(newWalker);
//        return true;
//    }
//
//    /**
//     * Add a walker to the world, if the process was successful and the
//     * walker was actually added it returns true, other wise returns false
//     * and this means the walker wasn't added :(
//     *
//     * @param x    the x position of the new walker
//     * @param y    the y position of the new walker
//     * @param size the size of the walker
//     * @return true if the operation was successful, false otherwise.
//     */
//    public final boolean addCircularWalker(int x, int y, int size) {
//
//        if (checkForErrorsAddingWalker(x, y, size)) return false;
//
//        RandomWalker newWalker = new RandomWalker(x, y, size, Constants.CIRCULAR_SHAPE);
//
//        //Update collision board
//        staticCollisionsBoard.attachWalker(newWalker);
//
//        //Check collisions
//        if (staticCollisionsBoard.checkForCollision(x, y, newWalker)) {
//            if (enablePrompt) System.out.println("Error: The walker was overlapping something.");
//            return false;
//        }
//
//        //Adding walker
//        walkers.add(newWalker);
//        return true;
//    }
//
//    public void putWalkerPositionInCache(int walkerIndex) {
//        cache.walkerXPosition = walkers.get(walkerIndex).positionX;
//        cache.walkerYPosition = walkers.get(walkerIndex).positionY;
//    }
//
//    public final Vector getWalkerPosition(int walkerIndex) {
//        return new Vector(walkers.get(walkerIndex).getPositionX(), walkers.get(walkerIndex).getPositionY());
//    }
//
//    public final int getWalkerPositionX(int walkerIndex) {
//        return walkers.get(walkerIndex).getPositionX();
//    }
//
//    public final int getWalkerPositionY(int walkerIndex) {
//        return walkers.get(walkerIndex).getPositionY();
//    }
//
//    public final int getWalkerSize(int walkerIndex) {
//        return walkers.get(walkerIndex).getSize();
//    }
//
//    public final void clearWalkers() {
//        walkers.clear();
//        walkers = new ArrayList<>();
//    }
//
//    //endregion WALKERS METHODS ----------------------------------------------------------------------------------------
//
//    //region TENDENCY METHODS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//
//    /**
//     * Set a tendency for a given point of the world. <br>
//     * Walkers positioned in this point will TEND to walk in this direction. <br>
//     * The Tendency is a Vector(xTendency, yTendency) witch direction from the position point
//     * and his getMagnitude constitute the Tendency itself. So, a tendency of (1,0) in the point
//     * (25,152) means that a walker positioned in the coordinate (25,152) will TEND to go North (Up).
//     * Due to the default values the minimal Tendency influence is about 0.4%. In the example above
//     * this mean the North direction have a 0.4% more chance to be picked as the next step direction
//     * of the walker.
//     *
//     * @param xPosition the x position you want to set a Tendency.
//     * @param yPosition the y position you want to set a Tendency.
//     * @param xTendency The value of the x Tendency.
//     * @param yTendency The value of the y Tendency.
//     */
//    public final void setTendency(int xPosition, int yPosition, int xTendency, int yTendency) {
//        globalTendencyBoard.setTendency(xPosition, yPosition, xTendency, yTendency);
//
//    }
//
//    public final void setCircularTendency(int centerX, int centerY, int radius, OnGetTendency onGetTendency) {
//        globalTendencyBoard.setCircularTendency(centerX, centerY, radius, onGetTendency);
//    }
//
//    public final void setSquareTendency(int centerX, int centerY, int size, OnGetTendency onGetTendency) {
//        globalTendencyBoard.setSquareTendency(centerX, centerY, size, onGetTendency);
//    }
//
//    public final void setRectangularTendency(int centerX, int centerY, int sizeX, int sizeY, OnGetTendency onGetTendency) {
//        globalTendencyBoard.setRectangularTendency(centerX, centerY, sizeX, sizeY, onGetTendency);
//    }
//
//    /**
//     * Set a tendency for a given point of the world. <br>
//     * Walkers positioned in this point will TEND to walk in this direction. <br>
//     * The Tendency is a Vector(xTendency, yTendency) witch direction from the position point
//     * and his getMagnitude constitute the Tendency itself. So, a tendency of (1,0) in the point
//     * (25,152) means that a walker positioned in the coordinate (25,152) will TEND to go North (Up).
//     * Due to the default values the minimal Tendency influence is about 0.4%. In the example above
//     * this mean the North direction have a 0.4% more chance to be picked as the next step direction
//     * of the walker. <br>
//     * Using a factor different that 1.0F will modified the original getMagnitude.
//     *
//     * @param xPosition the x position you want to set a Tendency.
//     * @param yPosition the y position you want to set a Tendency.
//     * @param xTendency The value of the x Tendency.
//     * @param yTendency The value of the y Tendency.
//     * @param factor    This factor will be multiplied to the getMagnitude of the tendency.
//     */
//    public final void setTendency(int xPosition, int yPosition, int xTendency, int yTendency, float factor) {
//        globalTendencyBoard.setTendency(xPosition, yPosition, (int) (xTendency * factor), (int) (yTendency * factor));
//    }
//
//    /**
//     * Add a given Tendency to the existing Tendency of a given Point. <br>
//     * The Tendency is a Vector(xTendency, yTendency) witch direction from the position point
//     * and his getMagnitude constitute the Tendency itself. So, a Tendency of (1,0) in the point
//     * (25,152) means that a walker positioned in the coordinate (25,152) will TEND to go North (Up).
//     * Due to the default values the minimal Tendency influence is about 0.4%. In the example above
//     * this mean the North direction have a 0.4% more chance to be picked as the next step direction
//     * of the walker. <br>
//     *
//     * @param xPosition the given x position
//     * @param yPosition the given y position
//     * @param xTendency the x Tendency component
//     * @param yTendency the y Tendency component
//     */
//    public final void addTendency(int xPosition, int yPosition, int xTendency, int yTendency) {
//        globalTendencyBoard.addTendency(xPosition, yPosition, xTendency, yTendency);
//    }
//
//    /**
//     * Get the tendency stored in this position.
//     * The Tendency is a Vector(xTendency, yTendency) witch direction from the position point
//     * and his getMagnitude constitute the Tendency itself. So, a Tendency of (1,0) in the point
//     * (25,152) means that a walker positioned in the coordinate (25,152) will TEND to go North (Up).
//     * Due to the default values the minimal Tendency influence is about 0.4%. In the example above
//     * this mean the North direction have a 0.4% more chance to be picked as the next step direction
//     * of the walker. <br>
//     *
//     * @param xPosition the given x Position
//     * @param yPosition the given y Position
//     * @return the result
//     */
//    public final Vector getTendency(int xPosition, int yPosition) {
//        return globalTendencyBoard.getTendency(xPosition, yPosition);
//    }
//
//    /**
//     * Set the same Tendency to all the points in the World.
//     * The Tendency is a Vector(xTendency, yTendency) witch direction from the position point
//     * and his getMagnitude constitute the Tendency itself. So, a Tendency of (1,0) in the point
//     * (25,152) means that a walker positioned in the coordinate (25,152) will TEND to go North (Up).
//     * Due to the default values the minimal Tendency influence is about 0.4%. In the example above
//     * this mean the North direction have a 0.4% more chance to be picked as the next step direction
//     * of the walker. <br>
//     *
//     * @param xGlobalTendency the Tendency x component
//     * @param yGlobalTendency the Tendency y component
//     */
//    public final void setGlobalTendency(int xGlobalTendency, int yGlobalTendency) {
//        globalTendencyBoard.setGlobalTendency(xGlobalTendency, yGlobalTendency);
//    }
//
//    public final void setLinearCircularTendencyArea(int centerX, int centerY, int radius,
//                                                    int centerXTendency, int centerYtendency,
//                                                    int borderXTendency, int borderYTendency) {
//
//
//    }
//
//    //endregion TENDENCY METHODS ---------------------------------------------------------------------------------------
//
//    //region PRIVATE METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//
//
//    /**
//     * Setup a new world data
//     *
//     * @param w
//     * @param h
//     */
//    private void setUpData(int w, int h) {
//        //Size
//        this.w = w;
//        this.h = h;
//
//        //Walkers
//        walkers = new ArrayList<>();
//        barrier = new ArrayList<>();
//
//        //Boards
//        globalTendencyBoard = new TendencyBoard(w, h);
//
//        //Collision Boards
//        staticCollisionsBoard = new CollisionBoard(w, h, barrier);
//
//        //Simulation
//        runnable = new RunThreadRunnable(this);
//        runThread = new Thread(runnable);
//
//        //PositionCounter
//        walkersCountBoard = new LabeledPositionsBoard(w, h);
//        walkersCountBoard.setEmptyLabel(0);
//        walkersCountBoard.fillBoard(0);
//
//        //cache
//        cache = new Cache();
//    }
//
//    private void setUpPreferences() {
//
//        //Deb
//        enablePrompt = false;
//
//        //Overrides
//        enableAfterStep = true;
//        enableBeforeStep = true;
//        enablePerPositionWalkerCount = false;
//
//        //Collisions
//        enableOnCollisionCallBack = false;
//
//        //Simulation
//        stepsCounter = 0;
//        lastRunSteps = 1;
//        stepsIncrement = 1;
//
//    }
//
//    /**
//     * Return true if occurred an error and prompt it
//     *
//     * @param x    data to check
//     * @param y    data to check
//     * @param size data to check
//     * @return the result
//     */
//    private boolean checkForErrorsAddingWalker(int x, int y, int size) {
//        if (isOutOfTheWorld(x, y)) {
//            if (enablePrompt) System.out.println("Error: The walker can not be outside of the World");
//            return true;
//        }
//
//        if (size <= 0) {
//            if (enablePrompt) System.out.println("Error: The size must be equal or greater than 1.");
//            return true;
//        }
//
//        return false;
//    }
//
//    //endregion PRIVATE METHODS ----------------------------------------------------------------------------------------
//
//    //region GETTERS AND SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//
//    /**
//     * Return the number of the steps per frames that the world did the last
//     * time run method was call.
//     *
//     * @return
//     */
//    public final int getLastRunSteps() {
//        return lastRunSteps;
//    }
//
//    /**
//     * Returns the width of the world
//     *
//     * @return
//     */
//    public final int getW() {
//        return w;
//    }
//
//    /**
//     * Returns the height of the worlds
//     *
//     * @return
//     */
//    public final int getH() {
//        return h;
//    }
//
//    /**
//     * Return the number of steps of the simulations. That means the number of time step() method
//     * has been called.
//     *
//     * @return
//     */
//    public final long getStepsCounter() {
//        return stepsCounter;
//    }
//
//    /**
//     * Return the current count of walkers in the world.
//     *
//     * @return the result
//     */
//    public final int getWalkersCount() {
//        return walkers.size();
//    }
//
//    /**
//     * Return the current count of barriers in the world.
//     *
//     * @return the result
//     */
//    public final int getBarrierCount() {
//        return barrier.size();
//    }
//
//    public final Vector getBarrierPosition(int barrierIndex) {
//        return new Vector(barrier.get(barrierIndex).getPositionX(), barrier.get(barrierIndex).getPositionY());
//    }
//
//    public final int getBarrierSize(int barrierIndex) {
//        return barrier.get(barrierIndex).getSize();
//    }
//
//    public final boolean isEnableOnCollisionCallBack() {
//        return enableOnCollisionCallBack;
//    }
//
//    public final void setEnableOnCollisionCallBack(boolean enableOnCollisionCallBack) {
//        this.enableOnCollisionCallBack = enableOnCollisionCallBack;
//    }
//
//    public final boolean isEnablePrompt() {
//        return enablePrompt;
//    }
//
//    public final int getRandomX() {
//        return r.nextInt(w);
//    }
//
//    public final int getRandomY() {
//        return r.nextInt(h);
//    }
//
//    public final int getWorldCenterX() {
//        return w / 2;
//    }
//
//    public final int getWorldCenterY() {
//        return h / 2;
//    }
//
//    public boolean isEnablePerPositionWalkerCount() {
//        return enablePerPositionWalkerCount;
//    }
//
//    public void setEnablePerPositionWalkerCount(boolean enablePerPositionWalkerCount) {
//        this.enablePerPositionWalkerCount = enablePerPositionWalkerCount;
//    }
//
//    public Cache getCache() {
//        return cache;
//    }
//
//    /**
//     * Will enable prompt, this have a debug propose,
//     * the world will them print a lot of things
//     * in the console such as Errors.
//     */
//    public final void setEnablePrompt(boolean b) {
//        enablePrompt = b;
//    }
//
//    public int getStepsIncrement() {
//        return stepsIncrement;
//    }
//
//    public void setStepsIncrement(int stepsIncrement) {
//        this.stepsIncrement = stepsIncrement;
//    }
//
//    //endregion GETTERS AND SETTERS ------------------------------------------------------------------------------------
//
//    //region PRIVATE CLASSES +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//
//    /**
//     * This class represent a Barrier. The only object that can collide
//     * with the Walkers. As RandomWalker Class it only is a container of
//     * characteristics, all the job is done by the World.
//     */
//    protected static class Barrier {
//
//        private int positionX;
//        private int positionY;
//        private final int shape;
//        private int sizeX;
//        private int sizeY;
//
//        /**
//         * Dou to Barrier support rectangular shapes,  you can set more than a
//         * size value. If the shape is SQUARE or CIRCULAR the first passed value
//         * will be set as the size/radio of the barrier, the rest will be drop.
//         * If the shape is RECTANGULAR the two first passed values will be set as
//         * sizeX, sizeY respectively. If only one value is passed it will set as
//         * boths.
//         *
//         * @param positionX
//         * @param positionY
//         * @param shape
//         * @param size
//         */
//        public Barrier(int positionX, int positionY, int shape, int... size) {
//
//            this.positionX = positionX;
//            this.positionY = positionY;
//            this.shape = shape;
//
//            if (shape == Constants.SQUARE_SHAPE || shape == Constants.CIRCULAR_SHAPE) {
//                sizeX = size[0];
//            } else if (shape == Constants.RECTANGULAR_SHAPE) {
//                if (size.length > 1) {
//                    sizeX = size[0];
//                    sizeY = size[1];
//                } else {
//                    sizeX = size[0];
//                    sizeY = size[0];
//                }
//            }
//        }
//
//
//        public int getSize() {
//            return sizeX;
//        }
//
//        public int getSizeX() {
//            return sizeX;
//        }
//
//        public int getSizeY() {
//            if (shape == Constants.SQUARE_SHAPE || shape == Constants.CIRCULAR_SHAPE) {
//                return sizeX;
//            } else if (shape == Constants.RECTANGULAR_SHAPE) {
//                return sizeY;
//            } else {
//                return sizeX;
//            }
//        }
//
//        public int getPositionX() {
//            return positionX;
//        }
//
//        public int getPositionY() {
//            return positionY;
//        }
//
//        public int getShape() {
//            return shape;
//        }
//
//        public void setPositionX(int positionX) {
//            this.positionX = positionX;
//        }
//
//        public void setPositionY(int positionY) {
//            this.positionY = positionY;
//        }
//
//    }
//
//    /**
//     * This class handle the Tendencies...
//     * A Tendency is a Vector (x,y) that represent how favored is
//     * a direction to be selected as the next step of the walker.
//     * This vector has its origin in the given position and his
//     * getMagnitude and direction depend of the xTendency and yTendency
//     * values stored in this position. So, if in the coordinate
//     * (25,26) of the world we have a tendency value of (1,0) this
//     * mean that the North (Up) direction is favored. Dou to the Default
//     * Values the min amount of tendency is 0.4%, so in the example the North
//     * direction has 0.4% more chance to be selected than the others. The walker
//     * located in this coordinate have the Tendency of going North.
//     */
//    private static class TendencyBoard {
//
//        //Boards
//        private final LabeledPositionsBoard xTendencyBoard;
//        private final LabeledPositionsBoard yTendencyBoard;
//
//        //OnIterationHandlers
//        private OnIterationActionHandler<OnGetTendency, LabeledPositionsBoard, Void> tendencySetterIterationHandler =
//                new OnIterationActionHandler<OnGetTendency, LabeledPositionsBoard, Void>() {
//                    @Override
//                    public void action(int x, int y) {
//                        if (getExtraTwo().isWithinBoard(x, y)) {
//                            Vector tendency = getExtraOne().getTendency(x, y);
//                            setTendency(x, y, tendency.x, tendency.y);
//                        }
//                    }
//                };
//
//
//        public TendencyBoard(int w, int h) {
//
//            xTendencyBoard = new LabeledPositionsBoard(w, h);
//            xTendencyBoard.fillBoard(0);
//            yTendencyBoard = new LabeledPositionsBoard(w, h);
//            yTendencyBoard.fillBoard(0);
//        }
//
//        /**
//         * Return a random direction depending of the tendency of the give direction
//         * This value can be used to move randomly through the world.
//         *
//         * @param xPosition
//         * @param yPosition
//         * @return
//         */
//        Vector tryDirection(int xPosition, int yPosition) {
//
//            int xTendency = xTendencyBoard.getLabelAt(xPosition, yPosition);
//            int yTendency = yTendencyBoard.getLabelAt(xPosition, yPosition);
//
//            if (xTendency == 0 && yTendency == 0) {
//                return new Vector(xPosition + r.nextInt(3) - 1,
//                        yPosition + r.nextInt(3) - 1);
//            }
//
//            int n = Constants.DEFAULT_CHANCE;
//            int e = Constants.DEFAULT_CHANCE;
//            int s = Constants.DEFAULT_CHANCE;
//            int w = Constants.DEFAULT_CHANCE;
//            int total = Constants.DEFAULT_TOTAL_CHANCE;
//
//            //Y
//            if (yTendency > 0) {
//                n += yTendency;
//                total += yTendency;
//            } else {
//                s -= yTendency;
//                total -= yTendency;
//            }
//
//            //X
//            if (xTendency > 0) {
//                e += xTendency;
//                total += xTendency;
//            } else {
//                w -= xTendency;
//                total -= xTendency;
//            }
//
//            int randomNumber = r.nextInt(total);
//            if ((randomNumber -= n) < 0) {
//                return new Vector(xPosition, yPosition - 1);
//            } else if ((randomNumber -= e) < 0) {
//                return new Vector(xPosition + 1, yPosition);
//            } else if ((randomNumber -= s) < 0) {
//                return new Vector(xPosition, yPosition + 1);
//            } else if (randomNumber - w < 0) {
//                return new Vector(xPosition - 1, yPosition);
//            } else return new Vector(xPosition, yPosition);
//
//        }
//
//        int tryXDirection(int xPosition, int yPosition) {
//
//            int xTendency = xTendencyBoard.getLabelAt(xPosition, yPosition);
//
//            if (xTendency == 0) {
//                return xPosition + r.nextInt(3) - 1;
//            }
//
//            int e = Constants.DEFAULT_CHANCE;
//            int w = Constants.DEFAULT_CHANCE;
//            int total = 2999;
//
//
//            //X
//            if (xTendency > 0) {
//                e += xTendency;
//                total += xTendency;
//            } else {
//                w -= xTendency;
//                total -= xTendency;
//            }
//
//            int randomNumber = r.nextInt(total);
//            if ((randomNumber -= e) < 0) {
//                return xPosition + 1;
//            }
//            if (randomNumber - w < 0) {
//                return xPosition - 1;
//            } else return xPosition;
//
//        }
//
//        int tryYDirection(int xPosition, int yPosition) {
//
//            int yTendency = yTendencyBoard.getLabelAt(xPosition, yPosition);
//
//            if (yTendency == 0) {
//                return yPosition + r.nextInt(3) - 1;
//            }
//
//            int n = Constants.DEFAULT_CHANCE;
//            int s = Constants.DEFAULT_CHANCE;
//            int total = 2999;
//
//            //Y
//            if (yTendency > 0) {
//                n += yTendency;
//                total += yTendency;
//            } else {
//                s -= yTendency;
//                total -= yTendency;
//            }
//
//            int randomNumber = r.nextInt(total);
//            if ((randomNumber -= n) < 0) {
//                return yPosition - 1;
//            } else if ((randomNumber - s) < 0) {
//                return yPosition + 1;
//            } else return yPosition;
//
//        }
//
//        /**
//         * Set a tendency in a given position of the tendency board
//         *
//         * @param xPosition
//         * @param yPosition
//         * @param xTendency
//         * @param yTendency
//         */
//        void setTendency(int xPosition, int yPosition, int xTendency, int yTendency) {
//            xTendencyBoard.setLabel(xTendency, xPosition, yPosition);
//            yTendencyBoard.setLabel(yTendency, xPosition, yPosition);
//        }
//
//        void setCircularTendency(int centerX, int centerY, int radius, OnGetTendency onGetTendency) {
//            tendencySetterIterationHandler.setExtraOne(onGetTendency);
//            tendencySetterIterationHandler.setExtraTwo(xTendencyBoard);
//            Iterations.iterateCircularArea(centerX, centerY, radius, new OnIterationActionHandler() {
//                @Override
//                public void action(int x, int y) {
//                    if (xTendencyBoard.isWithinBoard(x, y)) {
//                        Vector tendency = onGetTendency.getTendency(x, y);
//                        setTendency(x, y, tendency.x, tendency.y);
//                    }
//                }
//            });
//        }
//
//        void setSquareTendency(int centerX, int centerY, int size, OnGetTendency onGetTendency) {
//            tendencySetterIterationHandler.setExtraOne(onGetTendency);
//            tendencySetterIterationHandler.setExtraTwo(xTendencyBoard);
//            Iterations.iterateSquareArea(centerX, centerY, size, new OnIterationActionHandler() {
//                @Override
//                public void action(int x, int y) {
//                    if (xTendencyBoard.isWithinBoard(x, y)) {
//                        Vector tendency = onGetTendency.getTendency(x, y);
//                        setTendency(x, y, tendency.x, tendency.y);
//                    }
//                }
//            });
//        }
//
//        void setRectangularTendency(int centerX, int centerY, int sizeX, int sizeY, OnGetTendency onGetTendency) {
//            tendencySetterIterationHandler.setExtraOne(onGetTendency);
//            tendencySetterIterationHandler.setExtraTwo(xTendencyBoard);
//            Iterations.iterateRectangularArea(centerX, centerY, sizeX, sizeY, new OnIterationActionHandler() {
//                @Override
//                public void action(int x, int y) {
//                    if (xTendencyBoard.isWithinBoard(x, y)) {
//                        Vector tendency = onGetTendency.getTendency(x, y);
//                        setTendency(x, y, tendency.x, tendency.y);
//                    }
//                }
//            });
//        }
//
//        /**
//         * Set a Tendency to all the points of the tendency board
//         *
//         * @param xGlobalTendency
//         * @param yGlobalTendency
//         */
//        void setGlobalTendency(int xGlobalTendency, int yGlobalTendency) {
//            xTendencyBoard.fillBoard(xGlobalTendency);
//            yTendencyBoard.fillBoard(yGlobalTendency);
//        }
//
//        /**
//         * Add a given Tendency to the existing Tendency of a given Point
//         *
//         * @param xPosition
//         * @param yPosition
//         * @param xTendency
//         * @param yTendency
//         */
//        void addTendency(int xPosition, int yPosition, int xTendency, int yTendency) {
//            xTendencyBoard.setLabel(xTendencyBoard.getLabelAt(xPosition, yPosition) + xTendency, xPosition, yPosition);
//            yTendencyBoard.setLabel(yTendencyBoard.getLabelAt(xPosition, yPosition) + yTendency, xPosition, yPosition);
//        }
//
//        /**
//         * Get the Tendency of a given point of the Tendency Board.
//         *
//         * @param xPosition
//         * @param yPosition
//         * @return
//         */
//        Vector getTendency(int xPosition, int yPosition) {
//            return new Vector(xTendencyBoard.getLabelAt(xPosition, yPosition), yTendencyBoard.getLabelAt(xPosition, yPosition));
//        }
//
//    }
//
//    /**
//     * This class handle a lot of things required to support
//     * collision tracking.
//     */
//    private static class CollisionBoard {
//
//        private final int w;
//        private final int h;
//        ArrayList<Barrier> barriers;
//        private int minCircularWalkerSize;
//        private int minSquareWalkerSize;
//        public LabeledPositionsBoard globalCollisionBoard;
//
//        /**
//         * Store the value of the point where the last time
//         * a collision was detected.
//         */
//        private int lastCheckedCollisionValue = LabeledPositionsBoard.EMPTY;
//
//        private final ArrayList<LabeledPositionsBoard> squareWalkersCollisionBoards;
//        private final ArrayList<LabeledPositionsBoard> circularWalkersCollisionBoards;
//        private final ArrayList<Integer> squaredWalkerSizes;
//        private final ArrayList<Integer> circularWalkerSizes;
//
//        CollisionBoard(int w, int h, ArrayList<Barrier> barriers) {
//
//            this.w = w;
//            this.h = h;
//            this.barriers = barriers;
//            squaredWalkerSizes = new ArrayList<>();
//            squareWalkersCollisionBoards = new ArrayList<>();
//            circularWalkerSizes = new ArrayList<>();
//            circularWalkersCollisionBoards = new ArrayList<>();
//            minCircularWalkerSize = Integer.MAX_VALUE;
//            minSquareWalkerSize = Integer.MAX_VALUE;
//
//            //EmptyBoard
//            LabeledPositionsBoard emptyBoard = new LabeledPositionsBoard(w, h);
//            squareWalkersCollisionBoards.add(emptyBoard);
//            circularWalkersCollisionBoards.add(emptyBoard);
//            globalCollisionBoard = emptyBoard;
//
//        }
//
//        /**
//         * Add a new walker to track, if a similar walker was added
//         * before it will do nothing.
//         *
//         * @param walker
//         */
//        void attachWalker(RandomWalker walker) {
//
//            if (walker.getShape() == Constants.SQUARE_SHAPE) {
//                if (squaredWalkerSizes.contains(walker.getSize()))
//                    return;
//
//                //minSize
//                if (walker.getSize() < minSquareWalkerSize)
//                    minSquareWalkerSize = walker.getSize();
//
//                //New Size
//                squaredWalkerSizes.add(walker.getSize());
//
//                //new CollisionBoard
//                addCollisionBoard(walker.getSize(), walker.getShape());
//
//            } else if (walker.getShape() == Constants.CIRCULAR_SHAPE) {
//                if (circularWalkerSizes.contains(walker.getSize()))
//                    return;
//
//                //minSize
//                if (walker.getSize() < minCircularWalkerSize)
//                    minCircularWalkerSize = walker.getSize();
//
//                //New Size
//                circularWalkerSizes.add(walker.getSize());
//
//                //new CollisionBoard
//                addCollisionBoard(walker.getSize(), walker.getShape());
//            }
//
//            updateGlobalCollisionBoard();
//        }
//
//        /**
//         * Returns true if the given point is a collision point to any
//         * particle in the world. That means that all calls of the method
//         * checkForCollision will return true in this point.
//         *
//         * @param positionX the x position to check
//         * @param positionY the y position to check
//         * @return the result
//         */
//        boolean checkForGlobalCollisionifPoint(int positionX, int positionY) {
//            return globalCollisionBoard.isOccupied(positionX, positionY);
//        }
//
//        private void updateGlobalCollisionBoard() {
//            if (minSquareWalkerSize < Math.round(minCircularWalkerSize / Constants.SQUARE_ROOT_OF_TWO)) {
//                globalCollisionBoard = squareWalkersCollisionBoards.get(minSquareWalkerSize);
//            } else {
//                globalCollisionBoard = circularWalkersCollisionBoards.get(minCircularWalkerSize);
//            }
//        }
//
//        private void addCollisionBoard(int targetSize, int targetShape) {
//
//            //Capacity
//            ensureBoardsArrayListCapacity(targetSize, targetShape);
//
//            //Adding
//            if (targetShape == Constants.SQUARE_SHAPE) {
//
//                LabeledPositionsBoard newBoard = new LabeledPositionsBoard(w, h);
//                updateCollisionBoard(targetSize, targetShape, newBoard);
//                squareWalkersCollisionBoards.set(targetSize, newBoard);
//
//            } else if (targetShape == Constants.CIRCULAR_SHAPE) {
//
//                LabeledPositionsBoard newBoard = new LabeledPositionsBoard(w, h);
//                updateCollisionBoard(targetSize, targetShape, newBoard);
//                circularWalkersCollisionBoards.set(targetSize, newBoard);
//
//            }
//
//        }
//
//        /**
//         * Will update the permitted positions of a given board.
//         *
//         * @param targetSize
//         * @param targetShape
//         * @param board
//         */
//        private void updateCollisionBoard(int targetSize, int targetShape, LabeledPositionsBoard board) {
//
//            //Positioning barriers...
//            putBarriersInBoard(board);
//
//            //Extending restriction
//            extendBarriersRestriction(targetSize, targetShape, board);
//
//            //Border
//            addBorderRestriction(targetSize, board);
//
//        }
//
//        /**
//         * Fill the staticCollisionsBoard with the actual barriers.
//         *
//         * @param board
//         */
//        private void putBarriersInBoard(LabeledPositionsBoard board) {
//
//            int index = 0;
//            final int finalIndex = index;
//
//            //Setting Iterations
//            OnIterationActionHandler onIterationActionHandler = new OnIterationActionHandler() {
//                @Override
//                public void action(int x, int y) {
//                    if (board.isWithinBoard(x, y))
//                        board.setLabel(finalIndex, x, y);
//                }
//            };
//
//            //Barriers
//            for (Barrier barrier : barriers) {
//                if (barrier.getShape() == Constants.SQUARE_SHAPE) {
//                    Iterations.iterateSquareArea(barrier.getPositionX(), barrier.getPositionY(),
//                            barrier.getSize(), onIterationActionHandler);
//                } else if (barrier.getShape() == Constants.CIRCULAR_SHAPE) {
//                    Iterations.iterateCircularArea(barrier.getPositionX(), barrier.getPositionY(),
//                            barrier.getSize(), onIterationActionHandler);
//                } else if (barrier.getShape() == Constants.RECTANGULAR_SHAPE) {
//                    Iterations.iterateRectangularArea(barrier.getPositionX(), barrier.getPositionY(),
//                            barrier.getSizeX(), barrier.getSizeY(), onIterationActionHandler);
//                } else {
//                    if (enablePrompt) System.out.println("Error: Unsupported shape " + barrier.getShape());
//                    return;
//                }
//                index++;
//            }
//
//        }
//
//        private void extendBarriersRestriction(int targetSize, int targetShape, LabeledPositionsBoard board) {
//
//            int index = 0;
//            for (Barrier barrier : barriers) {
//
//                //Setting onIterationActionHandler
//                int finalIndex = index;
//                OnIterationActionHandler onIterationActionHandler;
//                if (targetShape == Constants.SQUARE_SHAPE) {
//
//                    onIterationActionHandler = new OnIterationActionHandler() {
//                        @Override
//                        public void action(int x, int y) {
//                            if (board.isWithinBoard(x, y))
//                                board.setSquarePerimeter(x, y, targetSize, finalIndex);
//                        }
//                    };
//                } else if (targetShape == Constants.CIRCULAR_SHAPE) {
//                    onIterationActionHandler = new OnIterationActionHandler() {
//                        @Override
//                        public void action(int x, int y) {
//                            if (board.isWithinBoard(x, y)) {
//                                //Circular perimeter produce empty points, that is
//                                // why it is needed to draw several perimeters
//                                board.setCircularPerimeter(x, y, targetSize, finalIndex);
//                                for (int i = 1; i < targetSize * 0.3; i++)
//                                    board.setCircularPerimeter(x, y, targetSize - i, finalIndex);
//                            }
//                        }
//                    };
//                } else {
//                    if (enablePrompt) System.out.println("Error: Unsupported shape " + barrier.getShape());
//                    return;
//                }
//
//                //Iteration t
//                if (barrier.getShape() == Constants.SQUARE_SHAPE) {
//                    Iterations.iterateSquarePerimeter(barrier.getPositionX(), barrier.getPositionY(), barrier.getSize(), onIterationActionHandler);
//                } else if (barrier.getShape() == Constants.CIRCULAR_SHAPE) {
//                    Iterations.iterateCircularPerimeter(barrier.getPositionX(), barrier.getPositionY(), barrier.getSize(), onIterationActionHandler);
//                } else if (barrier.getShape() == Constants.RECTANGULAR_SHAPE) {
//                    Iterations.iterateRectangularPerimeter(barrier.getPositionX(), barrier.getPositionY(), barrier.getSizeX(), barrier.getSizeY(),
//                            onIterationActionHandler);
//                } else {
//                    if (enablePrompt) System.out.println("Error: Unsupported shape " + barrier.getShape());
//                    return;
//                }
//
//
//                index++;
//
//            }
//
//        }
//
//        private void addBorderRestriction(int targetSize, LabeledPositionsBoard board) {
//
//            //Upper line
//            for (int y = 0; y < targetSize; y++) {
//                for (int x = 0; x < w; x++) {
//                    board.setLabel(Constants.BORDER, x, y);
//                }
//            }
//
//            //left Border lines
//            int lastY = h - targetSize;
//            for (int x = 0; x < targetSize; x++) {
//                for (int y = targetSize; y < lastY; y++) {
//                    board.setLabel(Constants.BORDER, x, y);
//                }
//            }
//
//            //right Border lines
//            int lastX = w - targetSize;
//            for (int x = lastX; x < w; x++) {
//                for (int y = targetSize; y < lastY; y++) {
//                    board.setLabel(Constants.BORDER, x, y);
//                }
//            }
//
//            //Upper line
//            for (int y = lastY; y < h; y++) {
//                for (int x = 0; x < w; x++) {
//                    board.setLabel(Constants.BORDER, x, y);
//                }
//            }
//
//        }
//
//        private void ensureBoardsArrayListCapacity(int minCapacity, int shape) {
//            if (shape == Constants.SQUARE_SHAPE)
//                while (squareWalkersCollisionBoards.size() <= minCapacity) {
//                    squareWalkersCollisionBoards.add(null);
//                }
//            else if (shape == Constants.CIRCULAR_SHAPE)
//                while (circularWalkersCollisionBoards.size() <= minCapacity) {
//                    circularWalkersCollisionBoards.add(null);
//                }
//        }
//
//        /**
//         * Return true is a walker will collide in a given position
//         *
//         * @return the result.
//         */
//        boolean checkForCollision(int positionX, int positionY, RandomWalker walker) {
//
//            int value;
//            if (walker.getShape() == Constants.SQUARE_SHAPE) {
//                value = squareWalkersCollisionBoards.get(walker.getSize()).
//                        getLabelAt(positionX, positionY);
//                if (value != LabeledPositionsBoard.EMPTY) {
//                    lastCheckedCollisionValue = value;
//                    return true;
//                }
//                return false;
//            } else if (walker.getShape() == Constants.CIRCULAR_SHAPE) {
//                value = circularWalkersCollisionBoards.get(walker.getSize()).
//                        getLabelAt(positionX, positionY);
//                if (value != LabeledPositionsBoard.EMPTY) {
//                    lastCheckedCollisionValue = value;
//                    return true;
//                }
//                return false;
//            } else {
//                if (enablePrompt) System.out.println("Error: Unsupported shape " + walker.getShape());
//                return false;
//            }
//        }
//
//    }
//
//    private static class RunThreadRunnable implements Runnable {
//
//        public volatile boolean running;
//        RandomWalkersWorld context;
//
//        public RunThreadRunnable(RandomWalkersWorld context) {
//            this.context = context;
//        }
//
//        @Override
//        public void run() {
//            while (running) {
//                context.step();
//            }
//        }
//
//        public void terminate() {
//            running = false;
//            context = null;
//        }
//    }
//
//    //endregion CLASSES ------------------------------------------------------------------------------------------------
//
//    //region PUBLIC CLASSES ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//
//    /**
//     * Contain all the constants of the world :)
//     */
//    public static class Constants {
//
//        //Threading
//        static String RUN_THREAD_NAME = "RunThreadRunnable";
//
//        //Random
//        static final Random r = new Random();
//
//        //Simulation
//        static final int DEFAULT_ITERATIONS_PER_FRAME = 50;
//
//        //Shapes
//        public static final int CIRCULAR_SHAPE = 186;
//        public static final int SQUARE_SHAPE = 468;
//        public static final int RECTANGULAR_SHAPE = 399;
//
//        //Values
//        static final float SQUARE_ROOT_OF_TWO = 1.41421F;
//
//        //Probabilities
//        static final int DEFAULT_CHANCE = 1000;
//        static final int DEFAULT_TOTAL_CHANCE = 4999;
//
//        //Board
//        public static final int BORDER = -2;
//
//    }
//
//    public static abstract class OnGetTendency<T> {
//
//        private T[] userData;
//
//        /**
//         * This method will be called by the world in any iteration of a caller tendency iterate method to
//         * get the value to be set as the tendency of the point.
//         *
//         * @param x the current x of the iteration
//         * @param y the current y of the iteration
//         * @return the tendency
//         */
//        public abstract Vector getTendency(int x, int y);
//
//        @SafeVarargs
//        public final void setUserData(T... userData) {
//            this.userData = userData;
//        }
//
//        public T[] getUserData() {
//            return userData;
//        }
//
//    }
//
//    /**
//     * This class help to avoid new objects creation by
//     * caching its relevant primitive data.
//     */
//    public class Cache {
//
//        Cache() {
//
//        }
//
//        //Cache
//        public int walkerXPosition;
//        public int walkerYPosition;
//
//    }
//
//    public final static class WorldAlreadyCreatedException extends Exception {
//
//        @Override
//        public String getMessage() {
//            return "Just a single world can exist!!!";
//        }
//    }
//
//    //endregion PUBLIC CLASSES -----------------------------------------------------------------------------------------

}