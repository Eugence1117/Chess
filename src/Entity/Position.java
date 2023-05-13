/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;
/**
 *
 * @author Eugence
 */
public class Position{
    private int x;
    private int y;

    public Position() {
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isExceed(){
        if(x > 8 || y > 8 || x < 1 || y < 1){
            return false;
        }
        else{
            return true;
        }
    }
    
    @Override
    public boolean equals(Object p){
        if(p instanceof Position){
            Position position = (Position)p;
             if(position.getX() == x && position.getY() == y){
            return true;
            }
            else{
                return false;
            }
        }
        return false;
    }
    
    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
    
    
}
