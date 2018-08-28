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
        private int size;

        public RandomWalker(int positionX, int positionY, int size, int shape) {

            this.positionX = positionX;
            this.positionY = positionY;
            this.size = size;
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


    }