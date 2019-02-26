package Core.Engine.Iterations;

import Core.Engine.Vector.Vector;

/**
 * A class that handle an Iteration Action.
 */
public abstract class OnIterationActionHandler<eT1, eT2, eT3> {

    public boolean break_ = false;
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