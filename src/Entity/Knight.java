/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import ADT.*;
import static Entity.Chess.clearInvalidPosition;

/**
 *
 * @author Eugence
 */
public class Knight extends Chess{

    public Knight() {
    }
    
    
    public Knight(String symbol,boolean isWhite, Position position) {
        super("Knight", symbol,isWhite,position,true);
    }
    
    @Override
    public DuplicateCheckInterface<Position> moveRange(){
        DuplicateCheckInterface<Position> positionList = new DuplicateCheckArray();
        int defaultX = 2;
        int defaultY = 1;
        
        Position p;
        /*Get down right*/
        p = new Position(this.getPosition().getX() + defaultX, this.getPosition().getY() + defaultY);
        positionList.add(p);
        p = new Position(this.getPosition().getX() + defaultY, this.getPosition().getY() + defaultX);
        positionList.add(p);
        
        /*Get up right*/
        p = new Position(this.getPosition().getX() - defaultX, this.getPosition().getY() + defaultY);
        positionList.add(p);
        p = new Position(this.getPosition().getX() - defaultY, this.getPosition().getY() + defaultX);
        positionList.add(p);
        
        /*Get down left*/
        p = new Position(this.getPosition().getX() + defaultX, this.getPosition().getY() - defaultY);
        positionList.add(p);
         p = new Position(this.getPosition().getX() + defaultY, this.getPosition().getY() - defaultX);
        positionList.add(p);
        
        /*Get up left*/
        p = new Position(this.getPosition().getX() - defaultX, this.getPosition().getY() - defaultY);
        positionList.add(p);
        p = new Position(this.getPosition().getX() - defaultY, this.getPosition().getY() - defaultX);
        positionList.add(p);
        
        return clearInvalidPosition(positionList, this.getPosition());
    }
    
    @Override
    public DuplicateCheckInterface<Position> attackRange(){
        return this.moveRange();
    }
    
}
