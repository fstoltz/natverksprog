/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TCPServerMultiThreaded;

import TCPServerWithProtocolSINGLETHREADED.Kompis;
import java.net.*;
import java.io.*;
import TCPServerWithProtocolSINGLETHREADED.KompisDAO;

/**
 *
 * @author fstoltz
 */
public class Server {
    static int port = 15000;
    //Server owns a static KompisDAO object that all threads operate on, static because it's a shared object??
    static KompisDAO dao = new KompisDAO();
    
    
    public Server() throws IOException{
        Kompis k1 = new Kompis("Olof", "0707754607");Kompis k2 = new Kompis("Peter", "0213107");Kompis k3 = new Kompis("Kalle", "0107131");Kompis k4 = new Kompis("Erik", "001931");Kompis k5 = new Kompis("Pachi", "764183");dao.addFriend("Olof", "0707754607");dao.addFriend("Kalle", "74871471");dao.addFriend("Pachi", "3651743");dao.addFriend("Mera", "837189351");
        
        ServerSocket listeningSocket = new ServerSocket(port);
        while(true){
            //add the master variable as a parameter to the ClientCaretakers constructor so we can send messages to it
            Thread newClientThread = new Thread(new ClientCaretaker(listeningSocket.accept(), this.dao));
            //Eachtime the listeningSocket.accept() returns a socket, create a new ClientCaretaker,
            //aswell as add this socket into the list of Sockets that the Master has as an instance-variable
            //the master has List<Socket> that contains all sockets currently connected.
            //If a client chooses to disconnect, go into this list and iterate through it,
            //and remove the socket that has disconnected.
            
            //master.addSocket(clientSocket);
            
            newClientThread.start();
            System.out.println("Number of threads: " + Thread.activeCount());
        }
    }
    
    
    public static void main(String[] args) throws IOException{
        System.out.println("Listening on port:" + Server.port);
        try {
            Server server = new Server();
        } catch (Exception e) {
            //swallow exception
        }
        
    }
    
}
