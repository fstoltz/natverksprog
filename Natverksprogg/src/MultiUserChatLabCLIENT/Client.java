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
        //Might need another thread client-side,
        //one thread that waits for user input,
        //one thread that waits for server input
        
        //lets first try only with letting user input
        
        this.serverSocket = new Socket(InetAddress.getLocalHost(), 15000);
        this.out = new PrintWriter(serverSocket.getOutputStream(), true);
        
        this.serverListener = new ServerListener(this.serverSocket, chatArea);
        this.serverListenerThread = new Thread(this.serverListener);
        //the serverListener will receive the serverSocket and start an inputstream on it
    }
    
    
    public void startListeningOnServer(){
        this.serverListenerThread.start();
    }
    
    
    public void startClient() throws UnknownHostException, IOException{
        //Socket serverSocket = new Socket(InetAddress.getLocalHost(), 15000);
        //Scanner sc = new Scanner(System.in);
        
        //PrintWriter out = new PrintWriter(serverSocket.getOutputStream(), true);
        
        //while(true){
          //  out.println(sc.nextLine());
        //}
    }
    
    public void sendMessage(String msg){
        out.println(msg);
    }
    
    
//    public static void main(String[] args) throws IOException{
//        Client client = new Client();
//        client.startClient();
//    }
    
}
