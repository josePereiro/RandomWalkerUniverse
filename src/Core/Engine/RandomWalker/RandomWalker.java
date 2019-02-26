package Core.Engine.RandomWalker;

import Core.Engine.Shape.Shape;

/**
 * This class represent the RandomWalkers that will exist
 * in the world. It do not have any methods beside the
 * setters and the getters of the characteristics of
 * the RandomWalker. So it is just a data representation
 * of a Walker. The world do all the job!!!
 */
public class RandomWalker {

    //Position
    public int positionX;
    public int positionY;

    //Shape
    public final Shape shape;

    public RandomWalker(int positionX, int positionY, Shape shape) {

        this.positionX = positionX;
        this.positionY = positionY;
        this.shape = shape;
    }

}