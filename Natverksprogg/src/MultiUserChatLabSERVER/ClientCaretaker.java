/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MultiUserChatLabSERVER;

import java.net.*;
import java.util.*;
import java.io.*;

/**
 *
 * @author fstoltz
 */
public class ClientCaretaker implements Runnable{
    Master m;
    Socket clientSocket;
    ObjectOutputStream objOut;
    BufferedReader in;
    Protocol protocol;
    
    public ClientCaretaker(Socket clientSocket, Master m) throws IOException{
        this.m = m;
        this.clientSocket = clientSocket;
        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
        this.objOut = new ObjectOutputStream(clientSocket.getOutputStream());
        
        objOut.writeObject("Please enter your username: ");
        objOut.flush();
        //m.addOutputStream(this.objOut); //add this stream to the 'Master'
        
        this.protocol = new Protocol(this.objOut, m);
        
    //lägg till outputstream i master
    }

    /*Maybe the protocol, when it has established a nickname for a user,
    then the protocol adds an outputstream to the Master object*/
    
    @Override
    public void run() {
        try {
            String clientString = null;
            
            while((clientString = in.readLine()) != null){
                //this.m.sendToEveryone(clientString);
                //send the string to the protocol
                this.protocol.parseInput(clientString);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
}
