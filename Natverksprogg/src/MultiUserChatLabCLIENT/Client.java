/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MultiUserChatLabCLIENT;


import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;


public class Client {
    PrintWriter out;
    Socket serverSocket;
    ServerListener serverListener;
    Thread serverListenerThread;
    
    
    public Client(JTextArea chatArea) throws IOException{
        this.serverSocket = new Socket(InetAddress.getByName("huerty.com"), 15000);
        this.out = new PrintWriter(serverSocket.getOutputStream(), true);
        this.serverListener = new ServerListener(this.serverSocket, chatArea);
        //the serverListener will receive the serverSocket and start an inputstream on it
        this.serverListenerThread = new Thread(this.serverListener);
        
    }
    
    
    public void startListeningOnServer(){
        this.serverListenerThread.start();
    }
    
    
    public void sendMessage(String msg){
        out.println(msg);
    }
}
