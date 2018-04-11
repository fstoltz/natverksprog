/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TCPServerWithProtocolSINGLETHREADED;

import java.io.*;
import java.net.*;

/**
 *
 * @author fstoltz
 */
public class Server {
    KompisDAO dao = new KompisDAO();
    
    
    public Server() throws IOException{

        
    }
    
    
    
    /*
    The goal for this is to have the server solely handle the SENDING / RECEIVING.
    The server class should not handle the logic for retreiving the appropriate data
    to send back to the client, it should only handle the actual sending.
    
    The actual response construction and database data retreival should be handled
    by a protocol class, which returns the objects that the server should send.
    
    In order to accomplish this, we need to implement states...??
    */
    public void startServer() throws IOException{
        PrintWriter out; //send strings to client(not applicable anymore?)
        BufferedReader in; //strings from client
        
        
        ServerSocket listeningSocket = new ServerSocket(15000); //this starts the "server"
                                                                //i.e opens a port for clients to connect to
        
        Socket clientSocket = listeningSocket.accept(); //waits for client to connect
        
        //Create an objectoutputstream, these objects will come from the protocol class
        ObjectOutputStream objOut = new ObjectOutputStream(clientSocket.getOutputStream());
        
        //initialize the input stream from the client (strings)
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
        
        String clientString = null;
        
        /*Creates a 'protocol' object that will take care of the strings from the client*/
        Protocol protocol = new Protocol();
        
        
        objOut.writeObject(protocol.parseInput(null)); //Sends the prompt to the user, without this, client & server are locked waiting for eachother
        
        while((clientString = in.readLine()) != null){ //Waits for user-input clientside (checks that it's not a null string)
            objOut.writeObject(protocol.parseInput(clientString));
        }
        
        
    }
    
    
    public static void main(String[] args) throws IOException{
        Server server = new Server();
        server.startServer();
    }
    
}
