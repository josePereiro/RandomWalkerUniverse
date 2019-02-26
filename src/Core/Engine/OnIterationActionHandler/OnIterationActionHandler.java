package Core.Engine.OnIterationActionHandler;

import Core.Engine.Vector.Vector;

import java.util.ArrayList;

/**
 * A class that handle an Iteration Action.
 */
public abstract class OnIterationActionHandler<eT1, eT2, eT3> {

    public static void main(String... args) {

        OnIterationActionHandler onOnIterationActionHandler = new OnIterationActionHandler<Integer, Vector, Void>() {

            int counter = 0;

            @Override
            public void action(int x, int y) {
                setExtraOne(counter);
                setExtraTwo(new Vector(x, y));
                System.out.println(getExtraOne());
                System.out.println(getExtraTwo());
                counter++;
            }
        };

        Iterations.iterateLine(0, 0, 0, 10, onOnIterationActionHandler);
    }

    public boolean iterate = true;
    private eT1 extraOne;
    private eT2 extraTwo;
    private eT3 extraThree;

    public abstract void action(int x, int y);

    public eT1 getExtraOne() {
        return extraOne;
    }

    public void setExtraOne(eT1 extraOne) {
        this.extraOne = extraOne;
    }

    public eT2 getExtraTwo() {
        return extraTwo;
    }

    public void setExtraTwo(eT2 extraTwo) {
        this.extraTwo = extraTwo;
    }

    public eT3 getExtraThree() {
        return extraThree;
    }

    public void setExtraThree(eT3 extraThree) {
        this.extraThree = extraThree;
    }
}