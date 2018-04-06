/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Minilabs;

import java.awt.BorderLayout;
import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.NORTH;
import static java.awt.BorderLayout.SOUTH;
import java.awt.GridLayout;
//import static java.awt.BorderLayout.*;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author fstoltz
 */
public class BorderLayoutDemo{
    
    
    
    public static void main(String[] args){
        JFrame frm = new JFrame("Frame");
        JPanel pnl = new JPanel();
        
        pnl.setLayout(new BorderLayout());
        frm.add(pnl);
        
        frm.add(new JLabel("This is my text"), NORTH);
        pnl.add(new JButton("Öppna fil"), NORTH);
        pnl.add(new JButton("Do That"), CENTER);
        pnl.add(new JButton("hjere ia m"), SOUTH);
        pnl.add(new JButton("Do this"), SOUTH);
        
        
        frm.pack();
        //frm.setSize(500,500);
        frm.setLocation(600,300);
        frm.setVisible(true);
        frm.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
