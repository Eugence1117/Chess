package ADT;

import java.util.Iterator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Eugence
 */
public class DuplicateCheckArray<T> implements DuplicateCheckInterface<T> {

    private T[] array;
    private int length;
    private static final int INITIAL_SIZE = 10;

    /*
    * No argument constructor, default autoExpand is true;
     */
    public DuplicateCheckArray() {
        this(INITIAL_SIZE);
    }

    public DuplicateCheckArray(int size) {
        array = (T[]) new Object[size];
        length = 0;
    }

    /*
    * Insert new element into array.
    * Return false if element already exist.
     */
    public boolean add(T newElement) {

        if (isFull()) {
            expand();
        }

        if (isRepeat(newElement)) {
            return false;
        } else {
            array[length] = newElement;
            length++;
            return true;
        }
    }

    /*
    * Return element at specific index, index start at 1.
     */
    public T get(int selectedIndex) {
        if (isEmpty()) {
            throw new NullPointerException("No element in the list.");
        } else if (selectedIndex > length || selectedIndex < 0) {
            throw new ArrayIndexOutOfBoundsException("Invalid index.");
        } else if (selectedIndex == 0) {
            throw new ArrayIndexOutOfBoundsException("Index should start at 1.");
        } else {
            return array[selectedIndex - 1];
        }
    }

    /*
    * Return index of specific element, -1 means not found.
     */
    public int getIndex(T selectedElement) {
        if (isEmpty()) {
            throw new NullPointerException("No element in the list.");
        } else if (selectedElement == null) {
            return -1;
        } else {
            int index = -1;
            for (int i = 0; i < length; i++) {
                if (array[i].equals(selectedElement)) {
                    index = i + 1;
                }
            }
            return index;
        }
    }

    /*
    * Get the size of the array    
     */
    public int getSize() {
        return length;
    }

    /*
    * Return TRUE if no element in the array.
     */
    public boolean isEmpty() {
        return (length == 0);
    }

    /*
    * Remove elemet at specific position. Return false if index not available.
     */
    public boolean remove(int selectedIndex) {
        if (isEmpty()) {
            throw new NullPointerException("No element in the list.");
        } else if (selectedIndex > length || selectedIndex < 0) {
            throw new ArrayIndexOutOfBoundsException("Invalid index.");
        } else if (selectedIndex == 0) {
            throw new ArrayIndexOutOfBoundsException("Index should start at 1.");
        } else {
            array[selectedIndex - 1] = null;
            length--;
            alignArray(selectedIndex - 1);
            return true;
        }
    }

    /*
    * Remove specific element. Return false if element not found.
     */
    public boolean remove(T selectedElement) {
        if (isEmpty()) {
            throw new NullPointerException("No element in the list.");
        } else if (selectedElement == null) {
            return false;
        } else {
            for (int i = 0; i < length; i++) {
                if (array[i].equals(selectedElement)) {
                    array[i] = null;
                    length--;
                    alignArray(i);
                }
            }
            return true;
        }
    }

    public DuplicateCheckInterface<T> merge(DuplicateCheckInterface<T> newList) {

        DuplicateCheckInterface<T> repeatedItem = new DuplicateCheckArray<T>();

        if (newList instanceof DuplicateCheckArray) {

            DuplicateCheckArray list = (DuplicateCheckArray) newList;
            for (int i = 1; i <= list.getSize(); i++) {
                if (!this.add((T) list.get(i))) {
                    repeatedItem.add((T) list.get(i));
                }
            }
        } else {
            throw new ClassFormatError("Parameter not DuplicateCheckArray()");
        }

        return repeatedItem;
    }

    @Override
    public Iterator getIterator() {
        return new DuplicateCheckIterator();
    }

    public String toString() {
        String msg = "";
        for (int i = 0; i < length; i++) {
            msg += array[i];
            if (i != (length - 1)) {
                msg += ", ";
            }
        }
        return msg;
    }

    private void alignArray(int removedIndex) {

        while (removedIndex != length) {
            array[removedIndex] = array[removedIndex + 1];
            removedIndex++;
        }
    }

    private boolean isFull() {
        return length == array.length ? true : false;
    }

    private void expand() {

        T[] temp = array;
        array = (T[]) new Object[array.length + INITIAL_SIZE];
        for (int i = 0; i < temp.length; i++) {
            array[i] = temp[i];
        }

    }

    private boolean isRepeat(T newElement) {
        for (int i = 0; i < length; i++) {
            if (array[i].equals(newElement)) {
                return true;
            }
        }
        return false;
    }

    private class DuplicateCheckIterator implements Iterator<T> {

        private int nextObject;

        private DuplicateCheckIterator() {
            nextObject = 0;
        }

        @Override
        public boolean hasNext() {
            return nextObject < length;
        }

        @Override
        public T next() {
            if (hasNext()) {
                T nextEntry = array[nextObject];
                nextObject++;

                return nextEntry;
            } else {
                return null;
            }
        }
    }
}
