package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> comparator;

    public MaxArrayDeque(Comparator<T> c) {
        this.comparator = c;
    }

    public T max() {
        if (isEmpty()) {
            return null;
        }
        T maxElement = get(0);
        int index = firstIndex;

        for (int i = 0; i < size(); i++) {
            T current = items[index];
            if (comparator.compare(current, maxElement) > 0) {
                maxElement = current;
            }
            index = (index + 1) % items.length;
        }
        return maxElement;
    }

    public T max(Comparator<T> c) {
        if (isEmpty()) {
            return null;
        }
        T maxElement = get(0);
        int index = firstIndex;

        for (int i = 0; i < size(); i++) {
            T current = items[index];
            if (c.compare(current, maxElement) > 0) {
                maxElement = current;
            }
            index = (index + 1) % items.length;
        }
        return maxElement;
    }
}
