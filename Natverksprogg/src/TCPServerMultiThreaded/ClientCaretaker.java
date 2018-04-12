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
    /*The ClientCaretaker needs another thread!!!! THIS THREAD LISTENS
    FOR INSERTIONS INTO THE "MESSAGEQUE" AND IN ITS CONSTRUCTOR I SEND THE 
    clientSocket!!! So that it also can send messages to the client!!!
    
    Server has one baby -> ClientCaretaker
    ClientCaretaker has one baby -> MessageListener(clientSocket);
    
    This way, we have two objects that are handling one clientSocket
    One object is listening for user input, and the other is listening for
    messageQue insertions and then outputs it to the clientSocket and then goes
    back to listening on the messageQue
    
    -- We need a shared MessageQue among all the Client threads
    
    This way, the MessageListener can sit and listen for insertions into
    the messageQue, and when an insertion has been made, send that insertion
    to the Socket!?? Is this feasable??? Can I instansiate two outputstreams for 
    the same socket? What if I try to write to them at the same time? Unlikely but anyways.
    
    */

    
    public ClientCaretaker(Socket clientSocket, KompisDAO dao) throws IOException{
        
        //Start another thread here, that sits and waits for other client messages...?
        
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
            
            
            //When it's locked below here, it needs to be able to be notified by the *****
            //that a message has arrived, and send this to the client.
            //in.readLine() blocks until client has sent something. During this idle waiting time
            //the thread needs to be able to receive messages from other clients and send it to the client
            while((clientString = in.readLine()) != null){ 
                //if clientString is == "leave" m.removeSocket(this.clientSocket);
                /*as soon as readLine got a message, call the method
                of the MASTER to send this message to all clients. The master can do this
                because he has a copy of all sockets. (this might be a really bad solution, i'm not sure)
                */
                //master.sendToEveryone(clientString);
                objOut.writeObject(this.protocol.parseInput(clientString));
            }
        } catch(Exception e){ e.printStackTrace(); }
    }
}
