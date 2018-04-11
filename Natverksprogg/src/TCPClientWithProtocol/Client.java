/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TCPClientWithProtocol;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 *
 * @author fstoltz
 */
public class Client {
    
    public Client(){
        //empty constructor
    }
    
    public void startClient() throws UnknownHostException, IOException, ClassNotFoundException{
        //Creates a socket to the server, at this point, the server has received a clientSocket at line:53
        Socket serverSocket = new Socket(InetAddress.getLocalHost(), 15000);
        
        Scanner sc = new Scanner(System.in);
        
        //String outputstream, now I'm able to send strings to the server
        PrintWriter out = new PrintWriter(serverSocket.getOutputStream(), true);
        
        /*Object input stream reader, reads objects sent from the server*/
        ObjectInputStream objIn = new ObjectInputStream(serverSocket.getInputStream());
        
        Object unknown;//this object will hold the servers response, datatype is Object, which 
        //is the superclass of all objects in Java, making it suitable for taking the server response
        //and then casting it to whichever is appropriate.
        while((unknown = objIn.readObject()) != null){
            
            //unkown to string, or cast unknown to String and then print the string
            System.out.println(unknown.toString()); //prints the data that was received from the server
            
            out.println(sc.nextLine()); //prompts user for input that will be sent to the server
        }
        
    }
    
    
    public static void main(String[] args) throws IOException, UnknownHostException, ClassNotFoundException{
        Client client = new Client();
        client.startClient();
    }
    
}
