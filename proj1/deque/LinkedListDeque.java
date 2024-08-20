package deque;
public class LinkedListDeque<T> {

    private class IntNode {
        public T item;
        public IntNode next;
        public IntNode prev;
        public IntNode(T i, IntNode n) {
            item = i;
            next = n;
        }
    }

    /* The first item (if it exists) is at sentinel.next. */
    private IntNode sentinel;
    private IntNode last;
    private int size;

    /** Creates an empty timingtest.SLList. */
    public LinkedListDeque() {
        sentinel = new IntNode(null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        last = sentinel;
        size = 0;
    }

    public LinkedListDeque(T item) {
        sentinel = new IntNode(null, null);
        sentinel.next = new IntNode(item, null);
        sentinel.next.prev = sentinel;
        sentinel.next.next = sentinel;
        sentinel.prev = sentinel.next;
        last = sentinel.next;
        size = 1;
    }

    /** Adds x to the front of the list. */
    public void addFirst(T item) {
        sentinel.next = new IntNode(item, sentinel.next);
        if (size > 0) {
            sentinel.next.next.prev = sentinel.next;
            sentinel.next.prev = sentinel;
        } else {
            sentinel.prev = sentinel.next;
            sentinel.next.next = sentinel;
            sentinel.next.prev = sentinel;
            last = sentinel.next;
        }
        size = size + 1;
    }

    /** Returns the first item in the list. */
    public T getFirst() {
        return sentinel.next.item;
    }

    /** Adds x to the end of the list. */
    public void addLast(T x) {
        last.next = new IntNode(x, null);
        last.next.prev = last;
        last = last.next;
        sentinel.prev = last;
        last.next = sentinel;
        size += 1;
    }

    /** returns last item in the list. */
    public T getLast() {
        return last.item;
    }

    /** Returns true if deque is empty, false otherwise. */
    public boolean isEmpty() {
        return (size == 0);
    }

    /** Returns the number of items in the deque. */
    public int size() {
        return size;
    }

    public void printDeque() {
        IntNode p = sentinel.next;
        for (int i = 0; i < size; i++) {
            System.out.print(p.item + " ");
        }
        System.out.println();
    }

    /** Removes and returns the item at the front of the deque. If no such item exists, returns null. */
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T first = sentinel.next.item;
        if (size == 1) {
            sentinel.next = sentinel;
            sentinel.prev = sentinel;
            last = last.prev;
        } else {
            sentinel.next.next.prev = sentinel;
            sentinel.next = sentinel.next.next;
        }
        size -= 1;
        return first;
    }

    /** Removes and returns the item at the back of the deque. If no such item exists, returns null. */
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T la = last.item;
        last.prev.next = sentinel;
        sentinel.prev = last.prev;
        last = last.prev;
        size -= 1;
        return la;
    }

    /** Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth. If no such item exists, returns null. */
    public T get(int index) {
        IntNode p = sentinel.next;
        for (int i = 0; i < index; i++) {
            p = p.next;
        }
        return p.item;
    }

    /** Returns whether or not the parameter o is equal to the Deque. */
    @Override
    public boolean equals(Object o) {
        if (o instanceof LinkedListDeque) {
            LinkedListDeque<?> other = (LinkedListDeque<?>) o;
            if (other.size == this.size) {
                LinkedListDeque<T> otherDeque = (LinkedListDeque<T>) other;

                IntNode p1 = this.sentinel.next;
                IntNode p2 = otherDeque.sentinel.next;

                for (int i = 0; i < this.size; i++) {
                    if (!p1.item.equals(p2.item)) {
                        return false;
                    }
                    p1 = p1.next;
                    p2 = p2.next;
                }
                return true;
            }
        }
        return false;
    }

    /**  Same as get, but uses recursion. */
    public T getRecursive(int index) {
        IntNode p = sentinel.next;
        return getRecur(index, p);
    }

    private T getRecur(int index, IntNode p) {
        if (index == 0) {
            return p.item;
        } else {
            return getRecur(index - 1, p.next);
        }
    }
}
