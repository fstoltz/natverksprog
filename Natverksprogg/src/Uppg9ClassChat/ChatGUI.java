package Uppg9ClassChat;

import java.awt.*;
import static java.awt.BorderLayout.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.SocketException;
import java.net.*;
import javax.swing.*;


/*
lol I realized now that I could've used the append method for adding text to the textarea,
i ddidnt see the method until now.

I just read the tips from Sigrun and I now understand how to do what I was trying to do.
Have the separate class that i made multicasthandler handle the receving, and as a paramater to it's consturctor
we could've passed the textarea object, and access it from there. This might've led to a scenario
where the need for a synchronized method would arrise? or? . not sure.
because you wouldn't want the sending socket to write to the textarea at the same time
as the receiving socket does, then they would clash with eachother.

----------
update on sunday-
I looked at Sigruns additional tips for implementation which made it clear up for me
on how to implement the MulticastHandler class and pass around the TextArea object.
I wasn't sure of how to do that initially, but now it's clear. And since we can use the
append method, we dont need to keep track of that "textHistory" variable anymore which would've
made it a bit more painful to make the socket handling in a separate class.
*/

public class ChatGUI extends Thread implements ActionListener{
    /*This class will draw a chat GUI and hold necessary components for chat functionality*/
    JFrame frame = new JFrame(); //FRAME
    /*PANELS*/
    JPanel mainPanel = new JPanel();
    JPanel buttonPanel = new JPanel();
    JPanel inputPanel = new JPanel();
    /*BUTTONS*/
    JButton conButton, disconButton;
    
    /*LABELS*/
    JLabel inputLabel = new JLabel("Set username: ");
    
    /*TEXT AREA*/
    JTextArea chatArea = new JTextArea();
    JTextField inputField = new JTextField("", 30);
    
    /*username is set or not holder*/
    boolean usernameSet = false;
    
    /*Connected or not tracker*/
    boolean connected = false;

    /*LISTENER SOCKET SETUP PART*/
    MulticastHandler handle = new MulticastHandler(chatArea);

    ChatGUI() throws UnknownHostException, IOException {
        /*LAYOUT SETUP FOR PANELS*/
        mainPanel.setLayout(new BorderLayout());
        buttonPanel.setLayout(new FlowLayout());
        inputPanel.setLayout(new FlowLayout());
        
        /*Instansiate Buttons*/
        conButton = new JButton("Connect");
        disconButton = new JButton("Disconnect");
        
        /*Add Action Listeners*/
        conButton.addActionListener(this);
        disconButton.addActionListener(this);
        inputField.addActionListener(this);

        /*BUTTONPANNEL ADDS*/
        buttonPanel.add(conButton);
        buttonPanel.add(disconButton);
        
        /*CHATAREA SETUP*/
        chatArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        this.chatArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(this.chatArea);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.chatArea.setAutoscrolls(true);
        this.chatArea.setLineWrap(true);
        this.chatArea.setBackground(Color.BLACK);
        
        /*FRAME ADDS*/
        frame.add(mainPanel);
        frame.add(buttonPanel, NORTH);

        /*MAINPANEL ADDS*/
        mainPanel.add(scroll, CENTER);
        mainPanel.add(inputPanel, SOUTH);
        /*INPUTPANEl ADDS*/
        inputPanel.add(inputLabel);
        inputPanel.add(inputField);
        
        /*CONFIG VALUES FOR FRAME(canvas)*/
        frame.setSize(750, 400); //Set the size of the frame(canvas)
        frame.setVisible(true);
        frame.setDefaultCloseOperation(3); //3 means exit
    }
    
    public void startListening(){
        //create a Multicast socket on a separate thread that sends the payload
        //of incoming packets to chatArea
        if(this.connected == false){
            Thread recThread = new Thread(this.handle); // this handle is a MulticastHandler object that implements Runnable, does not 'create' a new Thread. It only instansiates a object of Thread type.
            this.connected = true;
            //initiates another point of execution, the 'Run' method at MulticastHandler
            //'main' thread continues. the recThread immediately locks at the socket.receive()
            recThread.start();//this is the line where the actual thread is started.
        }        
    }
    
    /**
     * NOT IMPLEMENTED YET
     */
    public void stopListening(){
    }
    
    
    public void sendMessage(String msg) throws SocketException, IOException{
        if(this.usernameSet == false){ //if it's first time user enters something in box, set the username
            this.inputLabel.setText(msg + ": ");
            this.usernameSet = true;
            this.chatArea.append(">>> Successfully set username to: " + msg + "\n");
            return;//leave this method so that it doesn't send the username as a packet to listeners
        } else if(this.connected == true) { // if username has been set, append it to the message that will be sent
            this.chatArea.setForeground(Color.WHITE);
                                    /*the username*/    /*the message*/
            this.handle.sendMessage(this.inputLabel.getText() + msg); //The main thread goes and runs the sendMessage method that MulticastHandler supports
        } else {
            this.chatArea.append(">>> Please connect before trying to send a message.\n");
        }
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
