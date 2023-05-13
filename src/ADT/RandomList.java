/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ADT;

import java.util.Random;

/**
 *
 * @author ngyen
 * @param <T>
 */
public class RandomList<T> implements RandomListInterface<T> {

    //We assume this 'array' as our current list.
    private T[] array;

    //Length of the current list. Add and remove function will affect its value either increment or decrement.
    private int length;

    //Default List length will be 5 if client did not specify the length.
    private static final int DEFAULT_CAPACITY = 5;

    //This method use to fix the List length to DEFAULT_CAPACITY value.
    public RandomList() {
        this(DEFAULT_CAPACITY);
    }

    //This method use to initial the List.
    /**
     *
     * @param initialCapacity
     */
    public RandomList(int initialCapacity) {
        clear();
        array = (T[]) new Object[initialCapacity];
    }

    //Initial length is 0, but for human(client) is 1.
    @Override
    public boolean add(T newEntry) {
        if (isFull()) {
            doubleArray();
        }
        array[length] = newEntry; //we use length as array index because the current index will be next empty place in array for new element.
        //It is because array always start at zero. 0-4 equal to size of 5. 5 is the next number of 4.
        length++; //increase the lenght by 1.
        return true;
    }

    @Override
    public boolean add(int newPosition, T newEntry) {
        boolean isSuccessful = true;

        if ((newPosition >= 1) && (newPosition <= length + 1)) {
            if (!isFull()) {
                makeRoom(newPosition);
                array[newPosition - 1] = newEntry;
                length++;
            }
        } else {
            isSuccessful = false;
        }

        return isSuccessful;
    }

    @Override
    public T remove(int givenPosition) {
        T result = null;

        if ((givenPosition >= 1) && (givenPosition <= length)) {
            result = array[givenPosition - 1];

            if (givenPosition < length) {
                removeGap(givenPosition);
            }

            length--;
        }

        return result;
    }

    @Override
    public void clear() {
        length = 0;
    }

    @Override
    public boolean replace(int givenPosition, T newEntry) {
        boolean isSuccessful = true;

        if ((givenPosition >= 1) && (givenPosition <= length)) {
            array[givenPosition - 1] = newEntry;
        } else {
            isSuccessful = false;
        }

        return isSuccessful;
    }

    @Override
    public T getEntry(int givenPosition) {
        T result = null;

        if ((givenPosition >= 1) && (givenPosition <= length)) {
            result = array[givenPosition - 1];
        }

        return result;
    }

    @Override
    public boolean contains(T anEntry) {
        boolean found = false;
        for (int index = 0; !found && (index < length); index++) {
            if (anEntry.equals(array[index])) {
                found = true;
            }
        }

        return found;
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public boolean isEmpty() {
        return length == 0;
    }

    @Override
    public boolean isFull() {
        return length == array.length;
    }

    @Override
    public String toString() {
        String outputStr = "";
        for (int index = 0; index < length; ++index) {
            outputStr += array[index] + "\n";
        }

        return outputStr;
    }

    //Shift the element to create a specified position space for newElement.
    private void makeRoom(int newPosition) {
        int newIndex = newPosition - 1;
        int lastIndex = length - 1;

        // move each entry to next higher index, starting at end of
        // array and continuing until the entry at newIndex is moved
        for (int index = lastIndex; index >= newIndex; index--) {
            array[index + 1] = array[index];
        }
    }

    //Eliminate the unncessary space between entries
    private void removeGap(int givenPosition) {
        // move each entry to next lower position starting at entry after the
        // one removed and continuing until end of array
        int removedIndex = givenPosition - 1;
        int lastIndex = length - 1;

        for (int index = removedIndex; index < lastIndex; index++) {
            array[index] = array[index + 1];
        }
    }

    //Expand the space of the List.
    private void doubleArray() {
        T[] oldList = array;
        int oldSize = oldList.length;

        array = (T[]) new Object[2 * oldSize];
        for (int index = 0; index < oldList.length; index++) {
            array[index] = oldList[index];
        }
    }

    @Override
    public T getRandomEntry() {

        if (length >= 2) {
            Random rand = new Random();
            return array[(rand.nextInt(length))];
        }

        return null;
    }

    @Override
    public T pickRandomEntry() {

        if (length >= 2) {
            Random rand = new Random();
            int randomNumber = rand.nextInt(length);
            T object = array[randomNumber];

            int delete = randomNumber + 1;
            remove(delete); // I + 1 it is because in the remove gap it need to - 1, In the remove gap it get the user 's givenPosition so need to -1

            return object;
        }

        return null;
    }
    
    @Override
    public int removeDuplicate() {
        int counter = 0;
        if (length >= 1) {
            for (int i = 0; i < length; i++) {
                for (int j = 0; j < length; j++) {
                    if (i != j && array[i] == array[j]) {
                        counter++;
                        remove(j + 1);
                    }
                }
            }
        }
        return counter;
    }

}
