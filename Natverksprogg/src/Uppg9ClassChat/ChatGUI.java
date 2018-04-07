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
import java.net.*;
import java.time.LocalTime;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author fstoltz
 */
public class ChatGUI extends Thread implements ActionListener{
    /*This class will build a chat GUI*/
    JFrame frame = new JFrame(); //FRAME
    /*PANELS*/
    JPanel mainPanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JPanel inputPanel = new JPanel();
    /*BUTTONS*/
    JButton conButton, disconButton; //NORTH
    
    /*LABELS*/
    JLabel inputLabel = new JLabel("Set username: ");
    
    
    /*TEXT AREA*/
    JTextArea chatArea = new JTextArea();
    JTextField inputField = new JTextField("", 30);
    
    /*hold text history*/
    String textHistory = "";
    
    /*THREADS??? Chat GUI have a receive socket thread??*/
    Thread receiveThread = new Thread(this);
    
    /*set username*/
    boolean usernameSet = false;
    
    /*Connected or not tracker*/
    boolean connected = false;
//  int randomNum = ThreadLocalRandom.current().nextInt(30000, 40000);
    

    ChatGUI() throws UnknownHostException, IOException {
        
        this.receiveThread.setName("REC-Thread");
        
        mainPanel.setLayout(new BorderLayout());
        buttonPanel.setLayout(new FlowLayout());
        inputPanel.setLayout(new FlowLayout());
        
        chatArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        
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
        
        mainPanel.add(inputPanel, SOUTH);
        
        inputPanel.add(inputLabel);
        inputPanel.add(inputField);
        
        
        //mainPanel.add(inputField, SOUTH);
        //mainPanel.add(this.inputLabel, SOUTH);
        
        /*CONFIG VALUES FOR FRAME(canvas)*/
        frame.setSize(750, 400); //Set the size of the frame(canvas)
        frame.setVisible(true);
        frame.setDefaultCloseOperation(3); //3 means exit
        
        
    }
    
    
    @Override
    public void run(){
        
        try {
            InetAddress grp = InetAddress.getByName("234.235.236.237");
            MulticastSocket recSocket = new MulticastSocket(12540);
            recSocket.joinGroup(grp);
            
            byte[] buffer = new byte[256];
            DatagramPacket myPkt = new DatagramPacket(buffer, buffer.length); //Create the packet
            System.out.println("I'm heeeere");
            
            this.textHistory += ">>> Connected!\n";
            this.chatArea.setText(this.textHistory);
            while(true){ //THE THREADS LOCKS AT THIS WHILE LOOP, DOES IT FOREVER, .RECEIVE() is blocking, waits for a packet to arrive, then prints it, then starts waiting again
                //and the main thread is handling the ActionEvents/Listeners
                this.connected = true;
                recSocket.receive(myPkt); //Receive the packet
                this.connected = false;
                String received = new String(myPkt.getData(), 0, myPkt.getLength());
                LocalTime time = LocalTime.now();
                this.textHistory += time.getHour() + ":" + time.getMinute() + " " + received + "\t\t\t(from: " + myPkt.getSocketAddress() + ")\n";
                chatArea.setText(this.textHistory);
            }
            
            //System.out.println("RECIVED: " + received);
        } catch (Exception e){
            e.printStackTrace();
        }
//        this.textHistory += msg + "\n";
//        chatArea.setText(this.textHistory);
    }
    
    
    public void startListening(){
        //create a Multicast socket on a separate thread that sends the payload
        //of incoming packets to chatArea
        if(this.connected == false){
            this.start();
        } else {
            this.textHistory += ">>> You are already connected!\n";
            this.chatArea.setText(this.textHistory);
        }
        
    }
    
    
    public void stopListening(){
        
    }
    
    
    public void sendMessage(String msg) throws SocketException, IOException{
        if(this.usernameSet == false){ //if it's first time user enters something in box, set the username
            this.inputLabel.setText(msg + ": ");
            this.usernameSet = true;
            this.textHistory += ">>> Successfully set username to: " + msg + "\n"; 
            this.chatArea.setText(this.textHistory);
            return;//leave this method so that it doesn't send the username as a packet to listeners
        } else { // if username has been set, append it to the message that will be sent
                      /*the username*/    /*the message*/
            msg = this.inputLabel.getText() + msg;
        }
        
        InetAddress group = InetAddress.getByName("234.235.236.237");
        MulticastSocket sendSocket = new MulticastSocket(12540);
        
        sendSocket.joinGroup(group);
        
        DatagramPacket myPkt = new DatagramPacket(msg.getBytes(), msg.length(), group, 12540); //Create the packet
        
        if(this.connected == true){//Send the packet if user is also listening on socket
            sendSocket.send(myPkt); 
        } else { //Print to the user that they need to 'Connect' before sending a message
            this.textHistory += ">>> Please 'Connect' before trying to send a message...\n";
            this.chatArea.setText(this.textHistory);
            return;
        }
        
        //this.textHistory += msg + "\n";
        //chatArea.setText(this.textHistory);
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        //CALL METHODS FOR HANDLING SOCKETS AND MULTICAST STUFF
        
        if(e.getSource() == conButton){
            System.out.println("START LISTENING ON MULTICAST SOCKET(new thread), "
                    + "PRINTING MESSAGES ONTO THE TEXT AREA");
            this.startListening(); //calls the start method for (this)-> i.e creates a new thread (run() execution starts)
        }
        
        else if(e.getSource() == disconButton){
            System.out.println("STOP LISTENING TO MULTICAST SOCKET");
        }
        
        else if(e.getSource() == inputField) {
            try {
                this.sendMessage(inputField.getText());//SEND THIS TO THE MULTICAST SOCKET
                inputField.setText(""); // EMPTY THE BOX BECAUSE MESSAGE HAS BEEN SENT
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
        }
        
    }
    
    
}
