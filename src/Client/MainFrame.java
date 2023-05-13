/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Entity.SkillDeck;
import Entity.GameLog;
import Entity.ChessBoard;
import Entity.ChessGame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JTextField;

/**
 *
 * @author Eugence
 */
public class MainFrame {

    private static JDialog menuDialog;
    private static JFrame mainFrame;
    private static JMenuBar menuBar;

    public static void main(String args[]) {
        mainFrame = new JFrame("Chess");
        menuDialog = new JDialog(mainFrame,"Chess Game");
        menuBar = new JMenuBar();
        
        MenuListener menuListener = new MenuListener();
        JMenu gameMenu = new JMenu("Game");
        gameMenu.setMnemonic(KeyEvent.VK_G);
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic(KeyEvent.VK_H);

        JMenuItem newGame = new JMenuItem("New Game");
        newGame.setMnemonic(KeyEvent.VK_N);
        newGame.addActionListener(menuListener.getNewGameListener());
        gameMenu.add(newGame);

        JMenuItem aboutUs = new JMenuItem("About");
        aboutUs.setMnemonic(KeyEvent.VK_A);
        aboutUs.addActionListener(menuListener.getAboutUsListener());
        JMenuItem rule = new JMenuItem("Game Rules");
        rule.setMnemonic(KeyEvent.VK_R);
        rule.addActionListener(menuListener.getRulesListener());
        helpMenu.add(aboutUs);
        helpMenu.add(rule);

        menuBar.add(gameMenu);
        menuBar.add(helpMenu);

        Image icon = Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir") + "\\src\\media\\icon.png");
        mainFrame.setIconImage(icon);
        menuDialog.setIconImage(icon);

        SkillDeck deck = new SkillDeck();
        GameLog log = new GameLog();
        ChessBoard map = new ChessBoard(deck, log);

        //Include ChessBoard into Jframe
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setJMenuBar(menuBar);
        mainFrame.add(map, BorderLayout.CENTER);
        mainFrame.add(deck, BorderLayout.SOUTH);
        mainFrame.add(log, BorderLayout.EAST);
        mainFrame.setVisible(true);
        mainFrame.setSize(1100, 800);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getPlayerName(map);
        
    }

    public static void reset() {
        mainFrame.dispose();
        menuDialog.dispose();
    }

    public static void refreshFrame() {
        mainFrame.invalidate();
        mainFrame.validate();
        mainFrame.repaint();
    }

    public static void getPlayerName(ChessBoard map) {

        JTextField player1Name = new JTextField();
        JTextField player2Name = new JTextField();
        JButton startBtn = new JButton("Start Game");

        //TODO Design Prompt Player name message 
        menuDialog.setVisible(true);
        //menuDialog.setModal(true);
        menuDialog.setSize(350, 200);
        menuDialog.setLocationRelativeTo(null);

        JPanel heading = new JPanel(new GridLayout(3, 1));
        heading.add(new JLabel("Welcome to Chess", JLabel.CENTER), BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Player 1 : "));
        panel.add(player1Name);
        panel.add(new JLabel("Player 2 : "));
        panel.add(player2Name);
        heading.add(panel, BorderLayout.CENTER);

        startBtn.addActionListener(new startEvent(player1Name,player2Name,map));
        heading.add(startBtn);

        menuDialog.add(heading);
    }

    public static class startEvent implements ActionListener {

        private JTextField player1Name;
        private JTextField player2Name;
        private ChessBoard map;

        public startEvent(JTextField player1Name, JTextField player2Name, ChessBoard map) {
            this.player1Name = player1Name;
            this.player2Name = player2Name;
            this.map = map;
        }
        
        @Override
        public void actionPerformed(ActionEvent e) {
            menuDialog.setVisible(false);
            ChessGame game = new ChessGame(map);
            String name1 = player1Name.getText().equals("") ? "Player 1" : player1Name.getText();
            String name2 = player2Name.getText().equals("") ? "Player 2" : player2Name.getText();
            game.activateGame(name1, name2);
        }
    }

}
