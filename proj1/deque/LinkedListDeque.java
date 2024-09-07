package deque;

import java.util.Iterator;
public class LinkedListDeque<T> implements Iterable<T>, Deque<T> {

    private class IntNode {
        private T item;
        private IntNode next;
        private IntNode prev;
        IntNode(T i, IntNode n) {
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

    @Override
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

    @Override
    /** Adds x to the end of the list. */
    public void addLast(T x) {
        last.next = new IntNode(x, null);
        last.next.prev = last;
        last = last.next;
        sentinel.prev = last;
        last.next = sentinel;
        size += 1;
    }

    @Override
    /** Returns the number of items in the deque. */
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        IntNode p = sentinel.next;
        for (int i = 0; i < size; i++) {
            System.out.print(p.item + " ");
        }
        System.out.println();
    }

    @Override
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

    @Override
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

    @Override
    public T get(int index) {
        IntNode p = sentinel.next;
        for (int i = 0; i < index; i++) {
            p = p.next;
        }
        return p.item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !(o instanceof Deque)) {
            return false;
        }
        Deque<?> other = (Deque<?>) o;
        if (other.size() != this.size()) {
            return false;
        }
        for (int i = 0; i < this.size(); i++) {
            if (!this.get(i).equals(other.get(i))) {
                return false;
            }
        }
        return true;
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

    public Iterator<T> iterator() {
        return new LinkListDequeIterator();
    }

    private class LinkListDequeIterator implements Iterator<T> {
        private IntNode wisPos;

        LinkListDequeIterator() {
            wisPos = sentinel.next;
        }

        public boolean hasNext() {
            return wisPos != sentinel;
        }

        public T next() {
            T returnItem = wisPos.item;
            wisPos = wisPos.next;
            return returnItem;
        }
    }
}
