/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Client.MainFrame;
import ADT.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import Util.Constant;
import javax.swing.JOptionPane;
import java.util.Iterator;
/**
 *
 * @author Eugence
 */
public class ChessEngine {

    private ListInterface<Player> playerList;
    private Chess lastWhiteChessMoved;
    private Chess lastBlackChessMoved;
    private SpecialSkill event;

    public ChessEngine(ListInterface<Player> playerList) {
        this.playerList = playerList;
        this.lastBlackChessMoved = null;
        this.lastWhiteChessMoved = null;
    }

    public Chess getLastMoveChess(boolean isWhite) {
        return isWhite ? lastWhiteChessMoved : lastBlackChessMoved;
    }

    public void setLastMoveChess(Chess lastMoveChess, boolean isWhite) {
        if (isWhite) {
            lastWhiteChessMoved = lastMoveChess;
        } else {
            lastBlackChessMoved = lastMoveChess;
        }
    }

    public MouseListener getLabelMouseListener(Chess chess, ChessGame game) {
        return new labelMouseListener(chess, game);
    }

    public ActionListener getMoveBtnListener(Chess chess, ChessGame game) {
        return new moveBtnListener(chess, game);
    }

    public ListInterface<Chess> getChessList() {
        ListInterface<Chess> chessList = new List();
        chessList.addAll(playerList.getEntry(1).getChessList());
        chessList.addAll(playerList.getEntry(2).getChessList());
        return chessList;
    }

    public Chess searchChess(Position p) {
        ListInterface<Chess> chessList = getChessList();

        for (int i = 1; i <= chessList.getLength(); i++) {
            Chess chess = chessList.getEntry(i);
            if (p.getX() == chess.getPosition().getX() && p.getY() == chess.getPosition().getY() && !chess.getIsDead()) {
                return chess;
            }
        }
        return null;
    }

    public boolean identifyHostile(Position crPosition, boolean isWhite) {
        if (searchChess(crPosition).getIsWhite() != isWhite) {
            return true;
        }
        return false;
    }

    public ListInterface<Position> checkLocation() {
        ListInterface<Position> hasObject = new List();
        ListInterface<Chess> chessList = getChessList();

        for (int i = 1; i <= chessList.getLength(); i++) {
            Chess c = chessList.getEntry(i);
            if (!c.getIsDead()) {
                hasObject.add(c.getPosition());
            }
        }
        return hasObject;
    }

