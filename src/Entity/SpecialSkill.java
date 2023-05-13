/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import ADT.DuplicateCheckInterface;
import ADT.*;
import Util.Constant;
import Client.MainFrame;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import javax.swing.JButton;

/**
 *
 * @author Eugence
 */
public class SpecialSkill {

    private Chess chess;
    private ListInterface<Chess> chessList;
    private PlayerEngine playerEngine;

    public SpecialSkill(Chess chess, ListInterface<Chess> chessList, PlayerEngine playerEngine) {
        this.chess = chess;
        this.chessList = chessList;
        this.playerEngine = playerEngine;
    }

    public int preCheckSpecialSkill(Chess lastOpponentChess) {
        if (chess instanceof Pawn) {
            ListInterface<Position> availableSkill = ((Pawn) chess).inPassing(chessList, lastOpponentChess);
            if (availableSkill != null) {
                return Constant.IN_PASSING;
            }
        } else if (chess instanceof King) {
            ListInterface<Position> availableSkill = ((King) chess).checkCastling(chessList);
            if (availableSkill != null) {
                return Constant.CASTLING;
            }
        }

        return Constant.NO_SKILL;
    }

    public int postCheckSpecialSkill(ChessBoard map) {
        if (endGameHandler(playerEngine.getNextPlayer())) {
            map.addNewMessage("Game over.");
            map.addNewMessage(playerEngine.getCurrentPlayer().getName() + " is the winner.");
            return Constant.END_GAME;
        }

        if (chess instanceof Pawn) {
            boolean isPromote = ((Pawn) chess).isPromotion();
            if (isPromote) {
                Chess promotedChess = promotionHandler(map);
                map.removeComponent(chess.getPosition());
                map.updateNewLocation(promotedChess);
                map.disableSingleListener(promotedChess.getPosition());
                map.addNewMessage(playerEngine.getCurrentPlayer().getName() + " 's Pawn is promoted to " + promotedChess.getName() + ".");
                MainFrame.refreshFrame();
                if (checkHandler(map)) {
                    map.addNewMessage(playerEngine.getNextPlayer().getName() + " 's King is checked by opponent.");
                }
                return Constant.PROMOTION;
            }
        }

        if (checkHandler(map)) {
            map.addNewMessage(playerEngine.getNextPlayer().getName() + " 's King is checked by opponent.");
            return Constant.CHECK;
        }

        return Constant.NO_SKILL;
    }

