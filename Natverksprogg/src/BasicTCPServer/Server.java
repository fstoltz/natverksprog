/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BasicTCPServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fstoltz
 */
public class Server {
    KompisDAO dao = new KompisDAO();
    
    //public List<Kompis> list = new ArrayList<>();
    
    PrintWriter out;
    BufferedReader in;
    
    
    ServerSocket listeningSocket;
    
    public Server() throws IOException{
        listeningSocket = new ServerSocket(15000);
        
        Kompis k1 = new Kompis("Olof", "0707754607");
        Kompis k2 = new Kompis("Peter", "0213107");
        Kompis k3 = new Kompis("Kalle", "0107131");
        Kompis k4 = new Kompis("Erik", "001931");
        Kompis k5 = new Kompis("Pachi", "764183");
        
        dao.addFriend("Olof", "0707754607");
        dao.addFriend("Kalle", "74871471");
        dao.addFriend("Pachi", "3651743");
        dao.addFriend("Mera", "837189351");
        
    }
    
    
    public void startListening() throws IOException {
        /*When a client has connected, the servers resources are completely occupied by the single client and is not able
        to accept any further incoming connections, and is therefore not suited for multi-client scenarios, which is very common*/
        /*What we would like instead, is that as soon as a new connection is made from a client, a new thread is created and takes care
        of handling that request. Which makes the main thread able to go back to listening for incoming connections again.
        Multi-threaded application!!!*/
        Socket clientSocket = listeningSocket.accept(); //Blocks, waiting for a SYN/SYN-ACK/ACK to take place
        
        //Sends data as serialized objects
        ObjectOutputStream objOut;
        objOut = new ObjectOutputStream(clientSocket.getOutputStream());
        
        //Send intro object
        objOut.writeObject(new Intro("Welcome, I have accepted your connection."));
        
        
        //Creates an outputstream where I can print strings to the socket(client).
        this.out = new PrintWriter(clientSocket.getOutputStream(), true);
        
        //this.out.println("Enter person to look up details for: ");
        //Creates an inputstream from the socket. (takes strings as input, not objects)
        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
        String userString;
        String phone = null;
        while((userString = this.in.readLine()) != null){
            System.out.println("MESSAGE FROM USER: " + userString);
            //this.out.println(this.dao.getFriendInfo(userString)); //Sends the phone number as a string
            Response newResponse = new Response("foundfriend.useless string");
            Kompis k = this.dao.getFriendAsObj(userString);
            if(k != null){
                //this means I found the friend object
                newResponse.k = k;
                newResponse.foundFriend = true;
                objOut.writeObject(newResponse);
            } else { //could not find the friend
                newResponse.msg = ("Could not find friend.");
                objOut.writeObject(newResponse);
            }
            //objOut.writeObject(this.dao.getFriendAsObj(userString));
        }
    }
    
    
    public static void main(String[] args) throws IOException{
        Server server = new Server();
        while(true){
            server.startListening();
        }
        
    }
}
