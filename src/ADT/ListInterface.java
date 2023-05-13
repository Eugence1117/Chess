package ADT;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author QING HAO CHAI
 */
public interface ListInterface<T> {
    
    //Add new entry into List
    public boolean add (T newEntry);
    
    //Add new entry into the specific position.
    public boolean add(int newPosition, T newEntry);
    
    //Add a list of entries into list
    public int addAll(ListInterface<T> entries);
    
    //Remove the specific position's entry
    public T remove(int givenPosition);
    
    //Clear all the entries from the list
    public void clear();
    
    //Replace the entry with another enttry.
    public boolean replace(int givenPosition, T newEntry);
    
    //Get the value of entry from specific position
    public T getEntry(int givenPosition);
    
    //Check the list whether contain the given entry or not
    public boolean contains (T anEntry);
    
    //Get the length of the list
    public int getLength();
    
    //Check whether the list is empty
    public boolean isEmpty();
    
    //Check whether the list is full
    public boolean isFull();
    
    
}