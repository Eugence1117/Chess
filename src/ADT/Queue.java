/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ADT;

/**
 *
 * @author Acer
 */
public class Queue <T> implements QueueInterface<T> {

  private T[] array;
  private int backIndex;
  private final static int FIRST_INDEX = 0;
  private static final int DEFAULT_CAPACITY = 20;

  public Queue() {
    this(DEFAULT_CAPACITY);
  } 

  public Queue(int initialCapacity) {
    array = (T[]) new Object[initialCapacity];
    backIndex = -1;
  } 

  public void enqueue(T newEntry) {
    if (!isFull()) {
      backIndex++;
      array[backIndex] = newEntry;
    }
    else{
        throw new ArrayIndexOutOfBoundsException("Array is full.");
    }
  }
  
  public boolean offer(T newEntry) {
    if (!isFull()) {
      backIndex++;
      array[backIndex] = newEntry;
      return true;
    }
    else{
        return false;
    }
  }
  
  public T dequeue() {
    T front = null;

    if (!isEmpty()) {
      front = array[FIRST_INDEX];

      // shift remaining array items forward one position
      for (int i = FIRST_INDEX; i <= backIndex; ++i) {
          if(i != backIndex){
            array[i] = array[i + 1];
          }
      }

      backIndex--;
    } 
    return front;
  } 
  
  public T getFront() {
    T front = null;

    if (!isEmpty()) {
      front = array[FIRST_INDEX];
    }

    return front;
  }

  public boolean isEmpty() {
    return FIRST_INDEX > backIndex;
  } 

  public void clear() {
    if (!isEmpty()) { // deallocates only the used portion
      for (int index = FIRST_INDEX; index <= backIndex; index++) {
        array[index] = null;
      } 

      backIndex = -1;
    } 
  } 

  private boolean isFull() {
    return backIndex == array.length - 1;
  } 
  
  public int size(){
     return backIndex+1; 
  }
  
@Override
    public String toString()
    {
         StringBuilder sb = new StringBuilder();
         sb.append('[');
         for(int i = 0; i < backIndex+1 ;i++) {
             sb.append(array[i].toString());
             if(i < backIndex){
                 sb.append(",");
             }
         }
         sb.append(']');
         return sb.toString();
    }

}  
