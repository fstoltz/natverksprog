/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MultiUserChatLabSERVER;

import java.io.IOException;
import java.net.*;


public class Server {
    Master m;
    static int port = 15000;

    
    public Server(){
        m = new Master();
    }
    
    
    public void startServer() throws IOException{
        ServerSocket listeningSocket = new ServerSocket(this.port);
        while(true){
            Socket clientSocket = listeningSocket.accept();
            System.out.println("port "+clientSocket.getPort());
            ClientCaretaker cC = new ClientCaretaker(clientSocket, this.m);
            Thread newClientThread = new Thread(cC);
            newClientThread.start();
        }
    }
    
    
    public static void main(String[] args) throws IOException{
        Server server = new Server();
        server.startServer();
    }
}
