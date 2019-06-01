package Core.Basics.Collections;

/**
 * And implementation of a circular set... It has two indexes, one for reading and one for writing.
 * Its can be move forward and backward without concerning about getting out of range. If any index
 * become bigger than the dimensions of the array it is set to zero, and if any index become lower than zero y
 * will point to the last position in the array.
 */
public class CircularIntSet {

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