/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MultiUserChatLabSERVER;

import java.net.*;
import java.util.*;
import java.io.*;


public class Master {
    List<ObjectOutputStream> listOfOutStreams = new ArrayList<>();
    
    
    public void addOutputStream(ObjectOutputStream newStream){
        listOfOutStreams.add(newStream);
    }
    
    
    public void sendToEveryone(String clientString) throws IOException{
        int count = 0;
        for (ObjectOutputStream out : listOfOutStreams) {
            out.writeObject(clientString + "\n"); //the server appends a newline to all messages!
            out.flush();
            count++;
        }
        System.out.println("Sent a message to " + count + "streams.");
    }

    
    public void removeStream(ObjectOutputStream streamToRemove){
        for (ObjectOutputStream tempStream : this.listOfOutStreams) {
            if (tempStream.equals(streamToRemove)) {
                this.listOfOutStreams.remove(tempStream);
                return;
            }
        }
    }
}
