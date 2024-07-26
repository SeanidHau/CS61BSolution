/** Project 1A PART.1
 *  Deque implemented by Linked List.
 *  @author seanid
 */
public class LinkedListDeque<T> {
    public class Node {
        private T item;
        private Node pre;
        private Node next;

        public Node(T i, Node preTemp, Node nextTemp) {
            item = i;
            pre = preTemp;
            next = nextTemp;
        }

        public Node(Node preTemp, Node nextTemp) {
            pre = preTemp;
            next = nextTemp;
        }
    }
    private Node sentinel;
    private int size;

    /**
     * Constructs an empty deque.
     */
    public LinkedListDeque() {
        sentinel = new Node(null, null);
        size = 0;
        sentinel.pre = sentinel;
        sentinel.next = sentinel;
    }

    /**
     * Checks if the deque is empty.
     * @return true if the deque is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of items in the deque.
     * @return the number of items in the deque
     */
    public int size() {
        return size;
    }

    /**
     * Adds an item of type T to the front of the deque.
     * @param item the item to add
     */
    public void addFirst(T item) {
        Node newList = new Node(item, sentinel, sentinel.next);
        sentinel.next.pre = newList;
        sentinel.next = newList;
        size++;
    }

    /**
     * Adds an item of type T to the back of the deque.
     * @param item the item to add
     */
    public void addLast(T item) {
        Node newList = new Node(item, sentinel.pre, sentinel);
        sentinel.pre.next = newList;
        sentinel.pre = newList;
        size++;
    }

    /**
     * Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.
     * @return the item at the front of the deque, or null if the deque is empty
     */
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T item = sentinel.next.item;
        sentinel.next.next.pre = sentinel;
        sentinel.next = sentinel.next.next;
        size--;
        return item;
    }

    /**
     * Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.
     * @return the item at the back of the deque, or null if the deque is empty
     */
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T item = sentinel.pre.item;
        sentinel.pre.pre.next = sentinel;
        sentinel.pre = sentinel.pre.pre;
        size--;
        return item;
    }

    /**
     * Gets the item at the given index.
     * If no such item exists, returns null.
     * @param index the index to retrieve
     * @return the item at the given index, or null if it does not exist
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        Node ptr = sentinel;
        for (int i = 0; i <= index; i++) {
            ptr = ptr.next;
        }
        return ptr.item;
    }

    /**
     * Helper method for the recursive get method.
     * @param node the current node
     * @param index the index to retrieve
     * @return the item at the given index
     */
    public T getRecursiveHelp(Node node, int index) {
        if (index == 0) {
            return node.item;
        }
        return getRecursiveHelp(node.next, index - 1);
    }

    /**
     * Gets the item at the given index using recursion.
     * If no such item exists, returns null.
     * @param index the index to retrieve
     * @return the item at the given index, or null if it does not exist
     */
    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        return getRecursiveHelp(sentinel.next, index);
    }

    /**
     * Prints the items in the deque from first to last, separated by a space.
     */
    public void printDeque() {
        Node ptr = sentinel.next;
        while (ptr != sentinel) {
            System.out.print(ptr.item + " ");
            ptr = ptr.next;
        }
    }
}