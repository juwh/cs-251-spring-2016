package vandy.cs251;

import java.lang.IndexOutOfBoundsException;

/**
 * Provides a wrapper facade around primitive char lists, allowing
 * for dynamic resizing.
 */
public class CharList 
             implements Comparable<CharList>, 
                        Cloneable {
    /**
     * The head of the list.
     */
    // TODO - you fill in here
    private Node head;

    /**
     * The current size of the list.
     */
    // TODO - you fill in here
    private int size;

    /**
     * Default value for elements in the list.
     */
    // TODO - you fill in here
    private char defaultValue;

    /**
     * Constructs an list of the given size.
     *
     * @param size Non-negative integer size of the desired list.
     */
    public CharList(int size) {
        // TODO - you fill in here.  Initialize the List
        CharList list;
        list = new CharList(size, defaultValue);
        this.head = list.head;
        this.size = list.size;
    }

    /**
     * Constructs an list of the given size, filled with the provided
     * default value.
     *
     * @param size Nonnegative integer size of the desired list.
     * @param defaultValue A default value for the list.
     * @throw IndexOutOfBoundsException If size < 0.
     */
    public CharList(int size, char defaultValue) {
        // TODO - you fill in here
        if (size < 0) {
            throw new IndexOutOfBoundsException("Invalid size");
        } else {
            this.head = null;
            this.size = size;
            if (size > 0) {
                this.head = new Node(defaultValue, null);
                Node tmp = this.head;
                for (int ii = 0; ii < size - 1; ii++) {
                    tmp = new Node(defaultValue, tmp);
                }
            }
        }
    }

    /**
     * Copy constructor; creates a deep copy of the provided CharList.
     *
     * @param s The CharList to be copied.
     */
    public CharList(CharList s) {
        // TODO - you fill in here
        this.size = s.size;
        this.head = null;
        Node tmp = s.head;
        if (tmp != null) {
            this.head = s.head;
            Node cur = this.head;
            while(tmp.next != null) {
                tmp = tmp.next;
                cur.next = new Node(tmp.data, null);
                cur = cur.next;
            }
        }
    }

    /**
     * Creates a deep copy of this CharList.  Implements the
     * Prototype pattern.
     */
    @Override
    public Object clone() {
        // TODO - you fill in here (replace return null with right
        // implementation).
        CharList copy = new CharList(this);
	    return copy;
    }

    /**
     * @return The current size of the list.
     */
    public int size() {
        // TODO - you fill in here (replace return 0 with right
        // implementation).
    	return this.size;
    }

    /**
     * Resizes the list to the requested size.
     *
     * Changes the capacity of this list to hold the requested number of elements.
     * Note the following optimizations/implementation details:
     * <ul>
     *   <li> If the requests size is smaller than the current maximum capacity, new memory
     *   is not allocated.
     *   <li> If the list was constructed with a default value, it is used to populate
     *   uninitialized fields in the list.
     * </ul>
     * @param size Nonnegative requested new size.
     */
    public void resize(int size) {
        // TODO - you fill in here
        if (size < 0) {
            throw new IndexOutOfBoundsException("Invalid size");
        } else {
            int sizeDiff = java.lang.Math.abs(this.size - size);
            this.size = size;

        }
    }

    /**
     * @return the element at the requested index.
     * @param index Nonnegative index of the requested element.
     * @throws IndexOutOfBoundsException If the requested index is outside the
     * current bounds of the list.
     */
    public char get(int index) {
        // TODO - you fill in here (replace return '\0' with right
        // implementation).
        Node getNode = seek(index);
        char output = getNode.data;
        return output;
    }

    /**
     * Sets the element at the requested index with a provided value.
     * @param index Nonnegative index of the requested element.
     * @param value A provided value.
     * @throws IndexOutOfBoundsException If the requested index is outside the
     * current bounds of the list.
     */
    public void set(int index, char value) {
        // TODO - you fill in here
        Node setNode = seek(index);
        setNode.data = value;
    }

    /**
     * Locate and return the @a Node at the @a index location.
     */
    private Node seek(int index) {
        // TODO - you fill in here
        rangeCheck(index);
        Node outNode = this.head;
        for (int ii = 0; ii < index; ii++) {
            outNode = outNode.next;
        }
        return outNode;
    }

    /**
     * Compares this list with another list.
     * <p>
     * This is a requirement of the Comparable interface.  It is used to provide
     * an ordering for CharList elements.
     * @return a negative value if the provided list is "greater than" this list,
     * zero if the lists are identical, and a positive value if the
     * provided list is "less than" this list. These lists should be compred
     * lexicographically.
     */
    @Override
    public int compareTo(CharList s) {
        // TODO - you fill in here (replace return 0 with right
        // implementation).
	    return 0;
    }

    /**
     * Throws an exception if the index is out of bound.
     */
    private void rangeCheck(int index) {
        // TODO - you fill in here
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
    }

    /**
     * A Node in the Linked List.
     */
    private class Node {
        /**
         * Value stored in the Node.
         */
	// TODO - you fill in here
        private char data;

        /**
         * Reference to the next node in the list.
         */
	// TODO - you fill in here
        private Node next;

        /**
         * Default constructor (no op).
         */
        Node() {
        }

        /**
         * Construct a Node from a @a prev Node.
         */
        Node(Node prev) {
            // TODO - you fill in here
            prev.next = this;
            this.data = defaultValue;
            this.next = null;
        }

        /**
         * Construct a Node from a @a value and a @a prev Node.
         */
        Node(char value, Node prev) {
            // TODO - you fill in here
            prev.next = this;
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
            if (this.next != null) {
                this.next.prune();
                this.next = null;
            }
        }
    }
}
