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
public class Bishop extends Chess{

    public Bishop() {
    }
    
    public Bishop(String symbol, boolean isWhite,Position position) {
        super("Bishop", symbol,isWhite,position,true);
    }
    
    @Override
    public DuplicateCheckInterface<Position> moveRange(){
        DuplicateCheckInterface<Position> positionList = new DuplicateCheckArray();
        Position p;
        
        /*Get Up left path*/
        int x = this.getPosition().getX();
        int y = this.getPosition().getY();
        for(int i = 0 ; i < 8 ; i++){
            p = new Position(x,y);
            positionList.add(p);
            x++;
            y--;     
        }
        
        /*Get Up right path*/
        x = this.getPosition().getX();
        y = this.getPosition().getY();
        for(int i = 0 ; i < 8; i++){
            p = new Position(x,y);
            positionList.add(p);
            x++;
            y++;
        }
        
         /*Get down right path*/
        x = this.getPosition().getX();
        y = this.getPosition().getY();
        for(int i = 0 ; i < 8; i++){
            p = new Position(x,y);
            positionList.add(p);
            x--;
            y++;
        }
        
         /*Get down left path*/
        x = this.getPosition().getX();
        y = this.getPosition().getY();
        for(int i = 0 ; i < 8; i++){
            p = new Position(x,y);
            positionList.add(p);
            x--;
            y--;
        }
        return clearInvalidPosition(positionList,this.getPosition());
    }
    
    @Override
    public DuplicateCheckInterface<Position> attackRange(){
        return this.moveRange();
    }
    
    public static DuplicateCheckInterface<Position> moveRange(Position currentPosition, DuplicateCheckInterface<Position> positionList){
        Position p;
        
        /*Get Up left path*/
        int x = currentPosition.getX();
        int y = currentPosition.getY();
        for(int i = 0 ; i < 8 ; i++){
            p = new Position(x,y);
            positionList.add(p);
            x++;
            y--;     
        }
        
        /*Get Up right path*/
        x = currentPosition.getX();
        y = currentPosition.getY();
        for(int i = 0 ; i < 8; i++){
            p = new Position(x,y);
            positionList.add(p);
            x++;
            y++;
        }
        
         /*Get down right path*/
        x = currentPosition.getX();
        y = currentPosition.getY();
        for(int i = 0 ; i < 8; i++){
            p = new Position(x,y);
            positionList.add(p);
            x--;
            y++;
        }
        
         /*Get down left path*/
        x = currentPosition.getX();
        y = currentPosition.getY();
        for(int i = 0 ; i < 8; i++){
            p = new Position(x,y);
            positionList.add(p);
            x--;
            y--;
        }
        return clearInvalidPosition(positionList,currentPosition);
    }
}
