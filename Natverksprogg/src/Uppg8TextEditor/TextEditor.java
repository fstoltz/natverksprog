/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Uppg8TextEditor;

import java.awt.BorderLayout;
import static java.awt.BorderLayout.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author fstoltz
 */
public class TextEditor implements ActionListener{
    
    // FRAME
    JFrame frame = new JFrame();
    //PANELS
    JPanel mainPanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    //COMPONENTS
    JTextArea area = new JTextArea();
    JTextField fileTextField = new JTextField("C:\\Users\\fstoltz\\Desktop\\ok", 20);
    JButton readButton;
    JButton writeButton;
    JButton exitButton;
    JButton printDocButton;
    
    TextEditor(){
        mainPanel.setLayout(new BorderLayout());
        buttonPanel.setLayout(new FlowLayout());
        
        //Setup area (TextArea)
        area.setFont(new Font("Fira Code", Font.PLAIN, 14));
        
        //CREATE COMPONENETS
        readButton = new JButton("Read file");
        writeButton = new JButton("Save(write)");
        exitButton = new JButton("Exit");
        printDocButton = new JButton("Send to Printer");
        JLabel fileNameLabel = new JLabel("File path:");
        
        /*Setup Action Listeners*/
        readButton.addActionListener(this);
        writeButton.addActionListener(this);
        exitButton.addActionListener(this);
        printDocButton.addActionListener(this);
        
        /*
        Add components to the frame(canvas)
        Panel is treated as a componenet
        */
        buttonPanel.add(fileNameLabel);
        buttonPanel.add(fileTextField);
        buttonPanel.add(readButton);
        buttonPanel.add(writeButton);
        buttonPanel.add(printDocButton);
        buttonPanel.add(exitButton);
        
        
        
        frame.add(mainPanel); //Add mainPanel to the frame
        frame.add(buttonPanel, NORTH); //Add buttonPanel to
                                       //NORTH of mainPanel
        mainPanel.add(area, CENTER); //Add the area to the frame
        
        /*Set layout type for mainPanel*/
        
        
        /*CONFIG VALUES FOR FRAME(canvas)*/
        frame.setSize(800, 400); //Set the size of the frame(canvas)
        frame.setVisible(true);
        frame.setDefaultCloseOperation(3); //3 means exit
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String fullFileString = "";
        
        if(e.getSource() == this.readButton){ // IF 'OPEN' BUTTON WAS PRESSED
            String filePath = this.fileTextField.getText();
            System.out.println("SHOULD PRINT THE FILE CONTENTS TO THE TEXTAREA" + filePath);
            try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
                String line = null;
                while((line = br.readLine()) != null){
                    fullFileString += line + "\n";
                }
                area.setText(fullFileString);
            } catch (Exception ex){ex.printStackTrace();}
        }

        // IF 'SAVE' BUTTON WAS PRESSED
        // WRITE area contents to the file path that was specified when opening
        else if(e.getSource() == this.writeButton){
            int response = JOptionPane.showConfirmDialog(null, "Are you sure you wish to write changes to file? **NO UNDO**");
            
            //write contents to file
            if(response == 0){ //answered YES
                try (PrintWriter out = new PrintWriter(this.fileTextField.getText())) {
                    out.println(area.getText());
                } catch(Exception ex){  ex.printStackTrace();  }
            } else {
                //do nothing, no changes made
            }
        }
        
        else if(e.getSource() == this.exitButton){
            System.exit(0);
        }
        
        else if(e.getSource() == this.printDocButton){
            try {
                area.print();
            } catch (PrinterException ex) {
                Logger.getLogger(TextEditor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //System.err.println(area.getText()); //Print the entered text
    }
    
    
    public static void main(String[] args){
        TextEditor txtEd = new TextEditor();
    }

    
}
