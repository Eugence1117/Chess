/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import Client.MainFrame;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
/**
 *
 * @author Eugence
 */
public class SkillDeck extends JPanel{    
    
    private JLabel title = new JLabel("Skill Deck");
    private JButton inPassingBtn = new JButton("In Passing");
    private JButton castlingBtn = new JButton("Castling");
    
    public SkillDeck(){
        
        GridLayout layout = new GridLayout(3,1);
        layout.setHgap(100);
        layout.setVgap(0);
        
        
        GridLayout btnLayout = new GridLayout(1,4);
        btnLayout.setHgap(100);
        JPanel btnPanel = new JPanel(btnLayout);
        
        btnPanel.add(new JLabel());
        btnPanel.add(inPassingBtn);
        btnPanel.add(castlingBtn);
        btnPanel.add(new JLabel());
        
        title.setFont(new Font("", 0, 15));
        title.setHorizontalAlignment(JLabel.CENTER);
        
        setSize(800,100);
        setLayout(layout);
        
        add(title);
        add(btnPanel);
        
        disableBtn();
    }
    
    public void disableBtn(){
        inPassingBtn.setEnabled(false);
        castlingBtn.setEnabled(false);
    }
    
    public void enableInPassing(){
        inPassingBtn.setEnabled(true);
    }
    
    public void enableCastlingBtn(){
        castlingBtn.setEnabled(true);
    }
    
    public void removePreviousButton(ChessBoard map){
       map.removePathButton();
       MainFrame.refreshFrame();   
    }
    
    public void setInPassingListener(ActionListener al){
        inPassingBtn.addActionListener(al);
    }
    
    public void setCastlingListener(ActionListener al){
        castlingBtn.addActionListener(al);
    }
    
    public void removeListener(){
        ActionListener[] array = inPassingBtn.getActionListeners();
        if(array.length > 0){
            for(int i = 0 ; i < array.length; i++){
                inPassingBtn.removeActionListener(array[i]);
            }
        }
        
        array = castlingBtn.getActionListeners();
         if(array.length > 0){
            for(int i = 0 ; i < array.length; i++){
                castlingBtn.removeActionListener(array[i]);
            }
        }
        
    }
}