    public boolean checkHandler(ChessBoard map) {
        boolean isCheck = false;

        ListInterface<Chess> enemyChessList = playerEngine.getNextPlayer().getChessList();
        Chess king = null;
        for (int i = 1; i <= enemyChessList.getLength(); i++) {
            Chess c = enemyChessList.getEntry(i);
            if (c instanceof King && !c.getIsDead()) {
                king = c;
            }
        }

        ListInterface<Chess> currentPlayerList = playerEngine.getCurrentPlayer().getChessList();
        for (int i = 1; i <= currentPlayerList.getLength(); i++) {
            Chess c = currentPlayerList.getEntry(i);
            if (!c.getIsDead()) {
                DuplicateCheckInterface<Position> attackRange = c.attackRange();
                //Filter Invalid Attack Range
                boolean isKingInRange = false;
                for (int j = 1; j <= attackRange.getSize(); j++) {
                    Position p = attackRange.get(j);
                    if (p.equals(king.getPosition())) {
                        //This Chess has possibility to check king
                        isKingInRange = true;
                    }
                }

                if (isKingInRange) {
                    System.out.println("Chess :" + c.getName() + " Position: " + c.getPosition().toString());
                    System.out.println("Before Range =  " + attackRange.getSize());
                    ListInterface<StackInterface<Position>> sortedRange = c.organizeMoveDirection(attackRange);
                    ListInterface<Position> removedItem = new List();
                    Iterator iterator = attackRange.getIterator();
                    while (iterator.hasNext()) {
                        Position p = (Position) iterator.next();
                        for (int x = 1; x <= chessList.getLength(); x++) {
                            Chess chess = chessList.getEntry(x); //Object location
                            
                            //Have object block
                            if (p.equals(chess.getPosition())) {
                                //If Chess that we checking is Knight, direct remove
                                if (c instanceof Knight) {
                                    if (chess.getIsWhite() == c.getIsWhite()) { //Is Allies
                                        for (int y = 1; y <= sortedRange.getLength(); y++) {
                                            for (int z = 1; z <= sortedRange.getEntry(y).size(); z++) {
                                                if(sortedRange.getEntry(y).get(z).equals(p)){
                                                    sortedRange.getEntry(y).remove(p);
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    int direction = identifyDirection(chess.getPosition(), c.getPosition());
                                    StackInterface<Position> directionList = sortedRange.getEntry(direction);
                                    boolean isSkipped = false;
                                    if (removedItem.getLength() > 0) {
                                        for (int y = 1; y <= removedItem.getLength(); y++) {
                                            Position item = removedItem.getEntry(y);
                                            if (chess.getPosition().equals(item)) {
                                                isSkipped = true;
                                            }
                                        }

                                        if (!isSkipped) {
                                            while (!directionList.peek().equals(chess.getPosition())) {
                                                removedItem.add(directionList.pop());
                                            }
                                            if (chess.getIsWhite() != king.getIsWhite()) {
                                                if (directionList.peek().equals(chess.getPosition())) {
                                                    removedItem.add(directionList.pop());
                                                }
                                            }
                                        }
                                    } else {
                                        while (!directionList.peek().equals(chess.getPosition())) {
                                            removedItem.add(directionList.pop());
                                        }
                                        if (chess.getIsWhite() != king.getIsWhite()) { //Is Allies.
                                            if (directionList.peek().equals(chess.getPosition())) {
                                                removedItem.add(directionList.pop());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    System.out.println(sortedRange.toString());
                    for (int v = 1; v <= sortedRange.getLength(); v++) {
                        for (int b = 1; b <= sortedRange.getEntry(v).size(); b++) {
                            Position p = sortedRange.getEntry(v).get(b);
                            System.out.print(p.toString() + " ,");
                            if (p.equals(king.getPosition())) {
                                isCheck = true;
                            }
                        }
                    }
                }

            }
        }

        return isCheck;
    }

    public int identifyDirection(Position nextPosition, Position currentPosition) {

        /*Identify Top Left*/
        if (nextPosition.getY() < currentPosition.getY() && nextPosition.getX() < currentPosition.getX()) {
            return Constant.TOP_LEFT;
        } /*Identify Top Right*/ else if (nextPosition.getY() > currentPosition.getY() && nextPosition.getX() < currentPosition.getX()) {
            return Constant.TOP_RIGHT;
        } /*Identify Top*/ else if (nextPosition.getY() == currentPosition.getY() && nextPosition.getX() < currentPosition.getX()) {
            return Constant.TOP;
        } /*Identify Left*/ else if (nextPosition.getX() == currentPosition.getX() && nextPosition.getY() < currentPosition.getY()) {
            return Constant.LEFT;
        } /*Identify Right*/ else if (nextPosition.getX() == currentPosition.getX() && nextPosition.getY() > currentPosition.getY()) {
            return Constant.RIGHT;
        } /*Identify Bottom*/ else if (nextPosition.getX() > currentPosition.getX() && nextPosition.getY() == currentPosition.getY()) {
            return Constant.BOTTOM;
        } /*Identify Bottom Left*/ else if (nextPosition.getX() > currentPosition.getX() && nextPosition.getY() < currentPosition.getY()) {
            return Constant.BOTTOM_LEFT;
        } else {
            return Constant.BOTTOM_RIGHT;
        }
    }

    public boolean endGameHandler(Player opponent) {
        for (int i = 1; i <= opponent.getChessList().getLength(); i++) {
            Chess c = opponent.getChessList().getEntry(i);
            if (c instanceof King && c.getIsDead()) {
                return true;
            }
        }
        return false;
    }

    public Chess promotionHandler(ChessBoard map) {
        Chess newQueen = new Queen(chess.getIsWhite() ? "\u2655" : "\u265b", chess.getIsWhite(), chess.getPosition());
        playerEngine.getCurrentPlayer().getChessList().add(newQueen);
        chess.setIsDead(true);
        return newQueen;
    }

    public Chess searchChess(Position p) {
        for (int i = 1; i <= chessList.getLength(); i++) {
            Chess chess = chessList.getEntry(i);
            if (p.getX() == chess.getPosition().getX() && p.getY() == chess.getPosition().getY() && !chess.getIsDead()) {
                return chess;
            }
        }
        return null;
    }

    public ActionListener newInPassingListener(ChessGame game) {
        return new inPassingActionListener(chess, game);
    }

    public ActionListener newCastlingListener(ChessGame game) {
        return new castlingActionListener(chess, game);
    }

    public class inPassingActionListener implements ActionListener {

        private Chess chess;
        private ChessGame game;

        public inPassingActionListener(Chess chess, ChessGame game) {
            this.chess = chess;
            this.game = game;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            game.getMap().removePathButton();
            MainFrame.refreshFrame();
            //list();
            //Start get Handler;
            Chess opponentLastChess = game.getLastChess(!chess.getIsWhite());
            ListInterface<Position> availablePosition = ((Pawn) chess).inPassing(chessList, opponentLastChess);
            System.out.println("INPASSING SIZE: " + availablePosition.getLength());
            for (int i = 1; i <= availablePosition.getLength(); i++) {
                JButton button = new JButton();
                button.setBackground(new Color(200, 0, 0));
                button.addActionListener(new inPassingMoveListener(chess, game));
                System.out.println("Position :" + availablePosition.getEntry(i).toString());
                game.getMap().getPanelArray()[availablePosition.getEntry(i).getX() - 1][availablePosition.getEntry(i).getY() - 1].add(button);
            }
            MainFrame.refreshFrame();
        }
    }

    public class inPassingMoveListener implements ActionListener {

        private Chess chess;
        private ChessGame game;

        public inPassingMoveListener(Chess chess, ChessGame game) {
            this.chess = chess;
            this.game = game;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            game.setLastChess(chess, chess.getIsWhite());
            game.getMap().addNewMessage(playerEngine.getCurrentPlayer().getName() + " is using special skill \n - En Passant");
            for (int x = 0; x < 8; x++) {
                for (int y = 0; y < 8; y++) {
                    if (game.getMap().getPanelArray()[x][y].equals(((JButton) e.getSource()).getParent())) {
                        Position oldPosition = chess.getPosition();

                        System.out.println("search Enemey :" + chess.getPosition().getX() + " " + (y + 1));
                        Chess enemy = searchChess(new Position(chess.getPosition().getX(), y + 1));
                        chess.setPosition(new Position(x + 1, y + 1));
                        if (enemy != null) {
                            enemy.setIsDead(true);
                            game.getMap().addNewMessage(playerEngine.getNextPlayer().getName() + " 's " + enemy.getName() + " is dead");
                        }

                        //Remove Old Position
                        game.getMap().removeComponent(oldPosition);
                        //Remove Enemy Chess
                        game.getMap().removeComponent(enemy.getPosition());
                        //Update Own Location
                        game.getMap().updateNewLocation(chess);

                        game.getMap().removePathButton();
                        game.getMap().disableDeckBtn();
                        //Remove Listener on skill button
                        game.getMap().removeAllBtnListener();
                        MainFrame.refreshFrame();
                        game.proceedNextRound();
                    }
                }
            }
        }
    }

    public class castlingActionListener implements ActionListener {

        private Chess chess;
        private ChessGame game;

        public castlingActionListener(Chess chess, ChessGame game) {
            this.chess = chess;
            this.game = game;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            game.getMap().addNewMessage(playerEngine.getCurrentPlayer().getName() + " is using special skill \n - Castling");
            game.getMap().removePathButton();
            MainFrame.refreshFrame();

            //list();
            //Start get Handler;
            ListInterface<Position> availablePosition = ((King) chess).checkCastling(chessList);
            for (int i = 1; i <= availablePosition.getLength(); i++) {
                JButton button = new JButton();
                button.setBackground(new Color(200, 0, 0));
                button.addActionListener(new castlingMoveListener(chess, game));
                System.out.println("Position :" + availablePosition.getEntry(i).toString());
                game.getMap().getPanelArray()[availablePosition.getEntry(i).getX() - 1][availablePosition.getEntry(i).getY() - 1].add(button);
            }
            MainFrame.refreshFrame();
        }
    }

    public class castlingMoveListener implements ActionListener {

        private Chess chess;
        private ChessGame game;

        public castlingMoveListener(Chess chess, ChessGame game) {
            this.chess = chess;
            this.game = game;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            game.setLastChess(chess, chess.getIsWhite());
            for (int x = 0; x < 8; x++) {
                for (int y = 0; y < 8; y++) {
                    if (game.getMap().getPanelArray()[x][y].equals(((JButton) e.getSource()).getParent())) {

                        //Update King Position
                        Position oldPosition = chess.getPosition();
                        chess.setPosition(new Position(x + 1, y + 1));
                        chess.setIsFirstMove(false);
                        //Remove Old Position
                        game.getMap().removeComponent(oldPosition);
                        //Update Current Location
                        game.getMap().updateNewLocation(chess);

                        //Update Rook Position
                        int oldRookPositionY = 0;
                        int newRookPositionY = 0;

                        //Cast Right
                        if (chess.getPosition().getY() > oldPosition.getY()) {
                            oldRookPositionY = 8;
                            newRookPositionY = 6;
                        } //Cast Left
                        else {
                            oldRookPositionY = 1;
                            newRookPositionY = 4;
                        }

                        //Update Rook Position
                        Chess rook = searchChess(new Position(x + 1, oldRookPositionY));
                        game.getMap().removeComponent(new Position(x + 1, oldRookPositionY));
                        rook.setPosition(new Position(x + 1, newRookPositionY));
                        game.getMap().updateNewLocation(rook);
                        rook.setIsFirstMove(false);

                        game.getMap().removePathButton();

                        //Remove Listener on skill button
                        game.getMap().removeAllBtnListener();
                        game.getMap().disableDeckBtn();
                        MainFrame.refreshFrame();
                        game.proceedNextRound();
                    }
                }
            }
        }
    }
}
