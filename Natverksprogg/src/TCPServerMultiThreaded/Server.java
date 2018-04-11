/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TCPServerMultiThreaded;

import java.net.*;
import java.io.*;

/**
 *
 * @author fstoltz
 */
public class Server {
    static int port = 15000;
    
    public Server() throws IOException{
        ServerSocket listeningSocket = new ServerSocket(port);   
        while(true){
            Thread newClientThread = new Thread(new ClientCaretaker(listeningSocket.accept()));
            newClientThread.start();
            System.out.println("Number of threads: " + Thread.activeCount());
        }
    }
    
    
    public static void main(String[] args) throws IOException{
        System.out.println("Listening on port:" + Server.port);
        Server server = new Server();
    }
    
}
