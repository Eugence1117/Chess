/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ADT;

/**
 *
 * @author ngyen
 */
public interface RandomListInterface<T> {
    
    public boolean add(T newEntry);
    //Add new entry into List
    
    public boolean add(int newPosition, T newEntry);
    //Add new entry into the specific position.
    
    public T remove(int givenPosition);
    //Remove the specific position's entry
    
    public void clear();
     //Clear all the entries from the list
    
    public boolean replace(int givenPosition, T newEntry);
    //Replace the entry with another enttry.
    
    public T getEntry(int givenPosition);
    //Get the value of entry from specific position
    
    
    public T getRandomEntry();
    //Random get the value of entry from specific position, if return null that means only has one entries cannot random get the entries.
    
    public T pickRandomEntry();
    //Random pick the value of entry from specific position, if return null that means only has one entries cannot random get the entries.
    
    
    public int removeDuplicate();
    //Remove the duplicate value
    
    
    public boolean contains (T anEntry);
    //Check the list whether contain the given entry or not
    
    public int getLength();
    //Get the length of the list
    
    public boolean isEmpty();
    //Check whether the list is empty
    
    public boolean isFull();
    //Check whether the list is full
}
