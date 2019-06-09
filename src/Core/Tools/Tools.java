package Core.Tools;

import java.util.ArrayList;
import java.util.Arrays;

public class Tools {

    public static void main(String[] args) {
        Integer[] col = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8};
        System.out.println(Arrays.toString(col));
        CollectionsOps.moveItemsForward(col.length, col);
        System.out.println(Arrays.toString(col));
    }

    public static class CollectionsOps {

        public static float mean(float[] floats) {
            float s = 0;
            for (int i = 0; i < floats.length; i++) {
                s += floats[i];
            }
            return s / floats.length;
        }

        public static <T> void moveItemsForward(int from, T[] col) {
            for (int i = col.length - 1; i > from; i--) {
                col[i] = col[i - 1];
            }
        }

    }

    /**
     * And implementation of a circular set... It has two indexes, one for reading and one for writing.
     * Its can be move forward and backward without concerning about getting out of range. If any index
     * become bigger than the dimensions of the array it is set to zero, and if any index become lower than zero y
     * will point to the last position in the array.
     */
    public static class CircularFloatSet {

        private float[] data;
        private int readIndex;
        private int writeIndex;
        public final int length;

        public CircularFloatSet(float[] data) {
            this.data = data;
            this.length = data.length;
            readIndex = 0;
            writeIndex = 0;
        }

        public CircularFloatSet(int length) {
            data = new float[length];
            this.length = length;
            readIndex = 0;
            writeIndex = 0;
        }

        /**
         * Read the position previous to the current readIndex.
         * This method do not change the readIndex value.
         *
         * @return the previous item.
         */
        public float readPrevious() {
            return data[getPrevious(readIndex, length)];
        }

        /**
         * Read the position pointed by the readIndex.
         * This method do not change the readIndex value.
         *
         * @return teh current item.
         */
        public float read() {
            return data[readIndex];
        }

        /**
         * Read the position next to the current readIndex.
         * This method do not change the readIndex value.
         *
         * @return the next item.
         */
        public float readNext() {
            return data[getNext(readIndex, length)];
        }

        /**
         * Change the value of the readIndex so it points
         * to the next position.
         */
        public void moveReadIndexToNext() {
            readIndex = getNext(readIndex, length);
        }

        /**
         * Change the value of the readIndex so it points
         * to the previous position.
         */
        public void moveReadIndexToPrevious() {
            readIndex = getPrevious(readIndex, length);
        }

        /**
         * Change the value of readIndex. It will only
         * take affect if the given new index is inside
         * the boundaries of the array.
         *
         * @param index
         */
        public void moveReadIndexTo(int index) {
            if (index >= 0 && index < length) {
                readIndex = index;
            }
        }

        /**
         * Check if the previous item from readIndex is not null.
         *
         * @return
         */
        public boolean hasPrevious() {
            return data[getPrevious(readIndex, length)] != Float.NaN;
        }

        /**
         * Check if the next item from the readIndex is not null.
         *
         * @return
         */
        public boolean hasNext() {
            return data[getNext(readIndex, length)] != Float.NaN;
        }

        /**
         * Write item into the previous position from
         * the writeIndex.
         * This method do not change the value of the writeIndex.
         *
         * @param item the item to be written.
         */
        public void writePrevious(float item) {
            data[getPrevious(writeIndex, length)] = item;
        }

        /**
         * Write item into the current position of
         * the writeIndex.
         * This method do not change the value of the writeIndex.
         *
         * @param item the item to be written.
         */
        public void write(float item) {
            data[writeIndex] = item;
        }

        /**
         * Write item into the next position from
         * the writeIndex.
         * This method do not change the value of the writeIndex.
         *
         * @param item the item to be written.
         */
        public void writeNext(float item) {
            data[getNext(writeIndex, length)] = item;
        }

        /**
         * Change the value of the writeIndex so it points
         * to the next position.
         */
        public void moveWriteIndexToNext() {
            writeIndex = getNext(writeIndex, length);
        }

        /**
         * Change the value of the writeIndex so it points
         * to the previous position.
         */
        public void moveWriteIndexToPrevious() {
            writeIndex = getPrevious(writeIndex, length);
        }

        /**
         * Change the value of writeIndex. It will only
         * take affect if the given new index is inside
         * the boundaries of the array.
         *
         * @param index the new index
         */
        public void moveWriteIndexTo(int index) {
            if (index >= 0 && index < length) {
                writeIndex = index;
            }
        }

        /**
         * Make writeIndex point to the same position that readIndex.
         */
        public void moveWriteIndexToReadIndex() {
            writeIndex = readIndex;
        }

        /**
         * Make readIndex point to the same position that writeIndex.
         */
        public void moveReadIndexToWriteIndex() {
            readIndex = writeIndex;
        }

        public void clear() {
            for (int i = 0; i < data.length; i++) {
                data[i] = Float.NaN;
            }
        }

        public int getReadIndex() {
            return readIndex;
        }

        public int getWriteIndex() {
            return writeIndex;
        }

        public int getLength() {
            return length;
        }

        /**
         * Return the array instance of this CircularSet. It is actually
         * the inner array object used to store the data. So any change
         * in the data will be reflected in the CircularSet too.
         *
         * @return
         */
        public float[] getArray() {
            return data;
        }

        private static int getNext(int index, int setLength) {
            index++;
            if (index < setLength) {
                return index;
            } else return 0;
        }

        private static int getPrevious(int index, int setLength) {
            index--;
            if (index >= 0) {
                return index;
            } else return setLength - 1;
        }
    }

    /**
     * And implementation of a circular set... It has two indexes, one for reading and one for writing.
     * Its can be move forward and backward without concerning about getting out of range. If any index
     * become bigger than the dimensions of the array it is set to zero, and if any index become lower than zero y
     * will point to the last position in the array.
     */
    public static class CircularIntSet {

        private int[] data;
        private int readIndex;
        private int writeIndex;
        public final int length;

        public CircularIntSet(int[] data) {
            this.length = data.length;
            this.data = data;
            readIndex = 0;
            writeIndex = 0;
        }

        /**
         * Read the position previous to the current readIndex.
         * This method do not change the readIndex value.
         *
         * @return the previous value.
         */
        public int readPrevious() {
            return data[getPrevious(readIndex, length)];
        }

        /**
         * Read the position pointed by the readIndex.
         * This method do not change the readIndex value.
         *
         * @return teh current value.
         */
        public int read() {
            return data[readIndex];
        }

        /**
         * Read the value stored at the current readIndex,
         * and move it to the next index
         *
         * @return the current value.
         */
        public int readAndMoveForward() {
            int v = data[readIndex];
            readIndex = getNext(readIndex, length);
            return v;
        }

        /**
         * Read the position next to the current readIndex.
         * This method do not change the readIndex value.
         *
         * @return the next value.
         */
        public int readNext() {
            return data[getNext(readIndex, length)];
        }

        /**
         * Change the value of the readIndex so it points
         * to the next position.
         */
        public void moveReadIndexToNext() {
            readIndex = getNext(readIndex, length);
        }

        /**
         * Change the value of the readIndex so it points
         * to the previous position.
         */
        public void moveReadIndexToPrevious() {
            readIndex = getPrevious(readIndex, length);
        }

        /**
         * Change the value of readIndex. It will only
         * take affect if the given new index is inside
         * the boundaries of the array.
         *
         * @param index
         */
        public void moveReadIndexTo(int index) {
            if (index >= 0 && index < length) {
                readIndex = index;
            }
        }

        /**
         * Check if the previous value from readIndex is not null.
         *
         * @return
         */
        public boolean isPreviousNonZero() {
            return data[getPrevious(readIndex, length)] != 0;
        }

        /**
         * Check if the next value from the readIndex is not null.
         *
         * @return
         */
        public boolean isNextNonZero() {
            return data[getNext(readIndex, length)] != 0;
        }

        /**
         * Write value into the previous position from
         * the writeIndex.
         * This method do not change the value of the writeIndex.
         *
         * @param value the value to be written.
         */
        public void writePrevious(int value) {
            data[getPrevious(writeIndex, length)] = value;
        }

        /**
         * Write value into the current position of
         * the writeIndex.
         * This method do not change the value of the writeIndex.
         *
         * @param value the value to be written.
         */
        public void write(int value) {
            data[writeIndex] = value;
        }

        /**
         * Read the value stored at the current readIndex,
         * and move it to the next index
         *
         * @return the current value.
         */
        public void writeAndMoveForward(int value) {
            data[writeIndex] = value;
            writeIndex = getNext(writeIndex, length);
        }

        /**
         * Write value into the next position from
         * the writeIndex.
         * This method do not change the value of the writeIndex.
         *
         * @param value the value to be written.
         */
        public void writeNext(int value) {
            data[getNext(writeIndex, length)] = value;
        }

        /**
         * Change the value of the writeIndex so it points
         * to the next position.
         */
        public void moveWriteIndexToNext() {
            writeIndex = getNext(writeIndex, length);
        }

        /**
         * Change the value of the writeIndex so it points
         * to the previous position.
         */
        public void moveWriteIndexToPrevious() {
            writeIndex = getPrevious(writeIndex, length);
        }

        /**
         * Change the value of writeIndex. It will only
         * take affect if the given new index is inside
         * the boundaries of the array.
         *
         * @param index the new index
         */
        public void moveWriteIndexTo(int index) {
            if (index >= 0 && index < length) {
                writeIndex = index;
            }
        }

        /**
         * Make writeIndex point to the same position that readIndex.
         */
        public void moveWriteIndexToReadIndex() {
            writeIndex = readIndex;
        }

        /**
         * Make readIndex point to the same position that writeIndex.
         */
        public void moveReadIndexToWriteIndex() {
            readIndex = writeIndex;
        }

        public void clear() {
            for (int i = 0; i < data.length; i++) {
                data[i] = 0;
            }
        }

        public int getReadIndex() {
            return readIndex;
        }

        public int getWriteIndex() {
            return writeIndex;
        }

        public int getLength() {
            return length;
        }

        /**
         * Return the array instance of this CircularSet. It is actually
         * the inner array object used to store the data. So any change
         * in the data will be reflected in the CircularSet too.
         *
         * @return
         */
        public int[] getArray() {
            return data;
        }

        private static int getNext(int index, int setLength) {
            index++;
            if (index < setLength) {
                return index;
            } else return 0;
        }

        private static int getPrevious(int index, int setLength) {
            index--;
            if (index >= 0) {
                return index;
            } else return setLength - 1;
        }
    }

    public static float abs(float n) {
        return n > 0 ? n : -n;
    }

    /**
     * If template is zero, the target wont be modified.
     *
     * @param tp template
     * @param tg target
     * @return the target with the same sign of the template.
     */
    public static int toSameSign(int tp, int tg) {
        if (tp == 0) return tg;
        if (tp > 0) {
            if (tg > 0) {
                return tg;
            } else return -tg;
        } else {
            if (tg < 0) {
                return tg;
            } else return -tg;
        }
    }

    public static int[] multiples(int n) {
        ArrayList<Integer> temp = new ArrayList<>();
        temp.add(1);
        for (int i = 2; i < n / 2; i++) {
            if (n % i == 0) {
                temp.add(i);
            }
        }
        temp.add(n);
        int[] multiples = new int[temp.size()];
        for (int i = 0; i < temp.size(); i++) {
            multiples[i] = temp.get(i);
        }
        return multiples;
    }


}
