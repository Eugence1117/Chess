/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Client.MainFrame;
import java.awt.event.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Eugence
 */
public class MenuListener {
    
    public ActionListener getNewGameListener(){
        return new newGameListener();
    }
    
    public ActionListener getAboutUsListener(){
        return new aboutUsListener();
    }
    
    public ActionListener getRulesListener(){
        return new rulesListener();
    }
    
    private class newGameListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            int result = JOptionPane.showConfirmDialog(null, "Start a new game ?", "Message", JOptionPane.YES_NO_OPTION);
            if (result == 0) {
                MainFrame.reset();
                MainFrame.main(null);
            }
        }
    }
    
    private class aboutUsListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            String msg = "This chess is developed by: \n 1. Eugence \n 2. Chai Qing Hao \n 3. Lee Shi Hoi \n 4. Ng YenZhang \n 5. Tai Wei Lin";
            JOptionPane.showMessageDialog(null,msg,"About Us",JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private class rulesListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            String msg = "Rules of chess: \n";
            msg += "1. First player is decided randomly. \n";
            msg += "2. Moving is compulsory, it is illegal to skip a turn. \n";
            msg += "3. Game will end once one of the king is dead. \n";
            msg += "4. Promotion is a skill where pawn will promoted to queen once advances to the eighth rank. \n";
            msg += "5. Castling is a move where moving the king two squares along the first rank toward a rook, then placing the rook on the last square that the king just crossed. \n";
            msg += "6. Pawn only allow to make two-step advance when it is first move. \n";
            msg += "\n For More Information, please visit https://en.wikipedia.org/wiki/Chess \n";
            JOptionPane.showMessageDialog(null,msg,"Rules of chess",JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
