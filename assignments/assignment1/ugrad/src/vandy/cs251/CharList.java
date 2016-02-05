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
    // @@ Please prefix class member variables with 'm'; e.g. mFoo or mBar
    private Node mHead;

    /**
     * The current size of the list.
     */
    // TODO - you fill in here
    private int mSize;

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
        // calls constructor with specified defaultValue
	// @@ This is the *wrong* way to delegate:
        this(size, '\0');
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
        // valid size check
        if (size < 0) {
            throw new IndexOutOfBoundsException("Invalid size");
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
     * Copy constructor; creates a deep copy of the provided CharList.
     *
     * @param s The CharList to be copied.
     */
    public CharList(CharList s) {
        // TODO - you fill in here
        // initial variable assignments w/ defaultValue
        this.mSize = s.mSize;
        this.defaultValue = s.defaultValue;
	    // XX A similar comment as above:
        // temporary node for list traversal to copy
        Node tmp = s.mHead;
        // create initial dummy head
        this.mHead = new Node(tmp.data, null);
        Node cur = this.mHead;
        // loop node attachment until end of list
        while(tmp.next != null) {
            tmp = tmp.next;
            cur.next = new Node(tmp.data, null);
            cur = cur.next;
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
        // create clone with copy ctor
	// @@ This is incorrect:
        CharList copy = new CharList(this);
	    return copy;
    }

    /**
     * @return The current size of the list.
     */
    public int size() {
        // TODO - you fill in here (replace return 0 with right
        // implementation).
    	return this.mSize;
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
        Node end, erase;
	// @@ This is overll far too complicated and has too many special cases.
	// 2@ Using a dummy node will simplify. 
        // valid size check
        if (size < 0) {
            throw new IndexOutOfBoundsException("Invalid size");
        } else {
            // if current size is larger than new size
            end = this.mHead;
            if (size < this.mSize) {
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
                // initially seek, however issues arise with empty
                // and forces the implementation to be complex
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
     * @throws IndexOutOfBoundsException If the requested index is outside the
     * current bounds of the list.
     */
    public char get(int index) {
        // TODO - you fill in here (replace return '\0' with right
        // implementation).
        // seek to get node at index to pull data
	// XX Please just say 'return …'
        return seek(index).data;
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
        // seek to get node at index to set new value
        Node setNode = seek(index);
        setNode.data = value;
    }

    /**
     * Locate and return the @a Node at the @a index location.
     */
    private Node seek(int index) {
        // TODO - you fill in here
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
        // tmp nodes for traversal of both lists
        Node tmp1 = this.mHead.next;
        Node tmp2 = s.mHead.next;
        // empty check
	    // @@ Fewer special cases...
        // traverse until end of either list
        while(tmp1 != null && tmp2 != null || int result == 0) {
            // comparison checks
            result = (tmp1.data = tmp2.data);
            // next node
            tmp1 = tmp1.next;
            tmp2 = tmp2.next;
        }
        return 0;
    }

    /**
     * Throws an exception if the index is out of bound.
     */
    private void rangeCheck(int index) {
        // TODO - you fill in here
        // condition check and throw
        if (index >= mSize || index < 0) {
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
        Node(char value, Node prev) {
            // TODO - you fill in here
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
    }
}
