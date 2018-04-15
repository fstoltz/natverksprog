/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MultiUserChatLabCLIENT;

import java.util.*;
import java.io.*;
import java.net.*;
import javax.swing.*;


public class ServerListener implements Runnable{
    JTextArea chatArea;
    Socket serverSocket;
    
    public ServerListener(Socket serverSocket, JTextArea chatArea){
        this.serverSocket = serverSocket;
        this.chatArea = chatArea;
    }


    @Override
    public void run() {
        try {
            ObjectInputStream objIn = new ObjectInputStream(this.serverSocket.getInputStream());
        
            Object unknown;
            while((unknown = objIn.readObject()) != null){
                String serverMsg = (String) unknown;
                //System.out.println("Client: " + serverMsg);
                this.chatArea.setCaretPosition(chatArea.getDocument().getLength());
                this.chatArea.append(serverMsg);
                if(serverMsg.equalsIgnoreCase("EXIT")){
                    //break; //if this one gets the exit string it means the server received it and will remove this client from the client pool
                    //
                    //wait(); //jump out of the while loop, terminating the thread
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
