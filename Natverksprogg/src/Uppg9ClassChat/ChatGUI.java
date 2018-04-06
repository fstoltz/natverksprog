/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Uppg9ClassChat;

import java.awt.BorderLayout.*;
import java.awt.*;
import static java.awt.BorderLayout.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author fstoltz
 */
public class ChatGUI implements ActionListener{
    /*This class will build a chat GUI*/
    JFrame frame = new JFrame(); //FRAME
    /*PANELS*/
    JPanel mainPanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    /*BUTTONS*/
    JButton conButton, disconButton; //NORTH
    
    /*TEXT AREA*/
    JTextArea chatArea = new JTextArea();
    JTextField inputField = new JTextField("asd", 20);
    
    ChatGUI(){
        mainPanel.setLayout(new BorderLayout());
        buttonPanel.setLayout(new FlowLayout());
        chatArea.setFont(new Font("Fira Code", Font.PLAIN, 14));
        
        /*Instansiate Buttons*/
        conButton = new JButton("Connect");
        disconButton = new JButton("Disconnect");
        
        /*Add Action Listeners*/
        conButton.addActionListener(this);
        disconButton.addActionListener(this);
        inputField.addActionListener(this);

        /*Add components to the panel*/
        buttonPanel.add(conButton);
        buttonPanel.add(disconButton);
        
        
        
        /*Add panel to the frame*/
        frame.add(mainPanel);
        frame.add(buttonPanel, NORTH);
        
        mainPanel.add(chatArea, CENTER);
        
        mainPanel.add(inputField, SOUTH);
        
        /*CONFIG VALUES FOR FRAME(canvas)*/
        frame.setSize(600, 400); //Set the size of the frame(canvas)
        frame.setVisible(true);
        frame.setDefaultCloseOperation(3); //3 means exit
    }
    
    
    
    
    public void startListening(){
        //create a Multicast socket on a separate thread that sends the payload
        //of incoming packets to chatArea
    }
    
    
    public void stopListening(){
        
    }
    
    
    public void sendMessage(String msg) throws SocketException, IOException{
        
        InetAddress group = InetAddress.getByName("234.235.236.237");
        int randomNum = ThreadLocalRandom.current().nextInt(30000, 40000);
        
        MulticastSocket s = new MulticastSocket(12540); //Create a multicast-UDP-Socket
        s.joinGroup(group); //Join the specific IP address group
        
        DatagramPacket myPkt = new DatagramPacket(msg.getBytes(), msg.length(), group, 12540); //Create the packet
        s.send(myPkt); //Send the packet
        
        
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        //CALL METHODS FOR HANDLING SOCKETS AND MULTICAST STUFF
        
        if(e.getSource() == conButton){
            System.out.println("START LISTENING ON MULTICAST SOCKET(new thread), "
                    + "PRINTING MESSAGES ONTO THE TEXT AREA");
            this.startListening();
        }
        
        else if(e.getSource() == disconButton){
            System.out.println("STOP LISTENING TO MULTICAST SOCKET");
        }
        
        else if(e.getSource() == inputField) {
            try {
                this.sendMessage("FredrikS: " + inputField.getText());//SEND THIS TO THE MULTICAST SOCKET
                inputField.setText(""); // EMPTY THE BOX BECAUSE MESSAGE HAS BEEN SENT
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
        }
        
    }
    
    
}
