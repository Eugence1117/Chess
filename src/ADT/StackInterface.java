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
public interface StackInterface<T> {
    
    public void push(T item);
    
    public T pop();
    
    public T peek();
    
    public T get(int index);
    
    public boolean remove(T entry);
    
    public int size();
  
    public boolean contains(T entry);
    
    public void clear();
   
    public boolean isEmpty();
    
    public String toString();
}
