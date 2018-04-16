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
                this.chatArea.setCaretPosition(chatArea.getDocument().getLength());
                this.chatArea.append(serverMsg);
                if(serverMsg.equalsIgnoreCase("EXIT")){
                    //this means the server got my(or others?) EXIT message
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
