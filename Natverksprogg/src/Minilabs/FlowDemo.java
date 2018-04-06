/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Minilabs;

import java.awt.BorderLayout;
import static java.awt.BorderLayout.*;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import javafx.event.ActionEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author fstoltz
 */
public class FlowDemo extends JFrame implements ActionListener{
    
    JButton butn = new JButton("Click me");
    JLabel lbl = new JLabel("This is a label!");
    JButton nextButton = new JButton("You can click here aswell!");
    JTextField textField;
    JTextArea area = new JTextArea(10,60);
    
    FlowDemo(){
        this.textField = new JTextField("Enter here:");
        JFrame frm = new JFrame("Frame");
        JPanel pnl = new JPanel();
        area.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        
        
        pnl.add(area, BorderLayout.CENTER);
        nextButton.addActionListener(this);
        
        pnl.setLayout(new FlowLayout());
        frm.add(pnl);
        
        pnl.add(this.textField);
        //nextButton.addActionListener(this);
        butn.addActionListener(this);
        pnl.add(this.butn);
        pnl.add(this.lbl);
        pnl.add(this.nextButton);
        
        //frm.pack();
        frm.setSize(700,500);
        frm.setLocation(600,300);
        frm.setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    @Override
    public void actionPerformed(java.awt.event.ActionEvent e){
        if (e.getSource() == butn){
            
        } 
        System.err.println(area.getText());
        
        if(this.lbl.getText().equalsIgnoreCase("This is a label!")){
            this.lbl.setText("Switched!");
        } else {
            this.lbl.setText("This is a label!");
        }
    }
    
    
    public static void main(String[] args){
        FlowDemo myGUI = new FlowDemo();
    }
}

