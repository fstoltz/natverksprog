/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MultiUserChatLab;

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
    //Protocol protocol;
    
    public ClientCaretaker(Socket clientSocket, Master m) throws IOException{
        this.m = m;
        this.clientSocket = clientSocket;
        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.objOut = new ObjectOutputStream(clientSocket.getOutputStream());
        
        m.addOutputStream(this.objOut);
    //lägg till outputstream i master
    }

    
    
    @Override
    public void run() {
        try {
            String clientString = null;
            
            while((clientString = in.readLine()) != null){
                this.m.sendToEveryone(clientString);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
}
