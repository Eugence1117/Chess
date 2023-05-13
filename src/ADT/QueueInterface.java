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
public interface QueueInterface<T> {  //start interface
    //Adds a new entry to the back of the queue
    public void enqueue(T newEntry);
    
    //Removes and returns the entry at the front of the queue
    public T dequeue();
    
    //Adds newEntry to the rear of the queue
    public boolean offer(T newEntry);
    
    //Retrieves the entry at the front of the queue
    public T getFront();

    //Removes all entries from the queue
    public void clear();
    
    //Gets the number of entries currently in the queue
    public int size();
    
    //Detects whether the queue is empty
    public boolean isEmpty();
    
    public String toString();
} //end interface
