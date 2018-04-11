/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TCPServerMultiThreaded;


import TCPServerWithProtocolSINGLETHREADED.KompisDAO;

/**
 *
 * @author fstoltz
 */
public class Protocol {
    static KompisDAO dao;
    
    public Protocol(KompisDAO dao){
            this.dao = dao;
    }
    
    public Object parseInput(String clientString){
        if(clientString == null){ //server is sending initial message to client
            return "Hey, give me a name!";
        } else {
            return Protocol.dao.getFriendInfo(clientString);
        }
    }
}
