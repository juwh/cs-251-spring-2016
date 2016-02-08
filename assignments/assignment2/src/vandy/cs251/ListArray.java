package vandy.cs251;

import java.lang.ArrayIndexOutOfBoundsException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Provides a generic dynamically-(re)sized array abstraction.
 */
public class ListArray<T extends Comparable<T>>
             implements Comparable<ListArray<T>>,
                        Iterable<T> {
    /**
     * The underlying list of type T.
     */
    // TODO - you fill in here.
    private Node mHead;

    /**
     * The current size of the array.
     */
    // TODO - you fill in here.
    private int mSize;

    /**
     * Default value for elements in the array.
     */
    // TODO - you fill in here.
    // @@ Please prefix class member variables with 'm'; e.g. mFoo or mBar
    private T defaultValue;

    /**
     * Constructs an array of the given size.
     * @param size Nonnegative integer size of the desired array.
     * @throws NegativeArraySizeException if the specified size is
     *         negative.
     */
    @SuppressWarnings("unchecked")
    public ListArray(int size) throws NegativeArraySizeException {
        // TODO - you fill in here.
        this(size, null);
    }

    /**
     * Constructs an array of the given size, filled with the provided
     * default value.
     * @param size Nonnegative integer size of the desired array.
     * @param defaultValue A default value for the array.
     * @throws NegativeArraySizeException if the specified size is
     *         negative.
     */
    public ListArray(int size,
                     T defaultValue) throws NegativeArraySizeException {
        // TODO - you fill in here.
        // valid size check
        if (size < 0) {
            throw new NegativeArraySizeException("Invalid negative size");
        } else {
            // XX Using a dummy node would make all of this MUCH simpler:
            this.mSize = size;
            this.defaultValue = defaultValue;
            // create dummy node
            this.mHead = new Node(defaultValue, null);
            Node tmp = this.mHead;
            // loop node attachment to size value
            for (int ii = 0; ii < size; ii++) {
                tmp = new Node(defaultValue, tmp);
            }
        }
    }

    /**
     * Copy constructor; creates a deep copy of the provided array.
     * @param s The array to be copied.
     */
    public ListArray(ListArray<T> s) {
        // TODO - you fill in here.
        this.mSize = s.mSize;
        this.defaultValue = s.defaultValue;
        // XX A similar comment as above:
        // temporary node for list traversal to copy
        Node tmp = s.mHead;
        // create initial dummy head
        this.mHead = new Node(tmp.data, null);
        Node cur = this.mHead;
	// @@ Could you use a dummy node?
        // loop node attachment until end of list
        while(tmp.next != null) {
            tmp = tmp.next;
	    // @@ You sure about this?
            cur.next = new Node(tmp.data, null);
            cur = cur.next;
        }
    }

    /**
     * @return The current size of the array.
     */
    public int size() {
        // TODO - you fill in here (replace 0 with proper return
        // value).
        return this.mSize;
    }

    /**
     * Resizes the array to the requested size.
     *
     * Changes the size of this ListArray to hold the requested number of elements.
     * @param size Nonnegative requested new size.
     */
    public void resize(int size) {
        // TODO - you fill in here.
        Node end, erase;
        // XX This is overll far too complicated and has too many special cases.
        // 2@ Using a dummy node will simplify.
        // valid size check
        if (size < 0) {
            throw new ArrayIndexOutOfBoundsException("Invalid size");
	    // @@ You don't need "else"
        } else {
            // if current size is larger than new size
            end = this.mHead;
            if (size < this.mSize) {
		// @@ Seek?
                for(int jj = 0; jj < size; jj++) {
                    end = end.next;
                }
                erase = end.next;
                erase.prune();
                end.next = null;
            }
            // if current size is smaller than new size
            if (size > this.mSize) {
                // finds node at end of list
                // initially used seek, however issues arise with empty
                // and forces the implementation to be complex
		// @@ Seek?
                for(int kk = 0; kk < mSize; kk++) {
                    end = end.next;
                }
                // attach nodes to new size loop
                int addNum = size - this.mSize;
                for(int ii = 0; ii < addNum; ii++) {
                    end.next = new Node(this.defaultValue, end);
                    end = end.next;
                }
            }
            this.mSize = size;
        }
    }

    /**
     * @return the element at the requested index.
     * @param index Nonnegative index of the requested element.
     * @throws ArrayIndexOutOfBoundsException If the requested index is outside the
     * current bounds of the array.
     */
    public T get(int index) {
        // TODO - you fill in here (replace null with proper return
        // value).
        // seek to get node at index to pull data
        // XX Please just say 'return …'
        return seek(index).data;
    }

    /**
     * Sets the element at the requested index with a provided value.
     * @param index Nonnegative index of the requested element.
     * @param value A provided value.
     * @throws ArrayIndexOutOfBoundsException If the requested index is outside the
     * current bounds of the array.
     */
    public void set(int index, T value) {
        // TODO - you fill in here.
	// @@ One line:
        Node setNode = seek(index);
        setNode.data = value;
    }

    private Node seek(int index) {
        // TODO - you fill in here.
        rangeCheck(index);
        // begins at first node
        Node outNode = this.mHead.next;
        // list traversal to node
        for (int ii = 0; ii < index; ii++) {
            outNode = outNode.next;
        }
        return outNode;
    }

    /**
     * Removes the element at the specified position in this ListArray.
     * Shifts any subsequent elements to the left (subtracts one from their
     * indices).  Returns the element that was removed from the ListArray.
     *
     * @param index the index of the element to remove
     * @return element that was removed
     * @throws ArrayIndexOutOfBoundsException if the index is out of range.
     */
    public T remove(int index) {
        // TODO - you fill in here (replace null with proper return

        // value).
        rangeCheck(index);
        T output = null;
        // iterator creation
        Iterator<T> iter = this.iterator();
        iter.next();
        Node tmp = this.mHead;
        Node cur = this.mHead.next;
        // traverse list with iterator
        for (int ii = 0; ii < index; ii++) {
            output = iter.next();
        }
        // get data and remove
        iter.remove();
        return output;
    }

    /**
     * Compares this array with another array.
     * <p>
     * This is a requirement of the Comparable interface.  It is used to provide
     * an ordering for ListArray elements.
     * @return a negative value if the provided array is "greater than" this array,
     * zero if the arrays are identical, and a positive value if the
     * provided array is "less than" this array.
     */
    @Override
    public int compareTo(ListArray<T> s) {
        // TODO - you fill in here (replace 0 with proper return
        // value).
        // empty checks
        if (this.mSize != 0 && s.mSize != 0) {
            // tmp nodes for traversal of both lists
	    // @@ could you use iterators?
            Node tmp1 = this.mHead.next;
            Node tmp2 = s.mHead.next;
            // XX Fewer special cases...
            // traverse until end of either list
            for (int ii = 0; ii < Math.min(this.mSize, s.mSize); ii++) {
                // comparison checks
                int result = tmp1.data.compareTo(tmp2.data);
                if (result != 0) {
                    return result;
                }
                tmp1 = tmp1.next;
                tmp2 = tmp2.next;
            }
        }
        return this.mSize - s.mSize;
    }

    /** 
     * Throws an exception if the index is out of bound. 
     */
    private void rangeCheck(int index) {
        // TODO - you fill in here.
        if (index >= mSize || index < 0) {
            throw new ArrayIndexOutOfBoundsException("Index out of bounds");
        }
    }

    /**
     * Factory method that returns an Iterator.
     */
    public Iterator<T> iterator() {
        // TODO - you fill in here (replace null with proper return value).
        return new ListIterator();
    }

    private class Node implements Iterable<Node> {
        // TODO: Fill in any fields you require.
        /**
         * Value stored in the Node.
         */
        private T data;

        /**
         * Reference to the next node in the list.
         */
        private Node next;


        /**
         * Default constructor (no op).
         */
        Node() {
            // TODO - you fill in here.
        }

        /**
         * Construct a Node from a @a prev Node.
         */
        Node(Node prev) {
            // TODO - you fill in here.
            // empty check for previous node to attach to
            if (prev != null) {
                prev.next = this;
            }
            this.data = defaultValue;
            this.next = null;

        }

        /**
         * Construct a Node from a @a value and a @a prev Node.
         */
        Node(T value, Node prev) {
            // TODO - you fill in here.
            // empty check for previous node to attach to
            if (prev != null) {
                prev.next = this;
            }
            this.data = value;
            this.next = null;

        }

        /**
         * Ensure all subsequent nodes are properly deallocated.
         */
        void prune() {
            // TODO - you fill in here
            // Leaving the list fully linked could *potentially* cause
            // a pathological performance issue for the garbage
            // collector.
            // recursive method to end of list then node disconnection
            Node tmp = this.next;
            Node cur = this;
            while (cur.next != null) {
                // XX What if the list is *really* long?
                cur.next = null;
                cur = tmp;
                tmp = tmp.next;
            }

        }

        @Override
        public Iterator<Node> iterator() {
            // TODO - you fill in here.
            return new NodeIterator();
        }
    }

    private class NodeIterator implements Iterator<Node> {
        // TODO: Fill in any fields you require.
        /**
         * The current node of the array.
         */
        private Node curNode = mHead;

        /**
         * The previous node of the array.
         */
        private Node prevNode = mHead;

        /**
         * Keeps track of whether next has been called
         */
        private boolean nCalled = false;

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return (this.curNode.next != null);
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public Node next() {
            if (this.hasNext()) {
                // moves nodes right one
                this.prevNode = this.curNode;
                this.curNode = this.curNode.next;
                // ticks next call
                nCalled = true;
                return this.curNode;
            }
            throw new NoSuchElementException("The next element does not exist");
        }

        /**
         * Removes from the underlying collection the last element returned
         * by this iterator (optional operation).  This method can be called
         * only once per call to {@link #next}.  The behavior of an iterator
         * is unspecified if the underlying collection is modified while the
         * iteration is in progress in any way other than by calling this
         * method.
         *
         * @throws UnsupportedOperationException if the {@code remove}
         *                                       operation is not supported by this iterator
         * @throws IllegalStateException         if the {@code next} method has not
         *                                       yet been called, or the {@code remove} method has already
         *                                       been called after the last call to the {@code next}
         *                                       method
         * @implSpec The default implementation throws an instance of
         * {@link UnsupportedOperationException} and performs no other action.
         */
        @Override
        public void remove() {
            if (nCalled) {
                // relink without deleted node
                this.prevNode.next = curNode.next;
                // requirement of next for next remove causes need for curNode to be moved back
                this.curNode = prevNode;
                mSize--;
                // next call tick
                nCalled = false;
            } else {
                throw new IllegalStateException("next() has not been called since the last remove()");
            }
        }
    }

    /**
     * @brief This class implements an iterator for the list.
     */
    private class ListIterator implements Iterator<T> {
        // TODO: Fill in any fields you require.
        private NodeIterator nIter = new NodeIterator();

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public T next() {
            return nIter.next().data;
        }

        /**
         * Removes from the underlying collection the last element returned
         * by this iterator (optional operation).  This method can be called
         * only once per call to {@link #next}.  The behavior of an iterator
         * is unspecified if the underlying collection is modified while the
         * iteration is in progress in any way other than by calling this
         * method.
         *
         * @throws UnsupportedOperationException if the {@code remove}
         *                                       operation is not supported by this iterator
         * @throws IllegalStateException         if the {@code next} method has not
         *                                       yet been called, or the {@code remove} method has already
         *                                       been called after the last call to the {@code next}
         *                                       method
         * @implSpec The default implementation throws an instance of
         * {@link UnsupportedOperationException} and performs no other action.
         */
        @Override
        public void remove() {
            nIter.remove();
        }

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return nIter.hasNext();
        }
    }
}
