/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import ADT.*;

/**
 *
 * @author Eugence
 */
public class Queen extends Chess{

    public Queen() {
    }
    
    
    public Queen(String symbol, boolean isWhite,Position position) {
        super("Queen", symbol,isWhite,position,true);
    }
    
    @Override
    public DuplicateCheckInterface<Position> moveRange(){
        DuplicateCheckInterface<Position> positionList = new DuplicateCheckArray();
        
        /*Get Rook range*/
        positionList = Rook.moveRange(this.getPosition(), positionList);
        
        /*Get Bishop range*/
        positionList = Bishop.moveRange(this.getPosition(), positionList);
        
        positionList = clearInvalidPosition(positionList,this.getPosition());
        
        return positionList;
    }
    
    @Override
    public DuplicateCheckInterface<Position> attackRange(){
        return this.moveRange();
    }
}


