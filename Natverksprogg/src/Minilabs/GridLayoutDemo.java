/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Minilabs;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author fstoltz
 */
public class GridLayoutDemo {
    
    
    public static void main(String[] args){
        JFrame frm = new JFrame("Frame");
        JPanel pnl = new JPanel();
        
        pnl.setLayout(new GridLayout(2,6));
        frm.add(pnl);
        
        pnl.add(new JButton("Öppna fil"));
        pnl.add(new JButton("Do That"));
        pnl.add(new JButton("Do this"));
        pnl.add(new JButton("Say agian?"));
        pnl.add(new JButton("Say agian?"));
        pnl.add(new JButton("Say agian?"));
        pnl.add(new JButton("Say agian?"));
        pnl.add(new JButton("Say agian?"));
        pnl.add(new JButton("Say agian?"));
        pnl.add(new JButton("Say agian?"));
        
        
        
        frm.pack();
        frm.setLocation(600,300);
        frm.setVisible(true);
        
    }
    
    
}
