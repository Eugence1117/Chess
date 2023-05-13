/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import ADT.*;
import java.math.*;

/**
 *
 * @author Eugence
 */
public class King extends Chess {

    public King() {
    }

    public King(String symbol, Position position, boolean isWhite) {
        super("King", symbol, isWhite, position, true);
    }

    @Override
    public DuplicateCheckInterface<Position> moveRange() {
        int x = this.getPosition().getX() - 1;
        DuplicateCheckInterface<Position> positionList = new DuplicateCheckArray();
        Position position;
        for (int i = 0; i < 3; i++) {
            int y = this.getPosition().getY() - 1;
            position = new Position(x, y);
            positionList.add(position);
            for (int j = 0; j < 2; j++) {
                y++;
                position = new Position(x, y);
                positionList.add(position);
            }
            x++;
        }

        return clearInvalidPosition(positionList, this.getPosition());
    }

    @Override
    public DuplicateCheckInterface<Position> attackRange() {
        return this.moveRange();
    }

    public ListInterface<Position> checkCastling(ListInterface<Chess> chessList) {

        ListInterface<Chess> rookList = new List();
        ListInterface<Position> availablePosition = new List();

        if (this.getIsFirstMove()) {

            //Get the Rook 
            for (int i = 1; i <= chessList.getLength(); i++) {
                if (chessList.getEntry(i).getIsWhite() == this.getIsWhite() && !chessList.getEntry(i).getIsDead()) {
                    if (chessList.getEntry(i) instanceof Rook) {
                        if (chessList.getEntry(i).getIsFirstMove()) {
                            rookList.add(chessList.getEntry(i));
                        }
                    }
                }
            }

            if (rookList.getLength() > 0) {
                for (int i = 1; i <= rookList.getLength();i++) {
                    Chess chess = rookList.getEntry(i);
                    int rookPositionY = chess.getPosition().getY();
                    int kingPositionY = this.getPosition().getY();

                    int largerNumber = rookPositionY > kingPositionY ? rookPositionY - 1 : kingPositionY - 1;
                    int smallerNumber = rookPositionY > kingPositionY ? kingPositionY : rookPositionY;
                    System.out.println("Rook Position :" + chess.getPosition().toString() + "Number: " + largerNumber + ":" + smallerNumber);
                    boolean isBlocked = false;

                    //Check obstacle
                    while (largerNumber != smallerNumber) {
                        for (int j = 1; j <= chessList.getLength();j++) {
                            Chess c = chessList.getEntry(j);
                            if (c.getPosition().equals(new Position(this.getPosition().getX(), largerNumber)) && !c.getIsDead()) {
                                isBlocked = true;
                            }
                        }
                        largerNumber--;
                    }

                    if (!isBlocked) {
                        int positionY = (int) Math.round(((double) rookPositionY + kingPositionY) / 2);
                        availablePosition.add(new Position(this.getPosition().getX(), positionY));
                    }
                }
            }
        }
        System.out.println("Castling Size :" + availablePosition.getLength());
        return availablePosition.getLength()> 0 ? availablePosition : null;
    }

}
