package Core.World;

public class WalkersBuffer {

    private final int capacity;
    private final RandomWalker[] randomWalkers;
    private int count;

    public WalkersBuffer(int capacity) {
        this.capacity = capacity;
        randomWalkers = new RandomWalker[capacity];
        clear();
    }

    public RandomWalker get(int index) {
        checkIndex(index);
        return randomWalkers[index];
    }

    public RandomWalker getUncheked(int index) {
        return randomWalkers[index];
    }

    public void add(RandomWalker randomWalker) {
        if (isFull()) {
            throw new OutOfMemoryError("Buffer is full");
        }
        randomWalkers[count] = randomWalker;
        count++;
    }

    public void addIfPossible(RandomWalker randomWalker) {
        if (hasSpace()) {
            randomWalkers[count] = randomWalker;
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

    public void set(int index, RandomWalker randomWalker) {
        checkIndex(index);
        randomWalkers[index] = randomWalker;
    }

    public void setUnchecked(int index, RandomWalker randomWalker) {
        randomWalkers[index] = randomWalker;
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
