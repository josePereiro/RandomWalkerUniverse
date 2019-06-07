package Core.World;

import Core.Walkers.Walker;

public class WalkersBuffer {

    private final int capacity;
    private final Walker[] walkers;
    private int count;

    public WalkersBuffer(int capacity) {
        this.capacity = capacity;
        walkers = new Walker[capacity];
        clear();
    }

    public Walker get(int index) {
        checkIndex(index);
        return walkers[index];
    }

    public Walker getUncheked(int index) {
        return walkers[index];
    }

    public void add(Walker walker) {
        if (isFull()) {
            throw new OutOfMemoryError("Buffer is full");
        }
        walkers[count] = walker;
        count++;
    }

    public void addIfPossible(Walker walker) {
        if (hasSpace()) {
            walkers[count] = walker;
            count++;
        }
    }

    public void clear() {
        count = 0;
    }

    public boolean isFull() {
        return count == capacity;
    }

    public boolean hasSpace() {
        return count < capacity;
    }

    public void set(int index, Walker walker) {
        checkIndex(index);
        walkers[index] = walker;
    }

    public void setUnchecked(int index, Walker walker) {
        walkers[index] = walker;
    }

    private void checkIndex(int index) {
        if (index >= count) {
            throw new IndexOutOfBoundsException("index " + index + " >= " + count);
        }
    }

    public int getCount() {
        return count;
    }

    public int getCapacity() {
        return capacity;
    }
}
