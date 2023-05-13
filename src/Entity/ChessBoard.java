/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import ADT.ListInterface;
import ADT.List;
import Client.MainFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import Util.Constant;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Eugence
 */
public class ChessBoard extends JPanel {

    private JPanel[][] panelArray = new JPanel[8][8];
    private SkillDeck deck;
    private GameLog log;

    public ChessBoard(SkillDeck deck, GameLog log) {
        this.deck = deck;
        this.log = log;

        setLayout(new GridLayout(8, 8));
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                JPanel panel = new JPanel(new BorderLayout());
                if ((x + 1) % 2 != 0) {
                    if ((y + 1) % 2 == 0) {
                        panel.setBackground(Color.GRAY);
                    } else {
                        panel.setBackground(Color.white);
                    }
                } else {
                    if ((y + 1) % 2 != 0) {
                        panel.setBackground(Color.GRAY);
                    } else {
                        panel.setBackground(Color.white);
                    }
                }
                add(panel);
                panelArray[x][y] = panel;
            }
        }
        setVisible(true);
        setSize(700, 700);
    }

    public SkillDeck getDeck() {
        return deck;
    }

    public JPanel[][] getPanelArray() {
        return panelArray;
    }

    public void addNewMessage(String msg) {
        log.addNewMessage(msg);
    }

    public void splitLine() {
        log.addSplitLine();
    }

    public void removePathButton() {
        try {
            for (int x = 0; x < 8; x++) {
                for (int y = 0; y < 8; y++) {
                    Component[] array = panelArray[x][y].getComponents();
                    if (array.length != 0) {
                        for (int i = 0; i < array.length; i++) {
                            if (array[i] instanceof JButton) {
                                panelArray[x][y].remove(array[i]);
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
    }

    public void removeComponent(Position p) {
        Component[] comp = panelArray[p.getX() - 1][p.getY() - 1].getComponents();
        System.out.println("Removing Object " + p.toString() + ": " + comp.length);
        if (comp.length > 0) {
            for (Component c : comp) {
                panelArray[p.getX() - 1][p.getY() - 1].remove(c);
            }
        }

    }

    public void removeAllComponent(ListInterface<Chess> chessList) {
        for (int i = 1; i <= chessList.getLength(); i++) {
            Chess chess = chessList.getEntry(i);
            Component[] comp = panelArray[chess.getPosition().getX() - 1][chess.getPosition().getY() - 1].getComponents();
            for (Component c : comp) {
                panelArray[chess.getPosition().getX() - 1][chess.getPosition().getY() - 1].remove(c);
            }
        }
    }

    public void initLocation(ListInterface<Chess> chessList) {
        removeAllComponent(chessList);
        for (int i = 1; i <= chessList.getLength(); i++) {
            Chess chess = chessList.getEntry(i);
            if (!chess.getIsDead()) {
                JLabel label = new JLabel(chess.getSymbol(), JLabel.CENTER);
                label.setFont(new Font(null, Font.PLAIN, 55));
                label.setForeground(Color.black);
                //label.addMouseListener(chessEngine.getLabelMouseListener(chess, this));
                panelArray[chess.getPosition().getX() - 1][chess.getPosition().getY() - 1].add(label);
            }
        }
        MainFrame.refreshFrame();
    }

    public void updateNewLocation(Chess chess) {
        JLabel label = new JLabel(chess.getSymbol(), JLabel.CENTER);
        label.setFont(new Font(null, Font.PLAIN, 55));
        //label.addMouseListener(chessEngine.getLabelMouseListener(chess, this));
        label.setForeground(Color.black);
        panelArray[chess.getPosition().getX() - 1][chess.getPosition().getY() - 1].add(label);
    }

    public void disableSingleListener(Position pos) {
        Component[] comp = panelArray[pos.getX() - 1][pos.getY() - 1].getComponents();
        for (Component c : comp) {
            if (c instanceof JLabel) {
                if (c.getMouseListeners().length != 0) {
                    for (MouseListener ml : c.getMouseListeners()) {
                        c.removeMouseListener(ml);
                    }
                }
            }
        }
    }

    public void disableListener(ListInterface<Chess> chessList) {
        
        for (int i = 1; i <= chessList.getLength(); i++) {
            Chess chess = chessList.getEntry(i);
            Component[] comp = panelArray[chess.getPosition().getX() - 1][chess.getPosition().getY() - 1].getComponents();
            for (Component c : comp) {
                if (c instanceof JLabel) {
                    if (c.getMouseListeners().length != 0) {
                        c.removeMouseListener(c.getMouseListeners()[0]);
                    }
                }
            }
        }
     }

    public void addListener(Chess chess, MouseListener listener) {
        if (!chess.getIsDead()) {
            Component[] comp = panelArray[chess.getPosition().getX() - 1][chess.getPosition().getY() - 1].getComponents();
            for (Component c : comp) {
                if (c instanceof JLabel) {
                    c.addMouseListener(listener);
                }
            }
        }
        MainFrame.refreshFrame();
    }

    public void disableDeckBtn() {
        deck.disableBtn();
    }

    public void enableDeckBtn(int button, ActionListener al) {
        if (button == Constant.IN_PASSING) {
            System.out.println("Enable InPassing");
            deck.setInPassingListener(al);
            deck.enableInPassing();
        } else if (button == Constant.CASTLING) {
            deck.setCastlingListener(al);
            deck.enableCastlingBtn();
        }
    }

    public void removeAllBtnListener() {
        deck.removeListener();
    }
}
