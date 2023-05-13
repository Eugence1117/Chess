/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import ADT.*;
import java.awt.Component;
import javax.swing.JLabel;

/**
 *
 * @author Eugence
 */
public class PlayerEngine {
    
    private QueueInterface<Player> playerRound;
    private Player currentPlayer;
    private ListInterface<Player> playerList;
    
    public PlayerEngine(ListInterface<Player> playerList){
        this.playerList = playerList;
        this.currentPlayer = null;
        addPlayerToQueue();
    }
    
    public ListInterface<Player> getPlayerList() {
        return playerList;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    
    public Player getNextPlayer(){
        return playerRound.getFront();
    }
    
    public void addPlayerToQueue(){
        playerRound = new Queue(10);
        for(int i = 1; i <= playerList.getLength();i++){
            Player p = playerList.getEntry(i);
            playerRound.enqueue(p);
        }
    }
    
    public Player nextRound() {
       
        /*Get Next Player*/
        currentPlayer = playerRound.dequeue();
        
        System.out.println("Next Round:" + currentPlayer.getName());
        playerRound.enqueue(currentPlayer);

        return currentPlayer;
    }
    
    
}
