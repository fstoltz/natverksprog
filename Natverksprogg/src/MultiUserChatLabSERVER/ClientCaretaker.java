/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MultiUserChatLabSERVER;

import java.net.*;
import java.util.*;
import java.io.*;


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
        this.protocol = new Protocol(this.objOut, m);
    }

    
    @Override
    public void run() {
        try {
            String clientString = null;
            while((clientString = in.readLine()) != null){
                this.protocol.parseInput(clientString); //send the string to the protocol
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