    public void updateLocation(Chess chess, Position currentPos, ChessBoard map) {

        map.removeComponent(currentPos);

        /*Remove Object in new position*/
        map.removeComponent(chess.getPosition());

        //Update Location
        map.updateNewLocation(chess);

        /*Done Operation*/
        MainFrame.refreshFrame();
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

    public class labelMouseListener implements MouseListener {

        private Chess chess;
        private ChessGame game;

        public labelMouseListener(Chess chess, ChessGame game) {
            this.chess = chess;
            this.game = game;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            game.getMap().removeAllBtnListener();
            game.getMap().disableDeckBtn();
            game.getMap().removePathButton();
            MainFrame.refreshFrame();

            event = new SpecialSkill(chess, getChessList(), game.getPlayerEngine());
            int eventCode = event.preCheckSpecialSkill(getLastMoveChess(!chess.getIsWhite()));
            System.out.println("EVENT :" + eventCode);

            ActionListener al = null;
            switch (eventCode) {
                case Constant.IN_PASSING: {
                    al = event.newInPassingListener(game);
                    break;
                }
                case Constant.CASTLING: {
                    al = event.newCastlingListener(game);
                    break;
                }
            }
            if (al != null) {
                game.getMap().enableDeckBtn(eventCode, al);
            }

            /*Get Range*/
            DuplicateCheckInterface<Position> moveRange = chess.moveRange();
            DuplicateCheckInterface<Position> attackRange = chess.attackRange();

            /*Organise in direction*/
            ListInterface<StackInterface<Position>> directionList = chess.organizeMoveDirection(moveRange);

            /*Get Object Location on board*/
            ListInterface<Position> objectLocation = checkLocation();

            DuplicateCheckInterface<Position> removedObject = new DuplicateCheckArray();
            DuplicateCheckInterface<Position> hostileLocation = new DuplicateCheckArray();

            if (chess instanceof Knight) {
                Iterator iterator = moveRange.getIterator();
                while (iterator.hasNext()) {
                    Position range = (Position) iterator.next();
                    for (int j = 1; j <= objectLocation.getLength(); j++) {
                        Position object = objectLocation.getEntry(j);
                        if (range.equals(object) && !identifyHostile(object, chess.getIsWhite())) {
                            int direction = identifyDirection(range, chess.getPosition());
                            StackInterface<Position> directionLocation = directionList.getEntry(direction);
                            System.out.println("Object Remove Status: " + directionLocation.remove(range));
                        } else if (range.equals(object) && identifyHostile(object, chess.getIsWhite())) {
                            hostileLocation.add(object);
                        }
                    }
                }
            } else if (chess instanceof Pawn) {
                /*Calculate Move range*/
                Iterator moveRangeIterator = moveRange.getIterator();
                while (moveRangeIterator.hasNext()) {
                    Position range = (Position) moveRangeIterator.next();
                    for (int j = 1; j <= objectLocation.getLength(); j++) {
                        Position object = objectLocation.getEntry(j);
                        if (range.equals(object)) {
                            if (!removedObject.isEmpty()) {
                                boolean isSkiped = false;
                                if (removedObject.getIndex(range) != -1) {
                                    System.out.printf("Skip this position [%d][%d]\n", 
                                                      range.getX(), range.getY());
                                    isSkiped = true;
                                }
                                if (!isSkiped) {
                                    int direction = identifyDirection(range, chess.getPosition());
                                    StackInterface<Position> directionLocation = directionList.getEntry(direction);
                                    while (!directionLocation.peek().equals(object)) {
                                        removedObject.add(directionLocation.pop());
                                    }

                                    if (directionLocation.peek().equals(object)) {
                                        removedObject.add(directionLocation.pop());
                                    }
                                }
                            } else {
                                int direction = identifyDirection(range, chess.getPosition());
                                StackInterface<Position> directionLocation = directionList.getEntry(direction);
                                while (!directionLocation.peek().equals(object)) {
                                    removedObject.add(directionLocation.pop());
                                }

                                if (directionLocation.peek().equals(object)) {
                                    removedObject.add(directionLocation.pop());
                                }
                            }
                        }
                    }
                }

                System.out.println("Attack" + attackRange);

                /*Calculate attack range*/
                Iterator attackRangeIterator = attackRange.getIterator();
                while (attackRangeIterator.hasNext()) {
                    Position range = (Position) attackRangeIterator.next();
                    for (int j = 1; j <= objectLocation.getLength(); j++) {
                        Position object = objectLocation.getEntry(j);
                        if (range.equals(object)) {
                            boolean isHostile = identifyHostile(object, chess.getIsWhite());
                            if (isHostile) {
                                hostileLocation.add(object);
                                int direction = identifyDirection(range, chess.getPosition());
                                directionList.getEntry(direction).push(object);
                            } else {
                                int direction = identifyDirection(range, chess.getPosition());
                                StackInterface<Position> directionLocation = directionList.getEntry(direction);
                                for (int z = 1; z <= directionLocation.size(); z++) {
                                    Position ally = directionLocation.get(z);
                                    if (ally.equals(object)) {
                                        directionList.getEntry(direction).remove(ally);
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                System.out.println("Move Range = " + moveRange.toString());
                /*Calculate Move range*/
                Iterator iterator = moveRange.getIterator();
                while (iterator.hasNext()) {
                    Position range = (Position) iterator.next();
                    for (int j = 1; j <= objectLocation.getLength(); j++) {
                        Position object = objectLocation.getEntry(j);
                        if (range.equals(object)) {
                            boolean isHostile = identifyHostile(object, chess.getIsWhite());
                            if (!removedObject.isEmpty()) {
                                boolean isSkiped = false;
                                if (removedObject.getIndex(range) != -1) {
                                    System.out.printf("Skip this position [%d][%d]\n", range.getX(), range.getY());
                                    isSkiped = true;
                                }
                                if (!isSkiped) {
                                    int direction = identifyDirection(range, chess.getPosition());
                                    System.out.println("Get Direction " + direction + 
                                                       " Position: " + chess.getPosition());
                                    StackInterface<Position> directionLocation = directionList.getEntry(direction);
                                    while (!directionLocation.peek().equals(object)) {
                                        System.out.println("Item Removed: " + directionLocation.peek() + 
                                                           " Object: " + object + 
                                                           " isSame: " + directionLocation.peek().equals(object));
                                        removedObject.add(directionLocation.pop());
                                    }
                                    if (!isHostile) {
                                        if (directionLocation.peek().equals(object)) {
                                            System.out.println("Item Removed: " + directionLocation.peek() + 
                                                           " Object: " + object + 
                                                           " isSame: " + directionLocation.peek().equals(object));
                                            removedObject.add(directionLocation.pop());
                                        }
                                    } else {
                                        hostileLocation.add(object);
                                    }
                                }
                            } else {
                                //System.out.println(range.toString() + " has object");
                                int direction = identifyDirection(range, chess.getPosition());
                                StackInterface<Position> directionLocation = directionList.getEntry(direction);
                                //System.out.println("DirectionPath " + directionLocation);
                                while (!directionLocation.peek().equals(object)) {
                                    System.out.println("Item Removed: " + directionLocation.peek() + 
                                                           " Object: " + object + 
                                                           " isSame: " + directionLocation.peek().equals(object));
                                    removedObject.add(directionLocation.pop());
                                }

                                if (!isHostile) {
                                    if (directionLocation.peek().equals(object)) {
                                        System.out.println("Item Removed: " + directionLocation.peek() + 
                                                           " Object: " + object + 
                                                           " isSame: " + directionLocation.peek().equals(object));
                                        removedObject.add(directionLocation.pop());
                                    }
                                } else {
                                    hostileLocation.add(object);
                                }
                            }
                        }
                    }
                }
            }

            /*Place JButton in Move Range*/
            for (int i = 1; i <= directionList.getLength(); i++) {
                StackInterface<Position> stacker = directionList.getEntry(i);
                for (int j = 1; j <= stacker.size(); j++) {
                    JButton button = new JButton();
                    button.setBackground(new Color(200, 0, 0));
                    button.addActionListener(new moveBtnListener(chess, game));
                    if (!hostileLocation.isEmpty() && hostileLocation.getIndex(stacker.get(j)) != -1) {
                        Position movePosition = stacker.get(j);
                        for (Component c : game.getMap().getPanelArray()
                             [movePosition.getX() - 1][movePosition.getY() - 1].getComponents()) {
                            if (c instanceof JLabel) {
                                button.setText(((JLabel) c).getText());
                                //System.out.println(map.getIcon());
                                button.setFont(new Font(null, Font.PLAIN, 55));
                            }
                        }
                    }
                    game.getMap().getPanelArray()
                                  [stacker.get(j).getX() - 1][stacker.get(j).getY() - 1].add(button);
                }
            }
            /*Refresh JFrame*/
            MainFrame.refreshFrame();
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }
    }

    public class moveBtnListener implements ActionListener {

        private Chess chess;
        private ChessGame game;

        public moveBtnListener(Chess chess, ChessGame game) {
            this.chess = chess;
            this.game = game;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            setLastMoveChess(chess, chess.getIsWhite());
            game.getMap().disableDeckBtn();
            int x = 0;
            int y = 0;
            for (x = 0; x < 8; x++) {
                for (y = 0; y < 8; y++) {
                    try {
                        if (game.getMap().getPanelArray()[x][y].equals(((JButton) e.getSource()).getParent())) {
                            Position existPos = chess.getPosition();

                            /*Remove enemy from on-going location*/
                            Chess enemy = searchChess(new Position(x + 1, y + 1));
                            System.out.print("Searching Enemy:");
                            if (enemy != null) {
                                System.out.println("TRUE");
                                enemy.setIsDead(true);
                                game.getMap().addNewMessage(game.getPlayerEngine().getNextPlayer().getName() + " 's "
                                        + enemy.getName() + " is dead");
                            }

                            if (chess instanceof Pawn) {
                                ((Pawn) chess).setMoveTwoBlock(((Pawn) chess).isDoubleMove(new Position(x + 1, y + 1)));
                                if (((Pawn) chess).isMoveTwoBlock()) {
                                    ((Pawn) chess).setLastMove(2);
                                } else {
                                    ((Pawn) chess).setLastMove(1);
                                }
                            }

                            if (chess.getIsFirstMove()) {
                                chess.setIsFirstMove(false);
                            }

                            chess.setPosition(new Position(x + 1, y + 1));
                            game.getMap().removePathButton();

                            //Event
                            updateLocation(chess, existPos, game.getMap());

                            event = new SpecialSkill(chess, getChessList(), game.getPlayerEngine());
                            int eventCode = event.postCheckSpecialSkill(game.getMap());
                            System.out.println("Event :" + eventCode);

                            if (eventCode != Constant.END_GAME) {
                                game.proceedNextRound();
                            } else {
                                game.getMap().disableListener(getChessList());
                                int result = JOptionPane.showConfirmDialog(null, "Start a new game ?", "Message", JOptionPane.YES_NO_OPTION);
                                MainFrame.reset();
                                if (result == 0) {
                                    MainFrame.main(null);
                                }

                            }

                        }
                    } catch (Exception ex) {
                        System.out.println("Exception: " + ex.getMessage());
                    }
                }
            }
        }
    }

}
