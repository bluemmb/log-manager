package org.me.rules_evaluator.DataObjects;

public class FixedSizeStack {

    private final String[] array;
    private final int maxSize;
    private int index = 0;
    private int size = 0;

    public FixedSizeStack(int maxSize) {
        this.maxSize = maxSize;
        this.array = new String[maxSize];
    }

    public int getMaxSize() {
        return maxSize;
    }

    public synchronized int getSize() {
        return size;
    }

    public synchronized void push(String element) {
        array[index] = element;
        moveIndex();
        addToSize();
    }

    public synchronized String[] read(int maxCount) {
        maxCount = Math.min(maxCount, size);
        String[] result = new String[maxCount];
        for ( int i=0, pos=index ; i<maxCount ; i++ ) {
            pos = (pos == 0) ? maxSize-1 : pos-1;
            result[i] = array[pos];
        }
        return result;
    }

    private void moveIndex() {
        index = (index+1) % maxSize;
    }

    private void addToSize() {
        size = Math.min(size + 1, maxSize);
    }
}
