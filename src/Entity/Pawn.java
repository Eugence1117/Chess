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
public class Pawn extends Chess {

    private boolean moveTwoBlock;
    private int lastMove;

    public Pawn() {
    }

    public Pawn(String symbol, boolean isWhite, Position position) {
        super("Pawn", symbol, isWhite, position, true);
        this.lastMove = 0;
    }

    public boolean isMoveTwoBlock() {
        return moveTwoBlock;
    }

    public void setMoveTwoBlock(boolean moveTwoBlock) {
        this.moveTwoBlock = moveTwoBlock;
    }
    
    public int getLastMove() {
        return lastMove;
    }

    public void setLastMove(int lastMove) {
        this.lastMove = lastMove;
    }

    @Override
    public DuplicateCheckInterface<Position> moveRange() {

        final int DEFAULT_RANGE = 2;
        DuplicateCheckInterface<Position> positionList = new DuplicateCheckArray();
        Position p;

        if (this.getIsFirstMove()) {
            if (this.getIsWhite()) {
                for (int i = this.getPosition().getX(); i >= this.getPosition().getX() - DEFAULT_RANGE; i--) {
                    p = new Position(i, this.getPosition().getY());
                    positionList.add(p);
                }
            } else {
                for (int i = this.getPosition().getX(); i <= this.getPosition().getX() + DEFAULT_RANGE; i++) {
                    p = new Position(i, this.getPosition().getY());
                    positionList.add(p);
                }
            }
        } else {
            if (this.getIsWhite()) {
                p = new Position(this.getPosition().getX() - 1, this.getPosition().getY());
                positionList.add(p);
            } else {
                p = new Position(this.getPosition().getX() + 1, this.getPosition().getY());
                positionList.add(p);
            }
        }

        return clearInvalidPosition(positionList, this.getPosition());
    }

    @Override
    public DuplicateCheckInterface<Position> attackRange() {

        DuplicateCheckInterface<Position> positionList = new DuplicateCheckArray();
        Position p;

        int x = this.getPosition().getX();
        int y = this.getPosition().getY();
        /*Calculate attack range*/

        if (this.getIsWhite()) {
            p = new Position(x - 1, y - 1);
            positionList.add(p);
            positionList.add(new Position(x - 1, y + 1));
        } else {
            p = new Position(x + 1, y + 1);
            positionList.add(p);
            positionList.add(new Position(x + 1, y - 1));
        }
        return clearInvalidPosition(positionList, this.getPosition());
    }

    public ListInterface<Position> inPassing(ListInterface<Chess> chessList, Chess lastOpponentChess) {

        //Y need to == 4
        ListInterface<Position> inPassingPath = new List();
        DuplicateCheckInterface<Position> triggerPosition = new DuplicateCheckArray();
        triggerPosition.add(new Position(this.getPosition().getX(), this.getPosition().getY() + 1));
        triggerPosition.add(new Position(this.getPosition().getX(), this.getPosition().getY() - 1));
        triggerPosition = clearInvalidPosition(triggerPosition, this.getPosition());

        for (int x = 1; x <= chessList.getLength(); x++) {
            Chess chess = chessList.getEntry(x);
            for (int y = 1; y <= triggerPosition.getSize(); y++) {
                if (chess.getPosition().equals(triggerPosition.get(y)) && chess instanceof Pawn) {
                    if (chess.getIsWhite() != this.getIsWhite() && ((Pawn) chess).isMoveTwoBlock()) {
                        int fifthRank = this.getIsWhite() ? 4 : 5;
                        if (this.getPosition().getX() == fifthRank && ((Pawn)chess).getLastMove() == 2 && chess.equals(lastOpponentChess)) {
                            if (this.getIsWhite()) {
                                inPassingPath.add(new Position(chess.getPosition().getX() - 1, chess.getPosition().getY()));
                            } else {
                                inPassingPath.add(new Position(chess.getPosition().getX() + 1, chess.getPosition().getY()));
                            }
                        }
                    }
                }
            }
        }

        return inPassingPath.getLength() > 0 ? inPassingPath : null;
    }

    public boolean isDoubleMove(Position newPosition) {
        System.out.println("Old " + this.getPosition().getX() + "New " + newPosition.getX());
        if (this.getIsWhite()) {
            if (this.getIsFirstMove() && this.getPosition().getX() - 2 == newPosition.getX()) {
                return true;
            } else {
                return false;
            }

        } else {
            if (this.getIsFirstMove() && this.getPosition().getX() + 2 == newPosition.getX()) {
                return true;
            } else {
                return false;
            }

        }
    }

    public boolean isPromotion() {
        int lastRank = this.getIsWhite() ? 1 : 8;
        return this.getPosition().getX() == lastRank;
    }
}
