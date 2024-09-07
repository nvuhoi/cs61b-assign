package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Iterable<T>, Deque<T>{

    protected T[] items;
    private int size;
    private int nextFirst = 4;
    protected int firstIndex = 5;
    private int nextLast = 5;
    private int lastIndex = 4;

    /** Creates an empty list. */
    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
    }

    private void resetFirstIndex() {
        if (nextFirst != items.length - 1) {
            firstIndex = nextFirst + 1;
        } else {
            firstIndex = 0;
        }
    }

    private void resetLastIndex() {
        if (nextLast != 0) {
            lastIndex = nextLast - 1;
        } else {
            lastIndex = items.length - 1;
        }
    }

    /** Resizes the underlying array to the target capacity. */
    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        int index = firstIndex;
        for (int i = 0; i < size; i++) {
            a[i] = items[index];
            if (index != items.length - 1) {
                index += 1;
            } else {
                index = 0;
            }
        }
        items = a;
        nextFirst = items.length - 1;
        nextLast = size;
        resetFirstIndex();
        resetLastIndex();
    }

    @Override
    /** Inserts X into the back of the list. */
    public void addLast(T item) {
        if (size == items.length) {
            resize(size * 2);
        }

        items[nextLast] = item;
        if (nextLast == items.length - 1) {
            nextLast = 0;
        } else {
            nextLast += 1;
        }
        resetLastIndex();
        size += 1;
    }

    @Override
    public void addFirst(T item) {
        if (size == items.length) {
            resize(size * 2);
        }

        items[nextFirst] = item;
        if (nextFirst == 0) {
            nextFirst = items.length - 1;
        } else {
            nextFirst -= 1;
        }
        resetFirstIndex();
        size += 1;
    }

    public T getFirst() {
        return items[firstIndex];
    }

    public T getLast() {
        return items[lastIndex];
    }
    @Override
    /** Returns the number of items in the list. */
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        int index = firstIndex;
        for (int i = 0; i < size; i++) {
            System.out.print(items[index] + " ");
            if (index == items.length - 1) {
                index = 0;
            } else {
                index += 1;
            }
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T first = items[firstIndex];
        items[firstIndex] = null;
        if (nextFirst != items.length - 1) {
            nextFirst += 1;
        } else {
            nextFirst = 0;
        }
        resetFirstIndex();
        size -= 1;
        ifTooSmall();
        return first;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T last = items[lastIndex];
        items[lastIndex] = null;
        if (nextLast != 0) {
            nextLast -= 1;
        } else {
            nextLast = items.length - 1;
        }
        resetLastIndex();
        size -= 1;
        ifTooSmall();
        return last;
    }

    private void ifTooSmall() {
        if (items.length >= 16 && size < items.length * 0.25) {
            resize(items.length / 2);
        }
    }

    @Override
    public T get(int index){
        int target;
        if (firstIndex + index > items.length - 1){
            target = firstIndex + index - items.length;
        } else {
            target = firstIndex + index;
        }
        return items[target];
    }

    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int wisPos;

        public ArrayDequeIterator() {
            wisPos = firstIndex;
        }

        public boolean hasNext() {
            return wisPos != nextLast;
        }

        public T next() {
            T returnItem = items[wisPos];
            if (wisPos != items.length - 1) {
                wisPos += 1;
            } else {
                wisPos = 0;
            }
            return returnItem;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o instanceof ArrayDeque) {
            ArrayDeque<T> other = (ArrayDeque<T>) o;
            if (other.size == this.size) {

                int p1 = this.firstIndex;
                int p2 = other.firstIndex;

                for (int i = 0; i < this.size; i++) {
                    if (!this.items[p1].equals(other.items[p2])) {
                        return false;
                    }
                    if (p1 != items.length - 1) {
                        p1 += 1;
                    } else {
                        p1 = 0;
                    }
                    if (p2 != items.length - 1) {
                        p2 += 1;
                    } else {
                        p2 = 0;
                    }
                }
                return true;
            }
        }
        return false;
    }
}
