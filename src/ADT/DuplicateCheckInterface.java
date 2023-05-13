package ADT;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Iterator;
/**
 *
 * @author Eugence
 */
public interface DuplicateCheckInterface<T> {
    
    public boolean add(T newElement);
    public T get(int selectedIndex);
    public int getIndex(T selectedElement);
    public int getSize();
    public boolean isEmpty();
    public boolean remove(int selectedIndex);
    public boolean remove(T selectedElement);
    public DuplicateCheckInterface<T> merge(DuplicateCheckInterface<T> newList);
    public Iterator getIterator();
}
