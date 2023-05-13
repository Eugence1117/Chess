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
public abstract class Chess{
    private String name;
    private String symbol;
    private Position position;
    private boolean isWhite;
    private boolean isDead;
    private boolean isFirstMove;

    public Chess() {
        this.isDead = false;
    }
     
    public Chess(String name, String symbol, boolean isWhite, Position position,boolean isFirstMove){
        this.name = name;
        this.symbol = symbol;
        this.position = position;
        this.isWhite = isWhite;
        this.isFirstMove = isFirstMove;
        this.isDead = false;
    }

    public boolean getIsWhite() {
        return isWhite;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public boolean getIsFirstMove() {
        return isFirstMove;
    }

    public void setIsFirstMove(boolean isFirstMove) {
        this.isFirstMove = isFirstMove;
    }
    
    
    public void setPosition(Position position) {
        this.position = position;
    }
    
    public Position getPosition() {
        return position;
    }


    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    

    public boolean getIsDead() {
        return isDead;
    }
    
    public void setIsDead(boolean isDead) {
        this.isDead = isDead;
    }
    

    public abstract DuplicateCheckInterface<Position> attackRange();
    

    public abstract DuplicateCheckInterface<Position> moveRange();
    
    public static DuplicateCheckInterface<Position> clearInvalidPosition(DuplicateCheckInterface<Position> positionList, Position currentPosition){
        StackInterface<Position> pendingRemove = new Stack();
        pendingRemove.push(currentPosition); //Chess position should not include in move range
        for(int i = 1 ; i <= positionList.getSize(); i++){
            if(!positionList.get(i).isExceed()){
                 if(!pendingRemove.contains(positionList.get(i))){
                    pendingRemove.push(positionList.get(i));
                }
            }
        }
        
        while(!pendingRemove.isEmpty()){
            positionList.remove(pendingRemove.pop());
        }
        return positionList;
    }
    
    public ListInterface<StackInterface<Position>> organizeMoveDirection(DuplicateCheckInterface<Position> positionList){
        
        Position currentPosition = this.getPosition();
        
        ListInterface<Position> directionTopLeft = new List();
        ListInterface<Position> directionTopRight = new List();
        ListInterface<Position> directionTop = new List();
        ListInterface<Position> directionLeft = new List();
        ListInterface<Position> directionRight = new List();
        ListInterface<Position> directionBottom = new List();
        ListInterface<Position> directionBottomLeft = new List();
        ListInterface<Position> directionBottomRight = new List();
        
        for(int i = 1 ; i <= positionList.getSize(); i++){
            
            /*Identify Top Left*/
            if(positionList.get(i).getY() < currentPosition.getY() && positionList.get(i).getX() < currentPosition.getX()){
                directionTopLeft.add(positionList.get(i));
            }
            /*Identify Top Right*/
            else if(positionList.get(i).getY() > currentPosition.getY() && positionList.get(i).getX() < currentPosition.getX()){
                directionTopRight.add(positionList.get(i));
            }
            /*Identify Top*/
            else if(positionList.get(i).getY() == currentPosition.getY() && positionList.get(i).getX() < currentPosition.getX()){
                directionTop.add(positionList.get(i));
            }
            /*Identify Left*/
            else if(positionList.get(i).getX() == currentPosition.getX() && positionList.get(i).getY() < currentPosition.getY()){
                directionLeft.add(positionList.get(i));
            }
            /*Identify Right*/
            else if(positionList.get(i).getX() == currentPosition.getX() && positionList.get(i).getY() > currentPosition.getY()){
                directionRight.add(positionList.get(i));
            }
            /*Identify Bottom*/
            else if(positionList.get(i).getX() > currentPosition.getX() && positionList.get(i).getY() == currentPosition.getY()){
                directionBottom.add(positionList.get(i));
            }
            /*Identify Bottom Left*/
            else if(positionList.get(i).getX() > currentPosition.getX() && positionList.get(i).getY() < currentPosition.getY()){
                directionBottomLeft.add(positionList.get(i));
            }
            /*Identify Bottom Right*/
            else if(positionList.get(i).getX() > currentPosition.getX() && positionList.get(i).getY() > currentPosition.getY()){
                directionBottomRight.add(positionList.get(i));
            }  
        }
        
        ListInterface<StackInterface<Position>> listStacker = new List();
        
        listStacker.add(organizeSequenceTopLeft(directionTopLeft));// 1
        listStacker.add(organizeSequenceTopRight(directionTopRight));// 2
        listStacker.add(organizeSequenceTop(directionTop));// 3
        listStacker.add(organizeSequenceLeft(directionLeft));// 4
        listStacker.add(organizeSequenceRight(directionRight));  // 5  
        listStacker.add(organizeSequenceBottom(directionBottom));// 6
        listStacker.add(organizeSequenceBottomLeft(directionBottomLeft));// 7
        listStacker.add(organizeSequenceBottomRight(directionBottomRight));// 8
        
        return listStacker;
    }
    
    /*Organize Sequence*/
    public StackInterface<Position> organizeSequenceTopLeft(ListInterface<Position> directionTopLeft){
             /*Top Left*/
        StackInterface<Position> topLeft = new Stack();
        if(directionTopLeft.getLength() > 1){
            do{
                int lowestIndex = 1;
                Position lowest = directionTopLeft.getEntry(lowestIndex);
                for(int i = 1 ; i <= directionTopLeft.getLength(); i++){
                    Position p = directionTopLeft.getEntry(i);
                    if(p.getX() > lowest.getX() && p.getY() > lowest.getY()){
                        lowest = p;
                        lowestIndex = i;
                    }

                }
                topLeft.push(lowest);
                directionTopLeft.remove(lowestIndex);
            }while(directionTopLeft.getLength()!= 0);
        }
        else{
            if(directionTopLeft.getLength() == 1){
                topLeft.push(directionTopLeft.getEntry(1));
            }
        }
        return topLeft;
    }
    
    public StackInterface<Position> organizeSequenceTopRight(ListInterface<Position> directionTopRight){
         /*Top Right*/
        StackInterface<Position> topRight = new Stack();
        if(directionTopRight.getLength() > 1){
            do{
                int lowestIndex = 1;
                Position lowest = directionTopRight.getEntry(lowestIndex);
                for(int i = 1 ; i <= directionTopRight.getLength(); i++){
                    Position p = directionTopRight.getEntry(i);
                    if(p.getX() > lowest.getX() && p.getY() < lowest.getY()){
                        lowest = p;
                        lowestIndex = i;
                    }

                }
                topRight.push(lowest);
                directionTopRight.remove(lowestIndex);
            }while(directionTopRight.getLength() != 0);
        }
        else{
            if(directionTopRight.getLength() == 1){
                topRight.push(directionTopRight.getEntry(1));
            }
        }
        return topRight;
    }
    
    public StackInterface<Position> organizeSequenceTop(ListInterface<Position> directionTop){
        /*Top*/
        StackInterface<Position> top = new Stack();
        if(directionTop.getLength() > 1){
            do{
                int lowestIndex = 1;
                Position lowest = directionTop.getEntry(lowestIndex);
                for(int i = 1 ; i <= directionTop.getLength();i++){
                    Position p = directionTop.getEntry(i);
                    if(p.getX() > lowest.getX()){
                        lowest = p;
                        lowestIndex = i;
                    }
                }
                top.push(lowest);
                directionTop.remove(lowestIndex);
            }while(directionTop.getLength() != 0);
        }
        else{
            if(directionTop.getLength() == 1){
                top.push(directionTop.getEntry(1));
            }
        }
        return top;
    }
    
    public StackInterface<Position> organizeSequenceRight(ListInterface<Position> directionRight){
         /*Right*/
        StackInterface<Position> right = new Stack();
        if(directionRight.getLength() > 1){
            do{
                int lowestIndex = 1;
                Position lowest = directionRight.getEntry(lowestIndex);
                for(int i = 1 ; i <= directionRight.getLength();i++){
                    Position p = directionRight.getEntry(i);
                    if(p.getY() < lowest.getY()){
                        lowest = p;
                        lowestIndex = i;
                    }
                }
                right.push(lowest);
                directionRight.remove(lowestIndex);
            }while(directionRight.getLength() != 0);
        }
        else{
            if(directionRight.getLength() == 1){
                right.push(directionRight.getEntry(1));
            }
        }
        return right;
    }
    
    public StackInterface<Position> organizeSequenceLeft(ListInterface<Position> directionLeft){
        /*Left*/
        StackInterface<Position> left = new Stack();
        if(directionLeft.getLength() > 1){
            do{
                int lowestIndex = 1;
                Position lowest = directionLeft.getEntry(lowestIndex);
                for(int i = 1 ; i <= directionLeft.getLength();i++){
                    Position p = directionLeft.getEntry(i);
                    if(p.getY() > lowest.getY()){
                        lowest = p;
                        lowestIndex = i;
                    }
                }
                left.push(lowest);
                directionLeft.remove(lowestIndex);
            }while(directionLeft.getLength() != 0);
        }
        else{
            if(directionLeft.getLength() == 1){
                left.push(directionLeft.getEntry(1));
            }
        }
        
        return left;
    }
    
    public StackInterface<Position> organizeSequenceBottom(ListInterface<Position> directionBottom){
        /*Bottom*/
        StackInterface<Position> bottom = new Stack();
        if(directionBottom.getLength() > 1){
            do{
                int lowestIndex = 1;
                Position lowest = directionBottom.getEntry(lowestIndex);
                for(int i = 1 ; i <= directionBottom.getLength(); i++){
                    Position p = directionBottom.getEntry(i);
                    if(p.getX() < lowest.getX()){
                        lowest = p;
                        lowestIndex = i;
                    }
                }
                bottom.push(lowest);
                directionBottom.remove(lowestIndex);
            }while(directionBottom.getLength() != 0);
        }else{
            if(directionBottom.getLength() == 1){
                bottom.push(directionBottom.getEntry(1));
            }
        }
        return bottom;
    }
    
    public StackInterface<Position> organizeSequenceBottomLeft(ListInterface<Position> directionBottomLeft){
         /*Bottom Left*/
        StackInterface<Position> bottomLeft = new Stack();
        if(directionBottomLeft.getLength() > 1){
            do{
                int lowestIndex = 1;
                Position lowest = directionBottomLeft.getEntry(lowestIndex);
                for(int i = 1 ; i <= directionBottomLeft.getLength(); i++){
                    Position p = directionBottomLeft.getEntry(i);
                    if(p.getX() < lowest.getX() && p.getY() < lowest.getY()){
                        lowest = p;
                        lowestIndex = i;
                    }
                }
                bottomLeft.push(lowest);
                directionBottomLeft.remove(lowestIndex);
            }while(directionBottomLeft.getLength() != 0);
        }
        else{
            if(directionBottomLeft.getLength() == 1){
                bottomLeft.push(directionBottomLeft.getEntry(1));
            }
        }
        return bottomLeft;
    }
    
    public StackInterface<Position> organizeSequenceBottomRight(ListInterface<Position> directionBottomRight){
        /*Bottom Right*/
        StackInterface<Position> bottomRight = new Stack();
        if(directionBottomRight.getLength() > 1){
            do{
                int lowestIndex = 1;
                Position lowest = directionBottomRight.getEntry(lowestIndex);
                for(int i = 1 ; i <= directionBottomRight.getLength(); i++){
                    Position p = directionBottomRight.getEntry(i);
                    if(p.getX() < lowest.getX() && p.getY() < lowest.getY()){
                        lowest = p;
                        lowestIndex = i;
                    }
                }
                bottomRight.push(lowest);
                directionBottomRight.remove(lowestIndex);
            }while(directionBottomRight.getLength() != 0);
        }
        else{
            if(directionBottomRight.getLength() == 1){
                bottomRight.push(directionBottomRight.getEntry(1));
            }
        }
        return bottomRight;
    }
   
}
