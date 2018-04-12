/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MultiUserChatLab;

import java.net.*;
import java.util.*;
import java.io.*;
/**
 *
 * @author fstoltz
 */
public class Master {
    
    List<Socket> userList = new ArrayList<>();
    
    
    public void addSocket(Socket clientSocket){
        userList.add(clientSocket);
    }
    
    
    public void sendToEveryone(String clientString) throws IOException{
        /*Iterate through every Socket in userList,
        Open an outputstream for the socket and write clientString
        close the outputstream after it's been sent*/
        int count = 0;
        System.out.println("Server(master) received1: " + clientString);
        for (Socket clientSocket : userList) {
            count++;
            ObjectOutputStream objOut = new ObjectOutputStream(clientSocket.getOutputStream());
            objOut.writeObject(clientString); //writes same string to all users
            objOut.flush();
        }
        System.out.println("Server(master) received2: " + clientString);
        System.out.println("I sent the message to " + count + " sockets.");
    }
    
    public void removeSocket(Socket socketToRemove){ //i.e delete a user
        for (Socket clientSocket : userList) {
            if(clientSocket.equals(socketToRemove)){
                userList.remove(clientSocket);
                return;
            }
        }
    }
    
}
