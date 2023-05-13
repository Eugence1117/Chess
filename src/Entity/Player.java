/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import ADT.ListInterface;

/**
 *
 * @author Eugence
 */
public class Player {

    private String name;
    private boolean isWhite;
    private ListInterface<Chess> chessList;

    public Player() {
    }
     
    public Player(String name, boolean isWhite, ListInterface<Chess> chessList) {
        this.name = name;
        this.isWhite = isWhite;
        this.chessList = chessList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsWhite() {
        return isWhite;
    }

    public void setIsWhite(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public ListInterface<Chess> getChessList() {
        return chessList;
    }

    public void setChessList(ListInterface<Chess> chessList) {
        this.chessList = chessList;
    }
    
    
    
    
    
}
