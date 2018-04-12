/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MultiUserChatLabSERVER;

import java.net.*;
import java.util.*;
import java.io.*;

/**
 *
 * @author fstoltz
 */
public class Protocol {
    Master m;
    ObjectOutputStream outStreamToClient;
    //boolean usernameSet;
    
    private static final int USERNAME_NOT_SET = 0;
    private static final int USERNAME_SET = 1;
    private static final int WAITING_FOR_CLIENT_MESSAGE = 2;
    
    public int STATE;
    
    public Protocol() {
    }
    
    public Protocol(ObjectOutputStream outStreamToClient, Master m){
        this.m = m;
        this.outStreamToClient = outStreamToClient;
        this.STATE = USERNAME_NOT_SET;
//this.usernameSet = false;
    }
    
    
    public void parseInput(String clientString) throws IOException{
        if(STATE == USERNAME_NOT_SET){
            STATE = USERNAME_SET;
            this.m.addOutputStream(outStreamToClient);
        }
        else if (STATE == USERNAME_SET) {
            if(clientString.equalsIgnoreCase("EXIT")){
                //remove the outputstream from the list
                outStreamToClient.writeObject("You've been disconnected!...");
                m.removeStream(outStreamToClient);
            } else {
                this.m.sendToEveryone(clientString);
            }
            
        }


//        if(this.usernameSet == false){
//            this.m.addOutputStream(outStreamToClient);
//            this.usernameSet = true;
//        } else {
//            this.m.sendToEveryone(clientString);
//        }
        
        
//        if(this.usernameSet != true){
//            outStreamToClient.writeObject("Please enter desired nickname...FORMAT \"/nick YourNick\"");
//            this.usernameSet = true;
//            this.m.addOutputStream(this.outStreamToClient);
//        }
//        else if (this.usernameSet == true) {
//            this.m.sendToEveryone(clientString);
//        }
//        
    }
    
}

