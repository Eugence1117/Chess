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
public class Rook extends Chess {

    public Rook() {
    }

    public Rook(String symbol, boolean isWhite, Position position) {
        super("Rook", symbol, isWhite, position, true);
    }

    @Override
    public DuplicateCheckInterface<Position> moveRange() {
        DuplicateCheckInterface<Position> positionList = new DuplicateCheckArray();
        verticalMovePosition(1, 8, this.getPosition().getY(), positionList);
        horizontalMovePosition(1, 8, this.getPosition().getX(), positionList);
        return clearInvalidPosition(positionList, this.getPosition());

    }

    @Override
    public DuplicateCheckInterface<Position> attackRange() {
        return this.moveRange();
    }

    private static void verticalMovePosition(int first, int last, int y, DuplicateCheckInterface<Position> positionList) {
        if (first <= last) {
            positionList.add(new Position(first, y));
            verticalMovePosition(first + 1, last, y, positionList);
        }
    }

    private static void horizontalMovePosition(int first, int last, int x, DuplicateCheckInterface<Position> positionList) {
        if (first <= last) {
            positionList.add(new Position(x, first));
            horizontalMovePosition(first + 1, last, x, positionList);
        }
    }

    public static DuplicateCheckInterface<Position> moveRange(Position currentPosition, DuplicateCheckInterface<Position> positionList) {
        verticalMovePosition(1, 8, currentPosition.getY(), positionList);
        horizontalMovePosition(1, 8, currentPosition.getX(), positionList);

        return clearInvalidPosition(positionList, currentPosition);
    }

}
