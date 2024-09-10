/** Project 1A PART.2
 *  Deque implemented by array.
 *  @author seanid
 */
public class ArrayDeque<T> implements Deque<T>{
    private T[] array; // Internal array to store the elements
    private int size; // Number of elements in the deque
    private int length; // Current capacity of the internal array
    private int front; // Index of the front element
    private int back; // Index of the back element

    /**
     * Constructs an empty deque with an initial capacity of 8.
     */
    public ArrayDeque() {
        array = (T[]) new Object[8];
        size = 0;
        length = 8;
        front = 4;
        back = 4;
    }

    /**
     * Checks if the deque is empty.
     *
     * @return true if the deque is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of elements in the deque.
     *
     * @return the number of elements in the deque
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Moves the front index backward by one position, wrapping around if necessary.
     *
     * @param index the current front index
     * @param module the length of the array
     * @return the new front index
     */
    public int frontMove(int index, int module) {
        return (index - 1 + module) % module;
    }

    /**
     * Moves the back index forward by one position, wrapping around if necessary.
     *
     * @param index the current back index
     * @param module the length of the array
     * @return the new back index
     */
    public int backMove(int index, int module) {
        return (index + 1 + module) % module;
    }

    /**
     * Expands the capacity of the internal array by doubling its current length.
     */
    public void expand() {
        T[] newArray = (T[]) new Object[length * 2];
        int ptr1 = front;
        int ptr2 = length;
        while (ptr1 != back) {
            newArray[ptr2] = array[ptr1];
            ptr1 = backMove(ptr1, length);
            ptr2 = backMove(ptr2, length * 2);
        }
        front = length;
        back = ptr2;
        array = newArray;
        length *= 2;
    }

    /**
     * Shrinks the capacity of the internal array to half its current length.
     */
    public void shrink() {
        T[] newArray = (T[]) new Object[length / 2];
        int ptr1 = front;
        int ptr2 = length / 4;
        while (ptr1 != back) {
            newArray[ptr2] = array[ptr1];
            ptr1 = backMove(ptr1, length);
            ptr2 = backMove(ptr2, length / 2);
        }
        front = length / 4;
        back = ptr2;
        array = newArray;
        length /= 2;
    }

    /**
     * Adds an element to the front of the deque.
     *
     * @param item the element to add
     */
    @Override
    public void addFirst(T item) {
        if (size + 1 == length) {
            expand();
        }
        front = frontMove(front, length);
        array[front] = item;
        size++;
    }

    /**
     * Adds an element to the back of the deque.
     *
     * @param item the element to add
     */
    @Override
    public void addLast(T item) {
        if (size + 1 == length) {
            expand();
        }
        array[back] = item;
        back = backMove(back, length);
        size++;
    }

    /**
     * Removes and returns the element at the front of the deque.
     *
     * @return the element at the front of the deque, or null if the deque is empty
     */
    @Override
    public T removeFirst() {
        if (length > 16 && length / size >= 4) {
            shrink();
        }
        if (size == 0) {
            return null;
        }
        T item = array[front];
        front = backMove(front, length);
        size--;
        return item;
    }

    /**
     * Removes and returns the element at the back of the deque.
     *
     * @return the element at the back of the deque, or null if the deque is empty
     */
    @Override
    public T removeLast() {
        if (length > 16 && length / size >= 4) {
            shrink();
        }
        if (size == 0) {
            return null;
        }
        back = frontMove(back, length);
        T item = array[back];
        size--;
        return item;
    }

    /**
     * Gets the element at the specified index in the deque.
     *
     * @param index the index of the element to retrieve
     * @return the element at the specified index, or null if the index is out of bounds
     */
    @Override
    public T get(int index) {
        if (index >= size) {
            return null;
        }
        int ptr = front;
        for (int i = 0; i < index; i++) {
            ptr = backMove(ptr, length);
        }
        return array[ptr];
    }

    /**
     * Prints the elements in the deque from front to back.
     */
    @Override
    public void printDeque() {
        int ptr = front;
        while(ptr != back) {
            System.out.print(array[ptr] + " ");
            ptr = backMove(ptr, length);
        }
    }
}