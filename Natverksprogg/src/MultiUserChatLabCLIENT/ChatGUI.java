package MultiUserChatLabCLIENT;

import Uppg9ClassChat.*;
import java.awt.*;
import static java.awt.BorderLayout.*;
import static java.awt.Color.WHITE;
import java.awt.event.*;
import java.io.IOException;
import static java.lang.Thread.State.TERMINATED;
import static java.lang.Thread.State.WAITING;
import java.net.SocketException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;




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
    //MulticastHandler handle = new MulticastHandler(chatArea);
    /******************************/
    /******************************/
    /******************************/
    Client client = new Client(this.chatArea);
    
    boolean USERNAME_IS_SET;
    
    String username;
    /******************************/
    /******************************/
    /******************************/

    ChatGUI() throws UnknownHostException, IOException {
        /*LAYOUT SETUP FOR PANELS*/
        this.USERNAME_IS_SET = false;
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
        this.chatArea.setForeground(WHITE);
        
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
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //CALL METHODS FOR HANDLING SOCKETS AND MULTICAST STUFF
        if(e.getSource() == conButton){
            //System.out.println("START LISTENING ON MULTICAST SOCKET(new thread), "
            //        + "PRINTING MESSAGES ONTO THE TEXT AREA");
            //this.startListening(); //calls the start method for (this)-> i.e creates a new thread (run() execution starts)
            //I'M NOT ENTIRELY SURE WHAT THE CODE BELOW DOES AND WHY IT WORKS
            if(connected == false){
                this.client.startListeningOnServer();
                this.connected = true;
            } else {
                try {
                    this.client = new Client(this.chatArea);//especially this
                    this.client.startListeningOnServer();//especially this
                    this.USERNAME_IS_SET = false;
                } catch (IOException ex) {
                    Logger.getLogger(ChatGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        else if(e.getSource() == disconButton){
            System.out.println("STOP LISTENING TO MULTICAST SOCKET");
            this.client.sendMessage("EXIT"); //If user presses Disconnect, sends an "EXIT" string to the server
            //this.stopListening();
        }
        else if(e.getSource() == inputField) { //Goes here if user pressed 'Enter' in the inputField
            if(this.USERNAME_IS_SET == false){
                this.username = inputField.getText();
                this.inputLabel.setText(this.username);
                inputField.setText("");
                this.USERNAME_IS_SET = true;
            } else if (!inputField.getText().equals("")){
                this.client.sendMessage(this.username + ": " + inputField.getText());
                inputField.setText("");
            }
        }
    }

    
    public static void main(String[] args) throws IOException{
        ChatGUI gui = new ChatGUI();
    }
}
