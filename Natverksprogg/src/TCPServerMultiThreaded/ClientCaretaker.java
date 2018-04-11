/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TCPServerMultiThreaded;

import java.io.*;
import java.net.*;
import TCPServerWithProtocolSINGLETHREADED.KompisDAO;

/**
 *
 * @author fstoltz
 */
public class ClientCaretaker implements Runnable{
    Socket clientSocket;
    ObjectOutputStream objOut; //send objects to the client
    BufferedReader in; //string from client
    Protocol protocol;

    
    public ClientCaretaker(Socket clientSocket, KompisDAO dao) throws IOException{
        this.clientSocket = clientSocket;
        this.objOut = new ObjectOutputStream(clientSocket.getOutputStream()); //OUT-STREAM
        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream())); //IN-STREAM
        this.protocol = new Protocol(dao); // a separate protocol for each thread ofc, but the same KompisDAO object
    }
    
    @Override
    public void run() {
        try {
            String clientString = null;
            objOut.writeObject(this.protocol.parseInput(null)); //Initial prompt to client
            
            while((clientString = in.readLine()) != null){
                objOut.writeObject(this.protocol.parseInput(clientString));
            }
        } catch(Exception e){ e.printStackTrace(); }
    }
}
