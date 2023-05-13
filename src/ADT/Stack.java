/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ADT;

/**
 *
 * @author Admin
 */
public class Stack<T> implements StackInterface<T> {

    private T[] array;
    private int topIndex; // index of top entry
    private static final int DEFAULT_CAPACITY = 25;

    public Stack() {
        this(DEFAULT_CAPACITY);
    }

    public Stack(int initialCapacity) {
        array = (T[]) new Object[initialCapacity];
        topIndex = -1;
    }

    public void push(T item) {
        topIndex++;

        if (isFull()) {
            expand();
        }

        array[topIndex] = item;

    }

    public T peek() {
        T top = null;

        if (!isEmpty()) {
            top = array[topIndex];
        }

        return top;
    }

    public T pop() {
        T top = null;

        if (!isEmpty()) {
            top = array[topIndex];
            array[topIndex] = null;
            topIndex--;
        } // end if

        return top;
    }

    public T get(int index) {
        T entry = null;
        if (!isEmpty()) {
            entry = array[index - 1];
        }
        return entry;
    }

    public boolean remove(T entry) {
        boolean isRemove = false;
        for (int i = 0; i < topIndex + 1; i++) {
            if (array[i].equals(entry)) {
                isRemove = true;
                reArrange(i);
                topIndex--;
            }
        }
        return isRemove;
    }

    public int size() {
        return topIndex + 1;
    }

    public boolean contains(T entry) {
        boolean isExist = false;
        for (int i = 0; i < topIndex + 1; i++) {
            if (array[i].equals(entry)) {
                isExist = true;
            }
        }
        return isExist;
    }

    public boolean isEmpty() {
        return topIndex < 0;
    }

    public void clear() {
        topIndex = -1;
        array = (T[]) new Object[DEFAULT_CAPACITY];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 0; i < topIndex + 1; i++) {
            sb.append(array[i].toString());
            if (i < topIndex) {
                sb.append(",");
            }
        }
        sb.append(']');
        return sb.toString();
    }

    private boolean isFull() {
        return (topIndex >= array.length);
    }

    private void reArrange(int index) {
        while (index != topIndex) {
            array[index] = array[index + 1];
            index++;
        }
    }

    private void expand() {
        T[] tempArray = array;
        array = (T[]) new Object[array.length * 2];
        for (int i = 0; i < tempArray.length; i++) {
            array[i] = tempArray[i];
        }
    }
}
